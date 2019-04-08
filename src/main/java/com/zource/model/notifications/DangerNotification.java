/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model.notifications;

import lombok.Data;

@Data
public class DangerNotification extends Notification{

    public DangerNotification(String message) {
        super.message = message;
        super.type = "danger";
    }

}
