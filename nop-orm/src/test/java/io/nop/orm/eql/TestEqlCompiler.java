package io.nop.orm.eql;

import io.nop.api.core.json.JSON;
import io.nop.core.lang.sql.SQL;
import io.nop.orm.AbstractOrmTestCase;
import io.nop.orm.eql.ast.SqlProgram;
import io.nop.orm.eql.compile.ISqlCompileContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEqlCompiler extends AbstractOrmTestCase {

    @Test
    public void testCountStar() {
        SQL sql = SQL.begin().sql("select count(*) from SimsClass").end();
        long value = orm().findLong(sql, 0L);
        assertTrue(value > 0);
    }

    SQL compile(String sql) {
        ICompiledSql compiledSql = orm().getSessionFactory().compileSql("test", sql, true);
        return compiledSql.getSql();
    }

    @Test
    public void testEntityInLogicExpr() {
        String sqlText = "select o, (select count(*) from SimsCollege c where o.simsCollege = c) from SimsClass o";
        SQL sql = compile(sqlText);
        sql.dump();

        orm().findAll(SQL.begin().sql(sqlText).end());
    }

    @Test
    public void testAstTransform() {
        SQL sql = SQL.begin().allowUnderscoreName().sql("select o.class_id, o.studentNumber from sims_class o").end();

        IEqlAstTransformer astTransformer = new TestTransformer();
        ICompiledSql compiled = orm().getSessionFactory().compileSql("test", sql.getText(), false, astTransformer,
                false, true, false);
        System.out.println(compiled.getSql().getFormattedText());

        List<Map<String, Object>> list = orm().findAll(sql);
        System.out.println(JSON.serialize(list, true));

        Map<String, Object> item = list.get(0);
        assertTrue(item.containsKey("class_id"));
        assertTrue(item.containsKey("studentNumber"));
    }

    static class TestTransformer implements IEqlAstTransformer {
        @Override
        public void transformBeforeAnalyze(SqlProgram ast, String name, String sql, ISqlCompileContext context) {

        }

        @Override
        public void transformAfterAnalyze(SqlProgram ast, String name, String sql, ISqlCompileContext context) {

        }
    }
}
