package com.jh.ui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.DateUtil;
import com.jh.ui.common.UIUtil;

public class ExitListener extends MouseAdapter {

	private Client client;
	private Account account;
	
	public ExitListener(Client client, Account account) {
		this.client = client;
		this.account = account;
	}
	
	// ��굥��ʱ����
	@Override
	public void mouseClicked(MouseEvent e) {
		if (client != null) {
			Message m = new Message(Message.LOGOUT_MSG, account, account, DateUtil.getDate(), "�˳�");
			client.sendMessage(m);
		}
		System.exit(0);
	}
	
	// ��꾭��ʱ����
	@Override
	public void mouseEntered(MouseEvent e) {
		UIUtil.change(e, this.getClass().getResource("/images/exit_over.png"));
	}
	
	// ����뿪ʱ����
	public void mouseExited(MouseEvent e) {
		UIUtil.change(e, this.getClass().getResource("/images/exit_def.png"));
	}
}
