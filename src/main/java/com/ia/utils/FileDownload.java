package com.ia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownload extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取文件下载路径
    	//获取根路径
    	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String filename = "app-debug.apk"; 
        File file = new File(path + filename);

        if(file.exists()){
            //设置相应类型application/octet-stream
            resp.setContentType("application/x-msdownload");
            //设置头信息                 Content-Disposition为属性名  附件形式打开下载文件   指定名称  为 设定的filename
            resp.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            //输入流写入输出流
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream ouputStream = resp.getOutputStream();
            byte b[] = new byte[1024];
            int n ;
            //循环读取 !=-1读取完毕
            while((n = inputStream.read(b)) != -1){
                //写入到输出流 从0读取到n
                ouputStream.write(b,0,n);
            }
            //关闭流、释放资源
            ouputStream.close();
            inputStream.close();


        }else{
            req.setAttribute("errorResult", "文件不存在下载失败！");
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/01.jsp");
            dispatcher.forward(req, resp);
        }


    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req,resp);
    }

}