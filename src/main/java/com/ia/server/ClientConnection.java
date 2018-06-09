package com.ia.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ia.entity.Data;
import com.ia.utils.DataUtil;
import com.ia.utils.SendMsg;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ClientConnection {

	public Socket socket;
	private Thread connectThread;
	private RunThread runThread;

	public ClientConnection(Socket socket, String macName) throws IOException {
		this.socket = socket;

		runThread = new RunThread(socket, macName);
		connectThread = new Thread(runThread);
		connectThread.start();
	}

	public void stopClientThread() {
		runThread.isRun = false;
	}

}

class RunThread implements Runnable {

	private Socket socket;
	private String macName;// 硬件暂定名
	public boolean isRun = true;

	public RunThread(Socket socket, String macName) {
		this.socket = socket;
		this.macName = macName;
	}

	public void run() {

		String MacName = "";// 硬件发过来的真实硬件名
		String realyData = "";// 硬件发过来的真实数据
		InputStream in = null;// 接收socket的输入流
		DataOutputStream ou;// 发送socket的输出流
		byte[] buff;// 存放socket输入流中的数据
		boolean isfirst = true;// 标志位，是否是用户第一次发送的数据
		try {

			while (isfirst) {
				in = socket.getInputStream();
				buff = new byte[in.available()];
                //System.out.println("mac长度："+buff.length);
               
                if (buff.length>0) {
					// MacName = new String(buff);
					//DataInputStream dis = new DataInputStream(in);
                	
					//byte[] bytes = new byte[1024]; // 假设发送的字节数不超过 1024 个
					int size = in.read(buff); // size 是读取到的字节数
					MacName = bytesToHex(buff, 0, size, "");

					ClientConnection ccon = ZhnyServer.Hmap.get(macName);// 根据暂定硬件名获取对应的连接

					// 在HashMap中将之前的暂定名换为真实名
					ZhnyServer.Hmap.remove(macName);
					ZhnyServer.Hmap.put(MacName, ccon);
					System.out.println(
							"In HashMap," + macName + "(TemporaryName) is replaced to " + MacName + "(RealName)\n");
					byte send[] = { (byte) 0xef, (byte) 0xee, (byte) 0xfe };
					ou = new DataOutputStream(socket.getOutputStream());
					ou.write(send);

					isfirst = false;					
				}               
				Thread.sleep(100);
			}

			

			while (isRun) {
				in = socket.getInputStream();
				buff = new byte[in.available()];
				if (buff.length>0) {
					int size = in.read(buff); // size 是读取到的字节数
					realyData = bytesToHex(buff, 0, size, " ");
					Data d = DataUtil.analysisData(realyData);
					//System.out.println("真实数据類型：" + d.getTypeId());
					alert(d);
					add(d);//添加一条数据
					System.out.println("真实数据：" + realyData);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	private String bytesToHex(byte[] bytes, int begin, int end, String divChar) {
		System.out.println("byte[]:"+bytes.length+"begin:"+begin+"end:"+end);
		StringBuilder hexBuilder = new StringBuilder(2 * (end - begin));
		for (int i = begin; i < end; i++) {
			hexBuilder.append(Character.forDigit((bytes[i] & 0xF0) >> 4, 16)); // 转化高四位
			hexBuilder.append(Character.forDigit((bytes[i] & 0x0F), 16)); // 转化低四位
			hexBuilder.append(divChar); // 加一个空格将每个字节分隔开
		}
		return hexBuilder.toString().toUpperCase();
	}

	
	
	/**
	 * 插入操作
	 * */
    public static int add(Data data) {
        int i=0;
        String sql="insert into data (id,farmNum,createTime,data,devNum,typeId) values (?,?,?,?,?,?)";
        DBConnection db = new DBConnection();
        try {        
            PreparedStatement preStmt = (PreparedStatement) db.conn.prepareStatement(sql);
            preStmt.setString(1, null);
            preStmt.setString(2, data.getFarmNum());
            preStmt.setString(3, data.getCreateTime());
            preStmt.setDouble(4, data.getData());
            preStmt.setString(5, data.getDevNum());
            System.out.println("typeId:"+data.getTypeId());
            preStmt.setInt(6, data.getTypeId());
            preStmt.executeUpdate();
            
            preStmt.close();
            db.close();//关闭连接 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;//返回影响的行数，1为执行成功
    }
    
    /**
	 * 警报
	 * */
    public static void alert(Data data) {
        Statement stmt = null;
        String sql=" select * from  datatype where id="+data.getTypeId();
        DBConnection db = new DBConnection();
        try {        
        	 stmt =db.conn.createStatement(); 
        	 ResultSet rs = stmt.executeQuery(sql);
             
             // 展开结果集数据库
             while(rs.next()){
                 // 通过字段检索
                 Double fzmin = rs.getDouble("fzStart");
                 Double fzmax = rs.getDouble("fzEnd");
     
                 // 输出数据
                 System.out.print("最小值: " + fzmin);
                 System.out.print("最大值: " + fzmax);
                 
                 //发送报警短信
                 SendMsg.sendSms("17341930058", "向元浪", "50");
             }
             // 完成后关闭
             rs.close();
             stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * 连接数据库
 */
class DBConnection {

    String driver = "com.mysql.jdbc.Driver";
    String url= "jdbc:mysql:///iagriculture";
    String user = "root";
    String password = "123456";
    
    public Connection conn;

    public DBConnection() {

        try {
            Class.forName(driver);// 加载驱动程序
            conn = (Connection) DriverManager.getConnection(url, user, password);// 连续数据库
            
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            this.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}