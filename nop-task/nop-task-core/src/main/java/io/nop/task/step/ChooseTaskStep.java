/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.task.step;

import io.nop.api.core.convert.ConvertHelper;
import io.nop.commons.util.StringHelper;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.task.IEnhancedTaskStep;
import io.nop.task.ITaskStepRuntime;
import io.nop.task.TaskConstants;
import io.nop.task.TaskStepResult;
import jakarta.annotation.Nonnull;

import java.util.Map;

public class ChooseTaskStep extends AbstractTaskStep {
    private IEvalAction decider;

    private Map<String, IEnhancedTaskStep> caseSteps;

    private IEnhancedTaskStep defaultStep;

    public IEvalAction getDecider() {
        return decider;
    }

    public void setDecider(IEvalAction decider) {
        this.decider = decider;
    }

    public Map<String, IEnhancedTaskStep> getCaseSteps() {
        return caseSteps;
    }

    public void setCaseSteps(Map<String, IEnhancedTaskStep> caseSteps) {
        this.caseSteps = caseSteps;
    }

    public IEnhancedTaskStep getDefaultStep() {
        return defaultStep;
    }

    public void setDefaultStep(IEnhancedTaskStep defaultStep) {
        this.defaultStep = defaultStep;
    }

    @Nonnull
    @Override
    public TaskStepResult execute(ITaskStepRuntime stepRt) {
        String caseValue = stepRt.getStateBean(String.class);
        if (caseValue == null) {
            caseValue = ConvertHelper.toString(decider.invoke(stepRt));
            if (StringHelper.isEmpty(caseValue))
                caseValue = TaskConstants.DEFAULT_VALUE;
            stepRt.setStateBean(caseValue);
            stepRt.saveState();
        }

        IEnhancedTaskStep chosenStep = caseSteps.get(caseValue);
        if (chosenStep == null)
            chosenStep = defaultStep;

        if (chosenStep == null) {
            return TaskStepResult.CONTINUE;
        }

        return chosenStep.executeWithParentRt(stepRt);
    }
}
