package com.jh.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.jh.bean.Account;
import com.jh.bean.Message;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.common.DateUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;
import com.jh.ui.common.MessageMusics;
import com.jh.ui.listener.ExitListener;
import com.jh.ui.listener.MainListener;
import com.jh.ui.listener.MinListener;
import com.jh.ui.listener.StatusListener;
import com.jh.ui.listener.TxtFocusListener;
import com.jh.ui.listener.WindowCloseListener;
import com.jh.ui.panel.MainDialoguePanel;
import com.jh.ui.panel.MainFriendPanel;
import com.jh.ui.panel.MainGroupPanel;

/**
 * ������
 * @author Administrator
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 6495052281715015592L;
	
	private Account account; // ������˺�
	private Client client; // ����Ŀͻ���
	private JLabel messageLbl; // ���ȱ�ǩ
	private Message message; // ��Ϣ����
	private RequstThread requstThread; // �����������߳�
	private MainFriendPanel mainFriendPanel; // �����б�����
	private MainDialoguePanel mainDialoguePanel; // �Ự���
	private AccountDAO accountDAO; // �����������ݿ�
	
	public static JLabel bgLbl; // ������ǩ
	public static JLabel stateLbl; // ״̬��ǩ
	
	private Map<Account, ChatFrame> chatFrames; // ��ֵ�ԣ�һ���˺Ŷ�Ӧһ�����촰��
	
	public Map<Account, ChatFrame> getChatFrames() {
		return chatFrames;
	}

	public void setChatFrames(Map<Account, ChatFrame> chatFrames) {
		this.chatFrames = chatFrames;
	}

	public MainFrame(Account account, Client client) {
		this.account = account;
		this.client = client;
		client.setMainFrame(this); // �������崫�ݸ��ͻ���
		Constants.mainOk = true;
		CommonMethod.client = client;
		CommonMethod.account = account;
		chatFrames = new HashMap<Account, ChatFrame>();
		mm = new MessageMusics();
		LoginFrameCommon.setStyle(this);
		try {
			LookAndFeelInfo[] feel = UIManager.getInstalledLookAndFeels(); // ��ȡ���з��
			UIManager.setLookAndFeel(feel[0].getClassName()); // ����Windows���
		} catch (Exception e) {
			e.printStackTrace();
		}
		accountDAO = new AccountDAOImpl();
		account.setStatus(CommonMethod.IMG_URL);
		accountDAO.update(account);
		Message m = new Message(Message.UPDATE_FRIEND_MSG, account, account, DateUtil.getDate(), "״̬�޸�Ϊ" + account.getStatus());
		client.sendMessage(m);
		CommonMethod.setTray(this);
		initTopBtn();
		setBounds(950, 30, 280, 600);
		setBackground();
		initTop();
		initCenter();
		initBottom();
		addWindowListener(new WindowCloseListener(client, account));
	}

	/**
	 * ���ñ���
	 */
	private void setBackground() {
		Icon bgIcon = CommonMethod.getImg(this, account.getSkin() + ".png");
		bgLbl = new JLabel(bgIcon);
		bgLbl.setBounds(0, 0, 280, 600);
		add(bgLbl);
	}
	
	/**
	 * ��ʼ������ͷ���İ�ť
	 */
	private void initTopBtn() {
		Icon qqIconIcon = CommonMethod.getImg(this, "main_qq_icon.png");
		JLabel qqIconLbl = new JLabel(qqIconIcon);
		qqIconLbl.setBounds(0, 0, 60, 30);
		add(qqIconLbl);
		Icon minIcon = CommonMethod.getImg(this, "min_def.png");
		JLabel minLbl = new JLabel(minIcon);
		minLbl.setBounds(220, 0, 30, 30);
		minLbl.setToolTipText("��С��");
		minLbl.setName("task_min");
		minLbl.addMouseListener(new MinListener(this));
		add(minLbl);
		Icon exitIcon = CommonMethod.getImg(this, "exit_def.png");
		JLabel exitLbl = new JLabel(exitIcon);
		exitLbl.setBounds(250, 0, 30, 30);
		exitLbl.setToolTipText("�ر�");
		exitLbl.addMouseListener(new ExitListener(client, account));
		add(exitLbl);
	}
	
	private JLabel headLbl; // ͷ��
	private JLabel nicknameLbl; // �ǳ�
	private JTextField autographTxt; // ����ǩ��
	private SelectDataFrame selectData;
	private JLabel newsLbl; // ��Ϣ����
	

	public SelectDataFrame getSelectData() {
		return selectData;
	}

	public void setSelectData(SelectDataFrame selectData) {
		this.selectData = selectData;
	}
	
	public void updaDataAutograph(Account account) {
		if (selectData != null) {
			selectData.updateAutograph(account);
		}
	}

	/**
	 * �������������沿����Ϣ
	 */
	private void initTop() {
		// ͷ��
		Icon headIcon = CommonMethod.getImg(this, account.getHead() + ".png");
		headLbl = new JLabel(headIcon);
		headLbl.setBounds(5, 30, 80, 80);
		headLbl.setName("head");
		headLbl.addMouseListener(new MainListener(this, headLbl));
		bgLbl.add(headLbl);
		// �ǳ�
		nicknameLbl = new JLabel(account.getNickname());
		nicknameLbl.setBounds(90, 40, 50, 20);
		nicknameLbl.setFont(new Font("΢���ź�", Font.BOLD, 16));
		bgLbl.add(nicknameLbl);
		// ����״̬
		JLabel stateGroupLbl = new JLabel();
		stateGroupLbl.setBounds(140, 40, 40, 20);
		stateGroupLbl.addMouseListener(new StatusListener(this, stateGroupLbl, account, client));
		Icon stateIcon = CommonMethod.getImg(this, account.getStatus() + ".png");
		stateLbl = new JLabel(stateIcon);
		stateLbl.setBounds(0, 0, 20, 20);
		stateGroupLbl.add(stateLbl);
		Icon triangleIcon = CommonMethod.getImg(this, "triangle.png");
		JLabel triangleLbl = new JLabel(triangleIcon);
		triangleLbl.setBounds(20, 0, 20, 20);
		stateGroupLbl.add(triangleLbl);
		bgLbl.add(stateGroupLbl);
		// �ȼ�
		JLabel leaveLbl = new JLabel(CommonMethod.getImg(this, "leave89.png"));
		leaveLbl.setBounds(180, 40, 30, 20);
		leaveLbl.setName("grade");
		leaveLbl.addMouseListener(new MainListener(this, leaveLbl));
		bgLbl.add(leaveLbl);
		JLabel vipLbl = new JLabel(CommonMethod.getImg(this, "svipAdd.png"));
		vipLbl.setBounds(210, 40, 34, 20);
		vipLbl.setName("grade");
		vipLbl.addMouseListener(new MainListener(this, vipLbl));
		bgLbl.add(vipLbl);
		// ����
		Icon beautifulIcon = CommonMethod.getImg(this, "beautiful.png");
		JLabel beautifulLbl = new JLabel(beautifulIcon);
		beautifulLbl.setBounds(240, 40, 20, 20);
		beautifulLbl.setName("beautiful");
		beautifulLbl.addMouseListener(new MainListener(this, beautifulLbl));
		bgLbl.add(beautifulLbl);
		// ��JTextField
		JTextField nullTxt = new JTextField();
		nullTxt.setBounds(10, 0, 0, 0);
		bgLbl.add(nullTxt);
		// ����ǩ��
		autographTxt = new JTextField();
		autographTxt.setBounds(90, 60, 150, 30);
//		autographTxt.setBackground(new Color(0, 0, 0, 0)); // ����Ϊ͸��
		autographTxt.setOpaque(false);
		autographTxt.setBorder(null);
		if (account.getAutograph() == null) {
			autographTxt.setText(" �༭����ǩ��");
		} else {
			autographTxt.setText(account.getAutograph());
		}
		autographTxt.setName("autograph");
		autographTxt.addFocusListener(new TxtFocusListener(this, autographTxt, account));
		autographTxt.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					nullTxt.setFocusable(true);
					nullTxt.requestFocus();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
//		autographTxt.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0))); // ���ñ߿���ɫ
		bgLbl.add(autographTxt);
		// QQ�ռ�
		Icon spaceIcon = CommonMethod.getImg(this, "space.png");
		JLabel spaceLbl = new JLabel(spaceIcon);
		spaceLbl.setBounds(90, 85, 20, 20);
		spaceLbl.setName("space");
		spaceLbl.addMouseListener(new MainListener(this, spaceLbl));
		spaceLbl.setToolTipText("QQ�ռ�");
		bgLbl.add(spaceLbl);
		// ����
		Icon emailIcon = CommonMethod.getImg(this, "email.png");
		JLabel emailLbl = new JLabel(emailIcon);
		emailLbl.setBounds(110, 85, 20, 20);
		emailLbl.setName("email");
		emailLbl.addMouseListener(new MainListener(this, emailLbl));
		emailLbl.setToolTipText("QQ����");
		bgLbl.add(emailLbl);
		// ΢��
		Icon groupIcon = CommonMethod.getImg(this, "wblog.png");
		JLabel groupLbl = new JLabel(groupIcon);
		groupLbl.setBounds(130, 85, 20, 20);
		groupLbl.setName("group");
		groupLbl.addMouseListener(new MainListener(this, groupLbl));
		groupLbl.setToolTipText("΢��");
		bgLbl.add(groupLbl);
		// ����
		Icon paiIcon = CommonMethod.getImg(this, "paipai_top.png");
		JLabel paiLbl = new JLabel(paiIcon);
		paiLbl.setBounds(150, 85, 20, 20);
		paiLbl.setName("paipai");
		paiLbl.addMouseListener(new MainListener(this, paiLbl));
		paiLbl.setToolTipText("����");
		bgLbl.add(paiLbl);
		// �Ƹ�ͨ
		Icon purseIcon = CommonMethod.getImg(this, "purse.png");
		JLabel purseLbl = new JLabel(purseIcon);
		purseLbl.setBounds(170, 85, 20, 20);
		purseLbl.setName("purse");
		purseLbl.addMouseListener(new MainListener(this, purseLbl));
		purseLbl.setToolTipText("�Ƹ�ͨ");
		bgLbl.add(purseLbl);
		// ����
		Icon soIcon = CommonMethod.getImg(this, "soso.png");
		JLabel soLbl = new JLabel(soIcon);
		soLbl.setBounds(190, 85, 20, 20);
		soLbl.setName("sousou");
		soLbl.addMouseListener(new MainListener(this, soLbl));
		soLbl.setToolTipText("����");
		bgLbl.add(soLbl);
		// Ƥ��
		Icon skinIcon = CommonMethod.getImg(this, "skin.png");
		JLabel skinLbl = new JLabel(skinIcon);
		skinLbl.setBounds(240, 85, 20, 20);
		skinLbl.setName("skin");
		skinLbl.addMouseListener(new MainListener(this, skinLbl));
		skinLbl.setToolTipText("�������");
		bgLbl.add(skinLbl);
		// ��Ϣ����
		Icon newsIcon = CommonMethod.getImg(this, "news_def.png");
		newsLbl = new JLabel(newsIcon);
		newsLbl.setBounds(260, 85, 20, 20);
		newsLbl.setToolTipText("��Ϣ����");
		newsLbl.setName("news");
		newsLbl.addMouseListener(new MainListener(this, newsLbl));
		bgLbl.add(newsLbl);
		// ������
		JTextField seekTxt = new JTextField();
		seekTxt.setBounds(0, 110, 280, 30);
		seekTxt.setText("  ��������ϵ�ˡ������顢Ⱥ����ҵ");
		seekTxt.setOpaque(false);
		seekTxt.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		seekTxt.setName("seek1");
		seekTxt.addFocusListener(new TxtFocusListener(this, seekTxt, null));
		Icon seekIcon = CommonMethod.getImg(this, "seek.png");
		JLabel seekLbl = new JLabel(seekIcon);
		seekLbl.setBounds(250, 5, 20, 20);
		seekTxt.add(seekLbl);
		bgLbl.add(seekTxt);
	}
	
	/**
	 * �����м�������
	 */
	private void initCenter() {
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setBounds(10, 140, 260, 400);
		tabPane.setBorder(null);
		UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE); // ����ѡ���������͸��
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); // ����ѡ��Ĳ��ַ�ʽ
		mainFriendPanel = new MainFriendPanel(this, client, account, accountDAO.queryFriends(account.getNumber()));
		mainDialoguePanel = new MainDialoguePanel(this, null);
		tabPane.addTab("", CommonMethod.getImg(this, "contacts_def.png"), mainFriendPanel, "��ϵ��");
		tabPane.addTab("", CommonMethod.getImg(this, "dialogue_def.png"), mainDialoguePanel, "�Ự��Ϣ");
		tabPane.addTab("", CommonMethod.getImg(this, "group_def.png"), new MainGroupPanel(this, null), "Ⱥ��������");
		bgLbl.add(tabPane);
	}
	
	/**
	 * ����������ײ���һЩ��Ϣ
	 */
	private void initBottom() {
		// ��Ѷ΢��
		Icon groupIcon = CommonMethod.getImg(this, "QQGroup.png");
		JLabel groupLbl = new JLabel(groupIcon);
		groupLbl.setBounds(5, 545, 30, 20);
		groupLbl.setToolTipText("��Ѷ΢��");
		groupLbl.setName("group");
		groupLbl.addMouseListener(new MainListener(this, groupLbl));
		bgLbl.add(groupLbl);
		// QQ��Ϸ
		Icon gameIcon = CommonMethod.getImg(this, "QQGame.png");
		JLabel gameLbl = new JLabel(gameIcon);
		gameLbl.setBounds(35, 545, 30, 20);
		gameLbl.setToolTipText("QQ��Ϸ");
		gameLbl.setName("game");
		gameLbl.addMouseListener(new MainListener(this, gameLbl));
		bgLbl.add(gameLbl);
		// QQ����
		Icon musicIcon = CommonMethod.getImg(this, "music.png");
		JLabel musicLbl = new JLabel(musicIcon);
		musicLbl.setBounds(65, 545, 30, 20);
		musicLbl.setToolTipText("QQ����");
		musicLbl.setName("music");
		musicLbl.addMouseListener(new MainListener(this, musicLbl));
		bgLbl.add(musicLbl);
		// ��Ѷ��Ƶ
		Icon videoIcon = CommonMethod.getImg(this, "video.png");
		JLabel videoLbl = new JLabel(videoIcon);
		videoLbl.setBounds(95, 545, 30, 20);
		videoLbl.setToolTipText("��Ѷ��Ƶ");
		videoLbl.setName("video");
		videoLbl.addMouseListener(new MainListener(this, videoLbl));
		bgLbl.add(videoLbl);
		// QQ����
		Icon petIcon = CommonMethod.getImg(this, "QQPet.png");
		JLabel petLbl = new JLabel(petIcon);
		petLbl.setBounds(125, 545, 30, 20);
		petLbl.setToolTipText("QQ����");
		petLbl.setName("pet");
		petLbl.addMouseListener(new MainListener(this, petLbl));
		bgLbl.add(petLbl);
		// Ӧ�ù�����
		Icon menuIcon = CommonMethod.getImg(this, "menu.png");
		JLabel menuLbl = new JLabel(menuIcon);
		menuLbl.setBounds(245, 545, 30, 20);
		menuLbl.setToolTipText("��Ӧ�ù�����");
		menuLbl.setName("menu");
		menuLbl.addMouseListener(new MainListener(this, menuLbl));
		bgLbl.add(menuLbl);
		
		Icon functionIcon = CommonMethod.getImg(this, "function.png");
		JLabel functionLbl = new JLabel(functionIcon);
		functionLbl.setBounds(5, 570, 30, 20);
		functionLbl.setToolTipText("���˵�");
		functionLbl.setName("function");
		functionLbl.addMouseListener(new MainListener(this, functionLbl));
		bgLbl.add(functionLbl);
		Icon setIcon = CommonMethod.getImg(this, "set.png");
		JLabel setLbl = new JLabel(setIcon);
		setLbl.setBounds(35, 570, 30, 20);
		setLbl.setToolTipText("��ϵͳ����");
		setLbl.setName("set");
		setLbl.addMouseListener(new MainListener(this, setLbl));
		bgLbl.add(setLbl);
		Icon messageIcon = CommonMethod.getImg(this, "message_def.png");
		messageLbl = new JLabel(messageIcon);
		messageLbl.setBounds(65, 570, 30, 20);
		messageLbl.setToolTipText("����Ϣ������");
		messageLbl.setName("message");
		messageLbl.addMouseListener(new MainListener(this, messageLbl));
		bgLbl.add(messageLbl);
		Icon safeIcon = CommonMethod.getImg(this, "QQSafe.png");
		JLabel safeLbl = new JLabel(safeIcon);
		safeLbl.setBounds(95, 570, 30, 20);
		safeLbl.setToolTipText("�˺Ź���");
		safeLbl.setName("safe");
		safeLbl.addMouseListener(new MainListener(this, safeLbl));
		bgLbl.add(safeLbl);
		Icon seachIcon = CommonMethod.getImg(this, "group_seach.png");
		JLabel seachLbl = new JLabel("�����˺�", seachIcon, JLabel.LEFT);
		seachLbl.setBounds(125, 570, 80, 20);
		seachLbl.setToolTipText("�����˺�");
		seachLbl.setName("seachNumber");
		seachLbl.addMouseListener(new MainListener(this,seachLbl));
		bgLbl.add(seachLbl);
		Icon useIcon = CommonMethod.getImg(this, "use.png");
		JLabel useLbl = new JLabel("Ӧ�ñ�", useIcon, JLabel.LEFT);
		useLbl.setBounds(210, 570, 80, 20);
		useLbl.setToolTipText("Ӧ�ñ�");
		useLbl.setName("use");
		useLbl.addMouseListener(new MainListener(this, useLbl));
		bgLbl.add(useLbl);
	}
	
	public MessageMusics mm;
	public MusicThread musicThread;
	private MessageThread messageThread;
	/**
	 * ������Ӻ���ͼ�������Ͳ������ֵ��̵߳��߳�
	 */
	public void requstAddFriend(Message message) {
		this.message = message;
		musicThread = new MusicThread();
		new Thread(musicThread).start();
		requstThread = new RequstThread(this);
		new Thread(requstThread).start();
	}
	
	/**
	 * ������ͨ��Ϣ�������߳�
	 */
	public void requstMessage(Message message) {
		this.message = message;
		messageThread = new MessageThread(this);
		new Thread(messageThread).start();
	}
	/**
	 * ��Ϣ����get����
	 * @return
	 */
	public Message getMessage() {
		return this.message;
	}
	
	/**
	 * �ͻ��˶���get����
	 * @return
	 */
	public Client getClient() {
		return this.client;
	}
	
	/**
	 * ��ȡaccount����get����
	 * @return
	 */
	public Account getAccount() {
		return this.account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	/**
	 * �̵߳�get����
	 * @return
	 */
	public RequstThread getRequstThread() {
		return this.requstThread;
	}
	
	public MusicThread getMusicThread() {
		return this.musicThread;
	}
	
	public MessageThread getMessageThread() {
		return this.messageThread;
	}
	
	/**
	 * ��Ϣͼ���������߳�
	 * @author Administrator
	 *
	 */
	public class RequstThread implements Runnable {

		private MainFrame mainFrame; // ������Ĵ���
		private boolean needRunning = true; // �Ƿ�һֱ����
		private boolean isConversion = false; // �Ƿ����ͼƬ
		
		public RequstThread(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		
		// ���˵���û������������ͼ�꣬����Բ���������
		public void setNeedRunning(boolean needRunning) {
			this.needRunning = needRunning;
		}
		
		@Override
		public void run() {
			while (needRunning) {
				try {
					Thread.sleep(Constants.MESSAGE_SLEEP); // ����1s
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!isConversion) {
					messageLbl.setIcon(CommonMethod.getImg(mainFrame, "message_over.png"));
					isConversion = true;
				} else {
					messageLbl.setIcon(CommonMethod.getImg(mainFrame, "message_def.png"));
					isConversion = false;
				}
			}
		}
	}
	
	/**
	 * ��Ϣ��������
	 * @author Administrator
	 *
	 */
	public class MessageThread implements Runnable {

		private MainFrame mainFrame; // ������Ĵ���
		private boolean needRunning = true; // �Ƿ�һֱ����
		private boolean isConversion = false; // �Ƿ����ͼƬ
		
		public MessageThread(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		
		// ���˵���û������������ͼ�꣬����Բ���������
		public void setNeedRunning(boolean needRunning) {
			this.needRunning = needRunning;
		}
		
		public boolean getNeedRunning() {
			return this.needRunning;
		}
		@Override
		public void run() {
			while (needRunning) {
				try {
					Thread.sleep(Constants.MESSAGE_SLEEP); // ����1s
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (!isConversion) {
					newsLbl.setIcon(CommonMethod.getImg(mainFrame, "news_over.png"));
					isConversion = true;
				} else {
					newsLbl.setIcon(CommonMethod.getImg(mainFrame, "news_def.png"));
					isConversion = false;
				}
			}
		}
	}
	/**
	 * �յ���Ϣʱ�������ֵ��߳�
	 * @author Administrator
	 *
	 */
	public class MusicThread implements Runnable {

		private boolean needRunning = true;
		
		public void setNeedRunning(boolean needRunning) {
			this.needRunning = needRunning;
		}
		
		@Override
		public void run() {
			while (needRunning) {
				try {
					mm.loadSound();
					Thread.sleep(500); // ����1s
					mm.myStop();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ���º����б�
	 * @param account
	 */
	public void updateFriendList(Account account) {
		mainFriendPanel.updateFriendList(account);
	}
	
	/**
	 * �����û�����
	 */
	public void updateUserData(Account a) {
		account.setHead(a.getHead());
		account.setAutograph(a.getAutograph());
		account.setNickname(a.getNickname());
		account.setAge(a.getAge());
		account.setArea(a.getArea());
		account.setCity(a.getCity());
		account.setGender(a.getGender());
		account.setMobile(a.getMobile());
		account.setProvice(a.getProvice());
		headLbl.setIcon(CommonMethod.getImg(this, a.getHead() + ".png"));
		nicknameLbl.setText(a.getNickname());
		String value = a.getAutograph();
		if (value == null || value.equals("")) {
			autographTxt.setText(" �༭����ǩ��");
		} else {
			autographTxt.setText(value);
		}
	}
	
	/**
	 * ���º����б������
	 * @param account
	 */
	public void updateFriendData(Account account) {
		mainFriendPanel.updateFriendData(account);
	}
	
	public void selectPwdPrompt() {
		JOptionPane.showMessageDialog(this, "���Ѿ��޸�����", "�޸���������", JOptionPane.INFORMATION_MESSAGE);
		Message m = new Message(Message.LOGOUT_MSG, account, account, DateUtil.getDate(), "�˳�");
		client.sendMessage(m);
		this.dispose();
		new LoginFrame();
	}
}
