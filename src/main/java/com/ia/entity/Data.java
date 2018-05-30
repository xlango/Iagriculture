package com.ia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="data")
public class Data {

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private int id;//主键
	private String farmNum;//区域号
	private int typeId;//类型号,关联类型表
	private String devNum;//设备号
	private String createTime;//获取数据时间
	private double data;//光照强度、大气压、水分、空气温湿度、土壤温度、液位、雨水检测、二氧化碳浓度

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="farmNum",nullable=false,length=50)
	public String getFarmNum() {
		return farmNum;
	}
	public void setFarmNum(String farmNum) {
		this.farmNum = farmNum;
	}
	
	@Column(name="typeId",nullable=false,length=50)
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	@Column(name="devNum",nullable=false,length=50)
	public String getDevNum() {
		return devNum;
	}
	public void setDevNum(String devNum) {
		this.devNum = devNum;
	}
	
	@Column(name="data",nullable=false,length=50)
	public double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	
	@Column(name="createTime",nullable=false,length=50)
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
