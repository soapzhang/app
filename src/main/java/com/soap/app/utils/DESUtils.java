package com.soap.app.utils;


import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtils {

    /**
     * 聚光科技杭州公司
     */
    private final static String DES_PASSWORD="JGKJHZGS";
    /**
     * 加密
     * @param datasource byte[]
     * @return byte[]
     */
    public static  byte[] encrypt(byte[] datasource) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(DES_PASSWORD.getBytes("utf-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(datasource);
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param src byte[]
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(DES_PASSWORD.getBytes("utf-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        return cipher.doFinal(src);
    }


    public static String enBase64AndDES(String src){
        Base64 base64 = new Base64();
        try {
            byte[] encrypt = encrypt(src.getBytes("utf-8"));
            return new String(base64.encode(encrypt),"utf-8");
        }catch (Exception e){
            logger.error("Des加密过程异常:{}",e.getMessage());
        }
        return "";
    }
    public static String deBase64AndDES(String des){
        Base64 base64 = new Base64();
        try {
            byte[] decode = base64.decode(des.getBytes("utf-8"));
            return new String(decrypt(decode));
        }catch (Exception e){
            logger.error("Des解密过程异常:{}",e.getMessage());
        }
        return "";
    }

    private static Logger logger = LoggerFactory.getLogger(DESUtils.class);
}
