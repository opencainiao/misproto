package com.misproto.common.globalobj;

import java.util.List;

import org.mou.common.JsonUtil;

@SuppressWarnings("rawtypes")
public class PageVO {
	private int total; // �ܼ�¼��
	private int page; // ��ǰ�ǵڼ�ҳ
	private int maxPage; // �ܹ�����ҳ
	private int pageCount; // ÿҳ������

	private int offSet; // ��ѯ��ʼ����(�����)
	private List rows; // ���

	public PageVO() {
		offSet = 0;
		pageCount = Integer.MAX_VALUE;
	}

	/****
	 * ��ݴ���ĵ�ǰҳ�룬�����ѯ�����е�OFFSET
	 */
	public void calOffset() {
		this.offSet = this.page * this.pageCount;
	}

	/****
	 * �����ʼ������㵱ǰ�ǵڼ�ҳ
	 */
	private void calCurrentPage() {
		page = offSet / pageCount + 1;
	}

	/****
	 * ����ܼ�¼����㹲�ж���ҳ
	 */
	public void calMaxPage() {
		maxPage = total / pageCount;

		if (total % pageCount != 0) {
			maxPage += 1;
		}
	}

	/****
	 * ��ݼ�¼����Ϣ������ҳ����Ϣ
	 */
	public void calPageInf() {
		calMaxPage();
		calCurrentPage();
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int totalCount) {
		this.total = totalCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int currentPage) {
		this.page = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String toString() {
		return JsonUtil.toJsonStr(this);
	}
}
