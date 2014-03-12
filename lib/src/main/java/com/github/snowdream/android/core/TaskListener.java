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

    public void onEvent(TaskEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getType()) {
            case START:
                onStart(event.getTask());
                break;
            case PROGRESS:
                onProgressUpdate(event.getTask(),(Progress)event.getProgress());
                break;
            case SUCCESS:
                onSuccess(event.getTask(),(Output)event.getOutput());
                break;
            case CANCEL:
                onCancelled(event.getTask());
                break;
            case ERROR:
                onError(event.getTask(),event.getThrowable());
                break;
            case FINISH:
                onFinish(event.getTask());
                break;
            default:
                break;
        }
    }

    public void onStartMainThread(Task task) {
    }

    public void onProgressUpdateMainThread(Task task, Progress progress) {
    }

    public void onSuccessMainThread(Task task, Output output) {
    }

    public void onCancelledMainThread(Task task) {
    }

    public void onErrorMainThread(Task task, Throwable thr) {
    }

    public void onFinishMainThread(Task task) {
    }

    public void onEventMainThread(TaskEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getType()) {
            case START:
                onStartMainThread(event.getTask());
                break;
            case PROGRESS:
                onProgressUpdateMainThread(event.getTask(),(Progress)event.getProgress());
                break;
            case SUCCESS:
                onSuccessMainThread(event.getTask(),(Output)event.getOutput());
                break;
            case CANCEL:
                onCancelledMainThread(event.getTask());
                break;
            case ERROR:
                onErrorMainThread(event.getTask(),event.getThrowable());
                break;
            case FINISH:
                onFinishMainThread(event.getTask());
                break;
            default:
                break;
        }
    }

    public void onStartBackgroundThread(Task task) {
    }

    public void onProgressUpdateBackgroundThread(Task task, Progress progress) {
    }

    public void onSuccessBackgroundThread(Task task, Output output) {
    }

    public void onCancelledBackgroundThread(Task task) {
    }

    public void onErrorBackgroundThread(Task task, Throwable thr) {
    }

    public void onFinishBackgroundThread(Task task) {
    }

    public void onEventBackgroundThread(TaskEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getType()) {
            case START:
                onStartBackgroundThread(event.getTask());
                break;
            case PROGRESS:
                onProgressUpdateBackgroundThread(event.getTask(),(Progress)event.getProgress());
                break;
            case SUCCESS:
                onSuccessBackgroundThread(event.getTask(),(Output)event.getOutput());
                break;
            case CANCEL:
                onCancelledBackgroundThread(event.getTask());
                break;
            case ERROR:
                onErrorBackgroundThread(event.getTask(),event.getThrowable());
                break;
            case FINISH:
                onFinishBackgroundThread(event.getTask());
                break;
            default:
                break;
        }
    }

    public void onStartAsync(Task task) {
    }

    public void onProgressUpdateAsync(Task task, Progress progress) {
    }

    public void onSuccessAsync(Task task, Output output) {
    }

    public void onCancelledAsync(Task task) {
    }

    public void onErrorAsync(Task task, Throwable thr) {
    }

    public void onFinishAsync(Task task) {
    }

    public void onEventAsync(TaskEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getType()) {
            case START:
                onStartAsync(event.getTask());
                break;
            case PROGRESS:
                onProgressUpdateAsync(event.getTask(),(Progress)event.getProgress());
                break;
            case SUCCESS:
                onSuccessAsync(event.getTask(),(Output)event.getOutput());
                break;
            case CANCEL:
                onCancelledAsync(event.getTask());
                break;
            case ERROR:
                onErrorAsync(event.getTask(),event.getThrowable());
                break;
            case FINISH:
                onFinishAsync(event.getTask());
                break;
            default:
                break;
        }
    }
}
