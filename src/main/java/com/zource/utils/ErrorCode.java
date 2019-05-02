/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.utils;

public enum ErrorCode {
    NO_File("No file found. ");

    private ErrorCode(String value) {
        this.errordesc = value;
    }

    private String errordesc = "";

    public String errordesc() {
        return errordesc;
    }

    public void setValue(String errordesc) {
        this.errordesc = errordesc;
    }
    }