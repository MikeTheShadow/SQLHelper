package com.miketheshadow.sqlhelper;

public class SQLHelperBuilder {



    private String databaseURL;
    private String username;
    private String password;
    private String schema;
    private SQLObject pattern;

    public SQLHelperBuilder(String databaseURL, String username, String password,String schema,SQLObject pattern) {
        this.databaseURL = databaseURL;
        this.username = username;
        this.password = password;
        this.schema = schema;
    }

    public SQLHelperBuilder() {

    }

    public SQLHelperBuilder withDatabaseURL(String dbURL) {
        this.databaseURL = dbURL;
        return this;
    }

    public SQLHelperBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public SQLHelperBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public SQLHelperBuilder withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public SQLHelperBuilder withPattern(SQLObject pattern) {
        this.pattern = pattern;
        return this;
    }

    public SQLHelper build() {
        SQLHelper helper = new SQLHelper();
        helper.databaseURL = this.databaseURL;
        helper.username = this.username;
        helper.password = this.password;
        helper.schema = this.schema;
        helper.pattern = this.pattern;
        return helper;
    }
}
