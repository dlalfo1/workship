package com.gdu.workship.util;

import java.io.File;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {

	private String sep = Matcher.quoteReplacement(File.separator);
	
	
	
}
