package com.ia.utils;

import java.text.SimpleDateFormat;

import com.ia.entity.Data;

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
				System.out.println(datas[i + 1]);
				data.setFarmNum(datas[i + 1]);
				System.out.println(datas[i + 2]);
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
				System.out.println(datas[i + 3]);
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
