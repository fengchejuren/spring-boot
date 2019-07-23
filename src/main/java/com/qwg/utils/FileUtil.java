package com.qwg.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/22.
 */
public class FileUtil {

    /**
     * 合并文件
     * update by wangyue 2019年7月22日10:32:37
     */
    public static Map<String,Object> mergeFile(String filePath,File... files){
        Map<String,Object> map = new HashMap<String,Object>();
        File finalFile = new File("/web/quanwugou/tomcat/res/"+filePath);
        int bufSize = 1024;
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(finalFile));
            byte[] buffer = new byte[bufSize];
            for(File f: files){
                if(f==null || !f.exists() || f.isDirectory()){
                    continue;
                }
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(f));
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.flush();
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            map.put(AppConstants.code,AppConstants.fail);
            map.put(AppConstants.msg,"文件合并失败,请联系管理员");
            return map;
        }
        for(File f: files){
            if(f != null && f.exists()){
                f.delete();
            }
        }
        map.put(AppConstants.code,AppConstants.success);
        map.put(AppConstants.msg,"文件合并成功");
        map.put("fileName",finalFile.getName());
        return map;
    }

}
