package com.misproto.modules.global.model;

import java.util.ArrayList;
import java.util.List;

import mou.mongodb.MongoObjectId;

import org.mou.common.JsonUtil;

/****
 * 全局上传文件对象
 * 
 * @author NBQ
 *
 */
public class UploadedFile {

	private MongoObjectId _id;// mongo主键

	private String _id_m;

	// 上传日期
	private String uploadDate;

	// 上传目录
	private String uploadDir;

	// 附件上传之后的名称
	private String newName;

	// 附件的原始名称
	private String oriName;

	// 附件的类型，这个类型和contentType类型一致
	private String type;

	// 附件的后缀名
	private String suffix;

	// 附件的大小
	private long size;

	// 该附件是否是图片类型,0表示不是，1表示是
	private String isImg;

	// 150x120压缩图片的路径
	private String name150x120;

	// 相对于服务器根目录的路径
	private String serverPath;

	// 相对于服务器根目录的缩略图路径
	private String compressedPath;

	// 调整的图片大小的信息
	private List<ResizedPic> resizedPics;

	// 冗余属性，如果图片只有一张调整图片时，该路径表示调整后的图片的路径
	private String resizedPath;

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getOriName() {
		return oriName;
	}

	public void setOriName(String oriName) {
		this.oriName = oriName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName150x120() {
		return name150x120;
	}

	public void setName150x120(String name150x120) {
		this.name150x120 = name150x120;
	}

	public String getIsImg() {
		return isImg;
	}

	public void setIsImg(String isImg) {
		this.isImg = isImg;
	}

	public String get_id_m() {
		return _id_m;
	}

	public void set_id_m(String _id_m) {
		this._id_m = _id_m;
	}

	public MongoObjectId get_id() {
		return _id;
	}

	public void set_id(MongoObjectId _id) {
		this._id = _id;
		set_id_m(_id.toString());
	}

	public void set_id(String _id) {
		MongoObjectId _idNew = new MongoObjectId();
		_idNew.set$oid(_id);
		this._id = _idNew;

		set_id_m(_id);
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getCompressedPath() {
		return compressedPath;
	}

	public void setCompressedPath(String compressedPath) {
		this.compressedPath = compressedPath;
	}

	public List<ResizedPic> getResizedPics() {
		return resizedPics;
	}

	public void setResizedPics(List<ResizedPic> resizedPics) {
		this.resizedPics = resizedPics;
	}

	public String getResizedPath() {
		return resizedPath;
	}

	public void setResizedPath(String resizedPath) {
		this.resizedPath = resizedPath;
	}

	/****
	 * 增加一个修改大小的图片信息
	 * 
	 * @param resizedPic
	 */
	public void addResizedPic(ResizedPic resizedPic) {

		if (this.resizedPics == null) {
			this.resizedPics = new ArrayList<ResizedPic>();
		}

		this.resizedPics.add(resizedPic);
	}

	@Override
	public String toString() {
		return JsonUtil.toJsonStr(this);
	}
}
