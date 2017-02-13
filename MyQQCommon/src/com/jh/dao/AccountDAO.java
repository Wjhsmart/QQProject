package com.jh.dao;

import java.util.List;

import com.jh.bean.Account;
import com.jh.bean.Message;

public interface AccountDAO {

	/**
	 * ����Account��������ݿ��������
	 * @param account
	 * @return
	 */
	public Account add(Account account); 
	
	/**
	 * ��ѯ�������Ѿ�ע���Account
	 * @param account
	 * @return
	 */
	public List<Account> queryAll();
	
	/**
	 * �����˺������ѯ���ݿ�
	 * @param number
	 * @param pwd
	 * @return
	 */
	public Account query(String number, String pwd);
	
	/**
	 * �����˺�ȥ��ѯ���ݿ�
	 * @param number
	 * @return
	 */
	public Account query(String number);
	
	/**
	 * ��ѯ���ݿ��������Ϣ,���˴��ݽ����Ķ���
	 * @return
	 */
	public List<Account> queryAll(Account account); 
	
	/**
	 * �����˺Ż��ǳƲ�ѯ��Ϣ
	 * @param number
	 * @param nickname
	 * @return
	 */
	public List<Account> querySingle(String number, String nickname); 
	
	/**
	 * �����˺Ÿ������ݿ������
	 * @param account
	 */
	public void update(Account account); 
	
	/**
	 * �����˺�ȥ��������
	 * @param account
	 */
	public void updatePwd(Account account);
	/**
	 * ��������ӵ�t_friends����
	 * @param selfNumber
	 * @param number
	 */
	public void addFriends(String selfNumber, String number); 
	
	/**
	 * �����˺�ȥ��ѯ���ѱ�
	 * @param number
	 * @return
	 */
	public List<Account> queryFriends(String number);

	/**
	 * �����˺Ų�ѯ�����ߵĺ�����Ϣ
	 * @param number
	 * @return
	 */
	public List<Account> queryNotOfflineFriends(String number);
	
	/**
	 * �����˺�ȥ��ѯ���ݿ�t_friends��,�ж�����û��ǲ����Ѿ�����ĺ���
	 */
	public boolean queryFriend(String number, String toNumber);
	
	/**
	 * ������Ϣ����
	 * @param message
	 * @return
	 */
	public Message add(Message message); 
}
