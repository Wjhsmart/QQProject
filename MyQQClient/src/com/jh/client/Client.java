package com.jh.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.common.Constants;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.ChatFrame;
import com.jh.ui.MainFrame;

/**
 * QQ�ͻ��ˣ��������ӷ�������
 * ������Ϣ�Ľ��պͷ���
 * 
 * @author Administrator
 *
 */
public class Client {

	private Socket socket;
	private MainFrame mainFrame;
	private ChatFrame chatFrame;
	
	public Client() {}
	
	/**
	 * ���ô��壬�������崫�ݽ���
	 * @param frame
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	/**
	 * ��������QQ�ţ����Ұ�QQ���͵�������
	 * �����QQ�Ŵ��ڣ���������
	 * 
	 * ����ȥ��������Ϣ�̣߳����������Ƿ��з�����Ϣ����
	 * @param account
	 */
	public void login(Account account) {
		try {
			socket = new Socket("localhost", Constants.PORT); // ȥ���ӷ�����
			if (socket != null) {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); // ʵ���������
				oos.writeObject(account); // �Ѵ��QQ�ŵĶ��󴫵ݸ�������
				new Thread(new MessageReadThread(socket)).start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������������Ϣ�� ����������Ϣ�߳�
	 * @param message
	 */
	public void sendMessage(Message message) {
		MessageWriteThread mwt = new MessageWriteThread(socket);
		mwt.setMessage(message);
		new Thread(mwt).start(); // ����д��Ϣ�߳�
	}

	/**
	 * ���߳�������ȡ��������������Ϣ����Ҫһֱ����
	 * @author Administrator
	 *
	 */
	class MessageReadThread implements Runnable {
		
		private Socket socket;
		
		public MessageReadThread(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(Constants.MESSAGE_SLEEP);
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					Object obj = ois.readObject();
					if (obj instanceof Message) {
						Message message = (Message) obj;
						if (message != null) {
							chatFrame = mainFrame.getChatFrames().get(message.getFromAccount());
							if (message.getType() == Message.NORMAL_MSG) {
								System.out.println("��������" + message.getFromAccount().getNumber() + "����Ϣ��");
								System.out.println("��Ϣ���ݣ�" + message.getMessage());
								if (chatFrame == null || !chatFrame.isVisible()) { // ������촰���ǲ��ɼ��Ļ���û�д����촰��
									mainFrame.requstMessage(message); // ������Ϣ�������߳�
								} else {
									chatFrame.updateMessage(message); // ������Ϣ
								}
							} else if (message.getType() == Message.REQUST_MSG) {
								mainFrame.requstAddFriend(message); // ������Ϣ�����߳�
							} else if (message.getType() == Message.REQUST_REV_MSG) {
								Account account = message.getFromAccount(); // ��ȡ������Ϣ�Ķ���
								AccountDAO accountDAO = new AccountDAOImpl();
								accountDAO.addFriends(message.getToAccount().getNumber(), message.getFromAccount().getNumber()); // ��������ӵ����ݿ�
								mainFrame.updateFriendList(account);
							} else if (message.getType() == Message.LOGOUT_MSG) {
								Account account = message.getFromAccount();
								mainFrame.updateFriendData(account);
							} else if (message.getType() == Message.UPDATE_FRIEND_MSG) {
								Account account = message.getFromAccount();
								mainFrame.updateFriendData(account);
							} else if (message.getType() == Message.UPDATE_DATA_MSG) {
								Account account = message.getFromAccount();
								mainFrame.updateFriendData(account);
							} else if (message.getType() == Message.SELECT_PWD_MSG) {
								mainFrame.selectPwdPrompt();
							} else if (message.getType() == Message.SEND_FILE_MSG) {
								try {
									String fileName = message.getMessage();
									byte[] bytes = message.getBytes();
									FileOutputStream fos = new FileOutputStream("E:/workspace/MyQQClient/src/images/img/" + fileName);
									fos.write(bytes);
									fos.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								if (chatFrame == null || !chatFrame.isVisible()) { // ������촰���ǲ��ɼ��Ļ���û�д����촰��
									mainFrame.requstMessage(message); // ������Ϣ�������߳�
								} else {
									chatFrame.updateMessage(message); // ������Ϣ
								}
							} else if (message.getType() == Message.SHAKE_MSG) { // ��һ��
								if (chatFrame != null) {
									chatFrame.updateMessage(message);
									chatFrame.updateShake();
								}
							}
						}
					}
				} catch (SocketException e) {
					try {
						socket.close();
						break; // ����ѭ��
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
	}
	
	/**
	 * д��Ϣ�̣߳�����Ϣ���͸�����������Ϻ���߳�����
	 * @author Administrator
	 *
	 */
	class MessageWriteThread implements Runnable {
		
		private Socket socket;
		private Message message;
		
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
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(message);
				System.out.println("������Ϣ��" + message.getToAccount().getNumber());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
