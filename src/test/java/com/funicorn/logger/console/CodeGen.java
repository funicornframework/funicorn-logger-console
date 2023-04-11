package com.funicorn.logger.console;

import com.funicorn.framework.common.datasource.generator.CodeGenerator;

/**
 * @author Aimee
 * @since 2023/3/29 10:45
 */
public class CodeGen {

    public static void main(String[] args) {

        CodeGenerator generator = new CodeGenerator();
        generator.author="Aimee";
        generator.jdbcUrl="jdbc:mysql://106.13.217.154:3306/funicorn_log?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&rewriteBatchedStatements=true";
        generator.username="root";
        generator.password="Liuhao20201@";
        generator.packageName="com.funicorn.logger.console";
        generator.generateCode("user_app");
    }
}
