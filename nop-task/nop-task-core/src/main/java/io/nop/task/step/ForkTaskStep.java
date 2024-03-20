/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.task.step;

import io.nop.task.ITaskStep;
import io.nop.task.ITaskStepRuntime;
import io.nop.task.TaskStepResult;
import jakarta.annotation.Nonnull;

public class ForkTaskStep extends AbstractTaskStep {
    private ITaskStep body;

    @Nonnull
    @Override
    public TaskStepResult execute(ITaskStepRuntime stepRt) {
        return null;
    }
}
