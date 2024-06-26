package io.nop.markdown;

import io.nop.core.unittest.BaseTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMarkdownNormalizer extends BaseTestCase {
    @Test
    public void testNormalize() {
        String text = "## `$`x$\\delta +1 $  \n\n\n\n ### some \n # value \n ````xml\n <a/> \n````\na\n$$\n \\alpha\n$$";
        String normalized = new MarkdownNormalizer().normalizeText(text);
        System.out.println(normalized);
        assertEquals(normalizeCRLF(normalized).trim(), normalizeCRLF(attachmentText("normalized.md")).trim());
    }
}
