package com.soap.app.utils;

import freemarker.template.*;

import java.io.*;
import java.util.Map;

/**
 * json数据转入到模板，并导出成word文档
 */
public class JsonToWordUtils {
    private static Configuration cfg = null;

    static {
        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setDefaultEncoding("utf-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * 模板映射数据并导出成word文档
     * @param temPath 模板文件的路径
     * @param temName 模板文件名（muban.ftl）
     * @param filePath (保存的文件路径 //////XXX.doc)
     * @param dataMap （映射的数据）
     */
    public static File createFile(String temPath, String temName, String filePath, Map dataMap) {
        Template t=null;
        File outFile = new File(filePath);
        Writer out = null;
        try {
            cfg.setDirectoryForTemplateLoading(new File(temPath));
            t = cfg.getTemplate(temName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            t.process(dataMap, out);
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outFile;
    }
}
