package util;

import java.util.List;

public class Pageset<T> {

	private List<T> result;
	private int size;
	private int pageSize;
	private int page;
	private int pages;

	public Pageset() {
	}

	public Pageset(List<T> result, int size, int pageSize, int page) {
		super();
		this.result = result;
		this.size = size;
		this.pageSize = pageSize;
		this.page = page;
		calculatePagesAmount();
	}


	public List<T> getResult() {
		return result;
	}


	public void setResult(List<T> result) {
		this.result = result;
	}

	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
		calculatePagesAmount();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public int getPages() {
		return pages;
	}


	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		calculatePagesAmount();
	}
	

	protected void calculatePagesAmount(){
		if (!ValidationUtil.isEmpty(getPageSize()) && !ValidationUtil.isEmpty(getSize())) {
			this.pages = CastClassUtil.toInteger(Math.ceil(getSize() / getPageSize()),0);
		}
	}
}