package com.misproto.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ValCodeGenerator {

	final static private char[] chars = "12345678ABCDEFHKLMPRSTUVWXabcdefhkmnrstuvwxy"
			.toCharArray();
	private static String[] fontNames = new String[] { "Courier", "Arial",
			"Verdana", "Georgia", "Times", "Tahoma" };
	private static int[] fontStyle = new int[] { Font.PLAIN, Font.BOLD,
			Font.ITALIC, Font.BOLD | Font.ITALIC };

	private static int width = 160;
	private static int height = 50;
	private static int charCnt = 4;
	private static int disturbLineNum = 10;

	public static String genCod(OutputStream os) throws IOException {
		// System.out.println("==========静态方法调用");
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 创建BufferedImage类的对象
		Graphics2D g = image.createGraphics(); // 创建Graphics类的对象
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, width, height);

		drawDisturbLine(g);

		BufferedImage[] bis = new BufferedImage[charCnt];
		char[] codes = generateCode();
		for (int i = 0; i < charCnt; i++) {
			bis[i] = generateBuffImg(codes[i]);
			g.drawImage(bis[i], null, (int) (height * 0.6) * i, 0);
		}

		g.dispose();
		ImageIO.write(image, "JPEG", os);
		os.close();
		String sRand = new String(codes);
		// System.out.println("--------" + sRand);

		return sRand;
	}

	private static BufferedImage generateBuffImg(char c) {
		String tmp = Character.toString(c);
		Color forecolor = getRandomColor();
		Color backcolor = new Color(255, 255, 255, 0);
		String fontName = getRandomFontName();
		int fontStyle = getRandomStyle();
		int fontSize = getRandomSize();
		int strX = (height - fontSize) / 2;
		int strY = (height - fontSize) / 2 + fontSize*5/6;

		BufferedImage ret = new BufferedImage(height, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) ret.createGraphics();
		g.setColor(backcolor);
		g.fillRect(0, 0, height, height);

		g.setColor(forecolor);
		g.setFont(new Font(fontName, fontStyle, fontSize));
		g.drawString(tmp, strX, strY);

		g.dispose();
		return ret;
	}

	private static Color getRandomColor() {
		int r = (int) (Math.random() * 10000) % 200;
		int g = (int) (Math.random() * 10000) % 200;
		int b = (int) (Math.random() * 10000) % 200;
		return new Color(r, g, b);
	}

	private static String getRandomFontName() {
//		int pos = (int) (Math.random() * 10000) % (fontNames.length);
//		return fontNames[pos];
		
		return fontNames[0];
	}

	private static int getRandomStyle() {
		int pos = (int) (Math.random() * 10000) % (fontStyle.length);
		return fontStyle[pos];
	}

	private static int getRandomSize() {
		int max = (int) (height * 0.98);
		int min = (int) (height * 0.75);
//		return (int) (Math.random() * 10000) % (max - min + 1) + min;
		return (int)(height * 0.85);
	}

	private static char[] generateCode() {
		char[] ret = new char[charCnt];
		for (int i = 0; i < charCnt; i++) {
			int letterPos = (int) (Math.random() * 10000) % (chars.length);
			ret[i] = chars[letterPos];
		}
		return ret;
	}

	private static void drawDisturbLine(Graphics graphics) {
		for (int i = 0; i < disturbLineNum; i++) {
			graphics.setColor(getRandomColor());
			int x = (int) (Math.random() * 10000) % (width + 1) + 1;
			int x1 = (int) (Math.random() * 10000) % (width + 1) + 1;
			int y = (int) (Math.random() * 10000) % (height + 1) + 1;
			int y1 = (int) (Math.random() * 10000) % (height + 1) + 1;
			graphics.drawLine(x, y, x1, y1);
		}

	}

	/****
	 * 测试方法
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream("d:/tmp1.gif");
		String codes = ValCodeGenerator.genCod(fos);
		System.out.println(codes);
	}
}
