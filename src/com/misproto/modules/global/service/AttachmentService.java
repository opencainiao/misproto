package com.misproto.modules.global.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mou.mongodb.MongoCollectionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mou.common.StringUtil;

import com.misproto.common.util.FileUtil;
import com.misproto.modules.global.model.Attachment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

public class AttachmentService {
	private static final String COLLECTION_NAME_ATTACHMENT = "attachment"; // 附件表
	// 默认上传文件的路径
	public final static String UPLOAD_PATH = "/resources/upload";
	// 默认150x120的压缩图片路径
	public final static String UPLOAD_PIC_PATH_150x120 = "/150x120";

	private static final Logger logger = LogManager
			.getLogger(AttachmentService.class);

	/****
	 * 删除一个附件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param attachmentId
	 * @param contextPath
	 */
	public static void removeAttachment(String attachmentId, String contextPath) {

		if (StringUtil.isEmpty(attachmentId)) {
			return;
		}
		logger.debug("removeAttachment[" + attachmentId + "]");
		Attachment attachment = MongoCollectionUtil.findOneByIdObject(
				COLLECTION_NAME_ATTACHMENT, attachmentId, Attachment.class);

		if (attachment == null) {
			return;
		}

		// 1.删除原附件
		String uploadDir = attachment.getUploadDir();
		String oriPath = contextPath + AttachmentService.UPLOAD_PATH + "/"
				+ attachment.getNewName();
		if (StringUtil.isNotEmpty(uploadDir)) {
			oriPath = contextPath + AttachmentService.UPLOAD_PATH + "/"
					+ uploadDir + "/" + attachment.getNewName();
		}

		logger.debug("删除原附件");
		File file = new File(oriPath);
		logger.debug("oriPath[" + oriPath + "]");
		logger.debug("exist[" + String.valueOf(file.exists()) + "]");
		FileUtil.deleteFile(oriPath);
		logger.debug("exist[" + String.valueOf(file.exists()) + "]");

		// 2.删除缩略图
		String nameCompressed = attachment.getName150x120();
		if (StringUtil.isNotEmpty(nameCompressed)) {
			String compressPath = contextPath + nameCompressed;

			logger.debug("删除缩略图");
			file = new File(compressPath);
			logger.debug("exist[" + String.valueOf(file.exists()) + "]");
			logger.debug("compressPath[" + compressPath + "]");
			FileUtil.deleteFile(compressPath);
			logger.debug("exist[" + String.valueOf(file.exists()) + "]");
		}

		// 3.删除数据库文件
		MongoCollectionUtil
				.removeById(COLLECTION_NAME_ATTACHMENT, attachmentId);
	}

	/****
	 * 根据一组附件id，取附件的信息
	 * 
	 * @param attachIds
	 * @return
	 */
	public static List<Attachment> getAttachmentsByAttachIds(
			List<String> attachIds) {

		DBObject query = new BasicDBObject();

		// BasicDBList values = new BasicDBList();
		// for (String attachId : attachIds) {
		// MongoObjectId _idNew = new MongoObjectId();
		// _idNew.set$oid(attachId);
		// values.add(_idNew);
		// }
		// query.put("_id", new BasicDBObject("$in", values));

		List<ObjectId> oids = new ArrayList<ObjectId>();
		DBObject inSet = new BasicDBObject();
		for (String _id : attachIds) {
			oids.add(new ObjectId(_id));
		}
		inSet.put(QueryOperators.IN, oids);
		query.put("_id", inSet);

		logger.debug("querycondition[" + query + "]");

		List<Attachment> attachments = MongoCollectionUtil.findBatch(
				COLLECTION_NAME_ATTACHMENT, query, Attachment.class);

		logger.debug("search_ result----------");

		return attachments;
	}

	/****
	 * 更新一个附件
	 * 
	 * @param id
	 * @param update
	 */
	public static void updateOneById(String id, DBObject update) {
		MongoCollectionUtil.updateOneById(COLLECTION_NAME_ATTACHMENT, id,
				update);
	}
}
