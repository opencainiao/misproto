package com.misproto.common.globalhandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import mou.mongodb.MongoCollectionUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.JsonUtil;
import org.mou.common.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.reflect.TypeToken;
import com.misproto.common.util.FileUtil;
import com.misproto.modules.global.model.Attachment;
import com.misproto.modules.global.model.ResizedPic;
import com.misproto.modules.global.model.UploadedFile;
import com.mongodb.WriteResult;

/****
 * 处理所有的系统文件上传
 * 
 * @author NBQ
 *
 */
public class FileUploadHandler {

	// 默认上传文件的路径
	public final static String UPLOAD_PATH = "/resources/upload";

	public final static String RESIZED_DIR_NAME = "resized";

	// 默认150x120的压缩图片路径
	public final static String UPLOAD_PIC_PATH_150x120 = "/150x120";
	// 默认120x80的压缩图片路径
	public final static String UPLOAD_PIC_PATH_120x80 = "/120x80";

	public final static int THUMBNAIL_WIDTH = 150;
	public final static int THUMBNAIL_HEIGHT = 120;

	private final static List<String> imgTypes = Arrays.asList("jpg", "jpeg",
			"gif", "png");

	private static final Logger logger = LogManager
			.getLogger(FileUploadHandler.class);

	// 附件表
	private static final String COLLECTION_NAME_ATTACHMENT = "attachment";

	// 上传文件表
	private static final String COLLECTION_NAME_UPLOADFILE = "uploadfile";

	/****
	 * 上传一个附件<br>
	 * 1.存储在文件相应目录<br>
	 * 2.写入文件信息到数据库<br>
	 * 
	 * 如果是一个图片，并且要求压缩，则进行压缩，
	 * 
	 * @param attach
	 * @param request
	 * @param dirpath
	 * @param needCompress
	 * @return
	 * @throws IOException
	 */
	public static Attachment uploadOneAttachment(MultipartFile attach,
			HttpServletRequest request, String dirpath, boolean needCompress)
			throws IOException {

		if (attach.isEmpty()) {
			return null;
		}

		Attachment att = new Attachment();
		String ext = FilenameUtils.getExtension(attach.getOriginalFilename()).toLowerCase();
		att.setSuffix(ext);
		att.setIsAttach("0");
		if (imgTypes.contains(ext)) {
			att.setIsImg(true);
		} else {
			att.setIsImg(false);
		}
		att.setIsIndexPic("0");
		att.setOriName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
		att.setNewName(String.valueOf(new Date().getTime()) + "." + ext);
		att.setType(attach.getContentType());
		att.setSize(attach.getSize());
		att.setUploadDate(DateUtil.getCurdate());
		if (StringUtil.isNotEmpty(dirpath)) {
			att.setUploadDir(dirpath.trim());
		}

		// 1.进行文件上传
		doUploadOneFile(attach, request, att.getNewName(), dirpath);

		// 2.进行缩略图压缩
		if (att.getIsImg().equals("1")) {
			if (needCompress) {
				String thumbDir = request.getSession().getServletContext()
						.getRealPath(UPLOAD_PATH)
						+ UPLOAD_PIC_PATH_150x120;
				if (!(new File(thumbDir).isDirectory())) {
					new File(thumbDir).mkdir();
				}

				String thumbDir_120_80 = request.getSession()
						.getServletContext().getRealPath(UPLOAD_PATH)
						+ UPLOAD_PIC_PATH_120x80;

				if (!(new File(thumbDir_120_80).isDirectory())) {
					new File(thumbDir_120_80).mkdir();
				}

				String thumbPath = thumbDir + "/" + att.getNewName();
				String thumbPath_120_80 = thumbDir_120_80 + "/"
						+ att.getNewName();

				BufferedImage oldBi;

				oldBi = ImageIO.read(attach.getInputStream());
				int width = oldBi.getWidth();
				// 缩略图的处理
				// 1、将原图进行压缩
				BufferedImage tbi = Thumbnails.of(oldBi)
						.scale((THUMBNAIL_WIDTH * 1.2) / width)
						.asBufferedImage();
				// 2、进行切割并且保持
				Thumbnails
						.of(tbi)
						.scale(1.0f)
						.sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH,
								THUMBNAIL_HEIGHT).toFile(thumbPath);

				// 3.在150x120基础上，裁120x80
				Thumbnails.of(thumbPath)
						// 从原图哪里开始裁剪 裁减多少
						.sourceRegion(Positions.CENTER, 400, 400)
						// 新图的大小
						.size(120, 80).keepAspectRatio(false)
						.toFile(thumbPath_120_80);

				// 3、设置附件的压缩图片的名字
				att.setName150x120(UPLOAD_PATH + UPLOAD_PIC_PATH_150x120 + "/"
						+ att.getNewName());
			}
		}

		// 3.将附件写入附件表
		WriteResult result = MongoCollectionUtil.insertObj(
				COLLECTION_NAME_ATTACHMENT, att);
		att.set_id(result.getUpsertedId().toString());

		logger.debug(JsonUtil.toJsonStr(att));

		return att;
	}

	/****
	 * 上传一个文件<br>
	 * 1.存储在文件相应目录<br>
	 * 2.写入文件信息到数据库<br>
	 * 
	 * 如果是一个图片，并且要求压缩，则进行压缩，
	 * 
	 * @param attach
	 * @param request
	 * @param dirpath
	 * @param needCompress
	 * @return
	 * @throws IOException
	 */
	public static UploadedFile uploadOneFile(MultipartFile attach,
			HttpServletRequest request, String dirpath, boolean needCompress)
			throws IOException {

		if (attach.isEmpty()) {
			return null;
		}

		UploadedFile file = new UploadedFile();
		String ext = FilenameUtils.getExtension(attach.getOriginalFilename());
		file.setSuffix(ext);
		if (imgTypes.contains(ext)) {
			file.setIsImg("1");
		} else {
			file.setIsImg("0");
		}
		String newFileName = String.valueOf(new Date().getTime()) + "." + ext;
		file.setOriName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
		file.setNewName(newFileName);
		file.setType(attach.getContentType());
		file.setSize(attach.getSize());
		file.setUploadDate(DateUtil.getCurdate());
		if (StringUtil.isNotEmpty(dirpath)) {
			file.setUploadDir(dirpath);
			file.setServerPath(UPLOAD_PATH + "/" + dirpath + "/" + newFileName);
		} else {
			file.setServerPath(UPLOAD_PATH + "/" + newFileName);
		}

		// 1.进行文件上传
		doUploadOneFile(attach, request, file.getNewName(), dirpath);

		// 2.根据参数进行图片大小调整
		doResizePic(attach, file, needCompress, request);

		// 2.进行缩略图压缩
		if (file.getIsImg().equals("1")) {
			if (needCompress) {
				String thumbDir = request.getSession().getServletContext()
						.getRealPath(UPLOAD_PATH)
						+ UPLOAD_PIC_PATH_150x120;
				if (!(new File(thumbDir).isDirectory())) {
					new File(thumbDir).mkdir();
				}

				String thumbDir_120_80 = request.getSession()
						.getServletContext().getRealPath(UPLOAD_PATH)
						+ UPLOAD_PIC_PATH_120x80;

				if (!(new File(thumbDir_120_80).isDirectory())) {
					new File(thumbDir_120_80).mkdir();
				}

				String thumbPath = thumbDir + "/" + file.getNewName();
				String thumbPath_120_80 = thumbDir_120_80 + "/"
						+ file.getNewName();

				BufferedImage oldBi;

				oldBi = ImageIO.read(attach.getInputStream());
				int width = oldBi.getWidth();
				// 缩略图的处理
				// 1、将原图进行压缩
				BufferedImage tbi = Thumbnails.of(oldBi)
						.scale((THUMBNAIL_WIDTH * 1.2) / width)
						.asBufferedImage();
				// 2、进行切割并且保持
				Thumbnails
						.of(tbi)
						.scale(1.0f)
						.sourceRegion(Positions.CENTER, THUMBNAIL_WIDTH,
								THUMBNAIL_HEIGHT).toFile(thumbPath);

				// 3.在150x120基础上，裁120x80
				Thumbnails.of(thumbPath)
						// 从原图哪里开始裁剪 裁减多少
						.sourceRegion(Positions.CENTER, 400, 400)
						// 新图的大小
						.size(120, 80).keepAspectRatio(false)
						.toFile(thumbPath_120_80);

				// 3、设置附件的压缩图片的名字
				file.setName150x120(UPLOAD_PATH + UPLOAD_PIC_PATH_150x120 + "/"
						+ file.getNewName());

				file.setCompressedPath(UPLOAD_PATH + UPLOAD_PIC_PATH_150x120
						+ "/" + file.getNewName());
			}
		}

		// 3.将文件写入文件表
		WriteResult result = MongoCollectionUtil.insertObj(
				COLLECTION_NAME_UPLOADFILE, file);
		file.set_id(result.getUpsertedId().toString());
		file.set_id_m(result.getUpsertedId().toString());

		logger.debug(JsonUtil.toJsonStr(file));

		return file;
	}

	/****
	 * 进行图片大小调整并保存
	 * 
	 * @param attach
	 * @param file
	 * @param needCompress
	 * @param request
	 *            resize:[{w:150,h:50},{w:300,h:200},...]
	 * @throws IOException
	 */
	private static void doResizePic(MultipartFile attach, UploadedFile file,
			boolean needCompress, HttpServletRequest request)
			throws IOException {
		if (file.getIsImg().equals("1")) {
			if (needCompress) {

				String resizeParams = request.getParameter("resize");

				List<Map<String, Double>> sizes = null;
				try {
					sizes = JsonUtil.getGson().fromJson(resizeParams,
							new TypeToken<ArrayList<Map>>() {
							}.getType());

					if (sizes == null || sizes.isEmpty()) {
						return;
					}
				} catch (Exception e) {
					return;
				}

				BufferedImage oldBi;

				oldBi = ImageIO.read(attach.getInputStream());
				int oriWdth = oldBi.getWidth();

				for (Map<String, Double> size : sizes) {
					int widthNew = size.get("w").intValue();
					int heightNew = size.get("h").intValue();

					logger.debug("w:[" + widthNew + "]h[" + heightNew + "]");

					String thumbDir = request.getSession().getServletContext()
							.getRealPath(UPLOAD_PATH)
							+ RESIZED_DIR_NAME;

					if (!(new File(thumbDir).isDirectory())) {
						new File(thumbDir).mkdir();
					}

					String thumbPath = thumbDir + "/" + file.getNewName();

					// 1、将原图进行压缩
					BufferedImage tbi = Thumbnails.of(oldBi)
							.scale((widthNew * 1.2) / oriWdth)
							.asBufferedImage();

					// 2、进行切割并且保持
					Thumbnails
							.of(tbi)
							.scale(1.0f)
							.sourceRegion(Positions.CENTER, widthNew + 100,
									heightNew + 100).size(widthNew, heightNew)
							.keepAspectRatio(false).toFile(thumbPath);

					// 3、设置附件的压缩图片的名字
					String modelsize = widthNew + "" + heightNew;

					ResizedPic resizedPic = new ResizedPic();
					resizedPic.setHeight(heightNew);
					resizedPic.setWidth(widthNew);
					resizedPic.setModelsize(modelsize);
					resizedPic.setResizedPath(thumbPath + "/" + modelsize
							+ file.getNewName());

					file.addResizedPic(resizedPic);
				}
			}
		}

		logger.debug("处理图片压缩完毕,图片信息为");
		logger.debug(file);
	}

	/****
	 * 上传单个文件，只上传文件，不进行任何其他处理
	 * 
	 * @param attach
	 * @param request
	 * @param newFileName
	 * @param dirpath
	 * @throws IOException
	 */
	private static void doUploadOneFile(MultipartFile attach,
			HttpServletRequest request, String newFileName, String dirpath)
			throws IOException {

		String saveDir = request.getSession().getServletContext()
				.getRealPath(UPLOAD_PATH);

		if (StringUtil.isNotEmpty(dirpath)) {
			saveDir = request.getSession().getServletContext()
					.getRealPath(UPLOAD_PATH + "/" + dirpath);
		}

		if (!(new File(saveDir).isDirectory())) {
			new File(saveDir).mkdir();
		}

		String newFilePath = saveDir + "/" + newFileName;

		// 进行文件拷贝
		File f = new File(newFilePath);
		FileUtils.copyInputStreamToFile(attach.getInputStream(), f);

		logger.debug(newFilePath);
	}

	/****
	 * 删除一个附件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param _id_m
	 */
	public static void deleteOneAttachment(String _id_m,
			HttpServletRequest request) {

		logger.debug("remove[" + _id_m + "]");
		Attachment att = MongoCollectionUtil.findOneByIdObject(
				COLLECTION_NAME_ATTACHMENT, _id_m, Attachment.class);

		// 1.删除数据库文件
		MongoCollectionUtil.removeById(COLLECTION_NAME_ATTACHMENT, _id_m);

		String newName = att.getNewName();

		String oridirpath = request.getSession().getServletContext()
				.getRealPath("/")
				+ UPLOAD_PATH;

		// 2.删除原文件目录的文件
		String path = oridirpath + "/" + newName;
		if (att.getUploadDir() != null) {
			path = oridirpath + "/" + att.getUploadDir() + "/" + newName;
		}
		logger.debug("todel[" + path + "]");
		FileUtil.deleteFile(path);

		// 3.删除图片缩略图文件
		if (att.getIsImg().equals("1")) {
			String path150x120 = oridirpath + UPLOAD_PIC_PATH_150x120 + "/"
					+ newName;
			String path120x80 = oridirpath + UPLOAD_PIC_PATH_120x80 + "/"
					+ newName;
			logger.debug("todel[" + path150x120 + "]");
			FileUtil.deleteFile(path150x120);
			logger.debug("todel[" + path120x80 + "]");
			FileUtil.deleteFile(path120x80);
		}
	}

	public static void main(String[] args) {
		String resizeParams = "[{w:150,h:50},{w:300,h:200}]";

		List<Map<String, Double>> sizes = JsonUtil.getGson().fromJson(
				resizeParams, new TypeToken<ArrayList<Map>>() {
				}.getType());

		for (Map<String, Double> size : sizes) {
			int width = size.get("w").intValue();
			int height = size.get("h").intValue();

			logger.debug("w:[" + width + "]h[" + height + "]");
		}

	}
}
