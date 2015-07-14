package com.misproto.modules.global.service;

import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;

/****
 * 对图片文件进行压缩的压缩参数
 * 
 * @author NBQ
 *
 */
public class ThumbParam {

	private int width; // 宽
	private int height; // 高
	private String thumbParmPath; // 压缩文件的生成路径
	private ThumbType thumbType; // 压缩裁剪类型

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

	public String getThumbParmPath() {
		return thumbParmPath;
	}

	public void setThumbParmPath(String thumbParmPath) {
		this.thumbParmPath = thumbParmPath;
	}

	/****
	 * 取该压缩比对应的目录名
	 * 
	 * @return
	 */
	public String getFolderName() {
		return width + "x" + height;
	}

	public ThumbType getThumbType() {
		return thumbType;
	}

	public void setThumbType(ThumbType thumbType) {
		this.thumbType = thumbType;
	}
	
	public void setThumbType(String thumbType) {
		
		if (StringUtil.isEmpty(thumbType)){
			this.thumbType = ThumbType.COMPRESS_W_H_INNER;
		}
		
		if (thumbType.equals(ThumbType.COMPRESS_CAIJIAN.getCode())){
			this.setThumbType(ThumbType.COMPRESS_CAIJIAN);
		}else if (thumbType.equals(ThumbType.NO_COMPRESS_CAIJIAN.getCode())){
			this.setThumbType(ThumbType.NO_COMPRESS_CAIJIAN);
		}else if (thumbType.equals(ThumbType.COMPRESS_W_H_INNER.getCode())){
			this.setThumbType(ThumbType.COMPRESS_W_H_INNER);
		}else{
			this.setThumbType(ThumbType.COMPRESS_W_H_INNER);
		}
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonStr(this);
	}

}
