/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.task.step;

import io.nop.api.core.util.ICancelToken;
import io.nop.commons.util.StringHelper;
import io.nop.task.ITask;
import io.nop.task.ITaskManager;
import io.nop.task.ITaskRuntime;
import io.nop.task.ITaskStepState;
import io.nop.task.TaskStepResult;
import jakarta.annotation.Nonnull;

import java.util.Set;

public class CallTaskStep extends AbstractTaskStep {
    private String taskName;

    private long taskVersion;

    private ITaskManager taskManager;

    public long getTaskVersion() {
        return taskVersion;
    }

    public void setTaskVersion(long taskVersion) {
        this.taskVersion = taskVersion;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public ITaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(ITaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Nonnull
    @Override
    public TaskStepResult execute(ITaskStepState stepState, Set<String> outputNames, ICancelToken cancelToken, ITaskRuntime taskRt) {
        String taskId = (String) stepState.getStateBean();
        ITask task;
        ITaskRuntime subRt;
        if (StringHelper.isEmpty(taskId)) {
            subRt = taskRt.newChildContext(taskName, taskVersion);
            task = taskManager.getTask(subRt);

            stepState.setStateBean(subRt.getTaskInstanceId());
            stepState.save();
        } else {
            subRt = taskManager.getTaskContext(taskId);
            task = taskManager.getTask(subRt);
        }

        Object result = task.execute(subRt);

        return TaskStepResult.of(null, result);
    }
}