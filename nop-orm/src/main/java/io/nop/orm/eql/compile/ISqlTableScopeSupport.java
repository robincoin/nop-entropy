/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.orm.eql.compile;

import io.nop.orm.eql.ast.SqlTableSource;

public interface ISqlTableScopeSupport {
    SqlTableScope getTableScope();

    default SqlTableSource getTableByAlias(String alias) {
        SqlTableScope scope = getTableScope();
        if (scope == null)
            return null;
        return scope.getTableByAlias(alias);
    }
}
