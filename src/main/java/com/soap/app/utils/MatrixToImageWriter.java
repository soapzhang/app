package com.soap.app.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成二维码
 */
public final class MatrixToImageWriter implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 4321093449233369765L;
	
	/** ** 管理器 ** */
	private static MatrixToImageWriter instance = null;
	
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	private static String FORMAT = "png";
	
    // 图片宽度的一般  
    private static final int IMAGE_WIDTH = 80;  
    private static final int IMAGE_HEIGHT = 80;  
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;  
    private static final int FRAME_WIDTH = 2; 
    
    private File logFile;

	public MatrixToImageWriter() {
		logFile = new File(this.getClass().getClassLoader().getResource("custom/oms/fpi.png").getPath());
	}
	
	/**
	 * 实例化之
	 * 
	 * @return
	 */
	public static MatrixToImageWriter getInstance() {
		if (instance == null) {
			instance = new MatrixToImageWriter();
		}
		return instance;
	}

	/**
	 * 转换图片
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 写入文件
	 * 
	 * @param str
	 * @param format
	 * @param file
	 * @throws Exception
	 */
	public void writeToFile2(String str, String format, File file)
			throws Exception {
		// 二维码的图片格式
//		if (Validator.isNotNull(format)) {
//			FORMAT = format;
//		}
//		HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
//		// 内容所使用编码
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
//		BufferedImage image = toBufferedImage(bitMatrix);
		BufferedImage image = genBarcode(str,WIDTH, HEIGHT,"");
		if (!ImageIO.write(image, FORMAT, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}
	
	/**
	 * 写入文件
	 * 
	 * @param str
	 * @param format
	 * @param file
	 * @throws Exception
	 */
	public void writeToFile(String areaName, String companyName, String siteName, String str, String format, File file)
			throws Exception {
		// 二维码的图片格式
//		if (Validator.isNotNull(format)) {
//			FORMAT = format;
//		}
		BufferedImage image = genBarcode(str,WIDTH, HEIGHT,"");
		
		image = addImgText(image, WIDTH, HEIGHT, areaName,companyName,siteName);
		
		if (!ImageIO.write(image, FORMAT, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}
	
	/**
	 * 添加文字
	 * 
	 * @param srcImg
	 * @param width
	 * @param height
	 * @param areaName
	 * @param siteName
	 * @return
	 */
	public BufferedImage addImgText(BufferedImage srcImg, int width,
                                    int height, String areaName, String companyName, String siteName){
		
		int newHeight = height+160;
        BufferedImage image = new BufferedImage(width, newHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //g.setColor(new Color(200,220,200)); 
        //g.setBackground(Color.CYAN);
        g.fillRect(0, 0, width, newHeight);
        g.drawImage(srcImg,0, 100,width, height,  
                    Color.white, null);
        g.setColor(Color.BLACK);
        Font font20=new Font("宋体", Font.PLAIN,20);
        Font font15=new Font("宋体", Font.PLAIN,14);
        g.setFont(font20);
        g.drawString(companyName, getXLocation(companyName),50); 
        g.drawString(siteName, getXLocation(siteName), 75);         
        g.drawString(areaName, getXLocation(areaName),105);
        g.setFont(font15);        
        g.drawString("聚光科技(杭州)股份有限公司", 60, height+120);
        g.drawString("400-700-7555", 100, height+140);
        g.dispose();  
        
        return image; 		
	}
	
	/**
	 * 文字中间对齐
	 * @param text
	 * @return
	 */
	private int getXLocation(String text){
		int len = 300-text.length()*20;
		if(len<0){
			return 0;
		}
		return len/2;
	}
	
	/**
	 * 生成带log的二维码
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @param srcImagePath
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	private BufferedImage genBarcode(String content, int width,
                                     int height, String srcImagePath) throws WriterException,
            IOException {
        // 读取源图像  
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
                IMAGE_HEIGHT, true);  
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];  
        for (int i = 0; i < scaleImage.getWidth(); i++) {  
            for (int j = 0; j < scaleImage.getHeight(); j++) {  
                srcPixels[i][j] = scaleImage.getRGB(i, j);  
            }  
        }  
  
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        // 生成二维码  
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,  
                width, height, hint);  
  
        // 二维矩阵转为一维像素数组  
        int halfW = matrix.getWidth() / 2;  
        int halfH = matrix.getHeight() / 2;  
        int[] pixels = new int[width * height];  
  
        for (int y = 0; y < matrix.getHeight(); y++) {  
            for (int x = 0; x < matrix.getWidth(); x++) {  
                // 读取图片  
                if (x > halfW - IMAGE_HALF_WIDTH  
                        && x < halfW + IMAGE_HALF_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH  
                        && y < halfH + IMAGE_HALF_WIDTH) {  
                    pixels[y * width + x] = srcPixels[x - halfW  
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];  
                }   
                // 在图片四周形成边框  
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH  
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                - IMAGE_HALF_WIDTH + FRAME_WIDTH)  
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH  
                                && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH  
                                && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH  
                                + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {  
                    pixels[y * width + x] = 0xfffffff;  //白色
                } else {  
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；  
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000  
                            : 0xfffffff;  
                }  
            }  
        }  
  
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);  
  
        return image;  
    }  
  
    /** 
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标 
     *  
     * @param srcImageFile 
     *            源文件地址 
     * @param height 
     *            目标高度 
     * @param width 
     *            目标宽度 
     * @param hasFiller 
     *            比例不对时是否需要补白：true为补白; false为不补白; 
     * @throws IOException
     */  
    private BufferedImage scale(String srcImageFile, int height,
                                int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例  
       // File file = this.getClass().getClassLoader()//new File(srcImageFile);  
        BufferedImage srcImage = ImageIO.read(logFile);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例  
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {  
            if (srcImage.getHeight() > srcImage.getWidth()) {  
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();  
            } else {  
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();  
            }  
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);  
        }  
        if (hasFiller) {// 补白  
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);  
            if (width == destImage.getWidth(null)) {  
                graphic.drawImage(destImage, 0,  
                        (height - destImage.getHeight(null)) / 2,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);
            }
            else {  
                graphic.drawImage(destImage,  
                        (width - destImage.getWidth(null)) / 2, 0,  
                        destImage.getWidth(null), destImage.getHeight(null),  
                        Color.white, null);
            }
            graphic.dispose();  
            destImage = image;  
        }  
        return (BufferedImage) destImage;
    } 

//	/**
//	 * 写入二进制流
//	 * 
//	 * @param str
//	 * @param format
//	 * @param stream
//	 * @throws Exception
//	 */
//	public static void writeToStream(String str, String format,
//			OutputStream stream) throws Exception {
//		// 二维码的图片格式
//		HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
//		if (Validator.isNotNull(format)) {
//			FORMAT = format;
//		}
//		// 内容所使用编码
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
//		BufferedImage image = toBufferedImage(bitMatrix);
//		if (!ImageIO.write(image, FORMAT, stream)) {
//			throw new IOException("Could not write an image of format "
//					+ format);
//		}
//	}

}