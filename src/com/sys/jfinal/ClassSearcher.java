package com.sys.jfinal;

import java.io.File;

import java.net.URL;

import java.util.ArrayList;

import java.util.List;
/**
 * Class lookup
 * 
 * huilet 2013-3-20
 * @author yuanc
 */
public class ClassSearcher {
	private static List<File> classFiles = new ArrayList<File>();
	/**
	 * Recursive lookup file
	 * @param baseDirName
	 *            Folder path to find
	 * @param targetFileName
	 *            File name to find
	 */
	public static List<File> findFiles(String baseDirName,
			String targetFileName) {
		/**
		 * Algorithm description： Starting from a given search folder，Search for all sub folders and files in this folder，
		 * If the document，Then match，Matching success is added to the result set，If you are a child folder，Then enter the queue。 Queue not empty，Repeat the above operation，Queue is empty，End of program，Return result。
		 */
		String tempName = null;
		// 判断目录是否存在
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
		} else {
			File[] filelist = baseDir.listFiles();
			if(filelist!=null)
			for(File f:filelist){
				if(f.isDirectory()==true){
					findFiles(f.getPath(),targetFileName);
				}else{
					tempName = f.getName();
					if (ClassSearcher.wildcardMatch(targetFileName, tempName)) {
						classFiles.add(f.getAbsoluteFile());
					}
				}
			}
		}
		return classFiles;
	}
	/**
	 * Find by parent class
	 * @param clazz The parent class
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> findClasses(Class clazz) {
		List<Class> classList = new ArrayList<Class>();
		URL classPathUrl = ClassSearcher.class.getResource("/");
		List<File> classFileList = findFiles(classPathUrl.getFile(), "*.class");
//		String lib = new File(classPathUrl.getFile()).getParent() + "/lib/";
		for (File classFile : classFileList) {
			String className = className(classFile, "/classes");
			try {
				Class<?> classInFile = Class.forName(className);
				if (classInFile.getSuperclass() == clazz) {
					classList.add(classInFile);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classList;
	}
	@SuppressWarnings("rawtypes")
	public static List<Class> findClasses() {
		List<Class> classList = new ArrayList<Class>();
		URL classPathUrl = ClassSearcher.class.getResource("/");
		List<File> classFileList = findFiles(classPathUrl.getFile(), "*.class");
//		String lib = new File(classPathUrl.getFile()).getParent() + "/lib/";
		for (File classFile : classFileList) {
			String className = className(classFile, "/classes");
			try {
				Class<?> classInFile = Class.forName(className);
				classList.add(classInFile);
			} catch (Exception e) {
				continue;
			}
		}
		return classList;
	}
	private static String className(File classFile, String pre) {
		String objStr = classFile.toString().replaceAll("\\\\", "/");
		String className;
		className = objStr.substring(objStr.indexOf(pre) + pre.length(),
		objStr.indexOf(".class"));
		if (className.startsWith("/")) {
			className = className.substring(className.indexOf("/") + 1);
		}
		return className.replaceAll("/", ".");
	}
	/**
	 * Wildcard matching
	 * @param pattern
	 *            wildcard pattern
	 * @param str
	 *            String to be matched
	 * @return Matching success is returnedtrue，Otherwise returnfalse
	 */
	private static boolean wildcardMatch(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardMatch(pattern.substring(patternIndex + 1),
					str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else if (ch == '?') {
				// 通配符问号?表示匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配?了。
					return false;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}
}