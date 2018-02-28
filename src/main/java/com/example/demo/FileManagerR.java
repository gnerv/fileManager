package com.example.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 */
public class FileManagerR extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	private Map<String,Object> data=new HashMap<String,Object>();
	public FileManagerR() {
		put("code", 0);
		put("msg", "");
		put("data", data);
	}
	
	public static FileManagerR error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static FileManagerR error(String msg) {
		return error(500, msg);
	}
	
	public static FileManagerR error(int code, String msg) {
		FileManagerR r = new FileManagerR();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static FileManagerR ok(String msg) {
		FileManagerR r = new FileManagerR();
		r.put("msg", msg);
		return r;
	}
	
	public static FileManagerR ok(Map<String, Object> map) {
		FileManagerR r = new FileManagerR();
		r.putAll(map);
		return r;
	}
	
	public static FileManagerR ok() {
		return new FileManagerR();
	}

	public FileManagerR put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public FileManagerR data(String key,Object value){
		data.put(key, value);
		return this;
	}
	public FileManagerR data(Map<String,Object> map){
		data.putAll(map);
		return this;
	}

}
