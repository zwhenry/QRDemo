package com.cn.demo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeDemo {

	/**
	 * ���ɶ�ά��ͼƬ
	 * 
	 * @param content ����
	 * @param imgType ͼƬ����
	 * @param version �汾
	 * @param pixel ģ������ֵ
	 * @param imgPath ͼƬ��ַ
	 */
	public void createQrcode(String content, String imgType, 
			int version,int pixel, String imgPath) {
		//ȷ����ά��ͼƬ��С
		int imageSize = (17 + 4*version)*pixel+4;
		
		// ͼƬ�������л�������
		BufferedImage bufImage = new BufferedImage(imageSize, imageSize,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufImage.createGraphics();
		graphics.setBackground(Color.WHITE);
		graphics.clearRect(0, 0, imageSize, imageSize);
		graphics.setColor(Color.BLACK);

		// ��Ϣת��Ϊ��ά����
		boolean[][] codeOut = changeContent(content, version);
		
		// ���ݶ�ά���ݶ�ͼƬ�������
		fillImage(graphics, pixel, codeOut);

		// ͼƬ���
		try {
			ImageIO.write(bufImage, imgType, new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����jar��������ת��ΪQrCode�Ķ�ά����
	 * 
	 * @param content
	 * @param version
	 * @return
	 */
	private boolean[][] changeContent(String content, int version) {
		boolean[][] codeOut = null;
		Qrcode qrcode = new Qrcode();
		// ���ö�ά���Ŵ��ʣ���ѡL(7%)��M(15%)��Q(25%)��H(30%)���Ŵ���Խ�߿ɴ洢����ϢԽ��
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		// �������ö�ά��ߴ磬ȡֵ��Χ1-40��ֵԽ��ߴ�Խ�󣬿ɴ洢����ϢԽ��
		qrcode.setQrcodeVersion(version);
		//��ʼ��������
		
		try {
			codeOut=qrcode.calQrcode(content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codeOut;
	}

	/**
	 * ��ͼƬ���ݽ������
	 * 
	 * @param graphics ����
	 * @param codeOut ���ܺ������
	 */
	private void fillImage(Graphics2D graphics, int pixel, boolean[][] codeOut) {
		// �������> ��ά��
		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; j++) {
				//���
				if(codeOut[i][j]){
					graphics.fillRect(i*pixel+2, j*pixel+2, pixel, pixel);
				}
			}
		}
	}

	public static void main(String[] args) {
		String content = "��ϲ�������ֻ���Ϊ�������ſδ����������ǣ�������á�����";
		String imgType = "png";
		Integer version = 5;//�汾
		int pixel = 5;//ģ������ֵ
		String imgPath = "E:/QRCodeDemo.png";
		//ʵ�ֶ�ά��
		QRCodeDemo demo =new QRCodeDemo();
		demo.createQrcode(content, imgType, version, pixel, imgPath);
		System.out.println("��ά��������ϣ���鿴");
	}

}
