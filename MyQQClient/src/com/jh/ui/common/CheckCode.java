package com.jh.ui.common;

import java.awt.image.BufferedImage;

/**
 * ����������֤��ͼƬ����֤�����
 * @author Administrator
 *
 */
public class CheckCode {

	private String checkCode; // ��֤��
	private BufferedImage buffImg; // ��֤��ͼƬ

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public BufferedImage getBuffImg() {
		return buffImg;
	}

	public void setBuffImg(BufferedImage buffImg) {
		this.buffImg = buffImg;
	}
}
