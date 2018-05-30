package com.ia.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SendDataSocket {

	/**
	 * 模拟底层向服务器发送数据
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		try {
			
		
		Socket socket = new Socket("127.0.0.1", 20000);
		// 向服务端程序发送数据
		OutputStream ops = socket.getOutputStream();
		OutputStreamWriter opsw = new OutputStreamWriter(ops);
		BufferedWriter bw = new BufferedWriter(opsw);

		bw.write("hello world\r\n\r\n");
		bw.flush();

		
		 //从服务端程序接收数据
		 InputStream ips = socket.getInputStream();
		 InputStreamReader ipsr = new InputStreamReader(ips);
		 BufferedReader br = new BufferedReader(ipsr);
		 String s = "";
		 while((s = br.readLine()) != null)
		 System.out.println(s);
		 //socket.close();
		} catch (Exception e) {
			System.out.println("请求服务器失败");
		}
	}

}
