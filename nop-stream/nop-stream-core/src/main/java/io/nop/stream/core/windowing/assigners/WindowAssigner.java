/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.nop.stream.core.windowing.assigners;

import io.nop.core.context.IServiceContext;
import io.nop.stream.core.windowing.triggers.Trigger;
import io.nop.stream.core.windowing.windows.Window;

import java.io.Serializable;
import java.util.Collection;

/**
 * A {@code WindowAssigner} assigns zero or more {@link Window Windows} to an element.
 *
 * <p>In a window operation, elements are grouped by their key (if available) and by the windows to
 * which it was assigned. The set of elements with the same key and window is called a pane. When a
 * {@link Trigger} decides that a certain pane should fire the {@link
 * org.apache.flink.streaming.api.functions.windowing.WindowFunction} is applied to produce output
 * elements for that pane.
 *
 * @param <T> The type of elements that this WindowAssigner can assign windows to.
 * @param <W> The type of {@code Window} that this assigner assigns.
 */
public abstract class WindowAssigner<T, W extends Window> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Returns a {@code Collection} of windows that should be assigned to the element.
     *
     * @param element   The element to which windows should be assigned.
     * @param timestamp The timestamp of the element.
     * @param context   The {@link WindowAssignerContext} in which the assigner operates.
     */
    public abstract Collection<W> assignWindows(
            T element, long timestamp, WindowAssignerContext context);

    /**
     * Returns the default trigger associated with this {@code WindowAssigner}.
     */
    public abstract Trigger<T, W> getDefaultTrigger(IServiceContext env);

    /**
     * Returns {@code true} if elements are assigned to windows based on event time, {@code false}
     * otherwise.
     */
    public abstract boolean isEventTime();

    /**
     * A context provided to the {@link WindowAssigner} that allows it to query the current
     * processing time.
     *
     * <p>This is provided to the assigner by its containing {@link
     * org.apache.flink.streaming.runtime.operators.windowing.WindowOperator}, which, in turn, gets
     * it from the containing {@link org.apache.flink.streaming.runtime.tasks.StreamTask}.
     */
    public interface WindowAssignerContext {

        /**
         * Returns the current processing time.
         */
        long getCurrentProcessingTime();
    }
}
