package com.jh.ui.common;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jh.bean.Account;
import com.jh.client.Client;
import com.jh.ui.listener.TrayActionListener;

/**
 * �����й�����һЩ����
 * @author Administrator
 *
 */
public class CommonMethod {
	
	public static String IMG_URL = "online"; // ����״̬
	public static String head = "head"; // ����ͷ��
	public static Client client;
	public static Account account;

	/**
	 * ��ȡͼƬ
	 * @param frame
	 * @param url
	 * @return
	 */
	public static Icon getImg(JFrame frame, String url) {
		return new ImageIcon(frame.getClass().getResource("/images/" + url));
	}
	
	/**
	 * ��ȡ��JLabel����
	 * @param e
	 * @return
	 */
	public static String getLabelName(MouseEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JLabel) {
			JLabel label = (JLabel) obj;
			return label.getName();
		}
		return null;
	}
	
	/**
	 * ��������
	 * @param frame
	 */
	public static void setTray(JFrame frame) {
		if (SystemTray.isSupported()) { // �ж�ϵͳ�Ƿ�֧������
			ImageIcon icon = new ImageIcon(frame.getClass().getResource("/images/tray_" + IMG_URL + ".png")); // ��ȡͼƬ·��
			PopupMenu popupMenu = new PopupMenu(); // �Ҽ�����
			MenuItem item1 = new MenuItem("��������");
			item1.setActionCommand("boundary");
			item1.addActionListener(new TrayActionListener(frame));
			MenuItem item2 = new MenuItem("�˳�");
			item2.setActionCommand("exit");
			item2.addActionListener(new TrayActionListener(frame));
			popupMenu.add(item1);
			popupMenu.add(item2);
			TrayIcon trayIcon = new TrayIcon(icon.getImage(), "QQ", popupMenu); // ��������ͼƬ����
			trayIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(true);
				}
			});
			SystemTray systemTray = SystemTray.getSystemTray(); // ���ϵͳ���̶���
			try {
				TrayIcon[] icons = systemTray.getTrayIcons(); // ��ȡ��ϵͳ����ͼ�������
				for (TrayIcon i : icons) {
					systemTray.remove(i); // ÿ���������ͼ��ǰ�����Ȱ�ԭ����ͼ��remove��
				}
				systemTray.add(trayIcon); // ������ͼƬ��ӵ�ϵͳ������
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}

	// �������
	public static void shortcutOpen(String url) {
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)) { // �жϵ�ǰϵͳ�Ƿ�֧�ִ������
			try {
				desktop.browse(new URI(url)); // ��ָ����URI
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// ��������
	public static void setLblCursor(JLabel label) {
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // �������Ϊ����
	}
	
	// ���������ΪĬ�ϵ�
	public static void setLblCursorDef(JLabel label) {
		label.setCursor(Cursor.getDefaultCursor()); // �������ΪĬ�ϵ�
	}

	public static void setPanel(JPanel panel) {
		panel.setLayout(null);
		panel.setBounds(10, 175, 260, 400);
	}
	
	/**
	 * �������1-6
	 */
	public static int myRandom() {
		Random random = new Random();
		int a = random.nextInt(6);
		if (a != 0) {
			return a;
		}
		return 2;
	}
}
