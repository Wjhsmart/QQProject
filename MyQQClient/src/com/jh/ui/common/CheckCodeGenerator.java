package com.jh.ui.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jh.ui.MainFrame;

/**
 * ��֤���ȡ��
 * @author Administrator
 *
 */
public class CheckCodeGenerator {

	public static final int LENGTH = 4; // ��֤�볤��
	public static final String CHECK_CODE_STR = "3456789abcdefghjkmnpqrstuvwxyABCDEFGHJKLMNPQRSTUVWXY"; // �����ȡ����֤��
	
	public static CheckCode getCheckCode() {
		BufferedImage bi = new BufferedImage(50,30,BufferedImage.TYPE_INT_RGB); // ��ȡ��һ��ͼƬ���ڴ���
		Graphics g = bi.getGraphics();
		Graphics gg = null;
		BufferedImage bi1 = null;
		String checkCode = "";
		for (int i = 0; i < LENGTH; i++) {
			int index = new Random().nextInt(CHECK_CODE_STR.length()); // �������������һ���ֵ�����
			checkCode += CHECK_CODE_STR.charAt(index); // ���������������׷�ӵ��ַ�����
		}
		try {
			bi1 = ImageIO.read(MainFrame.class.getResource("/images/verify.png")); // ��ȡ��һ��ͼƬ
			gg = bi1.getGraphics(); 
			gg.setColor(Color.RED);
			gg.setFont(new Font("΢���ź�", Font.BOLD, 12));
		} catch (IOException e) {
			e.printStackTrace();
		}
		gg.drawString(checkCode, 8, 20); // ������������ַ�����������ͼƬ��
		g.drawImage(bi1, 0, 0, null); // Ȼ�������ͼƬ����ԭ�Ȼ����ڴ��ͼƬ��
		CheckCode cc = new CheckCode();
		cc.setBuffImg(bi); // ������ͼƬ��������
		cc.setCheckCode(checkCode); // ����֤��Ҳ��������
		return cc;
	}
}
