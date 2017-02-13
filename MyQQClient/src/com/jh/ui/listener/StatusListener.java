package com.jh.ui.listener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.jh.bean.Account;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.ui.common.CommonMethod;

public class StatusListener extends MouseAdapter {
	
	private JFrame frame;
	private JLabel statusLbl;
	private Account account;
	private Client client;
	
	public StatusListener(JFrame frame, JLabel statusLbl, Account account, Client client) {
		this.frame = frame;
		this.statusLbl = statusLbl;
		this.account = account;
		this.client = client;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPopupMenu menu = new JPopupMenu();
		StatusActionListener sal = new StatusActionListener(frame, statusLbl, account, client);
		JMenuItem item1 = new JMenuItem("��������", CommonMethod.getImg(frame, "online.png"));
		item1.setActionCommand("online");
		item1.addActionListener(sal);
		item1.setToolTipText("��ʾϣ�����ѿ���������");
		menu.add(item1);
		JMenuItem item2 = new JMenuItem("Q�Ұ�", CommonMethod.getImg(frame, "qme.png"));
		item2.setActionCommand("qme");
		item2.addActionListener(sal);
		item2.setToolTipText("��ʾϣ������������ϵ��");
		menu.add(item2);
		JMenuItem item3 = new JMenuItem("�뿪", CommonMethod.getImg(frame, "leave.png"));
		item3.setActionCommand("leave");
		item3.addActionListener(sal);
		item3.setToolTipText("��ʾ�뿪�����޷�������Ϣ");
		menu.add(item3);
		JMenuItem item4 = new JMenuItem("æµ", CommonMethod.getImg(frame, "busy.png"));
		item4.setActionCommand("busy");
		item4.addActionListener(sal);
		item4.setToolTipText("��ʾæµ�����ἰʱ������Ϣ");
		menu.add(item4);
		JMenuItem item5 = new JMenuItem("�������", CommonMethod.getImg(frame, "dont.png"));
		item5.setActionCommand("dont");
		item5.addActionListener(sal);
		item5.setToolTipText("��ʾ���뱻����");
		menu.add(item5);
		JMenuItem item6 = new JMenuItem("����", CommonMethod.getImg(frame, "hidden.png"));
		item6.setActionCommand("hidden");
		item6.addActionListener(sal);
		item6.setToolTipText("��ʾ���ѿ����������ߵ�");
		menu.add(item6);
		statusLbl.add(menu);
		menu.show(statusLbl, 20, 15);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (Constants.mainOk) {
			statusLbl.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190)));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		statusLbl.setBorder(null);
	}
}
