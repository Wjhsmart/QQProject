package com.jh.bean;

import java.io.Serializable;
import java.util.Date;

public class RecordMessage implements Serializable{

	private static final long serialVersionUID = -3269456478784061170L;
	
	private String number; // �Լ����˺�
	private String toNumber; // ���ѵ��˺�
	private String messages; // ��Ϣ����
	private Date sendTime; // ����ʱ��

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
