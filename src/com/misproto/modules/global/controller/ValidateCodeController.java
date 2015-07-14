package com.misproto.modules.global.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.misproto.common.util.ValCodeGenerator;

/****
 * 获取随机图片验证码
 * 
 * @author NBQ
 * @date 2012-3-27
 */
@Controller
@RequestMapping("/ValCode")
public class ValidateCodeController {

	/****
	 * 生成一个验证码图片，返回客户端
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getValCode", method = RequestMethod.GET)
	public void getValCod(HttpServletRequest request,
			HttpServletResponse response) {

		// 禁止缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");

		try {
			String code = ValCodeGenerator.genCod(response.getOutputStream());

			HttpSession session = request.getSession(true);
			session.setAttribute("vcode", code);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
