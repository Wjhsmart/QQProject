package com.jh.ui;

import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.WindowConstants;

import com.jh.common.Constants;
import com.jh.ui.common.CheckCode;
import com.jh.ui.common.CheckCodeGenerator;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.listener.LoginListener;

/**
 * ע����봰��
 * @author Administrator
 *
 */
public class RegisterFrame extends JFrame implements ItemListener {

	private static final long serialVersionUID = -6900370409601641777L;
	
	// ʡ
	private String[] province = {"����", "����", "���", "�ӱ�", "ɽ��", "����", "������", "����", "����", "�ຣ", "����", "����",
								"����", "����", "�㽭", "�㶫", "����", "����", "̨��", "����", "ɽ��", "�Ĵ�", "����", "����", "��΢"};
	// ��
	private String[] city = {"����", "�ϲ�", "������", "�Ž�", "Ƽ��", "����", "ӥ̶", "�˴�", "����", "����", "����"};
	// ��
	private String[] area = {"�¹���", "�ƽ𿪷���", "�Ͽ�", "���", "����", "�ŷ�", "����", "����", "����", "��Զ", "����", "����",
								"ȫ��", "�˹�", "����", "�ڶ�", "���", "Ѱ��", "ʯ��"};
	
	private JRadioButton boyBtn; // ��ѡ��ť��
	private JRadioButton girlBtn; // ��ѡ��ťŮ
	private JCheckBox ensureBox; // ��ѡ��ť
	private JLabel submitLbl; // �ύע���ǩ
	private JComboBox<String> yearBox; // ��
	private JComboBox<String> monthBox; // ��
	private JComboBox<String> dayBox;// ��
	private JComboBox<String> provinceBox; // ʡ
	private JComboBox<String> cityBox; // ��
	private JComboBox<String> areaBox; // ��
	public static JTextField nicknameTxt; // �ǳ������
	public static JPasswordField pwdTxt; // ���������
	public static JPasswordField conpwdTxt; // ȷ�����������
	public static JTextField mobileTxt; // �ֻ��������
	public static JTextField verificationTxt; // ��֤�������
	public static JLabel randLbl; // ��һ����֤��
	public static String checkCode; // �洢��֤��
	public static int ageAcc = 0; // �洢����
	public static String genderAcc = "��"; // �洢�Ա�
	public static String proviceAcc = "����ʡ"; // �洢ʡ
	public static String cityAcc = "������";
	public static String areaAcc = "�¹���";
	public static JLabel verLbl; // ����֤��ı�ǩ
	public static boolean submit = true; // ������ʶ�Ƿ����ע���˺�
	
	public RegisterFrame() {
		try {
			LookAndFeelInfo[] feel = UIManager.getInstalledLookAndFeels(); // ��ȡ���з��
			UIManager.setLookAndFeel(feel[1].getClassName()); // ����Windows���
		} catch (Exception e) {
			e.printStackTrace();
		}
		Constants.isRegisterFrameOpen = false;
		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/qq_icon.png")));
		setTitle("ע���˺�");
		setSize(500, 550);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		initWidgets();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Constants.isRegisterFrameOpen = true;
			}
		});
		setVisible(true);
	}
	
	private void initWidgets() {
		JLabel nicknameLbl = new JLabel("�ǳƣ�");
		nicknameLbl.setBounds(60, 55, 50, 20);
		add(nicknameLbl);
		nicknameTxt = new JTextField();
		nicknameTxt.setBounds(120, 50, 300, 30);
		add(nicknameTxt);
		JLabel pwdLbl = new JLabel("���룺");
		pwdLbl.setBounds(60, 95, 50, 20);
		add(pwdLbl);
		pwdTxt = new JPasswordField();
		pwdTxt.setBounds(120, 90, 300, 30);
		add(pwdTxt);
		JLabel conpwdLbl = new JLabel("ȷ�����룺");
		conpwdLbl.setBounds(35, 135, 80, 20);
		add(conpwdLbl);
		conpwdTxt = new JPasswordField();
		conpwdTxt.setBounds(120, 130, 300, 30);
		add(conpwdTxt);
		JLabel genderLbl = new JLabel("�Ա�");
		genderLbl.setBounds(60, 170, 50, 20);
		add(genderLbl);
		ButtonGroup group = new ButtonGroup(); // �ѵ�ѡ��ť���
		boyBtn = new JRadioButton("��", true);
		boyBtn.setBounds(120, 170, 50, 20);
		boyBtn.setName("boy");
		boyBtn.addItemListener(this);
		girlBtn = new JRadioButton("Ů", false);
		girlBtn.setBounds(170, 170, 50, 20);
		girlBtn.setName("girl");
		girlBtn.addItemListener(this);
		group.add(boyBtn);
		group.add(girlBtn);
		add(boyBtn);
		add(girlBtn);
		JLabel birthdayLbl = new JLabel("���գ�");
		birthdayLbl.setBounds(60, 205, 50, 20);
		add(birthdayLbl);
		JComboBox<String> calendarBox = new JComboBox<String>();
		calendarBox.setBounds(120, 200, 60, 30);
		calendarBox.addItem("����");
		calendarBox.addItem("ũ��");
		add(calendarBox);
		yearBox = new JComboBox<String>();
		yearBox.setBounds(190, 200, 80, 30);
		for (int i = 2016; i >= 1897; i--) {
			yearBox.addItem(String.valueOf(i) + "��");
		}
		yearBox.addItemListener(this);
		add(yearBox);
		monthBox = new JComboBox<String>();
		monthBox.setBounds(280, 200, 60, 30);
		
		for (int i = 1; i <= 12; i++) {
			monthBox.addItem(String.valueOf(i) + "��");
		}
		monthBox.addItemListener(this);
		add(monthBox);
		dayBox = new JComboBox<String>();
		dayBox.setBounds(350, 200, 60, 30);
		
		for (int i = 1; i <= 31; i++) {
			dayBox.addItem(String.valueOf(i) + "��");
		}
		dayBox.addItemListener(this);
		add(dayBox);
		JLabel locationLbl = new JLabel("���ڵأ�");
		locationLbl.setBounds(50, 245, 60, 20);
		add(locationLbl);
		provinceBox = new JComboBox<String>();
		provinceBox.setBounds(120, 240, 90, 30);
		for (int i = 0,size = province.length; i < size; i++) {
			provinceBox.addItem(province[i]);
		}
		provinceBox.addItemListener(this);
		add(provinceBox);
		cityBox = new JComboBox<String>();
		cityBox.setBounds(220, 240, 90, 30);
		for (int i = 0,size = city.length; i < size; i++) {
			cityBox.addItem(city[i]);
		}
		cityBox.addItemListener(this);
		add(cityBox);
		areaBox = new JComboBox<String>();
		areaBox.setBounds(320, 240, 90, 30);
		for (int i = 0,size = area.length; i < size; i++) {
			areaBox.addItem(area[i]);
		}
		areaBox.addItemListener(this);
		add(areaBox);
		JLabel mobileLbl = new JLabel("�ֻ����룺");
		mobileLbl.setBounds(40, 285, 80, 20);
		add(mobileLbl);
		mobileTxt = new JTextField();
		mobileTxt.setBounds(120, 280, 300, 30);
		add(mobileTxt);
		JLabel verificationLbl = new JLabel("��֤�룺");
		verificationLbl.setBounds(50, 325, 80, 20);
		add(verificationLbl);
		verificationTxt = new JTextField();
		verificationTxt.setBounds(120, 320, 100, 30);
		add(verificationTxt);
		CheckCode cc = CheckCodeGenerator.getCheckCode();
		verLbl = new JLabel(new ImageIcon(cc.getBuffImg()));
		checkCode = cc.getCheckCode();
		verLbl.setBounds(250, 320, 50, 30);
		add(verLbl);
		JLabel exchangeLbl = new JLabel("��һ��");
		exchangeLbl.setBounds(320, 325, 80, 20);
		exchangeLbl.setName("exchange");
		exchangeLbl.addMouseListener(new LoginListener(this, exchangeLbl));
		add(exchangeLbl);
		ensureBox = new JCheckBox("�����Ķ���ͬ����ط����������˽����", true);
		ensureBox.setBounds(115, 360, 300, 20);
		ensureBox.addItemListener(this);
		ensureBox.setName("ensure");
		add(ensureBox);
		Icon submitIcon = CommonMethod.getImg(this, "submit_def.png");
		submitLbl = new JLabel(submitIcon);
		submitLbl.setBounds(115, 400, 304, 50);
		submitLbl.setName("submit");
		submitLbl.addMouseListener(new LoginListener(this, submitLbl));
		add(submitLbl);
	}

	/**
	 * ��Ŀ�����¼�
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (ensureBox.isSelected()) { // �жϸ�ѡ���Ƿ�ѡ��
			submitLbl.setIcon(CommonMethod.getImg(this, "submit_def.png"));
			submitLbl.setEnabled(true); // ��ʶע��ͼ����Ե�
			submit = true;
		} else {
			submitLbl.setIcon(CommonMethod.getImg(this, "submit_over.png"));
			submitLbl.setEnabled(false);
			submit = false;
		}
		if (boyBtn.isSelected()) { // �ж����Ƿ�ѡ��
			genderAcc = "��";
		} else if (girlBtn.isSelected()) {
			genderAcc = "Ů";
		}
		if (yearBox.getSelectedItem() != null && monthBox.getSelectedItem() != null && dayBox.getSelectedItem() != null) {
			String str = (String) yearBox.getSelectedItem(); // ��ȡ��ѡ�е���
			String str1 = str.substring(0,4); // ��ȡ�ַ�����ǰ4λ
			int a = Integer.valueOf(str1);
			int age = 2016 - a;
			ageAcc = age; // �����û�������
		}
		if (provinceBox.getSelectedItem() != null && cityBox.getSelectedItem() != null && areaBox.getSelectedItem() != null) {
			String provice = (String) provinceBox.getSelectedItem();
			String city = (String) cityBox.getSelectedItem();
			String area = (String) areaBox.getSelectedItem();
			proviceAcc = provice;
			cityAcc = city;
			areaAcc = area;
		}
	}
}
