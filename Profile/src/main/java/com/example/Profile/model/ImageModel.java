package com.example.Profile.model;


import org.springframework.data.annotation.Id;
public class ImageModel {
	@Id
	private String id;
	private String name;
	private String type;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
	private byte[] picByte;
	
	public ImageModel() {
		super();
	}
	public ImageModel(String name, String type, byte[] picByte) {
		//this.id=(long)1;
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

}

