package com.misproto.modules.base;

import mou.mongodb.MongoObjectId;

import org.mou.common.JsonUtil;

/****
 * 所有域模型对象的基础类，包含公用的属性和方法
 * 
 * @author NBQ
 *
 */
public class BaseModel {

	protected MongoObjectId _id;// mongo主键
	protected String collectionname; // 对象对应的collection名字

	protected String cdate; // 创建日期
	protected String ctime; // 创建时间
	protected String cuserid; // 创建用户id
	protected String cusername;// 创建用户姓名
	protected String lmuserid;// 最后编辑用户id
	protected String lmusername;// 最后编辑用户姓名
	protected String lastoptime; // 最后一次操作时间

	protected String delflg; // 删除标志(用于逻辑删除)
	protected String useflg; // 启用标志

	public String getUseflg() {
		return useflg;
	}

	public void setUseflg(String useflg) {
		this.useflg = useflg;
	}

	public String getLastoptime() {
		return lastoptime;
	}

	public void setLastoptime(String lastoptime) {
		this.lastoptime = lastoptime;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCuserid() {
		return cuserid;
	}

	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}

	public String getCusername() {
		return cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getLmuserid() {
		return lmuserid;
	}

	public void setLmuserid(String lmuserid) {
		this.lmuserid = lmuserid;
	}

	public String getLmusername() {
		return lmusername;
	}

	public void setLmusername(String lmusername) {
		this.lmusername = lmusername;
	}

	public String getCollectionname() {
		return collectionname;
	}

	public void setCollectionname(String collectionname) {
		this.collectionname = collectionname;
	}

	public String getDelflg() {
		return delflg;
	}

	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	public String get_id_str() {
		if (_id == null) {
			return null;
		}
		return _id.toString();
	}

	public MongoObjectId get_id() {
		return _id;
	}

	public void set_id(MongoObjectId _id) {
		this._id = _id;
	}

	public void set_id(String _id) {
		MongoObjectId _idNew = new MongoObjectId();
		_idNew.set$oid(_id);
		this._id = _idNew;
	}

	public String toString() {
		return JsonUtil.toJsonStr(this);
	}

}
