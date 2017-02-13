package com.jh.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jh.bean.Account;
import com.jh.common.Constants;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;
import com.jh.ui.listener.AddFriendActionListener;
import com.jh.ui.listener.MinListener;

/**
 * ���ѵĸ������ϴ���
 * @author Administrator
 *
 */
public class FriendMessageFrame extends JFrame {

	private static final long serialVersionUID = -6929896302280661526L;
	
	private Account account;
	public JButton addBtn;

	public FriendMessageFrame(Account account) {
		this.account = account;
		LoginFrameCommon.setStyle(this);
		Constants.isFriendMessageFrameOpen = false;
		initTop();
		initHead();
		initCenter();
		setBounds(300, 100, 400, 500);
	}
	
	private void initTop() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setBounds(0, 0, 400, 40);
		topPanel.setBackground(new Color(30, 144, 255));
		Icon qqIconIcon = CommonMethod.getImg(this, "seek_icon.png");
		JLabel qqIconLbl = new JLabel("��������", qqIconIcon, JLabel.LEFT);
		qqIconLbl.setBounds(5, 5, 200, 30);
		topPanel.add(qqIconLbl);
		Icon minIcon = CommonMethod.getImg(this, "min_def.png");
		JLabel minLbl = new JLabel(minIcon);
		minLbl.setBounds(340, 0, 30, 30);
		minLbl.setToolTipText("��С��");
		minLbl.setName("min");
		minLbl.addMouseListener(new MinListener(this));
		topPanel.add(minLbl);
		Icon exitIcon = CommonMethod.getImg(this, "exit_def.png");
		JLabel exitLbl = new JLabel(exitIcon);
		exitLbl.setBounds(370, 0, 30, 30);
		exitLbl.setToolTipText("�ر�");
		exitLbl.setName("dispose");
		exitLbl.addMouseListener(new MinListener(this));
		topPanel.add(exitLbl);
		add(topPanel);
	}
	
	private void initHead() {
		JLabel bgLbl = new JLabel(CommonMethod.getImg(this, "datum_background" + CommonMethod.myRandom() + ".png"));
		bgLbl.setBounds(0, 40, 400, 150);
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 90, 400, 60);
		bgPanel.setBackground(new Color(0, 0, 0, 0.3f));
		bgLbl.add(bgPanel);
		// ͷ��
		JLabel headLbl = new JLabel(CommonMethod.getImg(this, account.getHead() + ".png"));
		headLbl.setBounds(10, 60, 80, 80);
		bgLbl.add(headLbl);
		// �ǳ�
		JLabel nicknameLbl = new JLabel(account.getNickname());
		nicknameLbl.setBounds(100, 70, 70, 20);
		bgLbl.add(nicknameLbl);
		// �˺�
		JLabel numberLbl = new JLabel(account.getNumber());
		numberLbl.setBounds(180, 70, 70, 20);
		bgLbl.add(numberLbl);
		// ����ǩ��
		JLabel autographLbl = new JLabel(account.getAutograph());
		autographLbl.setBounds(100, 100, 200, 20);
		bgLbl.add(autographLbl);
		// �Ӻ��ѵİ�ť
		addBtn = new JButton("��Ϊ����");
		addBtn.setBounds(310, 110, 80, 30);
		addBtn.setActionCommand("add1");
		addBtn.addActionListener(new AddFriendActionListener(this));
		bgLbl.add(addBtn);
		add(bgLbl);
		
		JPanel datumPanel = new JPanel();
		datumPanel.setLayout(null);
		datumPanel.setBounds(0, 190, 400, 30);
		datumPanel.setBackground(new Color(176, 196, 222));
		JLabel datumLbl = new JLabel("��������:");
		datumLbl.setBounds(10, 5, 80, 20);
		datumLbl.setFont(new Font("�����п�", Font.BOLD, 14));
		datumLbl.setForeground(Color.white);
		datumPanel.add(datumLbl);
		add(datumPanel);
	}
	
	private void initCenter() {
		JLabel numberLbl1 = new JLabel("�˺ţ�");
		numberLbl1.setBounds(20, 230, 60, 20);
		add(numberLbl1);
		JLabel numberLbl2 = new JLabel(account.getNumber());
		numberLbl2.setBounds(80, 230, 80, 20);
		add(numberLbl2);
		JLabel nicknameLbl1 = new JLabel("�ǳƣ�");
		nicknameLbl1.setBounds(20, 260, 60, 20);
		add(nicknameLbl1);
		JLabel nicknameLbl2 = new JLabel(account.getNickname());
		nicknameLbl2.setBounds(80, 260, 80, 20);
		add(nicknameLbl2);
		JLabel ageLbl1 = new JLabel("���䣺");
		ageLbl1.setBounds(20, 290, 60, 20);
		add(ageLbl1);
		JLabel ageLbl2 = new JLabel(String.valueOf(account.getAge()));
		ageLbl2.setBounds(80, 290, 80, 20);
		add(ageLbl2);
		JLabel genderLbl1 = new JLabel("�Ա�");
		genderLbl1.setBounds(20, 320, 60, 20);
		add(genderLbl1);
		JLabel genderLbl2 = new JLabel(account.getGender());
		genderLbl2.setBounds(80, 320, 80, 20);
		add(genderLbl2);
		JLabel mobileLbl1 = new JLabel("�ֻ����룺");
		mobileLbl1.setBounds(20, 350, 60, 20);
		add(mobileLbl1);
		JLabel mobileLbl2 = new JLabel(account.getMobile());
		mobileLbl2.setBounds(80, 350, 80, 20);
		add(mobileLbl2);
		String address = account.getProvice() + "  " + account.getCity() + "  " + account.getArea();
		JLabel addressLbl1 = new JLabel("��ַ��");
		addressLbl1.setBounds(20, 380, 60, 20);
		add(addressLbl1);
		JLabel addressLbl2 = new JLabel(address);
		addressLbl2.setBounds(80, 380, 300, 20);
		add(addressLbl2);
		
		JLabel bottomLbl = new JLabel(CommonMethod.getImg(this, "south.png"));
		bottomLbl.setBounds(0, 447, 400, 53);
		add(bottomLbl);
	}
}
