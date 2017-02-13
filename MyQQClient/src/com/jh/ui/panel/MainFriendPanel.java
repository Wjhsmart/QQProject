package com.jh.ui.panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jh.bean.Account;
import com.jh.client.Client;
import com.jh.ui.ChatFrame;
import com.jh.ui.MainFrame;

public class MainFriendPanel extends JPanel {

	private static final long serialVersionUID = -4523251512810571833L;
	
	private DefaultListModel<Account> listModel;
	private int index = -1;
	
	public JList<Account> fList;

	public MainFriendPanel(MainFrame mainFrame, Client client, Account account, List<Account> accounts) {
		setBounds(0, 0, 260, 360);
		setOpaque(false);
		setBorder(null);
		fList = new JList<Account>();
		fList.setFixedCellHeight(80);
		fList.setFixedCellWidth(280);
		fList.setVisibleRowCount(4);
		fList.setBorder(null);
		listModel = new DefaultListModel<Account>();
		if (accounts != null) {
			for (Account a : accounts) {
				listModel.addElement(a);
			}
		}
		fList.setModel(listModel);
		fList.setCellRenderer(new FriendListCellRenderer(mainFrame));
		fList.setOpaque(false);
		fList.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				@SuppressWarnings("rawtypes")
				JList list = (JList) e.getSource(); // ��ȡ��ǰ����λ��
				index = list.locationToIndex(e.getPoint()); // locationToIndex(Point)
				list.setSelectedIndex(index); // �����������λ�õ��Ǹ���Ŀ����������Ϊ��ѡ�У�һ������Ϊ��ѡ�У���CellRenderer���isSelected��Ϊtrue
			}
		});
		fList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) { // ������������������ҵ�������£�BUTTON1ָ��������BUTTON2ָ����,BUTTON3ָ����Ҽ�
					@SuppressWarnings("rawtypes")
					JList list = (JList)e.getSource();
					Account toAccount = (Account) list.getModel().getElementAt(index); // ��ȡ���������һ���˺�
					if (!mainFrame.getChatFrames().containsKey(toAccount)) { // ��������û�ж�Ӧ�����촰�壬��newһ�����촰��
						ChatFrame chatFrame = new ChatFrame(client, account, toAccount);
						mainFrame.getChatFrames().put(toAccount, chatFrame); // �Ѻ��Ѻͺ�������Ӧ�����촰��put��map����
					} else { // ����Ѿ��ж�Ӧ�����촰����
						mainFrame.getChatFrames().get(toAccount).setVisible(true); // ��toAcoount����Ӧ�Ĵ�������Ϊ�ɼ�
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		JScrollPane scroll = new JScrollPane(fList);
		scroll.setBounds(10, 0, 260, 360);
		scroll.getViewport().setOpaque(false);
		scroll.setOpaque(false);
		scroll.setBorder(null);
		add(scroll);
	}
	
	public void updateFriendList(Account account) {
		listModel.addElement(account);
	}
	
	public void updateFriendData(Account account) {
		int index = listModel.indexOf(account); // �����˺Ż�ȡ�����ѵ�����
		listModel.removeElement(account); // ��ԭ���ĺ���remove��
		listModel.insertElementAt(account, index); // �����µ�account���뵽ԭ����λ����
	}
}
