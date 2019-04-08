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

    public Notification info() {
        this.type = "info";
       return this;
    }

    public Notification success() {
        this.type = "success";
        return this;
    }

    public Notification warning() {
        this.type = "warning";
        return this;
    }

    public Notification danger() {
        this.type = "danger";
        return this;
    }

}




