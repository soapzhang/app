/**
 * @版权所有 聚光科技（杭州）股份有限公司
 * 
 */
package com.soap.app.utils;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 文件操作
 *  
 * @author yunjie_zhu
 * @since Epoch 1.0
 * @created 2015年8月18日
 */
public class FileUtil {

	private static final String EXTENSION_XLS = "xls";

	private static final String EXTENSION_XLSX = "xlsx";

	/**
	 * 解析excel文件，list分别表示sheet 行 列
	 *
	 * @param file
	 * @return
	 * @throws
	 * @throws IOException
	 */
	public static List<List<List<String>>> parseExcelFile(MultipartFile file) throws FileNotFoundException, IOException {
		String fileName = file.getOriginalFilename();
		byte[] fileByte = null;
		InputStream inputStream = file.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<List<List<String>>> excelData = new ArrayList<List<List<String>>>();
		try {
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, count);
			}
			fileByte = outputStream.toByteArray();
			if (fileByte == null) {
				return null;
			}
			InputStream is = new ByteArrayInputStream(fileByte);
			Workbook workbook = null;
			if (fileName.endsWith(EXTENSION_XLS)) {
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith(EXTENSION_XLSX)) {
				workbook = new XSSFWorkbook(is);
			}
			if (workbook != null && workbook.getNumberOfSheets() >= 1) {
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					List<List<String>> sheetData = new ArrayList<List<String>>();
					Sheet sheet = workbook.getSheetAt(i);
					for (int r = 0; r < sheet.getLastRowNum() + 1; r++) {
						Row row = sheet.getRow(r);
						List<String> cols = new ArrayList<String>();
						// 解析的行为null
						if (row == null) {
							cols = null;
						} else {
							for (int c = 0; c < row.getLastCellNum(); c++) {
								Cell cell = row.getCell(c);
								String cellValue = getCellValue(cell, true);
								cols.add(cellValue == null || "(null)".equals(cellValue.trim()) ? null : cellValue);
							}
						}
						sheetData.add(cols);
					}
					excelData.add(sheetData);
				}
			}
		} catch (Exception e) {

		} finally {
			closeInputStream(inputStream);
			closeOutputStream(outputStream);
		}
		return excelData;
	}

	/**
	 * 取单元格的值
	 *
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	private static String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && HSSFDateUtil.isCellDateFormatted(cell)) {// 时间格式
			return DateUtils.formatDateTime(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (treatAsStr) {
				// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
				// 加上下面这句，临时把它当做文本来读取
				cell.setCellType(Cell.CELL_TYPE_STRING);
			}
			return String.valueOf(cell.getStringCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			return null;
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	/**
	 * @param inputStream
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static long write2Disk(InputStream inputStream, File file)
			throws FileNotFoundException, IOException {
		FileOutputStream outputStream = null;
		long totalCount = 0;
		try {
			outputStream = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer)) > 0) {
				totalCount += count;
				outputStream.write(buffer, 0, count);
				outputStream.flush();
			}
		} finally {
			closeOutputStream(outputStream);
		}
		return totalCount;
	}
	
	/**
	 * 获取文件字节流
	 * 
	 * @param id
	 * @return
	 */
	public static byte[] getFileByte(String id, File file)  {
		InputStream inputStream = getFileStream(id,file);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count = 0;
		try {
			while ((count = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, count);
			}
			return outputStream.toByteArray();
		} catch (IOException e) {
		} finally {
			closeInputStream(inputStream);
			closeOutputStream(outputStream);
		}
		return null;
	}
	
	/**
	 * 获取文件流
	 * @param id
	 * @return
	 */
	private static InputStream getFileStream(String id, File file)  {
		try {
			return new FileInputStream(file);
		} catch (FileNotFoundException e) {
			return null;
		}
	}	
	
	/**
	 * 关闭输入流.
	 * 
	 * @param inputStream
	 *            .
	 */
	private static void closeInputStream(InputStream inputStream)
	     {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {

			}
		}
	}

	/**
	 * 关闭输出流.
	 * 
	 * @param outputStream
	 *            .
	 */
	private static void closeOutputStream(OutputStream outputStream)
			{
		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}



}
