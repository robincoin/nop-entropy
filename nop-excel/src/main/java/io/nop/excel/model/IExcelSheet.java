/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.excel.model;

import io.nop.core.model.table.ITableView;

import java.util.List;

public interface IExcelSheet {
    String getName();

    ExcelPageSetup getPageSetup();

    ExcelPageMargins getPageMargins();

    ExcelPageBreaks getPageBreaks();

    Double getDefaultRowHeight();

    Double getDefaultColumnWidth();

    ITableView getTable();

    List<ExcelImage> getImages();
}
