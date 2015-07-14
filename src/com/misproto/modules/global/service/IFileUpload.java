package com.misproto.modules.global.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.misproto.modules.global.model.Attachment;

/****
 * 全局文件上传Service
 * 
 * @author NBQ
 *
 */
public interface IFileUpload {

	/****
	 * 上传一个文件，以新文件名命名，上传到指定目录
	 * 
	 * @param attach
	 * @param newFileName
	 * @param dirpath
	 * @throws IOException
	 */
	public File doUploadOneFile(MultipartFile attach, String newFileName,
			String dirpath) throws IOException;

	/****
	 * 上传一个附件
	 * 
	 * @param attach
	 * @param request
	 * @param dirpath
	 * @param needCompress
	 * @return
	 * @throws IOException
	 */
	public Attachment uploadOneAttachment(MultipartFile attach,
			HttpServletRequest request, String dirpath, boolean needCompress,
			List<ThumbParam> tps) throws IOException;

	/****
	 * 删除一个附件<br>
	 * 1.删除数据库中的信息 <br>
	 * 2.删除存储目录中的文件 <br>
	 * 3.如果文件是图片，删除缩略图的文件
	 * 
	 * @param _id_m
	 */
	public void deleteOneAttachment(String _id_m, HttpServletRequest request);
}
