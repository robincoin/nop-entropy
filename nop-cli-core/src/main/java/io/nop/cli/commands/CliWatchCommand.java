/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.cli.commands;

import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.Guard;
import io.nop.commons.util.FileHelper;
import io.nop.core.lang.eval.IEvalScope;
import io.nop.core.resource.IResource;
import io.nop.core.resource.ResourceHelper;
import io.nop.core.resource.watch.FileChangeEvent;
import io.nop.core.resource.watch.FileWatcher;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XplModel;
import io.nop.xlang.ast.XLangOutputMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import static io.nop.cli.CliErrors.ARG_NAME;
import static io.nop.cli.CliErrors.ARG_PATH;
import static io.nop.cli.CliErrors.ERR_CLI_UNKNOWN_SCRIPT;

@CommandLine.Command(
        name = "watch",
        mixinStandardHelpOptions = true,
        description = "监控指定目录或者文件的变化"
)
public class CliWatchCommand implements Callable<Integer> {
    static final Logger LOG = LoggerFactory.getLogger(CliWatchCommand.class);

    @CommandLine.Parameters(index = "0", description = "监控文件目录", arity = "1")
    File watchDir;

    @CommandLine.Option(names = {"-p", "--patterns"},
            description = "监听的文件名模式,例如*.meta.xml。如果不指定，则任何文件变动都会触发事件")
    String[] fileNamePatterns;

    @CommandLine.Option(names = {"-e", "--execute"}, required = true,
            description = "发现文件变动后执行的脚本文件名称，脚本文件需要存放在当前的scripts目录下")
    String scriptName;

    @CommandLine.Option(names = {"-o", "--output"}, description = "输出目录，缺省为当前目录")
    File outputDir;

    @CommandLine.Option(names = {"-w", "--wait"},
            description = "延迟处理等待间隔，缺省为100毫秒")
    int debounceWait = 100;

    @Inject
    FileWatcherFactory fileWatcherFactory;

    @Override
    public Integer call() {
        FileWatcher watcher = fileWatcherFactory.newFileWatcher();
        Map<String, Object> state = new ConcurrentHashMap<>();

        if (outputDir == null)
            outputDir = FileHelper.currentDir();

        LOG.info("nop.cli.watch:dir={},outputDir={}", watchDir, outputDir);

        Guard.checkArgument(scriptName.indexOf("..") < 0);

        IResource resource = ResourceHelper.resolveRelativePathResource("scripts/" + scriptName + ".xrun");
        if (!resource.exists()) {
            throw new NopException(ERR_CLI_UNKNOWN_SCRIPT)
                    .param(ARG_NAME, scriptName)
                    .param(ARG_PATH, resource.getPath());
        }

        XplModel xpl = XLang.parseXpl(resource, XLangOutputMode.none);

        // 总是执行一次脚本，然后再watch
        processEvents(xpl, Collections.emptyList(), state);

        watcher.watch(Collections.singletonList(watchDir.getAbsolutePath()), fileNamePatterns == null ? null : Arrays.asList(fileNamePatterns),
                debounceWait, events -> processEvents(xpl, events, state));
        try {
            System.in.read();
        } catch (IOException e) {
        }

        return 0;
    }

    private void processEvents(XplModel xpl, Collection<FileChangeEvent> events, Map<String, Object> state) {
        IEvalScope scope = XLang.newEvalScope();
        scope.setLocalValue("changeEvents", events);
        scope.setLocalValue("globalState", state);
        scope.setLocalValue("watchDir", watchDir);
        scope.setLocalValue("outputDir", outputDir);
        xpl.invoke(scope);
    }
}