/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.xlang.ast;

import io.nop.api.core.util.ISourceLocationGetter;
import io.nop.xlang.scope.LexicalScope;

public interface IWithLexicalScope extends ISourceLocationGetter {
    LexicalScope getLexicalScope();

    void setLexicalScope(LexicalScope scope);
}
