package com.misproto.common.globalhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.misproto.common.globalobj.PageVO;
import com.mongodb.DBObject;

/****
 * 对分页查询的结果进行封装处理
 * 
 * @author NBQ
 *
 */
public class PageSearchResultHandler {

	private static final int PAGECOUNTNOPAGE = 100;
	private static final int CURPAGENUMNOPAGE = 1;

	/****
	 * 提取请求中的分页参数
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Integer> getPageInfoFromRequest(
			HttpServletRequest request) {

		Map<String, Integer> pageInfo = new HashMap<String, Integer>();

		int curpagenum = 1;
		int pagecount = 15;

		if (request.getParameter("page") != null) {
			curpagenum = Integer.parseInt(request.getParameter("page"));
		} else {
			curpagenum = CURPAGENUMNOPAGE;
		}

		if (request.getParameter("rp") != null) {
			pagecount = Integer.parseInt(request.getParameter("rp"));
		} else {
			pagecount = PAGECOUNTNOPAGE;
		}

		if (request.getParameter("total") != null) {
			int total = Integer.parseInt(request.getParameter("total"));
			if (total != -1) {
				pageInfo.put("total", total);
			}
		}

		pageInfo.put("curpagenum", curpagenum);
		pageInfo.put("pagecount", pagecount);

		return pageInfo;
	}

	/****
	 * 根据分页信息和数据，组装flexigrid需要的结果
	 * 
	 * @param list
	 * @param curpage
	 * @param pageCount
	 * @param total
	 * @return
	 */
	public static PageVO handleDBObjList(List<DBObject> list, int curpage,
			int pageCount, int total) {

		PageVO pageVO = new PageVO();
		pageVO.setPage(curpage);
		pageVO.setPageCount(pageCount);
		pageVO.setTotal(total);

		if (list != null) {
			for (DBObject dbobj : list) {
				dbobj.put("_id_m", dbobj.get("_id").toString());
			}
			pageVO.setRows(list);
		}

		return pageVO;
	}

	/****
	 * 根据数据，组装flexigrid需要的结果（无分页的情况）
	 * 
	 * @param list
	 * @param curpage
	 * @param pageCount
	 * @param total
	 * @return
	 */
	public static PageVO handleDBObjListNoPage(List<DBObject> list) {

		handleDBObjList(list);
		
		PageVO pageVO = new PageVO();
		if (list != null) {
			pageVO.setPage(1);
			pageVO.setPageCount(list.size());
			pageVO.setTotal(list.size());

			for (DBObject dbobj : list) {
				dbobj.put("_id_m", dbobj.get("_id").toString());
			}
			pageVO.setRows(list);
		}

		return pageVO;
	}

	/****
	 * 设置_id_m的字符串属性
	 * 
	 * @param list
	 */
	public static List<DBObject> handleDBObjList(List<DBObject> list) {

		if (list != null) {
			for (DBObject dbobj : list) {
				dbobj.put("_id_m", dbobj.get("_id").toString());
			}
		}

		return list;
	}

}
