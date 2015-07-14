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
import com.misproto.modules.infrastructure.model.SysConst;
import com.misproto.modules.infrastructure.service.ISysConstService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/****
 * 常量管理Controller<br>
 * 常量不能修改，只能删除
 * 
 * @author NBQ
 *
 */
@Controller
@RequestMapping("/backend/sysconst")
public class SysConstController extends BaseController {

	private static final Logger logger = LogManager
			.getLogger(SysConstController.class);

	@Resource(name = "sysConstService")
	private ISysConstService sysConstService;

	/****
	 * 进入添加常量页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@ModelAttribute("sysconst") SysConst sysconst,
			HttpServletRequest request) {

		// 开启modelDriven
		return "admin/infrastructure/sysconst/add";
	}

	/****
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object add(@Validated SysConst sysconst, BindingResult br,
			HttpServletRequest request) {

		HttpServletRequestUtil.debugParams(request);

		logger.debug("传入的常量对象\n{}", sysconst);

		if (br.hasErrors()) {
			return ErrorHandler.getRequestResultFromBindingResult(br);
		}

		// 非空校验，同时trim参数
		RequestResult rr = validateNotBlank(sysconst);
		if (rr != null) {
			return rr;
		}

		// 校验是否存在相同值的数据
		boolean isExitSameConstval = this.sysConstService
				.isExistSameConstval(sysconst);
		if (isExitSameConstval) {
			return this.handleValidateFalse("已经存在值为【" + sysconst.getVal()
					+ "】的常量!");
		}

		// 校验是否存在相同显示值的数据
		boolean isExitSameConstDispval = this.sysConstService
				.isExistSameConstDispval(sysconst);
		if (isExitSameConstDispval) {
			return this.handleValidateFalse("已经存在显示值为【" + sysconst.getDspval()
					+ "】的常量!");
		}

		try {
			// 新增
			sysconst.setUseflg("1");
			String _id = this.sysConstService.add(sysconst);

			rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);

			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}
	}

	/****
	 * 查看所有系统常量 信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {

		return "admin/infrastructure/sysconst/list";
	}

	/****
	 * 查询系统常量信息（条件查询，查询多笔，按照系统常量码或名称）
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Object list(Model model, String typecode, String typename,
			HttpServletRequest request) {

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
			
			if (StringUtil.isNotEmpty(typecode)){
				query.put("typecode", typecode.trim());
			}

			DBObject sort = new BasicDBObject();
			sort.put("typename", 1);
			DBObject returnFields = null;

			return this.sysConstService.batchSearchPage(query, sort,
					returnFields);

		} catch (Exception e) {
			return this.handleException(e);
		}
	}

	/****
	 * 查看单个常量 信息
	 * 
	 * @param _id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{_id}", method = RequestMethod.GET)
	public String detail(@PathVariable String _id, Model model) {

		SysConst sysconst = this.sysConstService.findOneByIdObject(_id);

		model.addAttribute("sysconst", sysconst);

		return "admin/infrastructure/sysconst/detail";
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

		SysConst sysconst = this.sysConstService.findOneByIdObject(_id);

		model.addAttribute("sysconst", sysconst);

		model.addAttribute("_id", _id);

		return "admin/infrastructure/sysconst/update";
	}

	private RequestResult validateNotBlank(SysConst sysconst) {

		String typecod = sysconst.getTypecode();
		if (StringUtil.isEmpty(typecod)) {
			return handleValidateFalse("常量类型不能为空");
		}
		sysconst.setTypecode(typecod.trim());

		String dspVal = sysconst.getDspval();
		if (StringUtil.isEmpty(dspVal)) {
			return handleValidateFalse("常量显示值不能为空");
		}
		sysconst.setDspval(dspVal.trim());

		String val = sysconst.getVal();
		if (StringUtil.isEmpty(val)) {
			return handleValidateFalse("常量值不能为空");
		}
		sysconst.setVal(val.trim());

		return null;
	}

	/****
	 * 更新系统常量 信息，返回json给客户端
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
			@Validated SysConst sysconst, BindingResult br,
			HttpServletRequest request) {

		if (br.hasErrors()) {
			return ErrorHandler.getRequestResultFromBindingResult(br);
		}

		// 非空校验，同时trim参数
		RequestResult rr = validateNotBlank(sysconst);
		if (rr != null) {
			return rr;
		}
		sysconst.set_id(_id);

		// 校验是否存在相同值的数据
		boolean isExitSameConstval = this.sysConstService
				.isExistSameConstval(sysconst);
		if (isExitSameConstval) {
			return this.handleValidateFalse("已经存在值为【" + sysconst.getVal()
					+ "】的常量!");
		}

		// 校验是否存在相同显示值的数据
		boolean isExitSameConstDispval = this.sysConstService
				.isExistSameConstDispval(sysconst);
		if (isExitSameConstDispval) {
			return this.handleValidateFalse("已经存在显示值为【" + sysconst.getDspval()
					+ "】的常量!");
		}

		try {
			DBObject updateResult = this.sysConstService.updatePart(null,
					sysconst);

			logger.debug("更新后的结果[{}]", updateResult);

			rr = new RequestResult();
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

			this.sysConstService.RemoveOneByIdLogic(_id);

			RequestResult rr = new RequestResult();
			rr.setSuccess(true);
			rr.setMessage(_id);
			return rr;
		} catch (Exception e) {
			return this.handleException(e);
		}
	}
}
