package com.andrmix.test.luxoftsitetest.utils.report;

import com.andrmix.test.luxoftsitetest.utils.TestUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Модель строки отчета
 * @author andrmix
 */
class ReportLine {

    private Date startTime;
    private Date endTime;
    private TestUnit testUnit;
    private String result;
    private String error;
    private String attachment;

    /**
     * Инициализация
     * @param startTime время начала теста элемента
     * @param endTime время окончания теста элемента
     * @param testUnit элемент теста
     * @param result результат теста элемента
     * @param error описание ошибки
     * @param attachment вложение
     */
    public ReportLine(Date startTime, Date endTime, TestUnit testUnit, String result, String error, String attachment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.testUnit = testUnit;
        this.result = result;
        this.error = error;
        this.attachment = attachment;
    }

    /**
     * Возвращает время начала теста элемента
     * @return время начала теста элемента
     */
    public String getStartTime() {
        try {
            return new SimpleDateFormat("HH:mm:ss").format(this.startTime);
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Задает время начала теста элемента
     * @param startTime время начала теста элемента
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Возвращает время окончания теста элемента
     * @return время окончания теста элемента
     */
    public String getEndTime() {
        try {
            return new SimpleDateFormat("HH:mm:ss").format(this.endTime);
        } catch (NullPointerException e) {
            return "";
        }
    }

    /**
     * Задает время окончания теста элемента
     * @param endTime время окончания теста элемента
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Возвращает тело элемента теста
     * @return тело элемента теста
     */
    public TestUnit getTestUnit() {
        return testUnit;
    }

    /**
     * Задает тело элемента теста
     * @param testUnit тело элемента теста
     */
    public void setTestUnit(TestUnit testUnit) {
        this.testUnit = testUnit;
    }

    /**
     * Возвращает результат теста элемента
     * @return результат теста элемента
     */
    public String getResult() {
        return result;
    }

    /**
     * Задает результат теста элемента
     * @param result результат теста элемента
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Возвращает описание ошибки
     * @return описание ошибки
     */
    public String getError() {
        return error;
    }

    /**
     * Задает описание ошибки
     * @param error описание ошибки
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Возвращает вложение
     * @return вложение
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * Задает вложение
     * @param attachment вложение
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

}
