package com.andrmix.test.luxoftsitetest.utils.report;

import com.andrmix.test.luxoftsitetest.utils.TestUnit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс генерируемого отчета в html
 * @author andrmix
 */
public class Report {

    private List<ReportLine> lines;

    /**
     * Инициализация
     */
    public Report() {
        lines = new ArrayList<ReportLine>();
    }

    /**
     * Добавление строки отчета
     * @param start время начала теста элемента
     * @param end время окончания теста элемента
     * @param testUnit элемент теста
     * @param result результат теста элемента
     * @param error описание ошибки
     * @param attachment вложение
     */
    public void addLine(Date start, Date end, TestUnit testUnit, String result, String error, String attachment) {
        lines.add(new ReportLine(start, end, testUnit, result, error, attachment));
    }

    /**
     * Генерация отчета
     * @param fileName путь к файлу отчета для его создания
     */
    public void generate(String fileName) {
        File file = new File(fileName);
        int i = 0;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print("<html>"
                        + "<head><title>[ отчет ]</title>"
                        + "<style type='text/css'>"
                        + " body { background:  #F0F8FF; margin: 0; } "
                        + " #panel { background: white; margin: auto; width: 70%; text-align: center; box-shadow: 0 0 10px rgba(0,0,0,0.5); }"
                        + ".report_title{ font-size:40px; text-align: left; padding:20px } "
                        + ".report_table{ margin:0; font-size: 17px; border-collapse: collapse; text-align: center; width: 100%; }"
                        + ".report_table th{ background: #483D8B; color: white; padding: 10px 20px; }"
                        + ".report_table td{ border-style: solid; border-width: 0 2px 2px 0; border-color: white; padding: 10px; }"
                        + ".err { color: red; }"
                        + ".report_table tbody tr:nth-child(odd) { background: #F0F8FF; }"
                        + ".image{ height: 60%; }"
                        + "</style></head><body>"
                        + "<div id='panel'>"
                        + "<div class='report_title'>[ отчет ]</div>"
                        + "<table class='report_table'>"
                        + "<tr><thead><th>#</th><th>Начало</th><th>Конец</th><th>Действие</th><th>Параметр</th><th>Статус</th><th>!</th></tr></thead><tbody>");

                for (ReportLine reportLine : lines) {
                    if (reportLine.getResult().equals("ERROR")) {
                        out.print("<tr class='err'>");
                    } else {
                        out.print("<tr>");
                    }
                    out.print("<td>" + ++i + "</td>");
                    out.print("<td>" + reportLine.getStartTime() + "</td>");
                    out.print("<td>" + reportLine.getEndTime() + "</td>");
                    out.print("<td>" + reportLine.getTestUnit().getText() + "</td>");
                    out.print("<td>" + reportLine.getTestUnit().getParam() + "</td>");
                    out.print("<td>" + reportLine.getResult() + "</td>");
                    out.print("<td>" + reportLine.getError() + "</td>");
                    out.print("</tr>");

                    if (!reportLine.getAttachment().isEmpty()) {
                        out.print("<tr>");
                        out.print("<td colspan='7'>"
                                + "<img class='image'"
                                + " src=" + reportLine.getAttachment() + "></td>");
                        out.print("</tr>");
                    }
                }

                out.print("</tbody></table></div></body></html>");
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
