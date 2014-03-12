/*
 * Copyright (C) 2014 Snowdream Mobile <yanghui1986527@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.snowdream.android.core;

import android.annotation.TargetApi;
import android.os.Build;
import com.github.snowdream.android.util.Log;
import de.greenrobot.event.EventBus;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by snowdream on 3/11/14.
 */
public class Task<Input, Progress, Output> {
    private static final String LOG_TAG = "Task";

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "Task #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    /**
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    /**
     * An {@link Executor} that executes tasks one at a time in serial
     * order.  This serialization is global to a particular process.
     */
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();

    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }

    /**
     * Default Executor
     *
     * @param exec
     */
    public static void setDefaultExecutor(Executor exec) {
        sDefaultExecutor = exec;
    }

    public static final EventBus DEFAULT_EVENTBUS = EventBus.getDefault();

    public static final EventBus NEW_EVENTBUS = new EventBus();

    private static volatile EventBus eventBus = NEW_EVENTBUS;

    private final boolean enableLog;
    private final List<Task> tasks;
    private Set<TaskListener> listeners;
    private final boolean isBottom;
    private Task parent = null;

    private Task() {
        enableLog = true;
        tasks = null;
        isBottom = true;
        parent = null;
    }

    private Task(Builder<Input, Progress, Output> builder) {
        this.enableLog = builder.enableLog;
        this.tasks = builder.tasks;
        this.isBottom = builder.isBottom;
        this.listeners = builder.listeners;
        registerListeners();
    }

    /**
     * Whether to write logs for all tasks.
     * if not set,true is the default.
     */
    public static void enableGlobalLog(boolean enableGlobalLog) {
        Log.setEnabled(enableGlobalLog);
    }

    /**
     * Check Whether to write logs for all tasks.
     */
    public static boolean isEnableGlobalLog() {
        return Log.isEnabled();
    }

    /**
     * Check Whether to write logs for this tasks.
     */
    public boolean isEnableLog() {
        return enableLog;
    }

    /**
     * Add TaskListener
     *
     * @param listener
     * @return
     */
    public void addListener(TaskListener listener) {
        if (listeners == null) {
            listeners = new CopyOnWriteArraySet<TaskListener>();
        }

        if (listener != null) {
            listeners.add(listener);
        }

        registerListeners();
    }

    /**
     * Register all task listeners.
     */
    private void registerListeners(){
        if (listeners == null){
            Log.i("There is no Listener to register.");
            return;
        }

        for (TaskListener listener : listeners){
            if (eventBus.isRegistered(listener)){
                continue;
            }

            eventBus.register(listener);
        }
    }

    /**
     * Unregister all task listeners.
     */
    private void unregisterListeners(){
        if (listeners == null){
            Log.i("There is no Listener to unregister.");
            return;
        }

        for (TaskListener listener : listeners){
            if (eventBus.isRegistered(listener)){
                eventBus.register(listener);
            }
        }
    }

    /**
     * set the parent task
     *
     * @param task
     */
    private void setParent(Task task) {
        if (task != null) {
            this.parent = task;
        }
    }

    /**
     * get the parent task
     *
     * @param task
     * @return
     */
    public Task getParent(Task task) {
        return this.parent;
    }

    /**
     * Whether the task is on the Top.
     *
     * @return
     */
    public boolean isTop() {
        return parent == null;
    }

    /**
     * Whether the task is on the Bottom.
     *
     * @return
     */
    public boolean isBottom() {
        return isBottom;
    }

    public static class Builder<Input, Progress, Output> {
        private boolean enableLog = true;
        private List<Task> tasks = null;
        private boolean isBottom = true;
        private Set<TaskListener> listeners = null;

        /**
         * Whether to write logs for this Task
         * if not set,true is the default.
         */
        public Builder enableLog(boolean enableLog) {
            this.enableLog = enableLog;
            return this;
        }

        /**
         * Add child task for this task.
         *
         * @param task
         * @return
         */
        public Builder addChild(Task task) {
            if (tasks == null) {
                tasks = new CopyOnWriteArrayList<Task>();
            }

            if (task != null) {
                tasks.add(task);
            }
            return this;
        }

        /**
         * Add TaskListener
         *
         * @param listener
         * @return
         */
        public Builder addListener(TaskListener listener) {
            if (listeners == null) {
                listeners = new CopyOnWriteArraySet<TaskListener>();
            }

            if (listener != null) {
                listeners.add(listener);
            }
            return this;
        }

        /**
         * Builds configured {@link Task} object
         */
        public Task build() {
            Task task = new Task(this);

            checkChildTasks(task);
            return task;
        }

        private void checkChildTasks(Task parent) {
            if (tasks == null) {
                isBottom = false;
                return;
            }

            for (Task task : tasks) {
                task.setParent(parent);
            }
        }
    }
}
