package com.andrmix.test.luxoftsitetest.utils.exceptions;

/**
 * Исключение при отсутствии элемента на странице
 * @author andrmix
 */
public class NotFoundElementException extends Exception {
    private final String MESSAGE = "Элемент не найден";

    /**
     * Возвращает текст ошибки
     * @return текст ошибки
     */
    public String getMESSAGE() {
        return MESSAGE;
    }
}
