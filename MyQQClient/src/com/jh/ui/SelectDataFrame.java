package com.jh.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jh.bean.Account;
import com.jh.common.Constants;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;
import com.jh.ui.listener.DataFrameListener;
import com.jh.ui.listener.MinListener;

/**
 * �޸ĸ������ϴ���
 * @author Administrator
 *
 */
public class SelectDataFrame extends JFrame implements ItemListener,KeyListener {

	private static final long serialVersionUID = -3112493938229583385L;

	private Account account;
	private MainFrame mainFrame;
	
	private static JButton saveBtn;
	public static JButton headBtn;
	public static String headName;
	private JLabel autographLbl;

	public SelectDataFrame(MainFrame mainFrame, Account account) {
		this.mainFrame = mainFrame;
		Constants.select = false;
		this.account = account;
		headName = account.getHead();
		Constants.saveData = false; // ÿ�δ򿪸������ϴ���ͱ�ʾĬ�ϲ����޸�����
		Constants.saveWin = false;
		LoginFrameCommon.setStyle(this);
		initTop();
		initCenter();
		setBounds(300, 100, 450, 600);
	}
	
	private void initTop() {
		JLabel bgLbl = new JLabel(CommonMethod.getImg(this, "data_background" + CommonMethod.myRandom() + ".png"));
		bgLbl.setLayout(null);
		bgLbl.setBounds(0, 0, 450, 220);
		Icon minIcon = CommonMethod.getImg(this, "min_def.png");
		JLabel minLbl = new JLabel(minIcon);
		minLbl.setBounds(390, 0, 30, 30);
		minLbl.setToolTipText("��С��");
		minLbl.setName("min");
		minLbl.addMouseListener(new MinListener(this));
		bgLbl.add(minLbl);
		Icon exitIcon = CommonMethod.getImg(this, "exit_def.png");
		JLabel exitLbl = new JLabel(exitIcon);
		exitLbl.setBounds(420, 0, 30, 30);
		exitLbl.setToolTipText("�ر�");
		exitLbl.setName("dispose");
		exitLbl.addMouseListener(new MinListener(this));
		bgLbl.add(exitLbl);
		
		JPanel lucencyPanel = new JPanel();
		lucencyPanel.setBounds(0, 150, 450, 100);
		lucencyPanel.setBackground(new Color(0, 0, 0, 0.2f));
		bgLbl.add(lucencyPanel);
		// ͷ��
		headBtn = new JButton(CommonMethod.getImg(this, account.getHead() + ".png"));
		headBtn.setBounds(10, 120, 80, 80);
		headBtn.setActionCommand("head");
		headBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		headBtn.addActionListener(new DataFrameListener(this, mainFrame));
		bgLbl.add(headBtn);
		// �ǳ�
		JLabel nicknameLbl = new JLabel(account.getNickname());
		nicknameLbl.setBounds(100, 130, 70, 20);
		bgLbl.add(nicknameLbl);
		// �˺�
		JLabel numberLbl = new JLabel(account.getNumber());
		numberLbl.setBounds(180, 130, 70, 20);
		bgLbl.add(numberLbl);
		// �ȼ�
		JLabel gradeLbl = new JLabel(CommonMethod.getImg(this, "grade.png"));
		gradeLbl.setBounds(100, 145, 82, 30);
		bgLbl.add(gradeLbl);
		// ����ǩ��
		autographLbl = new JLabel(account.getAutograph());
		autographLbl.setBounds(100, 170, 200, 20);
		bgLbl.add(autographLbl);
		add(bgLbl);
		
		JPanel dataPanel = new JPanel();
		dataPanel.setBounds(0, 220, 450, 30);
		dataPanel.setBackground(new Color(176, 196, 222));
		dataPanel.setLayout(null);
		JLabel dataLbl = new JLabel("��������:");
		dataLbl.setBounds(20, 5, 100, 20);
		dataLbl.setFont(new Font("�����п�", Font.BOLD, 15));
		dataLbl.setForeground(Color.WHITE);
		dataPanel.add(dataLbl);
		add(dataPanel);
		
		JLabel btnLbl = new JLabel();
		btnLbl.setBounds(-1, 249, 452, 50);
		btnLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// ���水ť
		saveBtn = new JButton("����");
		saveBtn.setBounds(300, 10, 60, 30);
		saveBtn.setEnabled(false);
		saveBtn.setActionCommand("save");
		saveBtn.addActionListener(new DataFrameListener(this, mainFrame));
		btnLbl.add(saveBtn);
		// �رհ�ť
		JButton exitBtn = new JButton("�ر�");
		exitBtn.setBounds(370, 10, 60, 30);
		exitBtn.setActionCommand("exit");
		exitBtn.addActionListener(new DataFrameListener(this, mainFrame));
		btnLbl.add(exitBtn);
		add(btnLbl);
	}
	
	private JTextField nicknameTxt; // �ǳ�
	private JTextField ageTxt; // ����
	private JTextField mobileTxt; // �ֻ�
	private JTextField proviceTxt; // ʡ��
	private JTextField cityTxt; // ����
	private JTextField areaTxt; // ����
	private JTextField autographTxt; // ����ǩ��
	private JComboBox<String> genderBox;
	private String gender = "��"; // �Ա�
	
	private void initCenter() {
		// �ǳ�
		JLabel nicknameLbl = new JLabel("��   ��:");
		nicknameLbl.setBounds(20, 320, 60, 20);
		add(nicknameLbl);
		nicknameTxt = new JTextField();
		nicknameTxt.setBounds(80, 315, 130, 30);
		nicknameTxt.setText(account.getNickname());
		nicknameTxt.addKeyListener(this);
		add(nicknameTxt);
		// �Ա�
		JLabel genderLbl = new JLabel("��   ��:");
		genderLbl.setBounds(230, 320, 60, 20);
		add(genderLbl);
		genderBox = new JComboBox<String>();
		String value = account.getGender();
		if (value.equals("��")) {
			genderBox.addItem("��");
			genderBox.addItem("Ů");
		} else {
			genderBox.addItem("Ů");
			genderBox.addItem("��");
		}
		genderBox.setBounds(290, 315, 130, 30);
		genderBox.setName("gender");
		genderBox.addItemListener(this);
		add(genderBox);
		// ����
		JLabel ageLbl = new JLabel("��   ��:");
		ageLbl.setBounds(230, 360, 60, 20);
		add(ageLbl);
		ageTxt = new JTextField();
		ageTxt.setBounds(290, 355, 130, 30);
		ageTxt.setText(String.valueOf(account.getAge()));
		ageTxt.addKeyListener(this);
		add(ageTxt);
		// �ֻ�
		JLabel mobileLbl = new JLabel("��   ��:");
		mobileLbl.setBounds(20, 360, 60, 20);
		add(mobileLbl);
		mobileTxt = new JTextField();
		mobileTxt.setBounds(80, 355, 130, 30);
		mobileTxt.setText(account.getMobile());
		mobileTxt.addKeyListener(this);
		add(mobileTxt);
		// ʡ��
		JLabel proviceLbl = new JLabel("ʡ   ��:");
		proviceLbl.setBounds(20, 400, 60, 20);
		add(proviceLbl);
		proviceTxt = new JTextField();
		proviceTxt.setBounds(80, 395, 130, 30);
		proviceTxt.setText(account.getProvice());
		proviceTxt.addKeyListener(this);
		add(proviceTxt);
		// ����
		JLabel cityLbl = new JLabel("��   ��:");
		cityLbl.setBounds(230, 400, 60, 20);
		add(cityLbl);
		cityTxt = new JTextField();
		cityTxt.setBounds(290, 395, 130, 30);
		cityTxt.setText(account.getCity());
		cityTxt.addKeyListener(this);
		add(cityTxt);
		// ����
		JLabel areaLbl = new JLabel("��   ��:");
		areaLbl.setBounds(20, 440, 60, 20);
		add(areaLbl);
		areaTxt = new JTextField();
		areaTxt.setBounds(80, 435, 130, 30);
		areaTxt.setText(account.getArea());
		add(areaTxt);
		// ����ǩ��
		JLabel autographLbl = new JLabel("����ǩ����");
		autographLbl.setBounds(20, 480, 80, 20);
		add(autographLbl);
		autographTxt = new JTextField();
		autographTxt.setBounds(20, 510, 400, 60);
		autographTxt.setText(account.getAutograph());
		autographTxt.addKeyListener(this);
		add(autographTxt);
	}
	
	public Account saveAccount() {
		Account a = new Account();
		a.setNumber(account.getNumber());
		a.setNickname(nicknameTxt.getText());
		a.setMobile(mobileTxt.getText());
		a.setProvice(proviceTxt.getText());
		a.setCity(cityTxt.getText());
		a.setArea(areaTxt.getText());
		a.setAutograph(autographTxt.getText());
		a.setAge(Integer.valueOf(ageTxt.getText()));
		a.setHead(headName);
		a.setGender(gender);
		a.setSkin(account.getSkin());
		a.setStatus(CommonMethod.IMG_URL);
		return a;
	}
	
	public static void setSaveBtn() {
		Constants.saveData = true;
		saveBtn.setEnabled(true);
	}
	
	public void updateAutograph(Account account) {
		autographLbl.setText(account.getAutograph());
		autographTxt.setText(account.getAutograph());
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (genderBox.getSelectedItem() != null) {
			setSaveBtn();
			gender = (String) genderBox.getSelectedItem(); // ��ȡ��ѡ�е���
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		setSaveBtn();
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
