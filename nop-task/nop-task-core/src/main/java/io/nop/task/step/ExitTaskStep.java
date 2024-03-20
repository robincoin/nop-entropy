/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.task.step;

import io.nop.core.lang.eval.IEvalAction;
import io.nop.task.ITaskStepRuntime;
import io.nop.task.TaskConstants;
import io.nop.task.TaskStepResult;
import jakarta.annotation.Nonnull;

import java.util.concurrent.CompletionStage;

public class ExitTaskStep extends AbstractTaskStep {
    private IEvalAction result;

    public IEvalAction getResult() {
        return result;
    }

    public void setResult(IEvalAction result) {
        this.result = result;
    }

    @Nonnull
    @Override
    public TaskStepResult execute(ITaskStepRuntime stepRt) {
        Object ret = result == null ? null : result.invoke(stepRt);
        if (ret instanceof CompletionStage)
            return TaskStepResult.ASYNC(TaskConstants.STEP_NAME_EXIT, (CompletionStage<?>) ret);

        return TaskStepResult.of(TaskConstants.STEP_NAME_EXIT, ret);
    }
}