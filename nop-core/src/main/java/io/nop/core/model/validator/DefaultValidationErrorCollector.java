package io.nop.core.model.validator;

import io.nop.api.core.beans.ErrorBean;
import io.nop.api.core.context.ContextProvider;
import io.nop.api.core.exceptions.NopRebuildException;
import io.nop.api.core.exceptions.NopValidateException;
import io.nop.api.core.validate.IValidationErrorCollector;
import io.nop.core.exceptions.ErrorMessageManager;
import io.nop.core.lang.json.JsonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DefaultValidationErrorCollector implements IValidationErrorCollector {
    static final Logger LOG = LoggerFactory.getLogger(DefaultValidationErrorCollector.class);
    private final int fatalSeverity;

    private final List<ErrorBean> errors = new ArrayList<>();

    public DefaultValidationErrorCollector(int fatalSeverity) {
        this.fatalSeverity = fatalSeverity;
    }

    public void end() {
        if (errors.isEmpty())
            return;

        throw new NopValidateException().errors(errors);
    }

    @Override
    public void addError(ErrorBean error) {
        if (error.getSeverity() >= fatalSeverity)
            throw NopRebuildException.rebuild(error);

        if (LOG.isDebugEnabled())
            LOG.info("nop.validate.error:{}", JsonTool.stringify(error));

        ErrorMessageManager.instance().resolveErrorDescription(error);
        errors.add(error);
    }

    @Override
    public void addException(Throwable e) {
        ErrorBean error = ErrorMessageManager.instance().buildErrorMessage(ContextProvider.currentLocale(), e);
        addError(error);
    }
}
