/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.xlang.xdef.domain;

import io.nop.api.core.beans.TreeBean;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.SourceLocation;
import io.nop.api.core.validate.IValidationErrorCollector;
import io.nop.commons.util.StringHelper;
import io.nop.commons.util.objects.ValueWithLocation;
import io.nop.core.CoreConstants;
import io.nop.core.lang.eval.IEvalAction;
import io.nop.core.lang.xml.XNode;
import io.nop.core.lang.xml.json.StdJsonToXNodeTransformer;
import io.nop.core.lang.xml.parse.XNodeParser;
import io.nop.core.reflect.ReflectionManager;
import io.nop.core.type.IGenericType;
import io.nop.core.type.PredefinedGenericTypes;
import io.nop.xlang.XLangConstants;
import io.nop.xlang.api.EvalCode;
import io.nop.xlang.api.ExprEvalAction;
import io.nop.xlang.api.XLang;
import io.nop.xlang.api.XLangCompileTool;
import io.nop.xlang.api.source.IWithSourceCode;
import io.nop.xlang.ast.Expression;
import io.nop.xlang.ast.XLangOutputMode;
import io.nop.xlang.expr.ExprPhase;
import io.nop.xlang.xdef.IStdDomainHandler;
import io.nop.xlang.xdef.IStdDomainOptions;
import io.nop.xlang.xdef.XDefConstants;
import io.nop.xlang.xdsl.XDslConstants;
import io.nop.xlang.xpl.tags.FilterBeanExpressionCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.nop.xlang.XLangErrors.ARG_PROP_NAME;
import static io.nop.xlang.XLangErrors.ARG_STD_DOMAIN;
import static io.nop.xlang.XLangErrors.ARG_TAG_NAME;
import static io.nop.xlang.XLangErrors.ARG_VALUE;
import static io.nop.xlang.XLangErrors.ERR_XDEF_ILLEGAL_BODY_VALUE_FOR_STD_DOMAIN;
import static io.nop.xlang.XLangErrors.ERR_XDEF_ILLEGAL_CONTENT_VALUE_FOR_STD_DOMAIN;
import static io.nop.xlang.XLangErrors.ERR_XDEF_ILLEGAL_PROP_VALUE_FOR_STD_DOMAIN;

public class XplStdDomainHandlers {
    public static abstract class AbstractXplType implements IStdDomainHandler {

        protected final XLangOutputMode outputMode;

        public AbstractXplType(XLangOutputMode outputMode) {
            this.outputMode = outputMode;
        }

        protected NopException newPropError(SourceLocation loc, String propName, String text) {
            return new NopException(ERR_XDEF_ILLEGAL_PROP_VALUE_FOR_STD_DOMAIN).loc(loc).param(ARG_PROP_NAME, propName)
                    .param(ARG_STD_DOMAIN, getName()).param(ARG_VALUE, text);
        }

        protected NopException newContentError(XNode node) {
            return new NopException(ERR_XDEF_ILLEGAL_CONTENT_VALUE_FOR_STD_DOMAIN).source(node)
                    .param(ARG_TAG_NAME, node.getTagName()).param(ARG_STD_DOMAIN, getName())
                    .param(ARG_VALUE, node.getContentValue());
        }

        protected NopException newBodyError(XNode node) {
            return new NopException(ERR_XDEF_ILLEGAL_BODY_VALUE_FOR_STD_DOMAIN).source(node)
                    .param(ARG_TAG_NAME, node.getTagName()).param(ARG_STD_DOMAIN, getName());
        }

        protected ExprEvalAction parsePropFullExpr(XLangOutputMode outputMode, SourceLocation loc, String propName,
                                                   String text, XLangCompileTool cp) {
            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(outputMode);
            try {
                return cp.compileFullExpr(loc, text);
            } catch (Exception e) {
                throw newPropError(loc, propName, text).cause(e);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        protected XLangOutputMode getOutputMode(XNode node) {
            XLangOutputMode outputMode = XLangOutputMode.fromText(node.attrText(XDslConstants.OUTPUT_MODE_NAME));
            if (outputMode == null)
                outputMode = this.outputMode;
            return outputMode;
        }

        @Override
        public boolean supportXmlChild() {
            return true;
        }

        protected ExprEvalAction parseXplBody(XNode node, XLangCompileTool cp) {
            XLangOutputMode outputMode = getOutputMode(node);

            if (node.hasChild() || outputMode != XLangOutputMode.none) {
                XLangOutputMode oldMode = cp.getOutputMode();
                cp.outputMode(outputMode);
                try {
                    return doCompileBody(cp, node, outputMode);
                } catch (Exception e) {
                    throw newBodyError(node).cause(e);
                } finally {
                    cp.outputMode(oldMode);
                }
            } else {
                ValueWithLocation content = node.content();
                String text = content.asString();
                if (StringHelper.isBlank(text))
                    return null;

                XLangOutputMode oldMode = cp.getOutputMode();
                cp.outputMode(outputMode);

                try {
                    return cp.compileFullExpr(content.getLocation(), text);
                } catch (Exception e) {
                    throw newContentError(node).cause(e);
                } finally {
                    cp.outputMode(oldMode);
                }
            }
        }

        protected ExprEvalAction doCompileBody(XLangCompileTool cp, XNode node, XLangOutputMode outputMode) {
            return cp.compileTagBody(node, outputMode);
        }

        @Override
        public void validate(SourceLocation loc, String propName, Object value, IValidationErrorCollector collector) {
            try {
                if (value instanceof String) {
                    String text = value.toString();
                    // 如果是复杂的xpl，则必须是完整的XML。否则认为是EL表达式
                    if (text.startsWith("<")) {
                        XNodeParser.instance().forFragments(true).parseFromText(loc, text);
                    } else {
                        XLang.newCompileTool().parseFullExpr(loc, text);
                    }
                } else if (value instanceof Map) {
                    TreeBean.createFromJson((Map<String, Object>) value);
                }
            } catch (Exception e) {
                collector.addException(e);
            }
        }

        @Override
        public XNode transformToNode(Object value) {
            if (value instanceof IWithSourceCode) {
                String source = ((IWithSourceCode) value).getSource();
                if (StringHelper.maybeXml(source)) {
                    XNode node = XNodeParser.instance().parseFromText(null, source);
                    if (node.isDummyNode() || node.getTagName().equals(XLangConstants.TAG_C_UNIT)) {
                        node.setTagName(CoreConstants.DUMMY_TAG_NAME);
                    } else {
                        XNode parent = XNode.make(CoreConstants.DUMMY_TAG_NAME);
                        parent.appendChild(node);
                        node = parent;
                    }
                    return node;
                } else {
                    XNode node = XNode.make(CoreConstants.DUMMY_TAG_NAME);
                    node.content(source);
                    return node;
                }
            }
            return StdJsonToXNodeTransformer.INSTANCE.transformToXNode(value);
        }
    }

    public static class XplType extends AbstractXplType {
        private final String name;
        private final IGenericType type;

        public XplType(String name, IGenericType type, XLangOutputMode outputMode) {
            super(outputMode);
            this.name = name;
            this.type = type;
        }

        public XLangOutputMode getOutputMode() {
            return outputMode;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public IGenericType getGenericType(boolean mandatory, IStdDomainOptions options) {
            return type;
        }

        @Override
        public Object parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                XLangCompileTool cp) {
            if (value instanceof ExprEvalAction)
                return value;
            if (value instanceof XNode)
                return parseXplBody((XNode) value, cp);

            String text = value.toString().trim();
            if (text.startsWith("<") || text.endsWith(">")) {
                XNode node = XNodeParser.instance().forFragments(true).parseFromText(loc, text);
                return parseXplBody(node, cp);
            }
            return parsePropFullExpr(outputMode, loc, propName, text, cp);
        }

        @Override
        public Object parseXmlChild(IStdDomainOptions options, XNode body, XLangCompileTool cp) {
            return parseXplBody(body, cp);
        }
    }

    public static class XplNoneType extends XplType {
        public XplNoneType() {
            super(XDefConstants.STD_DOMAIN_XPL, PredefinedGenericTypes.I_EVAL_ACTION_TYPE, XLangOutputMode.none);
        }

    }

    /**
     * 仅用于占位使用，避免装载workbook.xdef时编译报错，具体实现在nop-report模块中
     */
    public static class MockReportExprType extends XplType {
        static final Logger LOG = LoggerFactory.getLogger(MockReportExprType.class);

        public MockReportExprType() {
            super(XDefConstants.STD_DOMAIN_REPORT_EXPR, PredefinedGenericTypes.I_EVAL_ACTION_TYPE, XLangOutputMode.none);
        }

        @Override
        public Object parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                XLangCompileTool cp) {
            LOG.warn("nop.xlang.report-expr-domain-is-not-supported:report-expr domain is defined in rop-report module");
            return null;
        }

        @Override
        public Object parseXmlChild(IStdDomainOptions options, XNode body, XLangCompileTool cp) {
            LOG.warn("nop.xlang.report-expr-domain-is-not-supported:report-expr domain is defined in rop-report module");
            return null;
        }
    }

    public static class XplPredicateType extends XplType {
        public XplPredicateType() {
            super(XDefConstants.STD_DOMAIN_XPL_PREDICATE, PredefinedGenericTypes.I_EVAL_PREDICATE_TYPE, XLangOutputMode.none);
        }

        @Override
        public String getXDefPath() {
            return XLangConstants.XDSL_SCHEMA_QUERY_FILTER;
        }

        @Override
        public ExprEvalAction doCompileBody(XLangCompileTool cp, XNode node, XLangOutputMode outputMode) {
            cp.outputMode(outputMode);
            Expression expr = new FilterBeanExpressionCompiler(cp).compilePredicate(node);
            return cp.buildEvalAction(expr);
        }
    }

    public static IStdDomainHandler XPL_TYPE = new XplNoneType();

    public static IStdDomainHandler XPL_PREDICATE_TYPE = new XplPredicateType();

    public static IStdDomainHandler XPL_XML_TYPE = new XplType(XDefConstants.STD_DOMAIN_XPL_XML,
            PredefinedGenericTypes.I_TEXT_TEMPLATE_OUTPUT_TYPE, XLangOutputMode.xml);

    public static IStdDomainHandler XPL_TEXT_TYPE = new XplType(XDefConstants.STD_DOMAIN_XPL_TEXT,
            PredefinedGenericTypes.I_TEXT_TEMPLATE_OUTPUT_TYPE, XLangOutputMode.text);

    public static IStdDomainHandler XPL_HTML_TYPE = new XplType(XDefConstants.STD_DOMAIN_XPL_HTML,
            PredefinedGenericTypes.I_TEXT_TEMPLATE_OUTPUT_TYPE, XLangOutputMode.html);

    public static IStdDomainHandler XPL_SQL_TYPE = new XplType(XDefConstants.STD_DOMAIN_XPL_SQL,
            PredefinedGenericTypes.I_SQL_GENERATOR_TYPE, XLangOutputMode.sql);

    public static IStdDomainHandler XPL_NODE_TYPE = new XplType(XDefConstants.STD_DOMAIN_XPL_NODE,
            PredefinedGenericTypes.I_X_NODE_GENERATOR_TYPE, XLangOutputMode.node);

    public static IStdDomainHandler XPL_XJSON_TYPE = new XJsonType();

    public static IStdDomainHandler EVAL_CODE_TYPE = new EvalCodeType();

    public static abstract class AbstractExprType extends AbstractXplType {
        public AbstractExprType() {
            super(XLangOutputMode.none);
        }

        @Override
        public IGenericType getGenericType(boolean mandatory, IStdDomainOptions options) {
            return PredefinedGenericTypes.I_EVAL_ACTION_TYPE;
        }

        @Override
        public boolean supportXmlChild() {
            return false;
        }
    }

    public static class ExprType extends AbstractExprType {
        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_EXPR;
        }

        public IEvalAction parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                     XLangCompileTool cp) {
            if (value instanceof IEvalAction)
                return (IEvalAction) value;

            String text = value.toString();

            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(XLangOutputMode.none);
            try {
                return cp.compileSimpleExpr(loc, text);
            } catch (Exception e) {
                throw newPropError(loc, propName, text);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName, Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseSimpleExpr(loc, text, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }

    public static class SingleExprType extends AbstractExprType {
        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_S_EXPR;
        }

        public IEvalAction parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                     XLangCompileTool cp) {
            if (value instanceof IEvalAction)
                return (IEvalAction) value;

            String text = value.toString();

            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(XLangOutputMode.none);
            try {
                return cp.compileTemplateExpr(loc, text, true, ExprPhase.eval);
            } catch (Exception e) {
                throw newPropError(loc, propName, text);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName, Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseTemplateExpr(loc, text, true, ExprPhase.eval, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }

    public static class TplExprType extends AbstractExprType {
        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_T_EXPR;
        }

        public IEvalAction parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                     XLangCompileTool cp) {
            if (value instanceof IEvalAction)
                return (IEvalAction) value;

            String text = value.toString();

            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(XLangOutputMode.none);
            try {
                return cp.compileTemplateExpr(loc, text, false, ExprPhase.eval);
            } catch (Exception e) {
                throw newPropError(loc, propName, text);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName,
                             Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseTemplateExpr(loc, text, false, ExprPhase.eval, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }

    public static class XtExprType extends AbstractExprType {
        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_XT_EXPR;
        }

        public IEvalAction parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                     XLangCompileTool cp) {
            if (value instanceof IEvalAction)
                return (IEvalAction) value;

            String text = value.toString();

            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(XLangOutputMode.none);
            try {
                return cp.compileTemplateExpr(loc, text, true, ExprPhase.transform);
            } catch (Exception e) {
                throw newPropError(loc, propName, text);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName, Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseTemplateExpr(loc, text, true, ExprPhase.transform, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }

    public static class XtValueType extends AbstractExprType {
        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_XT_VALUE;
        }

        public IEvalAction parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                     XLangCompileTool cp) {
            if (value instanceof IEvalAction)
                return (IEvalAction) value;

            String text = value.toString();

            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(XLangOutputMode.none);
            try {
                return cp.compileTemplateExpr(loc, text, false, ExprPhase.transform);
            } catch (Exception e) {
                throw newPropError(loc, propName, text);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName,
                             Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseTemplateExpr(loc, text, false, ExprPhase.transform, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }

    public static class XJsonType extends XplType {
        public XJsonType() {
            super(XDefConstants.STD_DOMAIN_XPL_XJSON, PredefinedGenericTypes.I_EVAL_ACTION_TYPE, XLangOutputMode.xjson);
        }

        @Override
        public Object parseXmlChild(IStdDomainOptions options, XNode body, XLangCompileTool cp) {
            return cp.compileXjson(body);
        }
    }

    public static class EvalCodeType extends AbstractXplType {
        public EvalCodeType() {
            super(XLangOutputMode.none);
        }

        @Override
        public String getName() {
            return XDefConstants.STD_DOMAIN_EVAL_CODE;
        }

        @Override
        public IGenericType getGenericType(boolean mandatory, IStdDomainOptions options) {
            return ReflectionManager.instance().buildGenericType(EvalCode.class);
        }

        @Override
        public Object parseProp(IStdDomainOptions options, SourceLocation loc, String propName, Object value,
                                XLangCompileTool cp) {
            if (value instanceof EvalCode)
                return value;
            String code = (String) value;
            ExprEvalAction action = parsePropFullExpr(outputMode, loc, propName, code, cp);
            String configText = cp.getConfigText();
            if (configText != null) {
                code = configText + '\n' + code;
            }
            return new EvalCode(action, code);
        }

        @Override
        public Object parseXmlChild(IStdDomainOptions options, XNode body, XLangCompileTool cp) {
            ExprEvalAction action = parseXplBody(body, cp);
            if (action == null)
                return null;

            String code = body.innerXml();
            String configText = cp.getConfigText();
            if (configText != null) {
                code = configText + '\n' + code;
            }
            return new EvalCode(action, code);
        }

        protected NopException newPropError(SourceLocation loc, String propName, String text) {
            return new NopException(ERR_XDEF_ILLEGAL_PROP_VALUE_FOR_STD_DOMAIN).loc(loc).param(ARG_PROP_NAME, propName)
                    .param(ARG_STD_DOMAIN, getName()).param(ARG_VALUE, text);
        }

        protected ExprEvalAction parsePropFullExpr(XLangOutputMode outputMode, SourceLocation loc, String propName,
                                                   String text, XLangCompileTool cp) {
            XLangOutputMode oldMode = cp.getOutputMode();
            cp.outputMode(outputMode);
            try {
                return cp.compileFullExpr(loc, text);
            } catch (Exception e) {
                throw newPropError(loc, propName, text).cause(e);
            } finally {
                cp.outputMode(oldMode);
            }
        }

        @Override
        public void validate(SourceLocation loc, String propName,
                             Object value, IValidationErrorCollector collector) {
            String text = value.toString();
            XLangCompileTool cp = XLang.newCompileTool();
            try {
                cp.getCompiler().parseFullExpr(loc, text, cp.getScope(), false);
            } catch (Exception e) {
                collector.addException(e);
            }
        }
    }
}
