package com.cn.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class BorderQRCodeDemo {

	/**
	 * 生成二维码图片
	 * 
	 * @param content
	 *            内容
	 * @param imgType
	 *            图片类型
	 * @param version
	 *            版本
	 * @param pixel
	 *            模块像素值
	 * @param imgPath
	 *            图片地址
	 */
	public void createQrcode(String content, String imgType, int version,
			int pixel, String imgPath) {
		try {
			// 个性化底图
			BufferedImage bufImage = ImageIO.read(new File("image/底图.jpg"));
			Graphics2D graphics = bufImage.createGraphics();
			graphics.setColor(Color.GREEN);
			//图片大小
			int imageSize = bufImage.getWidth();
			// 信息转换为二维数组
			boolean[][] codeOut = changeContent(content, version);
			// 根据二维数据对图片进行填充
			fillImage(graphics, pixel, codeOut);
			// 获取图标
			BufferedImage logo = ImageIO.read(new File("image/大图标.jpg"));
			// 获取图片大小
			int width = logo.getWidth();
			int height = logo.getHeight();
			// 填充起始位置
			int x = imageSize / 2 - width / 2;
			int y = imageSize / 2 - height / 2;
			// 填充图标
			graphics.drawImage(logo, x, y, width, height, null);

			// 图片输出
			ImageIO.write(bufImage, imgType, new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用jar包把内容转换为QrCode的二维数组
	 * 
	 * @param content
	 * @param version
	 * @return
	 */
	private boolean[][] changeContent(String content, int version) {
		boolean[][] codeOut = null;
		Qrcode qrcode = new Qrcode();
		// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
		qrcode.setQrcodeVersion(version);
		// 开始加密内容
		try {
			codeOut = qrcode.calQrcode(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codeOut;
	}

	/**
	 * 对图片内容进行填充
	 * 
	 * @param graphics
	 *            画板
	 * @param codeOut
	 *            加密后的内容
	 */
	private void fillImage(Graphics2D graphics, int pixel, boolean[][] codeOut) {
		// 输出内容> 二维码
		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; j++) {
				// 填充
				if (codeOut[i][j]) {
					 graphics.fillRect(i*pixel+113, j*pixel+87, pixel, pixel);
				}
			}
		}
	}

	public static void main(String[] args) {
		String content = "恭喜，您的手机成为《青鸟优课大赛》幸运星，您将获得。。。";
		String imgType = "png";
		Integer version = 5;// 版本
		int pixel = 8;// 模块像素值
		String imgPath = "E:/BorderQRCodeDemo.png";
		// 实现个性化二维码
		BorderQRCodeDemo demo = new BorderQRCodeDemo();
		demo.createQrcode(content, imgType, version, pixel, imgPath);
		System.out.println("个性化二维码生成完毕，请查看");
	}

}
