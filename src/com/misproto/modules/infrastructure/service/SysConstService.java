package com.misproto.modules.infrastructure.service;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Service;

import com.misproto.common.globalobj.PageVO;
import com.misproto.modules.base.BaseService;
import com.misproto.modules.infrastructure.dao.SysConstDao;
import com.misproto.modules.infrastructure.model.SysConst;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/****
 * 系统常量类型服务实现
 * 
 * @author NBQ
 *
 */
@Service("sysConstService")
public class SysConstService extends BaseService implements ISysConstService {

	@Resource(name = "sysconstdao")
	private SysConstDao sysconstdao;

	private static final Logger logger = LogManager
			.getLogger(SysConstService.class);

	@Override
	public SysConst findOneByIdObject(String _id) {

		return this.sysconstdao.findOneByIdObject(_id, SysConst.class);
	}

	@Override
	public PageVO batchSearchPage(DBObject queryCondition, DBObject sort,
			DBObject returnFields) {
		return this.sysconstdao.batchSearchPage(queryCondition, sort,
				returnFields);
	}

	@Override
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields) {
		return this.sysconstdao.batchSearchOnePage(query, sort, returnFields);
	}

	@Override
	public String add(SysConst sysconst) {
		this.setCreateInfo(sysconst);
		return this.sysconstdao.insertObj(sysconst);
	}

	@Override
	public DBObject updatePart(DBObject returnFields, SysConst sysconst) {

		DBObject toUpdate = makeUpdate(sysconst);
		return this.sysconstdao.updateOneById(sysconst.get_id_str(),
				returnFields, toUpdate);
	}

	/****
	 * 转化对象为要更新的部分字段
	 * 
	 * @param update
	 * @return
	 */
	private DBObject makeUpdate(SysConst sysconst) {

		DBObject update = new BasicDBObject();
		DBObject updateSet = new BasicDBObject();

		updateSet.put("val", sysconst.getVal());
		updateSet.put("dspval", sysconst.getDspval());
		updateSet.put("valordernum", sysconst.getValordernum());

		this.setModifyInfo(updateSet);
		update.put("$set", updateSet);

		logger.debug("更新的对象信息\n{}", update);
		return update;
	}

	@Override
	public DBObject RemoveOneById(String _id) {
		return this.sysconstdao.findAndRemoveOneById(_id);
	}

	@Override
	public DBObject RemoveOneByIdLogic(String _id) {

		DBObject updateSet = new BasicDBObject();
		this.setModifyInfo(updateSet);
		return this.sysconstdao.findAndRemoveOneByIdLogic(_id, updateSet);
	}

	@Override
	public boolean isExistSameConstval(SysConst sysconst) {

		DBObject returnFields = new BasicDBObject();
		returnFields.put("_id", 1);

		DBObject queryCondition = new BasicDBObject();

		queryCondition.put("typecode", sysconst.getTypecode());// 常量类型
		queryCondition.put("val", sysconst.getVal());// 常量值
		queryCondition.put("useflg", "1");
		String _id = sysconst.get_id_str();
		if (StringUtil.isNotEmpty(_id)){
			queryCondition.put("_id",
					new BasicDBObject("$ne", new ObjectId(_id)));
		}

		DBObject result = this.sysconstdao.findOneByConditionPart(
				queryCondition, returnFields);

		if (result != null && result.get("_id") != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isExistSameConstDispval(SysConst sysconst) {

		DBObject returnFields = new BasicDBObject();
		returnFields.put("_id", 1);

		DBObject queryCondition = new BasicDBObject();

		queryCondition.put("typecode", sysconst.getTypecode());// 常量类型
		queryCondition.put("dspval", sysconst.getDspval());// 常量显示值
		queryCondition.put("useflg", "1");
		String _id = sysconst.get_id_str();
		if (StringUtil.isNotEmpty(_id)){
			queryCondition.put("_id",
					new BasicDBObject("$ne", new ObjectId(_id)));
		}

		DBObject result = this.sysconstdao.findOneByConditionPart(
				queryCondition, returnFields);

		if (result != null && result.get("_id") != null) {
			return true;
		}

		return false;
	}

}
