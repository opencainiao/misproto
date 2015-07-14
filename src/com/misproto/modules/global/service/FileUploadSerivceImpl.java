package com.misproto.modules.global.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import mou.mongodb.MongoCollectionUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.misproto.common.util.EncoderHandler;
import com.misproto.common.util.FileUtil;
import com.misproto.modules.base.BaseService;
import com.misproto.modules.global.model.Attachment;
import com.mongodb.WriteResult;

@Service("fileUplodService")
public class FileUploadSerivceImpl extends BaseService implements IFileUpload {

	private static final Logger logger = LogManager
			.getLogger(FileUploadSerivceImpl.class);

	// 默认上传文件的路径
	public final static String UPLOAD_PATH = "/resources/upload";
	// 附件表
	private static final String COLLECTION_NAME_ATTACHMENT = "attachment";
	// 压缩器
	@Resource(name = "picThumbService")
	private IPicThumb picThumbService;

	@Override
	public File doUploadOneFile(MultipartFile attach, String newFileName,
			String dirpath) throws IOException {
		if (StringUtil.isEmpty(dirpath) || StringUtil.isEmpty(newFileName)) {
			return null;
		}

		String newFilePath = dirpath + "/" + newFileName;

		FileUtil.creatParentDir(newFilePath);

		// 进行文件拷贝
		File f = new File(newFilePath);
		FileUtils.copyInputStreamToFile(attach.getInputStream(), f);
		return f;
	}

	@Override
	public Attachment uploadOneAttachment(MultipartFile attach,
			HttpServletRequest request, String dirpath, boolean needCompress,
			List<ThumbParam> tps) throws IOException {

		if (attach.isEmpty()) {
			return null;
		}
		String ext = FilenameUtils.getExtension(attach.getOriginalFilename());
		String newFileName = EncoderHandler.encodeByAES(this.getUserId()
				+ String.valueOf(new Date().getTime()))
				+ "." + ext;

		String uploadDir = request.getSession().getServletContext()
				.getRealPath(UPLOAD_PATH);
		if (StringUtil.isNotEmpty(dirpath)) {
			uploadDir = uploadDir + "/" + dirpath;
		}

		// 1.进行文件上传
		File uploadedFile = doUploadOneFile(attach, newFileName, uploadDir);

		// 2.创建附件文件
		Attachment att = new Attachment();
		att.setSuffix(ext);
		att.setIsAttach(request.getParameter("isattach"));
		att.setIsIndexPic(request.getParameter("isindexpic"));
		att.setOriName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
		att.setNewName(newFileName);
		att.setType(attach.getContentType());
		att.setSize(attach.getSize());
		att.setUploadDate(DateUtil.getCurdate());
		att.setUploadTime(DateUtil.getCurrentTimsmp());
		if (StringUtil.isNotEmpty(dirpath)) {
			att.setUploadDir(dirpath);
		}
		if (FileUtil.isImage(uploadedFile)) {
			att.setIsImg(true);
		} else {
			att.setIsImg(false);
		}
		att.setUploadFileAbsolutePath(uploadedFile.getAbsolutePath());

		// 3.进行缩略图压缩
		if (att.getIsImg().equals("1")) {
			if (needCompress && tps != null && tps.size() > 0) {

				for (ThumbParam tp : tps) {

					String folderName = tp.getFolderName();

					String thisFolderPath = uploadDir + "/" + folderName + "/";
					String thisThumbPath = thisFolderPath + newFileName;

					tp.setThumbParmPath(thisThumbPath);

					logger.debug(tp);
				}

				this.picThumbService.thumbFile(uploadedFile, tps);

				String compressedDir = dirpath + "/"
						+ tps.get(0).getFolderName();
				if (StringUtil.isEmpty(dirpath)) {
					compressedDir = tps.get(0).getFolderName();
				}
				att.setCompressedDir(compressedDir);
				att.setThumb_info(tps);
			}
		}

		// 3.将附件写入附件表
		WriteResult result = MongoCollectionUtil.insertObj(
				COLLECTION_NAME_ATTACHMENT, att);
		att.set_id(result.getUpsertedId().toString());

		logger.debug("上传文件完毕，上传之后的文件信息");
		logger.debug(MongoCollectionUtil.findOneByIdFields(
				COLLECTION_NAME_ATTACHMENT, result.getUpsertedId().toString(),
				null));

		return att;
	}

	/****
	 * 删除一个附件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param _id_m
	 */
	public void deleteOneAttachment(String _id_m, HttpServletRequest request) {

		logger.debug("remove[" + _id_m + "]");
		Attachment att = MongoCollectionUtil.findOneByIdObject(
				COLLECTION_NAME_ATTACHMENT, _id_m, Attachment.class);

		// 1.删除数据库文件
		MongoCollectionUtil.removeById(COLLECTION_NAME_ATTACHMENT, _id_m);

		// 2.删除原文件目录的文件
		String path = att.getUploadFileAbsolutePath();
		FileUtil.deleteFile(path);
		logger.debug("deleted[" + path + "]");

		// 3.删除图片缩略图文件
		if (att.getIsImg().equals("1")) {

			List<ThumbParam> tps = att.getThumb_info();

			if (tps != null && tps.size() > 0) {
				for (ThumbParam tp : tps) {
					String filetp = tp.getThumbParmPath();
					FileUtil.deleteFile(filetp);

					logger.debug("deleted-[{}]", filetp);
				}
			}
		}
	}
}
