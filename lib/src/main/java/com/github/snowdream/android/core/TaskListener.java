package com.github.snowdream.android.core;

/**
 * Created by snowdream on 3/11/14.
 */
public class TaskListener<Progress, Output> {
    public void onStart(Task task) {
    }

    public void onProgressUpdate(Task task, Progress progress) {
    }

    public void onSuccess(Task task, Output output) {
    }

    public void onCancelled(Task task) {
    }

    public void onError(Task task, Throwable thr) {
    }

    public void onFinish(Task task) {
    }
}
