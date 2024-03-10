/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.commons.text;

import io.nop.commons.text.regex.IRegex;
import io.nop.commons.text.regex.RegexHelper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRegex {
    @Test
    public void checkPattern() {
        String input = "java.util.List<com.github.test.SimpleDomainServiceDto>";

        // 正则表达式，用于检测 List 类型和提取泛型参数
        String regex = "^java\\.util\\.List<(.+?)>$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        assertTrue(matcher.find());
        // 是 List 类型
        String fullGenericTypeName = matcher.group(1);
        assertEquals("com.github.test.SimpleDomainServiceDto", fullGenericTypeName);
        System.out.println("List of type: " + fullGenericTypeName);

        // 提取简单类名
        String simpleNameRegex = "\\.([^.]+)$";
        Pattern simpleNamePattern = Pattern.compile(simpleNameRegex);
        Matcher simpleNameMatcher = simpleNamePattern.matcher(fullGenericTypeName);

        assertTrue(simpleNameMatcher.find());
        assertEquals("SimpleDomainServiceDto", simpleNameMatcher.group(1));

    }

    @Test
    public void checkIRegex() {
        String input = "java.util.List<com.github.test.SimpleDomainServiceDto>";
        // 正则表达式，用于检测 List 类型和提取泛型参数
        String regexString = "^java\\.util\\.List<(.+?)>$";
        IRegex regex = RegexHelper.compileRegex(regexString);

        assertTrue(regex.test(input));

        List<String> list = regex.exec(input);
        String fullGenericTypeName = list.get(1);
        assertEquals("com.github.test.SimpleDomainServiceDto", fullGenericTypeName);
        System.out.println("List of type: " + fullGenericTypeName);

        // 提取简单类名
        String regexString2 = "\\.([^.]+)$";
        IRegex regex2 = RegexHelper.compileRegex(regexString2);

        // test是判断是否匹配整个字符串
        assertTrue(!regex2.test(fullGenericTypeName));

        list = regex2.exec(fullGenericTypeName);
        String simpleName = list.get(1);
        assertEquals("SimpleDomainServiceDto", simpleName);
    }
}
