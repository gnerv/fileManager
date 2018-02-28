package com.example.demo;

public class FileManagerModel {
	private String uuid;
	private String name;
	private String path;
	private String md5;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return "FileManagerModel [uuid=" + uuid + ", name=" + name + ", path=" + path + ", md5=" + md5 + "]";
	}

}
