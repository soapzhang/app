/**
 * @版权所有 聚光科技（杭州）股份有限公司
 * 
 */
package com.soap.app.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 下载工具类
 */
public class DownLoadUtil {
	private static final Logger LOG = LoggerFactory.getLogger( DownLoadUtil.class);

	public static String getFileNotFoundPath() {
		return FileConstant.FILE_NOT_FOUND_PATH_WITHOUT_SUFFIX + ".jsp";
	}

	/**
	 * 根据文件名获取ContentDisposition，避免中文乱码
	 * 
	 * @param request
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getContentDisposition(HttpServletRequest request,
                                               String fileName) {
		String finalName = "";
		try {
			// 默认采用URLEncoder编码 ，适用于IE浏览器
			String disposition = URLEncoder.encode(fileName,
			        FileConstants.UTF_8);
			boolean flag = true;
			// 如果为firefox浏览器
			if (request.getHeader(FileConstants.USER_AGENT).toLowerCase()
			        .indexOf(FileConstants.FIREFOX) > 0) {
				// 采用ISO编码的中文输出，适用于firefox、chrome、safari浏览器
				disposition = new String(
				        fileName.getBytes(FileConstants.UTF_8),
				        FileConstants.ISO_8859_1);
			} else if (request.getHeader(FileConstants.USER_AGENT)
			        .toLowerCase().indexOf(FileConstants.OPERA) > 0) {// 如果为opera浏览器
				// 采用filename*方式,适用于opera浏览器
				finalName = "filename*=UTF-8''" + disposition;
				flag = false;
			} else if (request.getHeader(FileConstants.USER_AGENT)
			        .toLowerCase().indexOf(FileConstants.APPLEWEBKIT) > 0) {// 如果为chrome浏览器
				disposition = new String(
				        fileName.getBytes(FileConstants.UTF_8),
				        FileConstants.ISO_8859_1);
			} else if (request.getHeader(FileConstants.USER_AGENT)
			        .toLowerCase().indexOf(FileConstants.SAFARI) > 0) {// 如果为safari浏览器
				disposition = new String(
				        fileName.getBytes(FileConstants.UTF_8),
				        FileConstants.ISO_8859_1);
			}
			if (flag) {
				finalName = "filename = \"" + disposition + "\"";
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error("字符转换出错：" + e.getMessage());
		}
		return finalName;
	}

	/**
	 * 返回没有文件页面
	 * 
	 * @param response
	 */
	public static boolean returnNoFilePage(HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			String errorPage = SpringContextUtil.getServletContext()
			        .getContextPath() + getFileNotFoundPath();
			out.print("<script type='text/javascript'>window.location.href=\""
			        + errorPage + "\"</script>");
			out.flush();
			out.close();
		} catch (IOException e1) {
			LOG.error("文件读写异常：" + e1.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 返回下载文件
	 * 
	 * @param response
	 * @param request
	 * @param file
	 *            文件
	 * @param name
	 *            文件名称（已根据浏览器类型转化）
	 */
	public static boolean returnFile(HttpServletResponse response,
                                     HttpServletRequest request, File file, String name) {
		try {
			if (file.exists()) {
				// 设置下载文件名
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.addHeader("Content-Disposition", "attachment; " + name);
				response.setContentLength((int) file.length());
				FileInputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[2048];
				int count = 0;
				while ((count = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
					outputStream.flush();
				}
				inputStream.close();
			} else {
				LOG.error(name + "文件未找到");
				return false;
			}
		} catch (FileNotFoundException e) {
			LOG.error(name + "文件未找到：" + e.getMessage());
			return false;
		} catch (IOException e) {
			LOG.error(name + "文件读写异常：" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 文件转byte
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			LOG.error("文件读写异常：" + e.getMessage());
		}
		return null;
	}
}
