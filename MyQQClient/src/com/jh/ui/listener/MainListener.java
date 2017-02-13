package com.jh.ui.listener;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.common.DateUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.AlterSkinFrame;
import com.jh.ui.ApplyManageFrame;
import com.jh.ui.ChatFrame;
import com.jh.ui.MainFrame;
import com.jh.ui.MainFrame.MessageThread;
import com.jh.ui.MainFrame.MusicThread;
import com.jh.ui.MainFrame.RequstThread;
import com.jh.ui.SeekFriendFrame;
import com.jh.ui.SelectDataFrame;
import com.jh.ui.common.CommonMethod;

public class MainListener extends MouseAdapter {
	
	private String name;
	private MainFrame mainFrame;
	private Client client;
	private Account account;
	private JLabel label;
	private RequstThread requstThread;
	
	public MainListener(MainFrame mainFrame, JLabel label) {
		this.mainFrame = mainFrame;
		this.label = label;
		this.client = mainFrame.getClient();
		this.account = mainFrame.getAccount();
	}

	// ������갴���¼�
	@Override
	public void mouseClicked(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name != null) {
			if (name.equals("head")) { // ͷ��
				if (Constants.select) {
					SelectDataFrame selectData = new SelectDataFrame(mainFrame, account);
					mainFrame.setSelectData(selectData);
				}
			} else if (name.equals("space")) { // QQ�ռ�
				CommonMethod.shortcutOpen("http://i.qq.com/");
			} else if (name.equals("email")) { // ����
				CommonMethod.shortcutOpen("https://mail.qq.com/");
			} else if (name.equals("skin")) { // Ƥ��
				if (Constants.isAlterSkinFrameOpen) {
					new AlterSkinFrame(mainFrame);
				}
			} else if (name.equals("news")) { // ��Ϣ����
				MessageThread messageThread = mainFrame.getMessageThread();
				if (messageThread.getNeedRunning()) { // ֻ���������̲߳ſ��Ե������촰��
					messageThread.setNeedRunning(false); // ֹͣ����
					label.setIcon(CommonMethod.getImg(mainFrame, "news_def.png"));
					Message message = mainFrame.getMessage();
					Account toAccount = message.getFromAccount();
					if (!mainFrame.getChatFrames().containsKey(toAccount)) { // ��������û�ж�Ӧ�����촰�壬��newһ�����촰��
						ChatFrame chatFrame = new ChatFrame(client, account, toAccount);
						mainFrame.getChatFrames().put(toAccount, chatFrame); // �Ѻ��Ѻͺ�������Ӧ�����촰��put��map����
					} else { // ����Ѿ��ж�Ӧ�����촰����
						mainFrame.getChatFrames().get(toAccount).setVisible(true); // ��toAcoount����Ӧ�Ĵ�������Ϊ�ɼ�
					}
					ChatFrame chatFrame = mainFrame.getChatFrames().get(toAccount);
					chatFrame.updateMessage(message);
				}
			} else if (name.equals("group")) { // ΢��
				CommonMethod.shortcutOpen("http://t.qq.com/");
			} else if (name.equals("paipai")) { // ����
				CommonMethod.shortcutOpen("http://www.paipai.com/");
			} else if (name.equals("purse")) { // �Ƹ�ͨ
				CommonMethod.shortcutOpen("https://www.tenpay.com/v3/");
			} else if (name.equals("sousou")) { // ����
				CommonMethod.shortcutOpen("http://www.sogou.com/");
			} else if (name.equals("game")) { // QQ��Ϸ
				CommonMethod.shortcutOpen("http://qqgame.qq.com/");
			} else if (name.equals("music")) { // QQ����
				CommonMethod.shortcutOpen("http://y.qq.com/#type=index");
			} else if (name.equals("video")) { // ��Ѷ��Ƶ
				CommonMethod.shortcutOpen("http://v.qq.com/");
			} else if (name.equals("pet")) { // QQ����
				
			} else if (name.equals("menu")) { // Ӧ�ù�����
				if (Constants.isApplyManageFrameOpen) {
					new ApplyManageFrame();
				}
			} else if (name.equals("function")) { // ���˵�
				JPopupMenu menu = new JPopupMenu();
				JMenuItem selectItem = new JMenuItem("�޸�����");
				selectItem.setActionCommand("select");
				selectItem.addActionListener(new FunctionActionListener(mainFrame));
				menu.add(selectItem);
				JMenuItem changeItem = new JMenuItem("�л��˺�");
				changeItem.setActionCommand("change");
				changeItem.addActionListener(new FunctionActionListener(mainFrame));
				menu.add(changeItem);
				JMenuItem logoutItem = new JMenuItem("ע������");
				logoutItem.setActionCommand("logout");
				logoutItem.addActionListener(new FunctionActionListener(mainFrame));
				menu.add(logoutItem);
				label.add(menu);
				menu.show(label, 25, -65);
				
			} else if (name.equals("set")) { // ϵͳ����
				
			} else if (name.equals("message")) { // ��Ϣ������
				requstThread = mainFrame.getRequstThread();
				MusicThread musicThread = mainFrame.getMusicThread();
				if (requstThread != null || musicThread != null) {
					requstThread.setNeedRunning(false); // ֹͣ����
					musicThread.setNeedRunning(false);
					label.setIcon(CommonMethod.getImg(mainFrame, "message_def.png"));
					Message message = mainFrame.getMessage(); // ��ȡ����Ϣ����
					AccountDAO accountDAO = new AccountDAOImpl();
					boolean isOk = accountDAO.queryFriend(message.getFromAccount().getNumber(), message.getToAccount().getNumber()); // ÿ�ε���֮ǰȥ��ѯһ�����ݿ⣬�����Ƿ��Ѿ����Լ��ĺ���
					if (!isOk) {
						int result = JOptionPane.showConfirmDialog(mainFrame,
								message.getFromAccount().getNickname() + " �������Ϊ����", "��Ӻ���",
								JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE); // ����
						if (result == JOptionPane.OK_OPTION) { // ����û������ȷ��
							accountDAO.addFriends(message.getToAccount().getNumber(),
									message.getFromAccount().getNumber()); // ��������ӵ�t_friends����
							Message m = new Message(Message.REQUST_REV_MSG, message.getToAccount(),
									message.getFromAccount(), DateUtil.getDate(),
									message.getToAccount().getNickname() + "�������Ϊ����");
							client.sendMessage(m);
							mainFrame.updateFriendList(message.getFromAccount());
						}
					}
					
				}
			} else if (name.equals("safe")) { // �˺�����
				
			} else if (name.equals("use")) { // Ӧ�ñ�
				
			} else if (name.equals("seachNumber")) { // �����˺�
				if (Constants.isSeekFirendFrameOpen) {
					new SeekFriendFrame(mainFrame, account,  client);
				}
			}
		}
	}

	// ������꾭���¼�
	@Override
	public void mouseEntered(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name.equals("head")) {
			label.setBorder(BorderFactory.createLineBorder(new Color(0, 238, 118)));
		} else {
			label.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190)));
		}
	}

	// ��������뿪�¼�
	@Override
	public void mouseExited(MouseEvent e) {
		label.setBorder(null);
	}
}
