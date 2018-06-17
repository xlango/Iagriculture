package com.ia.utils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import com.ia.entity.Data;
import com.ia.entity.Device;
import com.mysql.jdbc.PreparedStatement;

/**
 * 数据解析类
 * 
 * @author xyl
 *
 */
public class DataUtil {

	// 真实数据解析
	public static Data analysisData(String botData) {
		Data data = new Data();
		String[] datas = botData.split(" ");
		System.out.println("数据："+botData+"数据长度" +datas.length);
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].equals("EE")) {
				data.setFarmNum(datas[i + 1]);
				switch (Integer.valueOf(datas[i + 2])) {
				case 2:
					data.setTypeId(1);
					break;
				case 3:
					data.setTypeId(2);
					break;
				case 4:
					data.setTypeId(3);
					break;
				case 5:
					data.setTypeId(4);
					break;
				case 6:
					data.setTypeId(5);
					break;
				case 7:
					data.setTypeId(6);
					break;
				case 8:
					data.setTypeId(7);
					break;
				case 9:
					data.setTypeId(8);
					break;
				default:
					break;
				}
				data.setDevNum(datas[i + 3]);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				data.setCreateTime(df.format(System.currentTimeMillis()));
				int val = Integer.valueOf(datas[i + 4], 16) * 16 * 16 + Integer.valueOf(datas[i + 5], 16);
				data.setData(val);
				break;
			}
			if((i+1)==datas.length) {
				data= null;
			}
		}
		return data;
	}

	// 真实数据解析
	public static Device analysisOrder(String order) {
		Device device = new Device();
		String[] datas = order.split(" ");
		System.out.println("指令："+order+"指令长度" +datas.length);
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].equals("EF")) {
				device.setFarmId(getIdbyFarmNum(datas[i+1]));
				device.setDevNum(datas[i+2]);
				if(datas[i+3].equals("80")) {
					device.setDevstate(1);
				}else {
					device.setDevstate(0);
				}
				break;
			}
			if((i+1)==datas.length) {
				device= null;
			}
		}
		return device;
	}
	
	// mac地址解析
	public static String analysisMac(String mac) {
		String trueMac=null;
		String[] maclist = mac.split(" ");
		System.out.println("数据长度" + maclist.length);
		for (int i = 0; i < maclist.length; i++) {
			if (maclist[i].equals("AA")) {
				trueMac=maclist[i+1];
			}
		}
		return trueMac;
	}

	
	/**
	 * 根据农场号获取农场ID
	 * */
    public static int getIdbyFarmNum(String farmNum) {
        int id=0;
        Statement stmt = null;
        String sql="select id from farm where farmnum='"+farmNum+"'";
        DBConnection db = new DBConnection();
        try {        
       	 stmt =db.conn.createStatement(); 
       	 ResultSet rs = stmt.executeQuery(sql);
            
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                 id= rs.getInt("id");    
                // 输出数据
                System.out.print("农场id:" + id);              
            }
            // 完成后关闭
            rs.close();
            stmt.close();
       } catch (Exception e) {
            e.printStackTrace();
        }
        return id;//返回影响的行数，1为执行成功
    }
	
	
	public static void main(String[] args) {
		/*
		 * String botData = "00 01 01"; String[] datas = botData.split(" "); byte[]
		 * bdata = null; int v=Integer.valueOf(datas[1], 16); int
		 * v1=Integer.valueOf(datas[2], 16); System.out.println(v*16*16+v1);
		 */

		Data d = analysisData("11 EE FF 02 DD 00 01 EF");
		System.out.println(d.getTypeId());
	}
}
