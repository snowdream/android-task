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

import com.github.snowdream.android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by snowdream on 3/11/14.
 */
public class Task<Input, Progress, Output> {

    private final boolean enableLog;
    private final List<Task> tasks;
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
