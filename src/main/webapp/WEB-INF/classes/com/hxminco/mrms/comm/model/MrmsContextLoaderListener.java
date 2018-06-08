package com.hxminco.mrms.comm.model;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by spiderman on 2018/2/27.
 */
public class MrmsContextLoaderListener extends ContextLoaderListener {

    /**
     * 在项目正常启动之前初始化之后添加了对项目下考试图片目录是否为空的判断,如果为空则从备份的目录(本地Tomcat下的examImg)复制过来
     * 这个主要是为了项目在更新时项目下的examImg会清除,此时就需要将备份的图片复制过来.
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        String path = context.getRealPath("/")+ "/res/examImg";
        File file = new File(path);
        String bakPath = "D:\\MyWork\\Tomcat8\\examImg";
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            File home = new File(bakPath);
            if (home != null && home.exists() && home.isDirectory()) {
                if(home.listFiles() != null && home.listFiles().length >0){
                    try {
                        FileUtils.copyDirectoryToDirectory(home, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
