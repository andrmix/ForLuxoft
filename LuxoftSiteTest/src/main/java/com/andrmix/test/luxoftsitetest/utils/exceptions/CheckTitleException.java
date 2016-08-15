package com.andrmix.test.luxoftsitetest.utils.exceptions;

/**
 * Исключение при несовпадении title сайта с заданным
 * @author andrmix
 */
public class CheckTitleException extends Exception {
    private final String MESSAGE = "Не верный сайт";

    /**
     * Возвращает текст ошибки
     * @return текст ошибки
     */
    public String getMESSAGE() {
        return MESSAGE;
    }
}
