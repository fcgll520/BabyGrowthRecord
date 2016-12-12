package com.baby.babygrowthrecord.Circle;


public class Circle {
	public int id;
	public String avator;
	public String name;
	public String content;
	public String time;
	public String[] urls;

	public Circle(int id, String avator, String name, String content, String[] urls) {
		this.id = id;
		this.avator = avator;
		this.name = name;
		this.content = content;
		this.urls = urls;
	}
	public Circle(int id, String avator, String name, String content) {
		this.id = id;
		this.avator = avator;
		this.name = name;
		this.content = content;
	}
}
