package com.andrmix.test.luxoftsitetest.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Производит поиск файлов в заданной директории по маске
 * @author andrmix
 */
public class FileSearcher implements FilenameFilter {

    String endWith;

    public FileSearcher(String str) {
        endWith = str;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(endWith);
    }

}
