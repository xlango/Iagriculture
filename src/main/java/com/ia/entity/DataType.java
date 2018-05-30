package com.ia.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="datatype")
public class DataType {

	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private int id;//主键
	private String typeName;//类型名
	private Double fzStart;//阀值最小值
	private Double fzEnd;//阀值最大值
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="typeName",nullable=false,length=50)
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name="fzStart",nullable=false,length=50)
	public Double getFzStart() {
		return fzStart;
	}
	public void setFzStart(Double fzStart) {
		this.fzStart = fzStart;
	}
	
	@Column(name="fzEnd",nullable=false,length=50)
	public Double getFzEnd() {
		return fzEnd;
	}
	public void setFzEnd(Double fzEnd) {
		this.fzEnd = fzEnd;
	}
	
	
}
