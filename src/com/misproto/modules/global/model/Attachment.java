package com.misproto.modules.global.model;

import java.util.List;

import mou.mongodb.MongoObjectId;

import org.mou.common.StringUtil;

import com.misproto.modules.global.service.ThumbParam;

/****
 * 全局附件
 * 
 * @author NBQ
 *
 */
public class Attachment {

	private MongoObjectId _id;// mongo主键

	private String _id_m;

	// 上传日期
	private String uploadDate;

	// 上传日期
	private String uploadTime;

	// 上传目录
	private String uploadDir;

	// 上传文件的绝对路径
	private String uploadFileAbsolutePath;

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

	// 该附件是否是主页图片
	private String isIndexPic;

	// 该附件是否是图片类型,0表示不是，1表示是
	private String isImg;

	// 附件归属id
	private String ownerid;

	// 是否是附件信息，0表示不是，1表示是，如果是附件信息就在文章的附件栏进行显示
	private String isAttach;

	// 压缩信息
	private List<ThumbParam> thumb_info;

	// 压缩后的文件存储目录
	private String compressedDir;

	// 150x120压缩图片的路径
	private String name150x120;

	public String getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}

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

	public String getIsIndexPic() {
		return isIndexPic;
	}

	public void setIsIndexPic(String isIndexPic) {

		if (StringUtil.isEmpty(isIndexPic)) {
			this.isIndexPic = "0";
			return;
		}

		if (isIndexPic.equals("1")) {
			this.isIndexPic = "1";
		} else {
			this.isIndexPic = "0";
		}
	}

	public String getIsImg() {
		return isImg;
	}

	public void setIsImg(boolean isImg) {
		if (isImg) {
			this.isImg = "1";
		} else {
			this.isImg = "0";
		}
	}

	public String getIsAttach() {
		return isAttach;
	}

	public String getCompressedDir() {
		return compressedDir;
	}

	public void setCompressedDir(String compressedDir) {
		this.compressedDir = compressedDir;
	}

	public void setIsAttach(String isAttach) {

		if (StringUtil.isEmpty(isAttach)) {
			this.isAttach = "0";
			return;
		}

		if (isAttach.equals("1")) {
			this.isAttach = "1";
		} else {
			this.isAttach = "0";
		}
	}

	public String getName150x120() {
		return name150x120;
	}

	public void setName150x120(String name150x120) {
		this.name150x120 = name150x120;
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

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
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

	public List<ThumbParam> getThumb_info() {
		return thumb_info;
	}

	public void setThumb_info(List<ThumbParam> thumb_info) {
		this.thumb_info = thumb_info;
	}

	public String getUploadFileAbsolutePath() {
		return uploadFileAbsolutePath;
	}

	public void setUploadFileAbsolutePath(String uploadFileAbsolutePath) {
		this.uploadFileAbsolutePath = uploadFileAbsolutePath;
	}

}
