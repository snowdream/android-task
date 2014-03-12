package com.github.snowdream.android.core;


/**
 * Created by snowdream on 3/12/14.
 */
public class TaskEvent<Progress, Output> {
    private TYPE type;
    private Task task;
    private Progress progress;
    private Output output;
    private Throwable throwable;

    protected enum TYPE {
        START, PROGRESS, SUCCESS, CANCEL, ERROR, FINISH
    }

    private TaskEvent() {
    }

    public TaskEvent(TYPE type, Task task, Progress progress, Output output, Throwable throwable) {
        this.type = type;
        this.task = task;
        this.progress = progress;
        this.output = output;
        this.throwable = throwable;
    }

    public TYPE getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public Progress getProgress() {
        return progress;
    }

    public Output getOutput() {
        return output;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
