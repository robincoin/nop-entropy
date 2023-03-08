/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.commons.crypto;

import java.io.InputStream;
import java.io.OutputStream;

public interface IStreamCipher {
    InputStream encryptInputStream(InputStream is);

    InputStream decryptInputStream(InputStream is);

    OutputStream encryptOutputStream(OutputStream os);

    OutputStream decryptOutputStream(OutputStream os);
}