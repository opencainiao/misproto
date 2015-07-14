package com.misproto.modules.infrastructure.service;

import com.misproto.common.globalobj.PageVO;
import com.misproto.modules.infrastructure.model.SysConstType;
import com.mongodb.DBObject;

/****
 * 常量类型服务接口
 * 
 * @author NBQ
 *
 */
public interface ISysConstTypeService {

	/****
	 * 根据id，查询一个对象
	 * 
	 * @param _id
	 * @return
	 */
	public SysConstType findOneByIdObject(String _id);

	/****
	 * 条件查询，分页
	 * 
	 * @param query
	 * @param sort
	 * @param returnFields
	 * @return
	 */
	public PageVO batchSearchPage(DBObject queryCondition, DBObject sort,
			DBObject returnFields);

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
	 * @param sysconsttype
	 * @return
	 */
	public String add(SysConstType sysconsttype);

	/****
	 * 更新一条记录，返回更新后的结果，根据对象的主键ObjectId
	 * 
	 * @param returnFields
	 * @param sysconsttype
	 * @return
	 */
	public DBObject updatePart(DBObject returnFields, SysConstType sysconsttype);

	/****
	 * 查询并删除一条记录
	 * 
	 * @param _id
	 * @return
	 */
	public DBObject RemoveOneById(String _id);

	/****
	 * 查询并删除一条记录(逻辑删除)<br>
	 * 
	 * delflg - 1 已删除 0 未删除
	 * 
	 * @param _id
	 * @return
	 */
	public DBObject RemoveOneByIdLogic(String _id);

	/****
	 * 是否存在相同的类型码
	 * 
	 * @param typecode
	 * @return
	 */
	public boolean isExistSameTypecode(String typecode);

	/****
	 * 是否存在相同的类型名
	 * 
	 * @param typename
	 * @return
	 */
	public boolean isExistSameTypename(String typename);

	/****
	 * 根据类型吗查询常量类型
	 * 
	 * @param typecode
	 * @return
	 */
	public SysConstType findOneByTypecode(String typecode);

}
