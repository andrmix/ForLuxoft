package com.andrmix.test.luxoftsitetest.utils;

import com.andrmix.test.luxoftsitetest.utils.exceptions.NotFoundElementException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.IOException;

/**
 * Класс для работы с PDF
 * @author andrmix
 */
public class PdfScaner {

    /**
     * Проверка присутствия строки
     * @param filePath путь к файлу PDF
     * @param searchTxt искомая строка
     * @return true при нахождении строки в файле, false при ее отсутсвии
     * @throws NotFoundElementException 
     */
    public boolean haveString(String filePath, String searchTxt) throws NotFoundElementException {
        boolean result = false;
        String txt;
        try {
            txt = parse(filePath);
        } catch (IOException ex) {
            throw new NotFoundElementException();
        }

        if (txt.contains(searchTxt)) {
            result = true;
        }
        return result;
    }
    
    /**
     * Парсинг PDF-файла
     * @param filename путь к файлу
     * @return текст файла (String)
     * @throws IOException 
     */
    private String parse(String filename) throws IOException {
        String txt;
        PdfReader reader = new PdfReader(filename);
        txt = PdfTextExtractor.getTextFromPage(reader, 1);
        reader.close();
        return txt;
    }
}
