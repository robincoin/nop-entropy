/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.graphql.core.engine;

import io.nop.api.core.beans.FieldSelectionBean;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.FutureHelper;
import io.nop.api.core.util.Guard;
import io.nop.core.context.ServiceContextImpl;
import io.nop.graphql.core.IGraphQLExecutionContext;
import io.nop.graphql.core.ParsedGraphQLRequest;
import io.nop.graphql.core.ast.GraphQLOperation;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import static io.nop.graphql.core.GraphQLErrors.ARG_LOADER_NAME;
import static io.nop.graphql.core.GraphQLErrors.ERR_GRAPHQL_DUPLICATED_LOADER;

public class GraphQLExecutionContext extends ServiceContextImpl implements IGraphQLExecutionContext {
    static final Logger LOG = LoggerFactory.getLogger(GraphQLExecutionContext.class);

    private GraphQLOperation operation;

    private final Map<String, DataLoader> loaders = new ConcurrentHashMap<>();

    private FieldSelectionBean fieldSelection;

    private boolean makerCheckerEnabled;

    @Override
    public boolean isMakerCheckerEnabled() {
        return makerCheckerEnabled;
    }

    @Override
    public void setMakerCheckerEnabled(boolean makerCheckerEnabled) {
        this.makerCheckerEnabled = makerCheckerEnabled;
    }

    public FieldSelectionBean getFieldSelection() {
        return fieldSelection;
    }

    public void setFieldSelection(FieldSelectionBean fieldSelection) {
        this.fieldSelection = fieldSelection;
    }

    @Override
    public GraphQLOperation getOperation() {
        return operation;
    }

    public void setOperation(GraphQLOperation operation) {
        this.operation = operation;
    }

    public synchronized ParsedGraphQLRequest getRequest() {
        return (ParsedGraphQLRequest) super.getRequest();
    }

    @Override
    public <K, V> DataLoader getDataLoader(String loaderName) {
        return loaders.get(loaderName);
    }

    @Override
    public <K, V> void registerDataLoader(String loaderName, DataLoader<K, V> loader) {
        Guard.notNull(loader, "loader");

        DataLoader old = loaders.put(loaderName, loader);
        if (old != null && old != loader)
            throw new NopException(ERR_GRAPHQL_DUPLICATED_LOADER).param(ARG_LOADER_NAME, loaderName);
    }

    @Override
    public synchronized CompletionStage<Void> dispatchAll() {
        if (loaders.isEmpty())
            return null;

        Map<String, DataLoader> loaders = new HashMap<>(this.loaders);
        this.loaders.clear();

        List<CompletionStage<?>> futures = new ArrayList<>();
        for (Map.Entry<String, DataLoader> entry : loaders.entrySet()) {
            DataLoader loader = entry.getValue();
            LOG.debug("loader.dispatch:{}", entry.getKey());
            futures.add(loader.dispatch());
        }
        return FutureHelper.waitAll(futures);
    }

}
