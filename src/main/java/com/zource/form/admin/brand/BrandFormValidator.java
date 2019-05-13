/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.form.admin.brand;

import com.zource.dao.BrandDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BrandFormValidator implements Validator {

    @Autowired
    private BrandDAO brandDAO;

    // This validator only checks for the BrandForm.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == BrandForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        BrandForm brandForm = (BrandForm) target;

        // Check the fields of ProductForm.
        /* ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.brandForm.name");*/


/*        String n = brandForm.getName();
        if (n != null) {

            errors.rejectValue("name", "NotEmpty.brandForm.name");
        }*/

/*        String code = brand.getCode();
        if (code != null && code.length() > 0) {
            if (code.matches("\\s+")) {
                errors.rejectValue("code", "Pattern.productForm.code");
            } else if (productForm.isNewProduct()) {
                Product product = productDAO.findProduct(code);
                if (product != null) {
                    errors.rejectValue("code", "Duplicate.productForm.code");
                }
            }
        }*/


    }

}