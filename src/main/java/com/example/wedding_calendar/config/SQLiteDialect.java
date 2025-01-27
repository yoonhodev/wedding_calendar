package com.example.wedding_calendar.config;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.sql.ast.SqlAstTranslatorFactory;
import org.hibernate.sql.ast.spi.StandardSqlAstTranslatorFactory;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;

public class SQLiteDialect extends Dialect implements FunctionContributor {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 41));  // SQLite 3.41 버전
    }

    // IDENTITY 컬럼 지원
    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl();
    }

    // SQL AST 변환기 (Hibernate 6.x에서는 파라미터 없이 사용)
    @Override
    public SqlAstTranslatorFactory getSqlAstTranslatorFactory() {
        return new StandardSqlAstTranslatorFactory();
    }

    // SQL 함수 등록 (Hibernate 6.2 방식)
    @Override
    public void contributeFunctions(FunctionContributions functionContributions) {
        SqmFunctionRegistry functionRegistry = functionContributions.getFunctionRegistry();

        // CONCAT 함수 등록
        functionRegistry.registerPattern("concat", "(?1 || ?2)");

        // MOD 함수 등록
        functionRegistry.registerPattern("mod", "(?1 % ?2)");

        // SUBSTRING 함수 등록
        functionRegistry.registerPattern("substring", "substr(?1, ?2, ?3)");
    }
}
