/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.xlang.exec;

import io.nop.api.core.util.SourceLocation;
import io.nop.core.lang.eval.EvalFrame;
import io.nop.core.lang.eval.EvalRuntime;
import io.nop.core.lang.eval.IExecutableExpression;
import io.nop.core.lang.eval.IExpressionExecutor;

public class AssignIdentifier {
    private final SourceLocation loc;
    private final int slotIndex;
    private final String varName;
    private final boolean useRef;
    private final IExecutableExpression initializer;

    public AssignIdentifier(SourceLocation loc, int slotIndex, String varName, boolean useRef,
                            IExecutableExpression initializer) {
        this.loc = loc;
        this.slotIndex = slotIndex;
        this.varName = varName;
        this.useRef = useRef;
        this.initializer = initializer;
    }

    public IExecutableExpression getInitializer() {
        return initializer;
    }

    public SourceLocation getLocation() {
        return loc;
    }

    public int getVarSlot() {
        return slotIndex;
    }

    public String getVarName() {
        return varName;
    }

    public boolean isUseRef() {
        return useRef;
    }

    public Object getDefaultValue(IExpressionExecutor executor, EvalRuntime rt) {
        if (initializer == null)
            return null;
        return executor.execute(initializer, rt);
    }

    public void assign(Object value, EvalRuntime rt) {
        if (slotIndex >= 0) {
            EvalFrame frame = rt.getCurrentFrame();
            if (useRef) {
                frame.setRefValue(slotIndex, value);
            } else {
                frame.setStackValue(slotIndex, value);
            }
        } else {
            rt.setLocalValue(loc, varName, value);
        }
    }
}
