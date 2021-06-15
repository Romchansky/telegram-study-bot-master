package ua.goit.util;

import org.apache.commons.validator.routines.EmailValidator;

public class MailValidator<E> implements Validator<E> {

    @Override
    public boolean valid(E entity) {
        return EmailValidator.getInstance().isValid(entity.toString());
    }

}
