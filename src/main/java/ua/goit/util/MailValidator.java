package ua.goit.util;

import org.apache.commons.validator.routines.EmailValidator;

public class MailValidator implements Validator {

    @Override
    public boolean valid(Object entity) {
        return EmailValidator.getInstance().isValid(entity.toString());
    }
}
