package com.miketheshadow.tests;

import com.miketheshadow.sqlhelper.SQLHelper;
import com.miketheshadow.sqlhelper.SQLHelperBuilder;

public class Test {

    public static void main(String[] args) {

        SQLHelper helper = new SQLHelperBuilder()
                .withDatabaseURL("jdbc:mysql://localhost:3306/test?useSSL=false")
                .withUsername(args[0])
                .withPassword(args[1])
                .withSchema("test")
                .withPattern(new TestObject())
                .build();

        helper.initializeTable();

    }


}
