/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.graphql.core.engine;

import io.nop.api.core.beans.ApiResponse;
import io.nop.api.core.beans.graphql.GraphQLResponseBean;
import io.nop.graphql.core.IGraphQLExecutionContext;

import java.util.concurrent.CompletionStage;

public interface IGraphQLExecutor {
    CompletionStage<ApiResponse<?>> executeOneAsync(IGraphQLExecutionContext context);

    CompletionStage<GraphQLResponseBean> executeAsync(IGraphQLExecutionContext context);
}
