package com.jh.thread;

import javax.swing.JFrame;

import com.jh.bean.Account;
import com.jh.client.Client;
import com.jh.ui.ErrorFrame;
import com.jh.ui.MainFrame;

/**
 * ����߳�������һ�����ɵ�Ч�����ӳ�5s����ȥ�ж��˺������Ƿ���ȷ��
 * Ȼ��new����Ӧ�Ĵ��壬����˺�������ȷ����new�������壬����new��������
 * @author Administrator
 *
 */
public class BeingLoginThread implements Runnable {
	
	private JFrame frame;
	private Account account;
	public static boolean isOk = true;
	
	public BeingLoginThread(JFrame frame, Account account) {
		this.frame = frame;
		this.account = account;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (isOk) {
			frame.dispose();
			if (account != null) { // �ж��˺��Ƿ����
				Client client = new Client();
				client.login(account);
				new MainFrame(account, client);
			} else {
				new ErrorFrame();
			}
		}
		
	}

}
