package com.misproto.modules.global.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.DateUtil;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.misproto.common.globalhandler.FileUploadHandler;
import com.misproto.modules.base.BaseController;
import com.misproto.modules.global.model.Attachment;
import com.misproto.modules.global.service.IFileUpload;
import com.misproto.modules.global.service.ThumbParam;

/****
 * 全局文件上传控制器
 * 
 * @author sks
 *
 */
@Controller
@RequestMapping("/attachmentupload")
public class AttachmentUploadController extends BaseController {

	private static final Logger logger = LogManager
			.getLogger(AttachmentUploadController.class);

	@Resource(name="fileUplodService")  
	private IFileUpload fileUplodService;

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

		logger.debug("ajaxUploadOneAttachment");

		Map<String, Object> result = new HashMap<String, Object>();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		String dirpath = DateUtil.getCurdate();

		String iscompress = request.getParameter("iscompress");
		
		boolean isCompress = false;
		if(!StringUtil.isEmpty(iscompress)&& iscompress.equals("1")){
			isCompress = true;
		}
		
		Attachment attach = null;

		try {
			List<ThumbParam> tps = null;
			
			String tpsStr = request.getParameter("cp_param");
			if (StringUtil.isNotEmpty(tpsStr)){
				tps = new ArrayList<ThumbParam>();
				
				String[] alltps = tpsStr.split(",");
				for (String tpstmp:alltps){
					String[] tp_arr = tpstmp.split("x");
					
					ThumbParam tp = new ThumbParam();
					tp.setWidth(Integer.parseInt(tp_arr[0]));
					tp.setHeight(Integer.parseInt(tp_arr[1]));
					tp.setThumbType(tp_arr[2]);
					tps.add(tp);
				}
			}
			
			
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile fileIn = multipartRequest.getFile(key);

				attach = this.fileUplodService.uploadOneAttachment(fileIn,
						multipartRequest, dirpath, isCompress, tps);
			}

			result.put("success", "y");
			result.put("attach", attach);
		} catch (Exception e) {
			return this.handleException(e);
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
			fileUplodService.deleteOneAttachment(_id_m, request);
			result.put("success", "y");
		} catch (Exception e) {
			result.put("success", "n");
			result.put("message", StringUtil.getStackTrace(e));
		}
		return result;
	}
}
