package com.misproto.modules.global.model;

import mou.mongodb.util.JsonUtil;

/****
 * 调整的图片的信息
 * 
 * @author NBQ
 *
 */
public class ResizedPic {

	private String modelsize;// 型号 width_height;
	private int width;
	private int height;
	private String resizedPath;

	public String getResizedPath() {
		return resizedPath;
	}

	public void setResizedPath(String resizedPath) {
		this.resizedPath = resizedPath;
	}

	public String getModelsize() {
		return modelsize;
	}

	public void setModelsize(String modelsize) {
		this.modelsize = modelsize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return JsonUtil.toJsonStr(this);
	}
}
