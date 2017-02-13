package com.jh.ui.panel;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.jh.bean.Account;
import com.jh.ui.MainFrame;
import com.jh.ui.common.CommonMethod;

/**
 * List�б���Ⱦ��
 * @author Administrator
 *
 */
public class FriendListCellRenderer implements ListCellRenderer<Account> {
	
	private MainFrame mainFrame;
	
	public FriendListCellRenderer(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Account> list, Account value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 260, 80);
		panel.setBorder(null);
		panel.setOpaque(false);
		// ͷ��
		JLabel headLbl = new JLabel(CommonMethod.getImg(mainFrame, value.getHead() + ".png"));
		headLbl.setBounds(10, 0, 80, 80);
		panel.add(headLbl);
		// �ǳ�
		JLabel nicknameLbl = new JLabel("  " + value.getNickname());
		nicknameLbl.setBounds(90, 10, 100, 20);
		panel.add(nicknameLbl);
		// ����״̬
		String statu = value.getStatus();
		if (statu.equals("online")) {
			statu = "����";
		} else if (statu.equals("leave")) {
			statu = "�뿪";
		} else if (statu.equals("busy")) {
			statu = "æµ";
		} else if (statu.equals("qme")) {
			statu = "Q�Ұ�";
		} else if (statu.equals("dont")) {
			statu = "�������";
		} else if (statu.equals("hidden")) {
			statu = "����";
		} else if (statu.equals("offline")) {
			statu = "����";
		}
		JLabel statusLbl = new JLabel("  " + statu);
		statusLbl.setBounds(90, 30, 80, 20);
		panel.add(statusLbl);
		// ����ǩ��
		String values = value.getAutograph();
		if (values == null || values.equals("")) {
			values = " ";
		}
		JLabel autographLbl = new JLabel("  ǩ����" + values);
		autographLbl.setBounds(90, 50, 200, 20);
		panel.add(autographLbl);
		if (cellHasFocus) { // �����
			panel.setOpaque(true);
			panel.setBackground(new Color(0.15f, 0.15f, 0.15f, 0.2f));
		} else {
			panel.setOpaque(false);
		}
		if (isSelected) { // ��꾭��
			panel.setOpaque(true);
			panel.setBackground(new Color(0.15f, 0.15f, 0.15f, 0.2f));
		} else {
			panel.setOpaque(false);
		}
		return panel;
	}

}
