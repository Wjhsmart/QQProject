package com.jh.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.common.DateUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.AlterHeadFrame;
import com.jh.ui.MainFrame;
import com.jh.ui.SelectDataFrame;

public class DataFrameListener implements ActionListener {
	
	private SelectDataFrame dataFrame;
	private MainFrame mainFrame; // ������
	private AccountDAO accountDAO; // AccountDAO�����������ݿ����
	private Account a; // ���º��Account
	private Client client;
	
	public DataFrameListener(SelectDataFrame dataFrame, MainFrame mainFrame) {
		this.dataFrame = dataFrame;
		this.mainFrame = mainFrame;
		this.client = mainFrame.getClient();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		accountDAO = new AccountDAOImpl();
		a = dataFrame.saveAccount();
		if (action.equals("head")) { // ͷ��
			if (Constants.isAlterHeadFrameOpen) {
				new AlterHeadFrame();
			}
		} else if (action.equals("save")) { // ����
			updateUserData();
		} else if (action.equals("exit")) { // �ر�
			if (Constants.saveData) {
				if (!Constants.saveWin) {
					int result = JOptionPane.showConfirmDialog(dataFrame, "�Ƿ񱣴��û�����?", "����",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (result == JOptionPane.OK_OPTION) { // ����û������ȷ������������ݿ�
						updateUserData();
					}
				} else {
					dataFrame.dispose();
					Constants.select = true;
				}
			} else {
				dataFrame.dispose();
				Constants.select = true;
			}
		}
	}
	
	private void updateUserData() {
		accountDAO.update(a); // �������ݿ�
		Constants.saveWin = true;
		Account account = accountDAO.query(a.getNumber()); // ��ѯ���ݿ�
		mainFrame.updateUserData(account); // ��������������
		Message m = new Message(Message.UPDATE_DATA_MSG, account, account, DateUtil.getDate(), account.getNumber() + "�Ѿ��޸�����");
		client.sendMessage(m);
	}

}
