package com.jh.ui.listener;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import com.jh.bean.Account;
import com.jh.common.Constants;
import com.jh.common.ReflectUtil;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.thread.BeingLoginThread;
import com.jh.ui.BeingLoginFrame;
import com.jh.ui.CodeLoginFrame;
import com.jh.ui.LoginFrame;
import com.jh.ui.ManyAccFrame;
import com.jh.ui.RegisterFrame;
import com.jh.ui.SetFrame;
import com.jh.ui.common.CheckCode;
import com.jh.ui.common.CheckCodeGenerator;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.UIUtil;

public class LoginListener extends MouseAdapter implements KeyListener {
	
	private String name;
	private JFrame frame = null;
	private JLabel label;
	private JLabel mobileLbl;
	
	public LoginListener(JFrame frame, JLabel label) {
		this.frame = frame;
		this.label = label;
	}

	// ������갴���¼�
	@Override
	public void mouseClicked(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name != null) {
			if (name.equals("register")) { // ע���˺�
				if (Constants.isRegisterFrameOpen) {
					new RegisterFrame();
				}
			} else if (name.equals("forget")) { // ��������
				CommonMethod.shortcutOpen("https://aq.qq.com/cn2/findpsw/pc/pc_find_pwd_input_account?source_id=1003&pt_clientver=5473&pt_src=1&ptlang=2052&aquin=null");
			} else if (name.equals("login")) { // ����
				login();
			} else if (name.equals("set")) { // ����
				frame.dispose();
				new SetFrame();
			} else if (name.equals("add")) { // ���
				frame.dispose();
				new ManyAccFrame();
			} else if (name.equals("code")) { // ��ά��
				frame.dispose();
				new CodeLoginFrame();
			} else if (name.equals("key")) { // ��ʾ����
				JPopupMenu menu = new JPopupMenu();
				JMenuItem item = new JMenuItem(CommonMethod.getImg(frame, "keyboard.png"));
				menu.add(item);
				label.add(menu);
				menu.show(label, -210, 22);
			} else if (name.equals("qq")) { // ���½ǵ�qqͼ��
				frame.dispose();
				new LoginFrame();
			} else if (name.equals("codeLogin")) { // ��ά�����
				
			} else if (name.equals("return")) { // ���ذ�ť
				frame.dispose();
				new LoginFrame();
			} else if (name.equals("cancel")) { // ȡ��
				BeingLoginThread.isOk = false;
				frame.dispose();
				new LoginFrame();
			} else if (name.equals("exchange")) { // ����֤��
				CheckCode cc = CheckCodeGenerator.getCheckCode();
				RegisterFrame.verLbl.setIcon(new ImageIcon(cc.getBuffImg()));
				RegisterFrame.checkCode = cc.getCheckCode(); // ��ʱ������֤��
			} else if (name.equals("submit")) { // �ύ��ť
				if (RegisterFrame.submit) {
					String nickname = RegisterFrame.nicknameTxt.getText();
					String pwd = new String(RegisterFrame.pwdTxt.getPassword());
					String conPwd = new String(RegisterFrame.conpwdTxt.getPassword());
					String mobile = RegisterFrame.mobileTxt.getText();
					String verification = RegisterFrame.verificationTxt.getText();
					String status = "offline";
					String skin = "main_background4";
					int size = mobile.length();
					if (nickname != null && !nickname.equals("") 
							&& pwd.equals(conPwd) 
							&& size == 11
							&& verification.equalsIgnoreCase(RegisterFrame.checkCode)) {
						String number = String.valueOf(new Random().nextInt(999999));
						Account account = new Account();
						account.setAge(RegisterFrame.ageAcc);
						account.setGender(RegisterFrame.genderAcc);
						account.setProvice(RegisterFrame.proviceAcc);
						account.setCity(RegisterFrame.cityAcc);
						account.setArea(RegisterFrame.areaAcc);
						account.setAutograph(null);
						account.setMobile(mobile);
						account.setNumber(number);
						account.setNickname(nickname);
						account.setPwd(pwd);
						account.setStatus(status);
						account.setSkin(skin);
						AccountDAO accountDAO = new AccountDAOImpl();
						Account a = accountDAO.add(account);
						if (a != null) {
							JOptionPane.showMessageDialog(frame, "ע��ɹ����˺�Ϊ��" + number);
							frame.dispose();
							LoginFrame.accBox.insertItemAt(number, 0);
						}
					} else {
						if (nickname == null || nickname.equals("")) {
							JOptionPane.showMessageDialog(frame, "�ǳƲ���Ϊ��");
						} else if (pwd == null || pwd.equals("")) {
							JOptionPane.showMessageDialog(frame, "���벻��Ϊ��");
						} else if (!pwd.equals(conPwd)) {
							JOptionPane.showMessageDialog(frame, "�������벻һ��");
						} else if (size != 11) {
							JOptionPane.showMessageDialog(frame, "�ֻ��������11λ��");
						} else if (!verification.equalsIgnoreCase(RegisterFrame.checkCode)) {
							JOptionPane.showMessageDialog(frame, "��֤�����");
						}
					}
				}
			}
		}
	}

	// ������꾭���¼�
	@Override
	public void mouseEntered(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name != null) {
			if (name.equals("register")) { 
				CommonMethod.setLblCursor(label);
				label.setForeground(UIUtil.myColor(Color.RED));
			} else if (name.equals("forget")) { 
				CommonMethod.setLblCursor(label);
				label.setForeground(UIUtil.myColor(Color.RED));
			} else if (name.equals("login")) { 
				setLblIcon("login_btn_over.png");
			} else if (name.equals("set")) {
				setLblIcon("triangle_over.png");
			} else if (name.equals("key")) {
				CommonMethod.setLblCursor(label);
				setLblIcon("key_over.png");
			} else if (name.equals("qq")) {
				setLblIcon("qq_over.png");
			} else if (name.equals("codeLogin")) { // ��ά�����
				Constants.codeX = 80;
				label.setBounds(Constants.codeX, Constants.codeY, 300, 190);
				Icon mobileIcon = CommonMethod.getImg(frame, "mobile.png");
				mobileLbl = new JLabel();
				mobileLbl.setIcon(mobileIcon);
				mobileLbl.setBounds(230, 100, 180, 190);
				frame.add(mobileLbl);
			} else if (name.equals("return")) { // ���ذ�ť
				setLblIcon("return_btn_over.png");
			} else if (name.equals("cancel")) { // ȡ��
				setLblIcon("cancel_btn_over.png");
			} else if (name.equals("exchange")) { // ����֤��
				CommonMethod.setLblCursor(label);
			} else if (name.equals("submit")) {
				CommonMethod.setLblCursor(label);
			} else if (name.equals("add")) {
				setLblIcon("person_over.png");
			} else if (name.equals("code")) {
				setLblIcon("code_over.png");
			}
		}
	}

	// ��������뿪�¼�
	@Override
	public void mouseExited(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name != null) {
			if (name.equals("register")) { 
				CommonMethod.setLblCursorDef(label);
				label.setForeground(UIUtil.myColor(Color.BLUE));
			} else if (name.equals("forget")) { 
				CommonMethod.setLblCursorDef(label);
				label.setForeground(UIUtil.myColor(Color.BLUE));
			} else if (name.equals("login")) { 
				setLblIcon("login_btn_def.png");
			} else if (name.equals("set")) {
				setLblIcon("triangle_def.png");
			} else if (name.equals("key")) {
				CommonMethod.setLblCursorDef(label);
				setLblIcon("key_def.png");
			} else if (name.equals("qq")) {
				setLblIcon("qq_def.png");
			} else if (name.equals("codeLogin")) { // ��ά�����
				Constants.codeX = 170;
				label.setBounds(Constants.codeX, Constants.codeY, 300, 190);
				frame.remove(mobileLbl);
			} else if (name.equals("return")) { // ���ذ�ť
				setLblIcon("return_btn_def.png");
			} else if (name.equals("cancel")) { // ȡ��
				setLblIcon("cancel_btn_def.png");
			} else if (name.equals("exchange")) { // ����֤��
				CommonMethod.setLblCursorDef(label);
			} else if (name.equals("submit")) {
				CommonMethod.setLblCursorDef(label);
			} else if (name.equals("add")) {
				setLblIcon("person_def.png");
			} else if (name.equals("code")) {
				setLblIcon("code_def.png");
			}
		}
	}
	
	private void setLblIcon(String url) {
		label.setIcon(CommonMethod.getImg(frame, url));
	}
	
	private void login() {
		String number = (String) LoginFrame.accBox.getSelectedItem(); // ��ȡ������ѡ�е���
		String pwd = new String(LoginFrame.pwdTxt.getPassword());
		if (number == null) { // �����⵽�����˵���ѡ�е�Ϊ�գ����÷����ȡ�������������ֵ
			number = (String) ReflectUtil.getFieldValue(JComboBox.class, LoginFrame.accBox, "selectedItemReminder");
		}
		if (number != null && !number.equals("") && pwd != null && !pwd.equals("")) { // ����˺�����򶼲�Ϊ�գ����ʾ���ܿ��Ե���
			AccountDAO accountDAO = new AccountDAOImpl();
			Account a = accountDAO.query(number); // ����˺��Ƿ����
			if (a.getStatus().equals("offline")) {
				Account a1 = accountDAO.query(number, pwd); // ����˺������Ƿ����
				frame.dispose(); // �ѵ�ǰ�����ͷŵ�
				BeingLoginFrame blf = new BeingLoginFrame();
				new Thread(new BeingLoginThread(blf, a1)).start();
			} else {
				JOptionPane.showMessageDialog(frame, "���Ѿ������˺�" + a.getNumber());
			}
		} else {
			JOptionPane.showMessageDialog(frame, "�˺ź����붼����Ϊ��");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			login();
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
