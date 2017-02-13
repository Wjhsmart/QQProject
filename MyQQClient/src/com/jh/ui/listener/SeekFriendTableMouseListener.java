package com.jh.ui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.jh.bean.Account;
import com.jh.client.Client;

public class SeekFriendTableMouseListener extends MouseAdapter {

	public static String number;
	public static Account account;
	public static Client client;
	private JFrame frame;
	
	public SeekFriendTableMouseListener( JFrame frame, Account account1, Client client1) {
		account = account1;
		client = client1;
		this.frame = frame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource(); // ��ȡ������¼�
		int index = table.getSelectedRow(); // ��ȡ��table��������
		if (e.getButton() == MouseEvent.BUTTON3 && index > -1) { // MouseEvent.BUTTON3:��ʾ�һ�
			number = (String) table.getModel().getValueAt(index, 0); // ��ȡ��table�ĵ�һ�����ӵ���Ϣ
			JPopupMenu menu = new JPopupMenu();
			JMenuItem datumItem = new JMenuItem("�鿴����");
			datumItem.setActionCommand("datum");
			datumItem.addActionListener(new AddFriendActionListener(frame));
			menu.add(datumItem);
			JMenuItem addItem = new JMenuItem("��Ӻ���");
			addItem.setActionCommand("add");
			addItem.addActionListener(new AddFriendActionListener(frame));
			menu.add(addItem);
			table.add(menu);
			menu.show(table, e.getX(), e.getY());
		}
	}
}
