/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.xlang.exec;

import io.nop.api.core.convert.ITypeConverter;
import io.nop.api.core.util.SourceLocation;
import io.nop.core.lang.eval.EvalRuntime;
import io.nop.core.lang.eval.IExecutableExpression;
import io.nop.core.lang.eval.IExecutableExpressionVisitor;
import io.nop.core.lang.eval.IExpressionExecutor;

public class ConvertWithDefaultExecutable extends AbstractExecutable {
    private final IExecutableExpression expr;
    private final String funcName;
    private final ITypeConverter converter;
    private final IExecutableExpression defaultExpr;

    public ConvertWithDefaultExecutable(SourceLocation loc, IExecutableExpression expr, String funcName,
                                        ITypeConverter converter, IExecutableExpression defaultExpr) {
        super(loc);
        this.expr = expr;
        this.funcName = funcName;
        this.converter = converter;
        this.defaultExpr = defaultExpr;
    }

    @Override
    public void display(StringBuilder sb) {
        expr.display(sb);
        sb.append('.').append(funcName);
        sb.append('(');
        defaultExpr.display(sb);
        sb.append(')');
    }

    @Override
    public boolean allowBreakPoint() {
        return false;
    }

    @Override
    public Object execute(IExpressionExecutor executor, EvalRuntime rt) {
        Object value = executor.execute(expr, rt);
        if (value != null)
            return converter.convertEx(rt, value, err -> newError(err));
        Object defaultValue = executor.execute(defaultExpr, rt);
        return converter.convertEx(rt, defaultValue, err -> newError(err));
    }

    @Override
    public void visit(IExecutableExpressionVisitor visitor) {
        if(visitor.onVisitExpr(this)) {
            expr.visit(visitor);
            defaultExpr.visit(visitor);
            visitor.onEndVisitExpr(this);
        }
    }
}
