/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

/* Bootstrap notify*/

package com.zource.model.notifications;

import lombok.Data;

@Data
public class Notification {


    public String message;
    public String type = "info";

    public  Notification() {
    }

    public Notification(String m) {
    this.message = m;
}

    public  Notification(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public void info() {
        this.type = "info";
    }

    public void success() {
        this.type = "success";
    }

    public void warning() {
        this.type = "warning";
    }

    public void danger() {
        this.type = "danger";
    }

}




