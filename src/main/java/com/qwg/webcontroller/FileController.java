package com.qwg.webcontroller;

import com.qwg.services.UserService;
import com.qwg.utils.AppConstants;
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
import java.util.Map;
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
        Map<String,Object> map = new HashMap<String,Object>();
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("/web/quanwugou/tomcat/res/"+file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
                map.put(AppConstants.code,AppConstants.state_process);
                map.put(AppConstants.msg,"成功上传第"+index+"个子文件!");
                if("1".equals(finished)){
                    try {
                        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
                        File[] files = new File[index+1];
                        for(int i=0; i<=index; i++){
                            File f = new File("/web/quanwugou/tomcat/res/"+fileName+".part"+i);
                            while (!f.exists() || !f.canRead()){
                                TimeUnit.MILLISECONDS.sleep(500);
                            }
                            files[i] = f;
                        }
                        map = FileUtil.mergeFile(fileName,files);
                    } catch (Exception e) {
                        e.printStackTrace();
                        map.put(AppConstants.code,AppConstants.fail);
                        map.put(AppConstants.msg,"文件合并失败,请联系管理员!");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put(AppConstants.code,AppConstants.fail);
                map.put(AppConstants.msg,"文件合并失败,请联系管理员!");
            } catch (IOException e) {
                e.printStackTrace();
                map.put(AppConstants.code,AppConstants.fail);
                map.put(AppConstants.msg,"文件合并失败,请联系管理员!");
            }
            return map;
        } else {
            map.put(AppConstants.code,AppConstants.fail);
            map.put(AppConstants.msg,"上传了一个空的文件");
            return map;
        }
    }
}
