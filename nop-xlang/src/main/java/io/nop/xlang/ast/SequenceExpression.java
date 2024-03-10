/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.xlang.ast;

import io.nop.api.core.util.Guard;
import io.nop.api.core.util.SourceLocation;
import io.nop.xlang.ast._gen._SequenceExpression;

import java.util.List;

public class SequenceExpression extends _SequenceExpression {

    public static SequenceExpression valueOf(SourceLocation loc, List<Expression> exprs) {
        Guard.notNull(exprs, "exprs is null");
        SequenceExpression node = new SequenceExpression();
        node.setLocation(loc);
        node.setExpressions(exprs);
        return node;
    }
}