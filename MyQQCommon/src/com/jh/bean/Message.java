package com.jh.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * QQ��Ϣ�࣬�������淢���ˣ������ˣ�����ʱ��ͷ��͵���Ϣ
 * 
 * @author Administrator
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1818829536440641381L;

	public static final int NORMAL_MSG = 1; // ��������Ϣ
	public static final int REQUST_MSG = 2; // ������Ϣ
	public static final int REQUST_REV_MSG = 3; // ��Ӻ��Ѻ�ͬ��󷵻���Ϣ
	public static final int LOGOUT_MSG = 4; // �˳���Ϣ
	public static final int UPDATE_FRIEND_MSG = 5; // ˢ�º����б���Ϣ
	public static final int UPDATE_DATA_MSG = 6; // �������޸����Ϻ󣬼�ʱ���º����б����͵���Ϣ
	public static final int SELECT_PWD_MSG = 7; // �޸�����ɹ�����Ϣ
	public static final int SEND_FILE_MSG = 8; // �����ļ����͵���Ϣ
	public static final int SHAKE_MSG = 9; // ��һ��
	
	private int type; // ��Ϣ����
	private Account fromAccount; // �����洢������
	private Account toAccount; // �����洢������
	private Date sendTime; // ����ʱ��
	private String message; // ���͵���Ϣ
	
	private byte[] bytes;

	public Message() {
	}

	public Message(int type, Account fromAccount, Account toAccount, Date sendTime, String message) {
		this.type = type;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.sendTime = sendTime;
		this.message = message;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}
