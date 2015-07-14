package com.misproto.modules.user.dao;

import org.springframework.stereotype.Repository;

import com.misproto.modules.base.BaseDao;

/****
 * 用户dao
 * 
 * @author NBQ
 *
 */
@Repository("userdao")
public class UserDao extends BaseDao {

	@Override
	public String getCollectionName() {
		return "user";
	}

}
