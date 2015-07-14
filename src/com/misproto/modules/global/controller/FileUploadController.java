package com.misproto.modules.global.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mou.common.DateUtil;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.misproto.common.globalhandler.FileUploadHandler;
import com.misproto.modules.global.model.Attachment;
import com.misproto.modules.global.model.UploadedFile;
import com.misproto.modules.global.service.UploadFileService;

/****
 * 全局文件上传控制器
 * 
 * @author sks
 *
 */
@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	/****
	 * 上传一个文件
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ajaxUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxUpload(HttpServletRequest request) {

		System.out.println("ajaxUpload");

		Map<String, String> result = new HashMap<String, String>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String dirpath = DateUtil.getCurdate();

		String realImagePath = null;

		try {
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile fileIn = multipartRequest.getFile(key);

				UploadedFile file = null;
				if (request.getParameter("zip") != null) {
					file = FileUploadHandler.uploadOneFile(fileIn, request,
							dirpath, true);
				} else {
					file = FileUploadHandler.uploadOneFile(fileIn, request,
							dirpath, false);
				}

				if (file != null) {
					realImagePath = file.getServerPath();
					result.put("fileid", file.get_id_m());
				} else {
					realImagePath = "";
				}

				result.put("success", "1");
				result.put("path", realImagePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", "0");
			result.put("path", "");
			result.put("message", StringUtil.getStackTrace(e));
		}

		return result;
	}

	/****
	 * 删除一个文件
	 * 
	 * @param imgurl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteOneFile", method = RequestMethod.GET)
	@ResponseBody
	public Object ajaxDeleteOneFile(String _id_m, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(_id_m)) {
			result.put("success", "y");
			return result;
		}

		try {
			UploadFileService.removeOneFile(_id_m, request);
			result.put("success", "y");
		} catch (Exception e) {
			result.put("success", "n");
			result.put("message", StringUtil.getStackTrace(e));
		}
		return result;
	}

	/****
	 * 上传一个附件 上传附件时，默认对图片生成缩略图
	 * 
	 * @param request
	 * @return 返回生成的附件信息
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/ajaxUploadOneAttachment", method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxUploadOneAttachment(HttpServletRequest request) {

		System.out.println("ajaxUpload");

		Map<String, Object> result = new HashMap<String, Object>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String dirpath = DateUtil.getCurdate();

		Attachment attach = null;

		try {
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile fileIn = multipartRequest.getFile(key);

				attach = FileUploadHandler.uploadOneAttachment(fileIn,
						multipartRequest, dirpath, true);
			}

			result.put("success", "y");
			result.put("attach", attach);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("success", "n");
			result.put("message", StringUtil.getStackTrace(e));
		}

		return result;
	}

	/****
	 * 删除一个附件
	 * 
	 * @param imgurl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxDeleteOneAttachment", method = RequestMethod.POST)
	@ResponseBody
	public Object ajaxDeleteOneAttachment(String _id_m,
			HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(_id_m)) {
			result.put("success", "y");
			return result;
		}

		try {
			FileUploadHandler.deleteOneAttachment(_id_m, request);
			result.put("success", "y");
		} catch (Exception e) {
			result.put("success", "n");
			result.put("message", StringUtil.getStackTrace(e));
		}
		return result;
	}
}
