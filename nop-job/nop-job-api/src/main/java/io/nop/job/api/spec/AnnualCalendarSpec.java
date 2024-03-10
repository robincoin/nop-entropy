/**
 * Copyright (c) 2017-2024 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-entropy
 * Github: https://github.com/entropy-cloud/nop-entropy
 */
package io.nop.job.api.spec;

import io.nop.api.core.annotations.data.DataBean;

import java.time.MonthDay;

@DataBean
public class AnnualCalendarSpec extends CalendarSpec {
    private static final long serialVersionUID = 1L;

    private MonthDay[] excludes;

    public MonthDay[] getExcludes() {
        return excludes;
    }

    public void setExcludes(MonthDay[] excludes) {
        this.excludes = excludes;
    }
}
