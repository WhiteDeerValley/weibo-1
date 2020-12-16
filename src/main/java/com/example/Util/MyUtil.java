package com.example.Util;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MyUtil {
    public static String createActivateCode(){
        return new Date().getTime() + UUID.randomUUID().toString().replace("-","");
    }

    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        return sdf.format(date);
    }
    public static boolean uploadOneFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            System.out.println("文件为空");
            return false;
        }
        String firstPath = "/Users/adimit/Downloads/spring-seven-db-master/src/main/resources/static/img";
        String fileName = file.getOriginalFilename();  // 文件名
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);

        String newName = UUID.randomUUID().toString().replaceAll("-", "")+'.'+prefix;
        MultipartFile file1 = file;
        File tmpFile = new File(firstPath,newName);
        if(!tmpFile.getParentFile().exists())
        {
            tmpFile.getParentFile().mkdirs();
        }
        file.transferTo(tmpFile);
        request.getSession().setAttribute("imgUrl",newName);
        return true;

    }

}
