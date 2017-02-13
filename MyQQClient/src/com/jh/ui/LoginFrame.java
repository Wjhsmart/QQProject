package com.jh.ui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.jh.bean.Account;
import com.jh.common.Constants;
import com.jh.common.ReflectUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;
import com.jh.ui.common.UIUtil;
import com.jh.ui.listener.LoginListener;
import com.jh.ui.listener.StatusListener;

/**
 * ���봰��
 * @author Administrator
 *
 */
public class LoginFrame extends JFrame implements ItemListener,FocusListener {
	
	public static final long serialVersionUID = 2L;
	
	public static JComboBox<String> accBox;
	public static JPasswordField pwdTxt;
	
	private JLabel headLbl;
	private JLabel statusLbl;
	private JCheckBox remBox;
	private JCheckBox autoBox;
	private String number;
	
	public LoginFrame() {
		LoginFrameCommon.setStyle(this);
		LoginFrameCommon.setTop(this);
		initCenter();
		LoginFrameCommon.setBottom(this);
		CommonMethod.setTray(this);
	}

	private void initCenter() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBounds(0, 200, 450, 110);
		centerPanel.setOpaque(false);
		Icon headIcon = CommonMethod.getImg(this, "head.png");
		headLbl = new JLabel(headIcon);
		headLbl.setBounds(40, 20, 80,80);
		Icon statusIcon = CommonMethod.getImg(this, "online.png");
		statusLbl = new JLabel(statusIcon);
		statusLbl.setBounds(60, 60, 20, 20);
		statusLbl.addMouseListener(new StatusListener(this, statusLbl, null, null));
		headLbl.add(statusLbl);
		centerPanel.add(headLbl);
		accBox = new JComboBox<String>();
		accBox.setEditable(true); // ���������˵��Ƿ�ɱ༭��trueΪ�ɱ༭��Ĭ��Ϊfalse�����ɱ༭
		accBox.setBounds(135, 25, 195, 25);
		accBox.setName("acc");
		accBox.addItemListener(this);
		accBox.setToolTipText("QQ����/�ֻ�/����");
		AccountDAO accountDAO = new AccountDAOImpl();
		List<Account> accounts = accountDAO.queryAll();
		for (Account a : accounts) {
			accBox.addItem(a.getNumber());
		}
		centerPanel.add(accBox);
		JLabel regLbl = new JLabel("ע���˺�");
		regLbl.setFont(UIUtil.myFont(12));
		regLbl.setForeground(Color.BLUE);
		regLbl.setBounds(350, 25, 80, 20);
		regLbl.setName("register");
		regLbl.addMouseListener(new LoginListener(this, regLbl));
		centerPanel.add(regLbl);
		pwdTxt = new JPasswordField();
		pwdTxt.setBounds(135, 50, 195, 25);
		pwdTxt.setName("pwd");
		pwdTxt.addFocusListener(this);
		pwdTxt.setToolTipText("����");
		pwdTxt.addKeyListener(new LoginListener(this, null));
		Icon keyIcon = CommonMethod.getImg(this, "key_def.png");
		JLabel keyLbl = new JLabel(keyIcon);
		keyLbl.setBounds(170, 4, 19, 18);
		keyLbl.setName("key");
		keyLbl.addMouseListener(new LoginListener(this, keyLbl));
		pwdTxt.add(keyLbl);
		centerPanel.add(pwdTxt);
		JLabel foLbl = new JLabel("�һ�����");
		foLbl.setFont(UIUtil.myFont(12));
		foLbl.setForeground(Color.BLUE);
		foLbl.setBounds(350, 55, 80, 20);
		foLbl.setName("forget");
		foLbl.addMouseListener(new LoginListener(this, foLbl));
		centerPanel.add(foLbl);
		remBox = new JCheckBox("��ס����");
		remBox.setBounds(135, 80, 80, 20);
		if (Constants.rememberPwd) {
			remBox.setSelected(true);
		}
		remBox.setName("rem");
		remBox.addItemListener(this);
		centerPanel.add(remBox);
		autoBox = new JCheckBox("�Զ�����");
		autoBox.setBounds(260, 80, 80, 20);
		autoBox.setName("auto");
		autoBox.addItemListener(this);
		centerPanel.add(autoBox);
		add(centerPanel);
	}
	
	private void updateHead(Account account) {
		headLbl.setIcon(CommonMethod.getImg(this, account.getHead() + ".png"));
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object obj = e.getSource();
		if (obj instanceof JComboBox) { // ������
			number = (String) accBox.getSelectedItem(); // ��ȡѡ�е���
			if (number == null) { // ���Ϊ��
				number = (String) ReflectUtil.getFieldValue(JComboBox.class, LoginFrame.accBox, "selectedItemReminder"); // ��ȡ�����ֵ
			}
			AccountDAO accountDAO = new AccountDAOImpl();
			Account a = accountDAO.query(number); // �����˺�ȥ��ѯ���ݿ��Ƿ��������˺�
			if (a != null) {
				updateHead(a);
				CommonMethod.head = a.getHead();
			} else {
				headLbl.setIcon(CommonMethod.getImg(this, "head.png"));
			}
		}
		if (obj instanceof JCheckBox) { // ��ѡ��
			JCheckBox check = (JCheckBox) obj;
			String name = check.getName();
			if (name.equals("rem")) { // ��ס����
				if (remBox.isSelected()) { // �����ס���뱻ѡ��
					Constants.rememberPwd = true;
				} else {
					Constants.rememberPwd = false;
				}
			} else if (name.equals("auto")) { // �Զ�����
				if (autoBox.isSelected()) { // ����Զ����뱻ѡ��
					
				}
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		Constants.lastPwd = String.valueOf(pwdTxt.getPassword());
	}

}
