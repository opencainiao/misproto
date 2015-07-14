package com.misproto.modules.infrastructure.dao;

import org.springframework.stereotype.Repository;

import com.misproto.modules.base.BaseDao;

/****
 * 用户dao
 * 
 * @author NBQ
 *
 */
@Repository("autoIncreaserDao")
public class AutoIncreaserDao extends BaseDao {

	@Override
	public String getCollectionName() {
		return "autoincreaser";
	}
}
