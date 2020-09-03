package com.miketheshadow.tests;

import com.miketheshadow.sqlhelper.annotation.IStorable;
import com.miketheshadow.sqlhelper.SQLObject;
import com.miketheshadow.sqlhelper.annotation.Ignorable;
import com.miketheshadow.sqlhelper.annotation.Index;

@IStorable(table = "testing")
public class TestObject implements SQLObject {

    @Index
    int x;
    String y;

    @Ignorable
    String z;

    public TestObject(int x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TestObject() {

    }
}
