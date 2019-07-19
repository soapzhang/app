package com.soap.app.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件处理的工具类
 */
public class AppFileUtils {

    /**
     * 文件下载，使用spring mvc的包装的api
     *
     * @param fileName
     * @param file
     * @return
     * @throws IOException
     */
    public static ResponseEntity<byte[]> downloadFile(String fileName, File file) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        //下载显示的文件名，解决中文名称乱码问题
        String dfileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        //application/octet-stream:二进制流数据（最常见的文件下载）
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //通知浏览器以attachment（下载方式）打开图片
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    /**
     * 文件下载的传统方式
     * @param file
     * @param response
     * @throws IOException
     */
    public static void downloadFileByCustom(File file, HttpServletResponse response) throws IOException {
        if (file == null) {
            return;
        }
        InputStream fin = null;
        OutputStream out = null;
        try {
            fin = new FileInputStream(file);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes(), "iso-8859-1"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            out = response.getOutputStream();
            byte[] buffer = new byte[2014];
            int read = -1;
            while ((read = fin.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件转为2进制数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filePath) throws IOException {
        File f = new File(filePath);
        return toByteArray2(f);
    }

    public static byte[] toByteArray2(File f) throws IOException {
        if (!f.exists()) {
            throw new FileNotFoundException();
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
