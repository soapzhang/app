package com.soap.app.utils;

import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;

/**
 * base64图片转换的工具
 */
public class Base64ImgUtils {
    /**
     * base64格式转为图片
     * @param fileType (.png)
     * @return
     */
    public static boolean GenerateImage(String value,String fileName,String fileType) {
        //简单验证
        if (value == null||fileType==null||"".equalsIgnoreCase(fileType.trim())) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(value);
            //项目路径
            String file=null;
            if(fileName!=null){
                file=System.getProperty("user.dir")+"/images/" + fileName + fileType;
            }else{
                file=System.getProperty("user.dir")+"/images/" + System.currentTimeMillis() + fileType;
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
