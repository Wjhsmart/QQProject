package com.jh.ui.listener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.common.DateUtil;
import com.jh.ui.ChatFrame;
import com.jh.ui.common.CommonMethod;

/**
 * ��������һ��JLabel�ĵ���¼�
 * @author Administrator
 *
 */
public class ChatMouseListener extends MouseAdapter {
	
	private JLabel label;
	private ChatFrame chatFrame;
	private Client client;
	private Account account;
	private Account toAccount;
	
	public ChatMouseListener(ChatFrame chatFrame, JLabel label, Client client) {
		this.label = label;
		this.chatFrame = chatFrame;
		this.client = client;
		this.account = chatFrame.getAccount();
		this.toAccount = chatFrame.getToAccount();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		String name = CommonMethod.getLabelName(e);
		if (name.equals("voice")) { // top_����ͨ������
			
		} else if (name.equals("video")) { // top_������Ƶͨ��
			
		} else if (name.equals("demonstrate")) { // top_Զ����ʾ
			
		} else if (name.equals("file")) { // top_�����ļ�
			
		} else if (name.equals("desktop")) { // top_Զ������
			
		} else if (name.equals("discuss")) { // top_����������
			
		} else if (name.equals("apply")) { // top_Ӧ��

		} else if (name.equals("font")) { // bottom_����
			
		} else if (name.equals("face")) { // bottom_����
			
		} else if (name.equals("magic")) { // bottom_ħ��
			
		} else if (name.equals("shake")) { // bottom_��һ��
			Message m = new Message(Message.SHAKE_MSG, account, toAccount, DateUtil.getDate(), account.getNumber() + "�����˶�һ��");
			client.sendMessage(m);
		} else if (name.equals("voice1")) { // bottom_������Ϣ
			setPopup("����������Ϣ", "������Ƶ����");
		} else if (name.equals("function")) { // bottom_�๦�����
			setPopup("��д����", "����ʶ��");
		} else if (name.equals("img")) { // bottom_ͼƬ
			JFileChooser jfc = new JFileChooser();
//			FileFilter ff = new FileNameExtensionFilter("ͼ���ļ�(JPG/GIF)", "JPG", "JPEG", "GIF"); // �����ļ�������
//			jfc.setFileFilter(ff);
			int result = jfc.showOpenDialog(chatFrame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile(); // ��ȡ��ѡ����ļ�
				Message m = new Message(Message.SEND_FILE_MSG, account, toAccount, DateUtil.getDate(), file.getName()); // ����һ�������ļ�����Ϣ
				try {
					FileInputStream fis = new FileInputStream(file); // ȥ�����ѡ�е��ļ�
					byte[] bytes = new byte[(int) file.length()]; // ����һ���ֽ����飬����Ϊ�ļ��Ĵ�С
					fis.read(bytes); // ���ļ���������ֽ���������
					m.setBytes(bytes); // ��ŵ�Message����
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				client.sendMessage(m);
			}
			
		} else if (name.equals("music")) { // bottom_���
			try {
				Runtime.getRuntime().exec("D:/�����װ/kugou/KGMusic/kugou"); // �����ṷ
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (name.equals("screen")) { // bottom_��ͼ
			JPopupMenu menu = new JPopupMenu();
			JMenuItem item1 = new JMenuItem("�½���ͼ");
			item1.setActionCommand("news");
			item1.addActionListener(new CutActionListener());
			menu.add(item1);
			JMenuItem item2 = new JMenuItem("�鿴��ͼ");
			item2.setActionCommand("seek");
			item2.addActionListener(new CutActionListener());
			menu.add(item2);
			label.add(menu);
			menu.show(label, 20, 20);
		} else if (name.equals("min")) { // ��С��
			chatFrame.setExtendedState(JFrame.ICONIFIED);
		} else if (name.equals("dispose")) { // �ر�
			chatFrame.setVisible(false);
			JTextArea area = chatFrame.getInputArea();
			String value = area.getText();
			if (!value.equals("") || value != null) {
				area.setText("");
			}
		} else if (name.equals("seek")) { // �鿴�ļ�
			if (Constants.isFile) {
				try {
					Desktop.getDesktop().open(new File("e:/workspace/MyQQClient/src/images/img/" + Constants.fileName));  
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		} else if (name.equals("open")) { // ���ļ�λ��
			try {
				Desktop.getDesktop().open(new File("e:/workspace/MyQQClient/src/images/img/"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		String name = CommonMethod.getLabelName(e);
		if (name.equals("min")) { // ��С��
			label.setIcon(CommonMethod.getImg(chatFrame, "min_over.png"));
		} else if (name.equals("dispose")) { // �ر�
			label.setIcon(CommonMethod.getImg(chatFrame, "exit_over.png"));
		} else if (name.equals("seek")) { // �鿴�ļ�
			if (Constants.isFile) {
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				label.setForeground(Color.RED);
			}
		} else if (name.equals("open")) { // ���ļ�λ��
			label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			label.setForeground(Color.RED);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		String name = CommonMethod.getLabelName(e);
		if (name.equals("min")) { // ��С��
			label.setIcon(CommonMethod.getImg(chatFrame, "min_def.png"));
		} else if (name.equals("dispose")) { // �ر�
			label.setIcon(CommonMethod.getImg(chatFrame, "exit_def.png"));
		} else if (name.equals("seek")) { // �鿴�ļ�
			label.setCursor(Cursor.getDefaultCursor());
			label.setForeground(Color.BLACK);
		} else if (name.equals("open")) { // ���ļ�λ��
			label.setCursor(Cursor.getDefaultCursor());
			label.setForeground(Color.BLACK);
		}
	}

	private void setPopup(String str, String str1) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item1 = new JMenuItem(str);
		menu.add(item1);
		JMenuItem item2 = new JMenuItem(str1);
		menu.add(item2);
		label.add(menu);
		menu.show(label, 20, 20);
	}
}
