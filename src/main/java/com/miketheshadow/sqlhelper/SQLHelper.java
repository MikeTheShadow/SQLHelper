package com.miketheshadow.sqlhelper;

import com.miketheshadow.sqlhelper.annotation.IStorable;
import com.miketheshadow.sqlhelper.annotation.Ignorable;
import com.miketheshadow.sqlhelper.annotation.Index;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLHelper {

    String databaseURL;
    String username;
    String password;
    String schema;
    SQLObject pattern;

    SQLHelper() {

    }

    public void initializeTable() {
        if(!validatePattern()) {
            System.err.println("Missing @Index annotation in " + pattern.getClass().getName());
            return;
        }
        String tableName =  getTableName();
        if(tableName == null) {
            System.err.println("Missing @IStorable annotation in " + pattern.getClass().getName());
            return;
        }
        String sql = "CREATE TABLE IF NOT EXISTS " + getTableName() + " (" + getTableParameters()
                + ");";
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTableParameters() {
        StringBuilder builder = new StringBuilder();
        for(Field field : pattern.getClass().getDeclaredFields()) {
            if(!field.isAnnotationPresent(Ignorable.class)) {
                field.setAccessible(true);
                if(field.getType() == int.class) {
                    builder.append("    ")
                            .append(field.getName())
                            .append(" INTEGER NOT NULL,\n");
                } else if(field.getType() == String.class) {
                    builder.append("    ")
                            .append(field.getName())
                            .append(" text NOT NULL,\n");
                }
            }
        }
        if(builder.length() < 1) {
            System.err.println("Unable to find fields in class: " + pattern.getClass().getName());
            return null;
        }
        return builder.substring(0,builder.length() - 2);
    }

    private String getTableName() {
        Class cls = pattern.getClass();
        Annotation[] annotations = cls.getAnnotations();

        for(Annotation annotation : annotations) {
            if(annotation instanceof IStorable) {
                IStorable iStorable = (IStorable) annotation;
                return iStorable.table();
            }
        }
        return null;
    }

    private boolean validatePattern() {
        for(Field field : this.pattern.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Index.class))return true;
        }
        return false;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(databaseURL,username,password);
        } catch (Exception e) {
            System.out.println("CANNOT LOAD DATABASE!");
            e.printStackTrace();
        }
        return null;
    }

        /*
        public void testStuff(SQLObject object) {
        Class cls = object.getClass();
        Annotation[] annotations = cls.getAnnotations();

        for(Annotation annotation : annotations) {
            if(annotation instanceof IStorable) {
                IStorable iStorable = (IStorable) annotation;
                System.out.println("Table name: " + iStorable.table());
            }
        }
        StringBuilder queryBuilder = new StringBuilder();

        for(Field field : cls.getDeclaredFields()) {

            if(!field.isAnnotationPresent(Ignorable.class)) {
                System.out.println("Field: " + field.getName());
            }
        }
    }
     */
}
