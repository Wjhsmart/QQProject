package com.jh.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.DateUtil;
import com.jh.ui.ChatFrame;

public class ChatFrameBtnListener implements ActionListener {

	private Client client;
	private ChatFrame chatFrame;
	
	public ChatFrameBtnListener(ChatFrame chatFrame, Client client) {
		this.chatFrame = chatFrame;
		this.client = client;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("close")) { // �ر�
			chatFrame.dispose();
		} else if (action.equals("send")) { // ����
			String msg = chatFrame.getInputArea().getText(); // ��ȡ�������ֵ
			Message m = new Message(Message.NORMAL_MSG, chatFrame.getAccount(), chatFrame.getToAccount(), DateUtil.getDate(), msg);
			JTextArea area = chatFrame.getMessageArea(); // ��ȡ�������JTextArea
			String text = area.getText() + "\n" + m.getFromAccount().getNickname() + "  " + DateUtil.getDateStr(DateUtil.getDate()) + "\n" + msg; // ���Լ��Ľ�����ʾ�Ļ�
			area.setText(text);
			client.sendMessage(m);
			chatFrame.getInputArea().setText("");
		}
	}

}
