/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.api.core.config;

import io.nop.api.core.util.SourceLocation;

import java.util.Map;

/**
 * 应用层获取配置以及监听配置项变化所使用的接口
 */
public interface IConfigProvider {
    Map<String, DefaultConfigReference<?>> getConfigReferences();

    <T> IConfigReference<T> getConfigReference(String varName, Class<T> clazz, T defaultValue, SourceLocation loc);


    <T> void updateConfigValue(IConfigReference<T> ref, T value);

    void assignConfigValue(String name, Object value);

    /**
     * 注册监听器。当配置发生变化时得到通知
     *
     * @param pattern  格式为a.b.c，最后一个部分可以是*，表示模糊匹配所有具有同样前缀的var
     * @param listener 回调函数
     * @return 取消函数，可以调用此返回函数来取消监听
     */
    Runnable subscribeChange(String pattern, IConfigChangeListener listener);

    <T> T getConfigValue(String varName, T defaultValue);
}