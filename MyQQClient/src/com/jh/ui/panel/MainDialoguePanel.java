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
import com.jh.bean.Message;
import com.jh.bean.RecordMessage;
import com.jh.dao.AccountDAO;
import com.jh.dao.AccountDAOImpl;
import com.jh.ui.MainFrame;

public class MainDialoguePanel extends JPanel {

	private static final long serialVersionUID = -4523251512810571833L;
	
	private int index = -1;
	private DefaultListModel<Message> listModel;
	private AccountDAO accountDAO;

	public MainDialoguePanel(MainFrame mainFrame, List<RecordMessage> messages) {
		accountDAO = new AccountDAOImpl();
		setBounds(0, 0, 260, 360);
		setOpaque(false);
		setBorder(null);
		JList<Message> fList = new JList<Message>();
		fList.setFixedCellHeight(80);
		fList.setFixedCellWidth(280);
		fList.setVisibleRowCount(4);
		fList.setBorder(null);
		listModel = new DefaultListModel<Message>();
		Message message  = new Message();
		if (messages != null) {
			for (RecordMessage rm : messages) {
				String number = rm.getNumber();
				message.setFromAccount(accountDAO.query(number));
				String toNumber = rm.getToNumber();
				message.setToAccount(accountDAO.query(toNumber));
				message.setSendTime(rm.getSendTime());
				message.setMessage(rm.getMessages());
				listModel.addElement(message);
			}
		}
		
		fList.setModel(listModel);
		fList.setCellRenderer(new DialogueListCellRenderer(mainFrame));
		fList.setOpaque(false);
		fList.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseMoved(MouseEvent e) {
				@SuppressWarnings("rawtypes")
				JList list = (JList) e.getSource(); // ��ȡ��ǰ����λ��
				int index = list.locationToIndex(e.getPoint()); // locationToIndex(Point)
				list.setSelectedIndex(index); // �����������λ�õ��Ǹ���Ŀ����������Ϊ��ѡ�У�һ������Ϊ��ѡ�У���CellRenderer���isSelected��Ϊtrue
			}
		});
		fList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) { // ������������������ҵ�������£�BUTTON1ָ��������BUTTON2ָ����,BUTTON3ָ����Ҽ�
					@SuppressWarnings("rawtypes")
					JList list = (JList)e.getSource();
					Message message = (Message) list.getModel().getElementAt(index); // ��ȡ���������һ���˺�
					Account toAccount = message.getToAccount();
					mainFrame.getChatFrames().get(toAccount).setVisible(true); // ��toAcoount����Ӧ�Ĵ�������Ϊ�ɼ�
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
	
	public void updateDialogueList(Message message) {
		int index = listModel.indexOf(message); // �����˺Ż�ȡ�����ѵ�����
		listModel.removeElement(message); // ��ԭ���ĺ���remove��
		listModel.insertElementAt(message, index); // �����µ�account���뵽ԭ����λ����
		
	}
}
