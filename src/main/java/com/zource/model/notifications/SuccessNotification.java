/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model.notifications;
import lombok.Data;

@Data
public class SuccessNotification extends Notification {

    public SuccessNotification(String message) {
        super.message = message;
        super.type = "success";
    }
}
