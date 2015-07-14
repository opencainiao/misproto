package com.misproto.modules.global.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mou.mongodb.MongoCollectionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.StringUtil;

import com.misproto.common.util.FileUtil;
import com.misproto.modules.global.model.ResizedPic;
import com.misproto.modules.global.model.UploadedFile;

public class UploadFileService {

	// 上传文件表
	private static final String COLLECTION_NAME_UPLOADFILE = "uploadfile";

	// 默认上传文件的路径
	public final static String UPLOAD_PATH = "/resources/upload";
	// 默认150x120的压缩图片路径
	public final static String UPLOAD_PIC_PATH_150x120 = "/150x120";
	// 默认120x80的压缩图片路径
	public final static String UPLOAD_PIC_PATH_120x80 = "/120x80";

	private static final Logger logger = LogManager
			.getLogger(UploadFileService.class);

	/****
	 * 删除一个文件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param attachmentId
	 * @param contextPath
	 */
	public static void removeOneFile(UploadedFile file,
			HttpServletRequest request) {

		if (file == null) {
			return;
		}

		logger.debug("removeAttachment[" + file + "]");

		String contextPath = request.getSession().getServletContext()
				.getRealPath("/");

		// 1.删除原文件
		String oriPath = contextPath + file.getServerPath();

		logger.debug("删除原文件");
		logger.debug(oriPath);
		FileUtil.deleteFile(oriPath);

		// 2.删除缩略图
		String compressedPath = file.getCompressedPath();
		if (StringUtil.isNotEmpty(compressedPath)) {

			String path150x120 = contextPath + compressedPath;

			logger.debug("删除缩略图");
			logger.debug(path150x120);
			FileUtil.deleteFile(path150x120);

			String path120x80 = path150x120.replaceAll(UPLOAD_PIC_PATH_150x120,
					UPLOAD_PIC_PATH_120x80);

			logger.debug("todel[" + path120x80 + "]");
			FileUtil.deleteFile(path120x80);
		}

		List<ResizedPic> resizedPics = file.getResizedPics();
		if (resizedPics != null && !resizedPics.isEmpty()) {
			for (ResizedPic pic : resizedPics) {
				String resizedPath = contextPath + pic.getResizedPath();
				
				logger.debug("删除修改的图片");
				logger.debug(pic);
				logger.debug(resizedPath);
				FileUtil.deleteFile(resizedPath);
			}
		}

		// 3.删除数据库文件
		MongoCollectionUtil.removeById(COLLECTION_NAME_UPLOADFILE, file
				.get_id().toString());
	}

	/****
	 * 删除一个文件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param attachmentId
	 * @param contextPath
	 */
	public static void removeOneFile(String id,
			HttpServletRequest request) {
		UploadedFile file = MongoCollectionUtil.findOneByIdObject(
				COLLECTION_NAME_UPLOADFILE, id, UploadedFile.class);
		
		removeOneFile(file,request);
	}
	
	
}
