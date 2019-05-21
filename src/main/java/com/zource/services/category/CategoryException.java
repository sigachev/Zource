/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.services.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryException extends RuntimeException {
    private static final long serialVersionUID = 7718828512143293558L;
    private final Integer code = 0;
    private String pageURL = "";


    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }


    public CategoryException(String message, String pageURL) {
        super(message);
        this.pageURL = pageURL;
    }


    public CategoryException(String message, Throwable cause, String pageURL) {
        super(message, cause);
        this.pageURL = pageURL;
    }

}
