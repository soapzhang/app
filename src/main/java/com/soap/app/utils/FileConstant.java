/**
 * @版权所有 聚光科技（杭州）股份有限公司
 * 
 */
package com.soap.app.utils;

import org.springframework.beans.factory.annotation.Value;

/**
 * 
 * 文件常量
 * 
 */
public class FileConstant {
	/**
	 * 文件未找到路径
	 */
	public static final String FILE_NOT_FOUND_PATH_WITHOUT_SUFFIX = "/file/fileNotFound";

	/**
	 * 2003word后缀名
	 */
	public static final String SUFFIX_WORD_2003 = ".doc";

	/**
	 * 2007word后缀名
	 */
	public static final String SUFFIX_WORD_2007 = ".docx";

	/**
	 * 2003excel后缀名
	 */
	public static final String SUFFIX_EXCEL_2003 = ".xls";

	/**
	 * 2007excel后缀名
	 */
	public static final String SUFFIX_EXCEL_2007 = ".xlsx";

	/**
	 * txt后缀名
	 */
	public static final String SUFFIX_TXT = ".txt";

	/**
	 * pdf后缀名
	 */
	public static final String SUFFIX_PDF = ".pdf";

	/**
	 * swf工具根目录
	 */
	@Value("${swf.root}")
	public static final String SWF_ROOT = "";

	/**
	 * openoffice工具根目录
	 */
	@Value("${openoffice.root}")
	public static final String OPENOFFICE_ROOT = "";

	/**
	 * 文件转换时需要放到的本地目录
	 */
	@Value("${localFile.path}")
	public static final String LOCALFILE_PATH = "";
	

}
