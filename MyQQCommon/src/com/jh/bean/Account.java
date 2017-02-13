package com.jh.bean;

import java.io.Serializable;

/**
 * QQ�˺��࣬�����洢QQ�û���һЩ������Ϣ
 * 
 * ʵ��Serializable�ӿ��ṩ���кţ����������������ϴ��Ͷ���
 * 
 * @author Administrator
 *
 */
public class Account implements Serializable {

	private static final long serialVersionUID = -1144616260318367649L;
	
	private String number; // QQ�˺�
	private String pwd; // QQ����
	private String nickname; // QQ�ǳ�
	private String gender; // �Ա�
	private int age; // ����
	private String autograph; // ǩ��
	private String provice; // ʡ
	private String city; // ��
	private String area; // ��
	private String head; // ͷ��
	private String mobile; // �绰����
	private String status; // ����״̬
	private String skin; // Ƥ��

	public Account() {
	}

	public Account(String number, String pwd, String nickname) {
		this.number = number;
		this.pwd = pwd;
		this.nickname = nickname;
	}
	
	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public String toString() {
		return "Account [number=" + number + ", pwd=" + pwd + ", nickname=" + nickname + ", gender=" + gender + ", age="
				+ age + ", autograph=" + autograph + ", provice=" + provice + ", city=" + city + ", area=" + area
				+ ", head=" + head + ", mobile=" + mobile + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
}
