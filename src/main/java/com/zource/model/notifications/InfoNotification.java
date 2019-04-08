/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model.notifications;

import lombok.Data;

@Data
public class InfoNotification extends Notification{

    public InfoNotification(String message) {
        super.message = message;
        super.type = "info";
    }

}
