package com.jh.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jh.bean.Account;
import com.jh.client.Client;
import com.jh.common.Constants;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;
import com.jh.ui.common.TableVector;
import com.jh.ui.listener.MinListener;
import com.jh.ui.listener.SeekFriendTableMouseListener;
import com.jh.ui.listener.TxtFocusListener;

/**
 * ���Һ��ѵĴ���
 * @author Administrator
 *
 */
public class SeekFriendFrame extends JFrame implements ActionListener,KeyListener {

	private static final long serialVersionUID = -5126310786420996711L;
	
	private Account account; // �Ѿ�������û��˺���Ϣ
	private Client client;
	private MainFrame mainFrame;
	
	private JLabel resultLbl; // �����Ƽ�
	private JTextField seekTxt; // ���Һ��ѵ������
	private AccountDAO accountDAO; // ����ִ�в������ݿ����
	private TableVector toVector; // ���
	private Vector<String> columnNames; // ��������
	private DefaultTableModel dataModel; // ����ֵ
	
	public SeekFriendFrame(MainFrame mainFrame, Account account, Client client) {
		this.account = account;
		this.client = client;
		this.mainFrame = mainFrame;
		Constants.isSeekFirendFrameOpen = false;
		LoginFrameCommon.setStyle(this);
		accountDAO = new AccountDAOImpl();
		initWidgets();
		initTable();
		setBounds(300, 100, 620, 460);
	}

	private void initWidgets() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, 620, 40);
		topPanel.setBackground(new Color(30, 144, 255));
		Icon qqIconIcon = CommonMethod.getImg(this, "seek_icon_bottom.png");
		JLabel qqIconLbl = new JLabel("����", qqIconIcon, JLabel.LEFT);
		qqIconLbl.setBounds(5, 5, 200, 30);
		topPanel.add(qqIconLbl);
		Icon minIcon = CommonMethod.getImg(this, "min_def.png");
		JLabel minLbl = new JLabel(minIcon);
		minLbl.setBounds(560, 0, 30, 30);
		minLbl.setToolTipText("��С��");
		minLbl.setName("min");
		minLbl.addMouseListener(new MinListener(this));
		topPanel.add(minLbl);
		Icon exitIcon = CommonMethod.getImg(this, "exit_def.png");
		JLabel exitLbl = new JLabel(exitIcon);
		exitLbl.setBounds(590, 0, 30, 30);
		exitLbl.setToolTipText("�ر�");
		exitLbl.setName("dispose");
		exitLbl.addMouseListener(new MinListener(this));
		topPanel.add(exitLbl);
		add(topPanel);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);
		centerPanel.setBounds(0, 40, 620, 80);
		centerPanel.setBackground(new Color(175, 238, 238));
		// ���Һ��ѵ������
		seekTxt = new JTextField("����QQ����/�ǳƲ��Һ���");
		seekTxt.setBounds(20, 20, 450, 40);
		seekTxt.setName("seek");
		seekTxt.addKeyListener(this);
		seekTxt.addFocusListener(new TxtFocusListener(mainFrame, seekTxt, null));
		centerPanel.add(seekTxt);
		// ���Ұ�ť
		JButton seekBtn = new JButton("����");
		seekBtn.setBounds(500, 20, 100, 40);
		seekBtn.setActionCommand("seek");
		seekBtn.addActionListener(this);
		centerPanel.add(seekBtn);
		add(centerPanel);
		
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(null);
		resultPanel.setBounds(0, 120, 620, 30);
		resultPanel.setBackground(new Color(176, 196, 222));
		resultLbl = new JLabel("�����Ƽ�:");
		resultLbl.setBounds(10, 5, 80, 20);
		resultLbl.setFont(new Font("�����п�", Font.BOLD, 14));
		resultLbl.setForeground(Color.white);
		resultPanel.add(resultLbl);
		add(resultPanel);
		
	}

	private void initTable() {
		JTable table = new JTable();
		columnNames = new Vector<String>();
		columnNames.add("�˺�");
		columnNames.add("�ǳ�");
		columnNames.add("�Ա�");
		columnNames.add("����");
		columnNames.add("�ֻ�����");
		toVector = new TableVector();
		dataModel = new DefaultTableModel();
		dataModel.setDataVector(toVector.toVector(accountDAO.queryAll(account)), columnNames);
		table.setModel(dataModel);
		table.addMouseListener(new SeekFriendTableMouseListener(this, account, client));
		JScrollPane scroll = new JScrollPane(table); // ������������ӵ����������
		scroll.setBounds(10, 150, 600, 300);
		add(scroll);
	}

	// �û������Ұ�ťʱ����
	@Override
	public void actionPerformed(ActionEvent e) {
		seekMethod();
	}
	
	public void seekMethod() {
		String value = seekTxt.getText();
		List<Account> accounts = accountDAO.querySingle(value, value); // �����û����������ȥ��ѯ���ݿ�
		dataModel.setDataVector(toVector.toVector(accounts), columnNames); // �滻table�е�����
		resultLbl.setText("���ҽ��:");
	}

	// �û����س���ʱ����
	@Override
	public void keyTyped(KeyEvent e) {
		seekMethod();
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
