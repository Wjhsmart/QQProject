package com.jh.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.bean.QQSocket;
import com.jh.common.Constants;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;

/**
 * QQ����ˣ������������е�Socket����������Socket����
 * ��Ϣ�Ľ��պ���Ϣ��ת������Ϣ�Ĵ��ݣ�
 * @author Administrator
 *
 */
public class Server {
	
	private List<QQSocket> sockets;

	public Server() {
		sockets = new ArrayList<QQSocket>();
		new Thread(new Connector()).start(); // ��ʼ�����߳�
	}
	
	/**
	 * ͨ���߳����������ӣ��˽����������ò�ֹͣ�ģ�һֱ��Ҫ�ͻ��˵�QQ�û����룬
	 * һ�����û����룬��Ѵ�Socket��QQ�û��������ٱ��浽����˵�sockets�б���
	 * 
	 * ���������ӵĶ�ȡ�̣߳�ȥ��ȡ��QQ�û���������Ϣ
	 * @author Administrator
	 *
	 */
	class Connector implements Runnable {
		@Override
		public void run() {
			try {
				@SuppressWarnings("resource") // ѹ�ƾ���
				ServerSocket ss = new ServerSocket(Constants.PORT);
				while (true) {
					System.out.println("���������ڵȴ�����");
					Socket socket = ss.accept(); // �ȴ�����
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream()); // ����һ������
					Object obj = ois.readObject();
					if (obj instanceof Account) { // �ж϶������Ķ����Ƿ���Account�Ķ���
						Account account = (Account) obj; // ǿ�ưѸö���ת����Account����
						System.out.println(account.getNumber() + "�Ѿ�����");
						QQSocket qqSocket = new QQSocket(account, socket); // ��QQSocket���ú��û���Socket
						sockets.add(qqSocket); // ��qqSocket��ӵ�������
						new Thread(new MessageReadThread(qqSocket)).start(); // ��������Ϣ�߳�
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �ô��߳�����QQ�û����͹�������Ϣ
	 * ֻҪ���������ӣ��ͻ��˾��п��ܲ���ʱ�ķ�����Ϣ���������Է���˾ͱ���һֱȥ��
	 * 
	 * һ������Ϣ����ȡ������Ҫ�Ѵ���Ϣת�������ߵĺ��ѣ����ж��������Ƿ��иú��ѣ�����У���ʹ��ObjectOutputStream
	 * @author Administrator
	 *
	 */
	class MessageReadThread implements Runnable {
		
		private QQSocket qqSocket;
		
		public MessageReadThread(QQSocket qqSocket) {
			this.qqSocket = qqSocket;
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(Constants.MESSAGE_SLEEP); // ʹ�߳�����1s��1000ms��
					ObjectInputStream ois = new ObjectInputStream(qqSocket.getSocket().getInputStream()); 
					Object obj = ois.readObject(); // ȥ�����ݽ����Ķ���
					if (obj instanceof Message) { // �жϴ��ݽ�����obj�����Ƿ���Message�Ķ���
						Message message = (Message) obj;
						System.out.println("��ȡ����" + message.getFromAccount().getNumber() + "����Ϣ");
						Socket toSocket = searchSocket(message.getToAccount()); // ��ȡ��Ҫ������Ϣ��socket
						if (toSocket != null) { // ��ʾҪ������Ϣ��socket����
							AccountDAO accountDAO = new AccountDAOImpl();
							if (message.getType() == Message.LOGOUT_MSG) { // ����յ�����Ϣ���˳���Ϣ
								System.out.println(message.getFromAccount().getNumber() + "���˳�");
								Account account = message.getFromAccount();
								account.setStatus("offline"); // ���û���״̬���ó�����״̬
								accountDAO.update(account); // �������ݿ�
								
								List<Account> accounts = accountDAO.queryNotOfflineFriends(account.getNumber());
								for (Account a : accounts) {
									Socket toSocket1 = searchSocket(a); // ��ȡ�Է��˺�����Ӧ��Socket����
									if (toSocket1 != null) {
										MessageWriteThread writer = new MessageWriteThread(toSocket1);
										writer.setMessage(message);
										new Thread(writer).start();
									}
								}
								removeAccountSocket(message.getFromAccount()); // remove���˳���Socket
							} else if (message.getType() == Message.UPDATE_FRIEND_MSG) { // ����յ����Ǹ��º����б����Ϣ
								List<Account> accounts = accountDAO.queryNotOfflineFriends(message.getFromAccount().getNumber());
								for (Account a : accounts) {
									Socket toSocket1 = searchSocket(a); // ��ȡ�Է��˺�����Ӧ��Socket����
									if (toSocket1 != null) {
										MessageWriteThread writer = new MessageWriteThread(toSocket1);
										writer.setMessage(message);
										new Thread(writer).start();
									}
								}
							} else if (message.getType() == Message.UPDATE_DATA_MSG) { // ���º�����Ϣ
								List<Account> accounts = accountDAO.queryNotOfflineFriends(message.getFromAccount().getNumber());
								for (Account a : accounts) {
									Socket toSocket1 = searchSocket(a); // ��ȡ�Է��˺�����Ӧ��Socket����
									if (toSocket1 != null) {
										MessageWriteThread writer = new MessageWriteThread(toSocket1);
										writer.setMessage(message);
										new Thread(writer).start();
									}
								}
							}  else {
								MessageWriteThread mwt = new MessageWriteThread(toSocket); // ���߷�����������Ϣ��socket
								mwt.setMessage(message); // ��Ҫת������Ϣ���ݸ��߳�
								new Thread(mwt).start(); // ִ�з���Ϣ�߳�
							}
						}
					}
				} catch (SocketException e) {
					try {
						qqSocket.getSocket().close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ���߳�����ת���û���Ϣ
	 * 
	 * �������Ϣ����������ִ�д��߳�����ת����Ϣ��ת����ϣ��߳�����
	 * @author Administrator
	 *
	 */
	class MessageWriteThread implements Runnable {
		
		private Socket socket;
		private Message message; // ���������Ϣ
		
		// �÷�����֪���Ǹ��ĸ��û�����Ϣ
		public MessageWriteThread(Socket socket) {
			this.socket = socket;
		}
		
		public Message getMessage() {
			return message;
		}
		
		public void setMessage(Message message) {
			this.message = message;
		}
		
		@Override
		public void run() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); // ʵ������������ݶ���
				oos.writeObject(message); // �Ѵ����Ϣ�Ķ��󴫵ݳ�ȥ
				System.out.println("����Ϣ������" + message.getToAccount().getNumber());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �ж�Ҫ���յ��˺��Ƿ��Ѿ����Ӵ˷�����
	 * @param account
	 * @return
	 */
	private Socket searchSocket(Account account) {
		for (QQSocket qqSocket : sockets) { // ȥ����sockets�б�
			Account a = qqSocket.getAccount();
			if (a.equals(account)) {
				return qqSocket.getSocket();
			}
		}
		return null;
	}
	
	/**
	 * �����Ƴ�Socket
	 * @param account
	 */
	public void removeAccountSocket(Account account) {
		Iterator<QQSocket> ite = sockets.iterator(); // ��ȡ��sockets��Ӧ�ĵ�����
		while (ite.hasNext()) { // �жϵ��������Ƿ���ֵ
			QQSocket qqSocket = ite.next(); // ��ȡ���������е�ֵ
			if (qqSocket.getAccount().equals(account)) {
				ite.remove(); // �Ƴ�Socket
				break; // ����ѭ��
			}
		}
	}
	
}
