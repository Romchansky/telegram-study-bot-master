package ua.goit.util;

@FunctionalInterface
public interface Validator<E> {

    boolean valid(E entity);
}
