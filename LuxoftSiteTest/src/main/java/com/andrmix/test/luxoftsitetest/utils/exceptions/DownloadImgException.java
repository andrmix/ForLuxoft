package com.andrmix.test.luxoftsitetest.utils.exceptions;

/**
 * Исключение при загрузке картинок с сайта
 * @author andrmix
 */
public class DownloadImgException extends Exception{
    private final String MESSAGE = "Ошибка при загрузке файла";

    /**
     * Возвращает текст ошибки
     * @return текст ошибки
     */
    public String getMESSAGE() {
        return MESSAGE;
    }
    
}
