package com.soap.app.controller;


import com.soap.app.utils.AppFileUtils;
import com.soap.app.utils.JsonToWordUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("file")
public class FileController {

    /**
     * 文件创建并下载的实现，使用spring mvc的包装方法
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/export",method = RequestMethod.POST)
    public ResponseEntity<byte[]> exportFile(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", request.getParameter("title"));
        dataMap.put("description", request.getParameter("description"));
        String img = request.getParameter("img");
        img = img.substring(img.indexOf("base64,") + 7);
        dataMap.put("img_1", img);
        String imgName = request.getParameter("imgName");
        //保存图片的实现
//        boolean b = Base64ImgUtils.GenerateImage(img, imgName, ".png");
        File file = JsonToWordUtils.createFile("F:/app/src/main/resources/static/word/", "muban1.ftl",
                "F:/app/src/main/resources/static/word/" + System.currentTimeMillis() + ".doc",
                dataMap);
        try {
            return AppFileUtils.downloadFile("haha.doc", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
