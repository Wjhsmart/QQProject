package com.jh.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.common.DateUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.FriendMessageFrame;
import com.jh.ui.common.CommonMethod;

public class AddFriendActionListener implements ActionListener {

	private Account account;
	private Client client;
	private JFrame frame;
	private AccountDAO accountDAO;
	private String number;
	
	public AddFriendActionListener(JFrame frame) {
		this.account = SeekFriendTableMouseListener.account;
		this.client = SeekFriendTableMouseListener.client;
		this.number = SeekFriendTableMouseListener.number;
		this.accountDAO = new AccountDAOImpl();
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("add")) { // ����е����
			requstMessage();
		} else if (action.equals("datum")) { // ��񵯴��еĲ鿴����
			Account a = accountDAO.query(number); // �����˺�ȥ��ѯ���ݿ����Ϣ
			if (a != null) {
				if (Constants.isFriendMessageFrameOpen) {
					new FriendMessageFrame(a);
				}
			}
		} else if (action.equals("add1")) { // �������Ͽ��ϵ���Ӱ�ť
			requstMessage();
		}
	}
	
	private void requstMessage() {
		Account toAccount = accountDAO.query(number);
		boolean result = accountDAO.queryFriend(account.getNumber(), toAccount.getNumber());
		if (toAccount.getNumber().equals(account.getNumber())) {
			JOptionPane.showMessageDialog(frame, "��������Լ�Ϊ����", "��Ӻ���", JOptionPane.QUESTION_MESSAGE, CommonMethod.getImg(frame, "seek_icon_bottom.png"));
		} else if (result) {
			JOptionPane.showMessageDialog(frame, "�Է��Ѿ�����ĺ��ѣ��벻Ҫ�ظ����", "��Ӻ���", JOptionPane.QUESTION_MESSAGE, CommonMethod.getImg(frame, "seek_icon_bottom.png"));
		} else {
			Message m = new Message(Message.REQUST_MSG, account, toAccount, DateUtil.getDate(), "���������Ϊ����");
			client.sendMessage(m);
		}
	}

}
