package com.ia.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import com.ia.entity.Data;
import com.ia.utils.DataUtil;
import com.sun.prism.paint.Stop;

public class ZhnyServer {

	private static ListenTask listenTask;// 服务线程
	private static Thread listen;// 监听接收所有请求的socket并处理
	public static ConcurrentHashMap<String, ClientConnection> Hmap = new ConcurrentHashMap<String, ClientConnection>();// 存放硬件名及相应连接

	private ZhnyServer() throws IOException, InterruptedException {
		listenTask = new ListenTask(Hmap);
		listen = new Thread(listenTask);
		listen.start();
	}

	/**
	 * 功能：开启服务
	 * 
	 * @return 开启成功/失败
	 */
	public static boolean startServer() {
		try {
			new ZhnyServer();
			return true;
		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

	/**
	 * 功能：关闭服务
	 * 
	 */
	public static void stopServer() {
		listenTask.isRun = false;
		listen.stop();
		System.gc();
	}

	/**
	 * 功能：写入指令
	 * 
	 * @param macName
	 *            硬件名（HashMap中的key）
	 * @param Order
	 *            指令
	 * @return 成功 连接中/失败 连接已断开或从未连接过
	 */
	public static boolean writeOrder(String macName, String order) {
		System.out.println("开始发送数据" + Hmap);
		if (Hmap.containsKey(macName)) {
			System.out.println("mac匹配成功");
			ClientConnection ccon = ZhnyServer.Hmap.get(macName);
			DataOutputStream ou;
			try {
				ou = new DataOutputStream(ccon.socket.getOutputStream());
				String[] orders = order.split(" ");
				System.out.println("数据长度" + orders.length);
				byte send[] = new byte[orders.length];
				int i = 0;
				for (String str : orders) {
					send[i] = (byte) Integer.parseInt(str, 16);
					i++;
				}
				ou.write(send);
				System.out.println("发送完成");
				return true;
			} catch (IOException e) {
				ZhnyServer.Hmap.get(macName).stopClientThread();// 关闭客户端线程
				ZhnyServer.Hmap.remove(macName);// 连接已断开，移除Hmap中的连接
				System.out.println("已移除客户端：" + macName + "!");
				return false;
			}
		} else
			return false;

	}

	/**
	 * 功能：写入指令,返回一个数据检查设备状态
	 * 
	 * @param macName
	 *            硬件名（HashMap中的key）
	 * @param Order
	 *            指令
	 * @return 成功 连接中/失败 连接已断开或从未连接过
	 */
	public static String devState(String macName, String order) {
		System.out.println("开始发送数据" + Hmap);
		if (Hmap.containsKey(macName)) {
			System.out.println("mac匹配成功");
			ClientConnection ccon = ZhnyServer.Hmap.get(macName);
			DataOutputStream ou;
			InputStream in = null;// 接收socket的输入流
			byte[] buff;// 存放socket输入流中的数据
			String returnData = "";// 硬件发过来的真实数据
			try {
				ou = new DataOutputStream(ccon.socket.getOutputStream());
				String[] orders = order.split(" ");
				System.out.println("数据长度" + orders.length);
				byte send[] = new byte[orders.length];
				int i = 0;
				for (String str : orders) {
					send[i] = (byte) Integer.parseInt(str, 16);
					i++;
				}
				ou.write(send);
				System.out.println("发送完成");
				in = ccon.socket.getInputStream();
				buff = new byte[in.available()];
				if (buff.length > 0) {
					int size = in.read(buff); // size 是读取到的字节数
					returnData = bytesToHex(buff, 0, size, " ");
					System.out.println("状态检测返回数据：" + returnData);
				}
				return returnData;
			} catch (IOException e) {
				ZhnyServer.Hmap.get(macName).stopClientThread();// 关闭客户端线程
				ZhnyServer.Hmap.remove(macName);// 连接已断开，移除Hmap中的连接
				System.out.println("已移除客户端：" + macName + "!");
				return null;
			}
		} else
			return null;

	}

	/**
	 * 将 byte 数组转化为十六进制字符串
	 *
	 * @param bytes
	 *            byte[] 数组
	 * @param begin
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @return byte 数组的十六进制字符串表示
	 */
	private static String bytesToHex(byte[] bytes, int begin, int end, String divChar) {
		System.out.println("byte[]:" + bytes.length + "begin:" + begin + "end:" + end);
		StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
		for (int i = begin; i < end; i++) {
			hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
			hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			hexBuilder.append(divChar); // 加一个空格将每个字节分隔开
		}
		return hexBuilder.toString().toUpperCase();
	}

	/**
	 * 功能：测试使用
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// startServer();
		stopServer();
	}
}

class ListenTask implements Runnable {
	private Socket socket; // 用于接受accept方法返回的socket
	private ServerSocket serverSocket;// 服务器端的socket
	private static int count = 0;// 记录客户端数量

	private ConcurrentHashMap<String, ClientConnection> Hmap;// 存放硬件名及相应连接
	public boolean isRun = true;

	public ListenTask(ConcurrentHashMap<String, ClientConnection> hmap2) {
		this.Hmap = hmap2;
	}

	public void run() {
		DataOutputStream ou;// 发送socket的输出流
		try {
			serverSocket = new ServerSocket(8020);
			System.out.println("123");
			while (isRun) {
				socket = serverSocket.accept();
				byte sendb[] = { (byte) 0xef, (byte) 0x00, (byte) 0xee, (byte) 0xff };
				ou = new DataOutputStream(socket.getOutputStream());
				ou.write(sendb);
				String macName = "Mac" + ++count;// 服务器给用户暂定硬件名
				// String macName = "00";
				ClientConnection ccon = new ClientConnection(socket, macName);

				Hmap.put(macName, ccon);// 将该客户端连接加入哈希map

				System.out.println(macName + "(TemporaryName) join success! Now,size of HashMap：" + Hmap.size() + "\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}