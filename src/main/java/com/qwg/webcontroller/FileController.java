package com.qwg.webcontroller;

import com.qwg.services.UserService;
import com.qwg.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/6/10.
 */
@Controller
@RequestMapping("file")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private UserService userService;

    @Value("${file.location}")
    private String fileLocation;

    @RequestMapping("toUpload")
    public String toUpload(Model model, String user_id, HttpServletRequest request){
        HashMap user = userService.findById(user_id);
        model.addAttribute("name","张三");
        model.addAttribute("user",user);
        return "fileupload/upload";
    }

    @RequestMapping(value = "/largeFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object largeFileUpload(@RequestParam("file") MultipartFile file, int index,String finished){
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("/web/quanwugou/tomcat/res/"+file.getOriginalFilename())));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
                if("1".equals(finished)){
                    try {
                        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
                        File[] files = new File[index+1];
                        for(int i=0; i<index; i++){
                            File f = new File("/web/quanwugou/tomcat/res/"+fileName+".part"+i);
                            files[i] = f;
                        }
                        FileUtil.mergeFile(fileName,files);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "fileupload/upload";
        } else {
            return "fileupload/upload";
        }
    }
}
