package com.github.snowdream.android.core;

/**
 * Created by snowdream on 3/11/14.
 */
public class AbstractProcesser<In,Out>{
      protected In in = null;
      protected Out out = null;


    /**
     * Get the Result
     *
     * @return the Result
     */
    public Out getResult(){
        return out;
    }
}
