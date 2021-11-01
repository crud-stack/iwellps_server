package com.iwell.eye.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassFinderUtilForModelAlias {

	@SuppressWarnings("rawtypes")
	public static Class[] getClasses(String packageName) throws IOException, ClassNotFoundException {
		System.out.println("┌──────────────────────────────");
		System.out.println("│ Doing ClassFinderUtilForModel findClasses ...");
		System.out.println("│ ");
		System.out.println("│ Find and Aliase Class with Suffix 'vo', 'dto', 'model', 'form', 'result'");
		List<Class> classes = new ArrayList<Class>();
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:com/crud/**/**/*");
		for (Resource r : resources) {
			String filename = r.getURL().toString();
			if (filename.endsWith(".class")) {
				String fullName = r.getURL().toString().replaceAll("/", ".");
				String dummy = fullName.split(packageName)[0];
				fullName = fullName.replaceAll(dummy, "");
				fullName = fullName.substring(0, fullName.length() - 6);
				String simpleName = filename.substring(0, filename.length() - 6);
				String comparePattern = filename.substring(0, filename.length() - 6).toLowerCase();
				if (comparePattern.endsWith("vo") || comparePattern.endsWith("model")
						|| comparePattern.endsWith("dto") || comparePattern.endsWith("form") || comparePattern.endsWith("result")) {
					classes.add(Class.forName(fullName));
					System.out.println("│ Added Aliase Class :: " + fullName + " -> " + simpleName);
				}
			}
		}
		System.out.println ( "└──────────────────────────────"	);	
		return classes.toArray(new Class[classes.size()]);
	}

}