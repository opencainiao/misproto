package com.misproto.modules.infrastructure.controller;

import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mou.common.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misproto.common.globalhandler.ErrorHandler;
import com.misproto.common.globalobj.RequestResult;
import com.misproto.common.util.HttpServletRequestUtil;
import com.misproto.common.util.RegexPatternUtil;
import com.misproto.modules.base.BaseController;
import com.misproto.modules.infrastructure.model.SysConstType;
import com.misproto.modules.infrastructure.service.ISysConstTypeService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/****
 * 常量类型管理Controller
 * 
 * @author NBQ
 *
 */
@Controller
@RequestMapping("/backend/sysconsttype")
public class SysConstTypeController extends BaseController {

	private static final Logger logger = LogManager
			.getLogger(SysConstTypeController.class);

	@Resource(name = "sysConstTypeService")
	private ISysConstTypeService sysConstTypeService;

	/****
	 * 进入常量类型的常量值管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/manage_sysconst", method = RequestMethod.GET)
	public String manage(Model model, String typecode,
			HttpServletRequest request) {

		// 查询常量类型信息
		SysConstType sysconsttype = this.sysConstTypeService
				.findOneByTypecode(typecode);

		model.addAttribute("sysconsttype", sysconsttype);

		// 开启modelDriven
		return "admin/infrastructure/sysconsttype/manage_sysconst";
	}

	/****
	 * 进入添加常量类型页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(
			@ModelAttribute("sysconsttype") SysConstType sysconsttype,
			HttpServletRequest request) {

		// 开启modelDriven
		return "admin/infrastructure/sysconsttype/add";
	}

	/****
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(@Validated SysConstType sysconsttype, BindingResult br,
			HttpServletRequest request) {

		HttpServletRequestUtil.debugParams(request);

		logger.debug("传入的常量类型对象\n{}", sysconsttype);

		if (br.hasErrors()) {
			return ErrorHandler.getRequestResultFromBindingResult(br);
		}
		try {
			// 1.校验是否已存在相同的类型码
			boolean isExist = this.sysConstTypeService
					.isExistSameTypecode(sysconsttype.getTypecode());
			if (isExist) {
				RequestResult rr = new RequestResult();
				rr.setSuccess(false);
				rr.setMessage("已经存在类型码【" + sysconsttype.getTypecode().trim()
						+ "】的常量类型!");
				return rr;
			}

			isExist = this.sysConstTypeService.isExistSameTypename(sysconsttype
					.getTypename());
			if (isExist) {
				RequestResult rr = new RequestResult();
				rr.setSuccess(false);
				rr.setMessage("已经存在类型名【" + sysconsttype.getTypename().trim()
						+ "】的常量类型!");
				return rr;
			}

			// 2.新增
			sysconsttype.setUseflg("1");
			String _id = this.sysConstTypeService.add(sysconsttype);

			RequestResult rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);

			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}
	}

	/****
	 * 查看所有系统常量类型 信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {

		return "admin/infrastructure/sysconsttype/list";
	}

	/****
	 * 查询系统常量类型信息（条件查询，查询多笔，按照系统常量类型码或名称）
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(Model model, HttpServletRequest request) {

		HttpServletRequestUtil.debugParams(request);
		try {

			HttpServletRequestUtil.debugParams(request);

			String search_condition = request.getParameter("search_condition");
			if (StringUtil.isNotEmpty(search_condition)) {
				search_condition = search_condition.trim();
			}

			DBObject query = new BasicDBObject();

			if (StringUtil.isNotEmpty(search_condition)) {
				Pattern pattern = RegexPatternUtil
						.getLikePattern(search_condition);

				BasicDBList condList = new BasicDBList();

				condList.add(new BasicDBObject("typename", pattern));
				condList.add(new BasicDBObject("typecode", pattern));

				query.put("$or", condList);
			}
			query.put("useflg", "1");

			DBObject sort = new BasicDBObject();
			sort.put("typename", 1);
			DBObject returnFields = null;

			return this.sysConstTypeService.batchSearchPage(query, sort,
					returnFields);

		} catch (Exception e) {
			return this.handleException(e);
		}
	}

	/****
	 * 查看单个常量类型 信息
	 * 
	 * @param _id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{_id}", method = RequestMethod.GET)
	public String detail(@PathVariable String _id, Model model) {

		SysConstType sysconsttype = this.sysConstTypeService
				.findOneByIdObject(_id);

		model.addAttribute("sysconsttype", sysconsttype);

		return "admin/infrastructure/sysconsttype/detail";
	}

	/****
	 * 进入更新页面
	 * 
	 * @param zzdhid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{_id}/update", method = RequestMethod.GET)
	public String update(@PathVariable String _id, Model model) {

		SysConstType sysconsttype = this.sysConstTypeService
				.findOneByIdObject(_id);

		model.addAttribute("sysconsttype", sysconsttype);

		model.addAttribute("_id", _id);

		return "admin/infrastructure/sysconsttype/update";
	}

	/****
	 * 更新系统常量类型 信息，返回json给客户端
	 * 
	 * @param _id
	 * @param sysconsttype
	 * @param br
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/{_id}/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable String _id,
			@Validated SysConstType sysconsttype, BindingResult br,
			HttpServletRequest request) {

		if (br.hasErrors()) {
			return ErrorHandler.getRequestResultFromBindingResult(br);
		}

		String name = sysconsttype.getTypename();
		if (StringUtil.isEmpty(name)) {
			return handleValidateFalse("常量类型名称不能为空");
		}

		name = name.trim();

		SysConstType sysconsttypeori = this.sysConstTypeService
				.findOneByIdObject(_id);
		String nameOri = sysconsttypeori.getTypename();

		if (nameOri.equals(name)) {
			RequestResult rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);
			return rr;
		}

		boolean isExitByName = this.sysConstTypeService
				.isExistSameTypename(name);
		if (isExitByName) {
			RequestResult rr = new RequestResult();
			rr.setSuccess(false);
			rr.setMessage("已经存在名称为【" + name + "】的常量类型!");
			return rr;
		}

		try {
			sysconsttype.set_id(_id);
			DBObject updateResult = this.sysConstTypeService.updatePart(null,
					sysconsttype);

			logger.debug("更新后的结果[{}]", updateResult);

			RequestResult rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);
			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}
	}

	/****
	 * 删除一条记录
	 * 
	 * @param zzdhid
	 * @return
	 */
	@RequestMapping(value = "/{_id}/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@PathVariable String _id, HttpServletRequest request) {

		try {

			this.sysConstTypeService.RemoveOneByIdLogic(_id);

			RequestResult rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);
			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}
	}
}
