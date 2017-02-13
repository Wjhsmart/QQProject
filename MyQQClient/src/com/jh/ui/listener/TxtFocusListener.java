package com.jh.ui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.DateUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.MainFrame;

public class TxtFocusListener implements FocusListener {

	private JTextField txt;
	private Account account;
	private Client client;
	private String name;
	private String value;
	private String lastValue;
	private MainFrame mainFrame;
	
	public TxtFocusListener(MainFrame mainFrame, JTextField txt, Account account) {
		this.txt = txt;
		this.account = account;
		this.mainFrame = mainFrame;
		this.client = mainFrame.getClient();
	}

	// ��ý���
	@Override
	public void focusGained(FocusEvent e) {
		name = ((JTextField) e.getSource()).getName();
		lastValue = txt.getText();
		if (lastValue.equals(" �༭����ǩ��") || lastValue.equals("  ��������ϵ�ˡ������顢Ⱥ����ҵ") || lastValue.equals("����QQ����/�ǳƲ��Һ���")) {
			txt.setText("");
		}
	}

	// ʧȥ����
	@Override
	public void focusLost(FocusEvent e) {
		name = ((JTextField) e.getSource()).getName();
		value = txt.getText();
		if (name.equals("seek")) { // ���������
			setTxt("����QQ����/�ǳƲ��Һ���");
		} else if (name.equals("autograph")) { // ����ǩ��
			if (value.equals("") || value == null) {
				txt.setText(" �༭����ǩ��");
			} else {
				AccountDAO accountDAO = new AccountDAOImpl();
				account.setAutograph(txt.getText());
				accountDAO.update(account);
				mainFrame.updaDataAutograph(account);
				Message m = new Message(Message.UPDATE_DATA_MSG, account, account, DateUtil.getDate(), account.getNumber() + "�Ѿ�����������");
				client.sendMessage(m);
			}
		} else if (name.equals("seek1")) { // ����
			setTxt("  ��������ϵ�ˡ������顢Ⱥ����ҵ");		
		}
	}
	
	private void setTxt(String str) {
		if (value.equals("") || value == null) {
			txt.setText(str);
		}
	}
}
