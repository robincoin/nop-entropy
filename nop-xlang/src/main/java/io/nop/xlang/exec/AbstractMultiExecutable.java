/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.xlang.exec;

import io.nop.api.core.util.SourceLocation;
import io.nop.core.lang.eval.IExecutableExpression;

public abstract class AbstractMultiExecutable extends AbstractExecutable {
    protected final IExecutableExpression[] exprs;

    public AbstractMultiExecutable(SourceLocation loc, IExecutableExpression[] exprs) {
        super(loc);
        this.exprs = exprs;
    }

    public IExecutableExpression[] getExprs() {
        return exprs;
    }

    public void display(StringBuilder sb) {
        sb.append("seq()");
    }
}