package test;

import java.util.List;

import mou.mongodb.MongoClientManager;
import mou.mongodb.MongoCollectionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.DBObject;

public class UserTest {

	private static final Logger logger = LogManager.getLogger(UserTest.class);
	private static final String COLLECTION_NAME_USER = "user";// 系统用户注册Collection

	public static void main(String[] args) {
		MongoClientManager.init("182.92.238.54", "bxb");
		
//		MongoClientManager.init( "test");
		

		List<DBObject> all = MongoCollectionUtil.findBatchDBObject(COLLECTION_NAME_USER, null, null);
		
		logger.debug(all);
	}
}
