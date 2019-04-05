/*
 * Copyright (c) 2018.
 * Author: Mikhail Sigachev
 */

package com.zource.form;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class EmailForm {

    @NotEmpty
    public String name;

    @NotEmpty
    @Email
    @Value("defaultemail@email.com")
    public String to;

    @NotEmpty
    @Email
    public String email;

    @NotEmpty
    public String subject;

    @NotEmpty
    @Size(min = 10, message = "Please, enter your message. Min size is 10 symbols.")
    public String message;

    public EmailForm() {

        this.setTo("defaultemail@email.com");
    }

}
