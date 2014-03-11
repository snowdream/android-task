package com.github.snowdream.android.core;

/**
 * Created by snowdream on 3/11/14.
 */
public class TaskListener<Progress, Output> {
    public void onStart() {
    }

    public void onProgressUpdate(Progress progress) {
    }

    public void onSuccess(Output output) {
    }

    public void onCancelled() {
    }

    public void onError(Throwable thr) {
    }

    public void onFinish() {
    }
}
