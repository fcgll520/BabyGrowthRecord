package com.baby.babygrowthrecord.Circle;


public class Circle {
	public int id;
	public int friend_id;
	public String avator;
	public String name;
	public String content;
	public String time;
	public String[] urls;

	public Circle() {

	}

	public Circle(String avator) {
		this.avator = avator;
	}

	//发布照片的动态
	public Circle(int id, String avator, String name, String content, String[] urls) {
		this.id = id;
		this.avator = avator;
		this.name = name;
		this.content = content;
		this.urls = urls;
	}
	//没有发布照片的动态
	public Circle(int id, String avator, String name, String content) {
		this.id = id;
		this.avator = avator;
		this.name = name;
		this.content = content;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getAvator() {
		return avator;
	}
}
