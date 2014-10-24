package com.gng.network.html;

import java.util.Arrays;
import java.util.List;


public class HTMLTag extends Tag {

	public static final List<String> htmlTagNames = Arrays.asList(
			"title",
			"link",
			"meta",
			"style",
			"p",
			"h1", "h2", "h3", "h4", "h5", "h6",
			"strong",
			"em",
			"abbr",
			"acronym",
			"address",
			"bdo",
			"blockquote",
			"cite",
			"q",
			"code",
			"ins",
			"del",
			"dfn",
			"kbd",
			"pre",
			"samp",
			"var",
			"br",
			"a",
			"base",
			"img",
			"area",
			"map",
			"object",
			"param",
			"ul",
			"ol",
			"li",
			"dl",
			"dt",
			"dd"
);
	
	@Override
	public String toString() {
		return "HTMLTag [tagName=" + super.getTagName() + ", attributes=" + super.getAttributes() + "]";
	}
	
}
