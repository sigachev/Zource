/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model.notifications;
import lombok.Data;

@Data
public class WarningNotification extends Notification{

        public WarningNotification(String message) {
            super.message = message;
            super.type = "warning";
        }

}
