package com.misproto.modules.base;

import java.util.List;

import com.misproto.common.globalobj.PageVO;
import com.mongodb.BulkWriteResult;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/****
 * 通用持久层基础类
 * 
 * @author NBQ
 *
 */
public interface IBaseDao {

	/****
	 * 判断一个表的某记录是否存在
	 * 
	 * @param collectionName
	 * @param _id
	 * @return
	 */
	public boolean isExist(String _id);

	/****
	 * 根据id，查询一个对象
	 * 
	 * @param collectionName
	 * @param _id
	 * @param clazz
	 * @return
	 */
	public <T> T findOneByIdObject(String _id, Class<T> clazz);

	/****
	 * 根据id，查询一个对象的部分信息
	 * 
	 * @param collectionName
	 * @param _id
	 * @param returnFields
	 * @return
	 */
	public DBObject findOneByIdPart(String _id, DBObject returnFields);

	/****
	 * 根据条件查询一个对象
	 * 
	 * @param collectionName
	 * @param queryCondition
	 * @param clazz
	 * @return
	 */
	public <T> T findOneByConditionObject(DBObject queryCondition,
			Class<T> clazz);

	/****
	 * 根据条件，查询一个对象的部分信息
	 * 
	 * @param collectionName
	 * @param queryCondition
	 * @param returnFields
	 * @return
	 */
	public DBObject findOneByConditionPart(DBObject queryCondition,
			DBObject returnFields);

	/****
	 * 条件查询，分页
	 * 
	 * @param query
	 * @param sort
	 * @param returnFields
	 * @return
	 */
	public PageVO batchSearchPage(DBObject query, DBObject sort,
			DBObject returnFields);

	/****
	 * 条件查询,不分页,返回DBObject
	 * 
	 * @param query
	 * @param sort
	 * @param returnFields
	 * @return
	 */
	public List<DBObject> batchSearch(DBObject query, DBObject sort,
			DBObject returnFields);

	/****
	 * 条件查询，不分页，返回对象
	 * 
	 * @param query
	 * @param clazz
	 * @param sort
	 * @return
	 */
	public <T> List<T> findBatchObject(DBObject query, Class<T> clazz,
			DBObject sort);

	/****
	 * 条件查询，1页，只查询指定的条数
	 * 
	 * @param query
	 * @param sort
	 * @param returnFields
	 * @param count
	 *            指定查询的条数
	 * @return
	 */
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields, int count);

	/****
	 * 条件查询，1页，查询所有
	 * 
	 * @param query
	 * @param sort
	 * @param returnFields
	 * @return
	 */
	public PageVO batchSearchOnePage(DBObject query, DBObject sort,
			DBObject returnFields);

	/****
	 * 插入对象，返回插入后的生成的ObjectId
	 * 
	 * @param obj
	 * @return
	 */
	public String insertObj(Object obj);

	/****
	 * 批量插入
	 * 
	 * @param objs
	 * @param isOrder
	 *            是否有序插入 true-有序 false-无序
	 * @return
	 */
	public BulkWriteResult batchInsertObjs(List<? extends Object> objs,
			boolean isOrder);

	/****
	 * 更新一条记录，返回更新后的结果，根据查询条件
	 * 
	 * @param query
	 * @param returnFields
	 *            指定更新后返回的数据域
	 * @param update
	 * @param upsert
	 *            true-不存在则创建,false-不存在不处理
	 * @return
	 */
	public DBObject updateOneByCondition(DBObject query, DBObject returnFields,
			DBObject update, boolean upsert);

	/****
	 * 更新一条记录，返回更新后的结果，根据对象的主键ObjectId
	 * 
	 * @param _id
	 * @param returnFields
	 * @param update
	 * @return
	 */
	public DBObject updateOneById(String _id, DBObject returnFields,
			DBObject update);

	/****
	 * 查询并删除一条记录
	 * 
	 * @param _id
	 * @return
	 */
	public DBObject findAndRemoveOneById(String _id);

	/****
	 * 查询并删除一条记录(逻辑删除)<br>
	 * 
	 * delflg - 1 已删除 0 未删除
	 * 
	 * @param _id
	 * @return
	 */
	public DBObject findAndRemoveOneByIdLogic(String _id, DBObject updateSet);

	/****
	 * 删除数据，根据ids
	 * 
	 * @param _ids
	 * @return
	 */
	public WriteResult removeByIds(List<String> _ids);

	/****
	 * 删除数据,根据ids(逻辑删除)<br>
	 * 
	 * @param _ids
	 * @return
	 */
	public WriteResult removeByIdsLogic(List<String> _ids, DBObject updateSet);

}
