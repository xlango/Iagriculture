package com.ia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farm")
public class Farm {

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private int id;
	private String farmname;//农场名
	private String farmnum;//农场编号
	private int model;//情景模式:0为手动，1为自动，默认值1
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="farmname",nullable=false,length=50)
	public String getFarmname() {
		return farmname;
	}
	public void setFarmname(String farmname) {
		this.farmname = farmname;
	}
	
	@Column(name="farmnum",nullable=false,length=50)
	public String getFarmnum() {
		return farmnum;
	}
	public void setFarmnum(String farmnum) {
		this.farmnum = farmnum;
	}
	
	@Column(name="model",nullable=false,length=50)
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	
	
}
