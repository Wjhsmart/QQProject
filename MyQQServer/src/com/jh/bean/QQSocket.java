package com.jh.bean;

import java.net.Socket;

/**
 * ����������뵽����˵�QQ�û������Ӧ�Ľ�����Socket���ӹ�ϵ��һ��QQ�û�����ö�Ӧһ��Socket����
 * @author Administrator
 *
 */
public class QQSocket {

	private Account account;
	private Socket socket;
	
	public QQSocket() {}
	
	public QQSocket(Account account, Socket socket) {
		this.account = account;
		this.socket = socket;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
}
