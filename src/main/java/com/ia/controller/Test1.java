package com.ia.controller;

import org.junit.Test;

import com.aliyuncs.exceptions.ClientException;
import com.ia.utils.SendMsg;

public class Test1 {

	//@Test
	public  void test() {
		try {
			SendMsg.sendSms("17341930058", "向元浪", "50");
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String arg[]) {
		int i = 5;
		do {
		System.out.print(i);
		} while (--i>5);
		System.out.println("Finished");
		}
}
