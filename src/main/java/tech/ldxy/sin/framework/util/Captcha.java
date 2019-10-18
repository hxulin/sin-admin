package tech.ldxy.sin.framework.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 功能描述: 验证码工具类
 *
 * @author hxulin
 */
public class Captcha {
	
	// 图片的宽度
	private int width = 160;
	// 图片的高度
	private int height = 40;
	
	// 验证码字符个数
	private int codeCount = 4;
	// 验证码干扰线数
	private int lineCount = 20;
	
	// 验证码
	private String code = null;
	// 验证码图片Buffer
	private BufferedImage buffImg = null;
	private Random random = new Random();

	public Captcha() {
		createImage();
	}

	public Captcha(int width, int height) {
		this.width = width;
		this.height = height;
		createImage();
	}

	public Captcha(int width, int height, int codeCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		createImage();
	}

	public Captcha(int width, int height, int codeCount, int lineCount) {
		this.width = width;
		this.height = height;
		this.codeCount = codeCount;
		this.lineCount = lineCount;
		createImage();
	}

	// 生成图片
	private void createImage() {
		int fontWidth = width / codeCount;  // 字体的宽度
		int fontHeight = height - 5;  // 字体的高度
		int codeY = height - 8;

		// 图像buffer
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = buffImg.getGraphics();
		// 设置背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设置字体
		Font font = new Font("monospace", Font.BOLD + Font.ITALIC, fontHeight);
		g.setFont(font);

		// 设置干扰线
		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width);
			int ye = ys + random.nextInt(height);
			g.setColor(getRandColor(1, 255));
			g.drawLine(xs, ys, xe, ye);
		}

		// 添加噪点
		float yawpRate = 0.01f;  // 噪声率
		int area = (int) (yawpRate * width * height);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			buffImg.setRGB(x, y, random.nextInt(255));
		}

		String str1 = randomStr(codeCount);  // 得到随机字符
		this.code = str1;
		for (int i = 0; i < codeCount; i++) {
			String strRand = str1.substring(i, i + 1);
			g.setColor(getRandColor(1, 255));
			g.drawString(strRand, i * fontWidth + 3, codeY);
		}

	}

	// 得到随机字符串
	private String randomStr(int len) {
		String str = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefhijkmnprstuvwxyz2345678";
		StringBuilder sBuilder = new StringBuilder(len);
		double r;
		for (int i = 0; i < len; i++) {
			r = (Math.random()) * (str.length());
			sBuilder.append(str.charAt((int) r));
		}
		return sBuilder.toString();
	}

	// 得到随机颜色
	private Color getRandColor(int fc, int bc) {  // 给定范围获得随机颜色
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public void write(OutputStream outputStream) throws IOException {
		ImageIO.write(buffImg, "png", outputStream);
		outputStream.close();
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public String getCode() {
		return code.toLowerCase();
	}
}
