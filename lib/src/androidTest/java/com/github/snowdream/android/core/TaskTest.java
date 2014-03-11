/*
 * Copyright (C) 2012 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.snowdream.android.core;

import java.lang.ref.WeakReference;

import com.github.snowdream.android.core.Task;
import junit.framework.TestCase;
import android.app.Activity;
import android.util.Log;
import de.greenrobot.event.EventBus;

/**
 * @author Markus Junginger, greenrobot
 */
public class TaskTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testTaskOnTop() {
      Task.Builder builder = new Task.Builder<Void,Void,Void>();
        Task taskd = builder.build();
        Task taskB = builder.addChild(taskd).build();
        Task taskc = builder.build();
        Task taskA = builder.addChild(taskB).addChild(taskc).build();

        assertEquals(true,taskA.isTop());
        assertEquals(true,taskc.isBottom());
    }
}