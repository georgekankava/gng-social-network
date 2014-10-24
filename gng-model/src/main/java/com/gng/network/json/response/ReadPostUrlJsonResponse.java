package com.gng.network.json.response;

import java.util.List;

import com.gng.network.html.HTMLTag;

public class ReadPostUrlJsonResponse {
	private String pageMetaDescription;
	private List<HTMLTag> pageImages;
	
	public String getPageMetaDescription() {
		return pageMetaDescription;
	}
	public void setPageMetaDescription(String pageMetaDescription) {
		this.pageMetaDescription = pageMetaDescription;
	}
	public List<HTMLTag> getPageImages() {
		return pageImages;
	}
	public void setPageImages(List<HTMLTag> pageImages) {
		this.pageImages = pageImages;
	}

}
