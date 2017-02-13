package com.jh.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.LoginFrameCommon;

/**
 * ���ô���
 * @author Administrator
 *
 */
public class SetFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 6477755005683328159L;

	public SetFrame() {
		LoginFrameCommon.setStyle(this);
		LoginFrameCommon.setTopButton(this);
		initWidgets();
		CommonMethod.setTray(this);
	}
	
	private void initWidgets() {
		JLabel label1 = new JLabel("��������");
		label1.setBounds(20, 70, 80, 20);
		add(label1);
		JLabel label2 = new JLabel("���ͣ�");
		label2.setBounds(30, 100, 50, 20);
		add(label2);
		JComboBox<String> box = new JComboBox<String>();
		box.addItem("��ʹ�ô���");
		box.addItem("HTTP����");
		box.addItem("SOCKET5����");
		box.addItem("ʹ���������������");
		box.setBounds(70, 100, 110, 20);
		add(box);
		JLabel label3 = new JLabel("��ַ��");
		label3.setBounds(190, 100, 50, 20);
		add(label3);
		JTextField field1 = new JTextField();
		field1.setBounds(225, 100, 80, 20);
		add(field1);
		JLabel label4 = new JLabel("�˿ڣ�");
		label4.setBounds(315, 100, 50, 20);
		add(label4);
		JTextField field2 = new JTextField();
		field2.setBounds(350, 100, 80, 20);
		add(field2);
		JLabel label5 = new JLabel("�û�����");
		label5.setBounds(20, 130, 50, 20);
		add(label5);
		JTextField field3 = new JTextField();
		field3.setBounds(70, 130, 110, 20);
		add(field3);
		JLabel label6 = new JLabel("���룺");
		label6.setBounds(190, 130, 50, 20);
		add(label6);
		JTextField field4 = new JTextField();
		field4.setBounds(225, 130, 80, 20);
		add(field4);
		JLabel label7 = new JLabel("��");
		label7.setBounds(325, 130, 50, 20);
		add(label7);
		JTextField field8 = new JTextField();
		field8.setBounds(350, 130, 80, 20);
		add(field8);
		JButton btn = new JButton("����");
		btn.setEnabled(false);
		btn.setBounds(350, 160, 80, 20);
		add(btn);
		
		JLabel label8 = new JLabel("���������");
		label8.setBounds(20, 210, 80, 20);
		add(label8);
		JLabel label9 = new JLabel("���ͣ�");
		label9.setBounds(30, 250, 50, 20);
		add(label9);
		JComboBox<String> box1 = new JComboBox<String>();
		box1.addItem("��ʹ�ø߼�ѡ��");
		box1.addItem("UDP����");
		box1.addItem("TCP����");
		box1.setBounds(70, 250, 110, 20);
		add(box1);
		JLabel label10 = new JLabel("��ַ��");
		label10.setBounds(190, 250, 50, 20);
		add(label10);
		JTextField field9 = new JTextField();
		field9.setBounds(225, 250, 80, 20);
		add(field9);
		JLabel label11 = new JLabel("�˿ڣ�");
		label11.setBounds(315, 250, 50, 20);
		add(label11);
		JTextField field10 = new JTextField();
		field10.setBounds(350, 250, 80, 20);
		add(field10);
		
		JPanel downPanel = new JPanel();
		downPanel.setLayout(null);
		downPanel.setBackground(Color.GRAY);
		downPanel.setBounds(0, 315, 450, 40);
		JButton confrimBtn = new JButton("ȷ��");
		confrimBtn.addActionListener(this);
		confrimBtn.setBounds(220, 5, 100, 30);
		downPanel.add(confrimBtn);
		JButton cancelBtn = new JButton("ȡ��");
		cancelBtn.addActionListener(this);
		cancelBtn.setBounds(325, 5, 100, 30);
		downPanel.add(cancelBtn);
		add(downPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		new LoginFrame();
	}
}
