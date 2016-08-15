package com.andrmix.test.luxoftsitetest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


/**
 * Класс для работы с XLS
 * @author andrmix
 */
public class XlsParser {

    /**
     * Парсинг XLS-файла
     * @param name путь к файлу
     * @return коллекция элементов теста
     */
    public static List<TestUnit> parse(String name) {

        List<TestUnit> result = new ArrayList<TestUnit>();
        InputStream in = null;
        HSSFWorkbook wb = null;
        try {
            in = new FileInputStream(name);
            wb = new HSSFWorkbook(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();

        if (it.hasNext()) {
            it.next();
        }

        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            TestUnit testUnit = new TestUnit();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellNumber = cell.getColumnIndex();
                switch (cellNumber) {
                    case 0:
                        testUnit.setOperation(cell.getStringCellValue());
                        break;
                    case 1:
                        testUnit.setCode(cell.getStringCellValue());
                        break;
                    case 2:
                        testUnit.setText(cell.getStringCellValue());
                        break;
                    case 3:
                        testUnit.setParam(cell.getStringCellValue());
                        break;
                    case 4:
                        testUnit.setWait(cell.getStringCellValue());
                        break;
                    case 5:
                        testUnit.setActionOnError(cell.getStringCellValue());
                        break;
                    default:
                        break;
                }
            }
            result.add(testUnit);
        }

        return result;
    }
}
