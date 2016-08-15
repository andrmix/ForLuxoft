package com.andrmix.test.luxoftsitetest.utils;

/**
 * Класс модели элемента теста
 * @author andrmix
 */
public class TestUnit {
    private String operation;
    private String code;
    private String text;
    private String param;
    private String wait;
    private String actionOnError;

    public TestUnit() {}

    /**
     * Возвращает код операции
     * @return код операции (String)
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Задает код операции
     * @param operation код операции
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Возвращает код поиска элемента
     * @return код поиска элемента (String)
     */
    public String getCode() {
        return code;
    }

    /**
     * Задает код поиска элемента
     * @param code код поиска элемента
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Возвращает текст элемента поиска
     * @return текст элемента поиска (String)
     */
    public String getText() {
        return text;
    }

    /**
     * Задает текст элемента поиска
     * @param text текст элемента поиска
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Возвращает параметр поиска
     * @return параметр поиска (String)
     */
    public String getParam() {
        return param;
    }

    /**
     * Задает параметр поиска
     * @param param параметр поиска
     */
    public void setParam(String param) {
        this.param = param;
    }

    /**
     * Возвращает задержку для элемента в милисекундах
     * @return задержка для элемента в милисекундах
     */
    public String getWait() {
        return wait;
    }

    /**
     * Задает задержку для элемента в милисекундах
     * @param wait задержка для элемента в милисекундах
     */
    public void setWait(String wait) {
        this.wait = wait;
    }
    
    /**
     * Возвращает действие при ошибке (stop или skip)
     * @return действие при ошибке (stop или skip)
     */
    public String getActionOnError() {
        return actionOnError;
    }

    /**
     * Задает действие при ошибке (stop или skip)
     * @param actionOnError действие при ошибке (stop или skip)
     */
    public void setActionOnError(String actionOnError) {
        this.actionOnError = actionOnError;
    }
}
