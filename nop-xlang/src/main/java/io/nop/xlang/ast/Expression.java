/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.xlang.ast;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.nop.core.type.IGenericType;
import io.nop.xlang.ast._gen._Expression;
import io.nop.xlang.ast.print.XLangExpressionPrinter;
import io.nop.xlang.ast.trans.XLangASTTransformer;

public abstract class Expression extends _Expression {
    private transient IGenericType returnTypeInfo;

    @JsonIgnore
    public IGenericType getReturnTypeInfo() {
        return returnTypeInfo;
    }

    public void setReturnTypeInfo(IGenericType returnTypeInfo) {
        this.returnTypeInfo = returnTypeInfo;
    }

    public String toString() {
        return toExprString();
    }

    public String toExprString() {
        XLangExpressionPrinter out = new XLangExpressionPrinter();
        out.visit(this);
        return out.getResult();
    }

    public Expression replaceIdentifier(String name, Object value) {
        return XLangASTTransformer.replaceIdentifier(this, name, value);
    }
}