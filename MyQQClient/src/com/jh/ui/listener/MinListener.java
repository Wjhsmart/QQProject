package com.jh.ui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.jh.common.Constants;
import com.jh.ui.common.CommonMethod;
import com.jh.ui.common.UIUtil;

public class MinListener extends MouseAdapter {
	
	private JFrame frame;
	private String name;
	
	public MinListener(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name.equals("task_min")) { // ��С��������
			frame.setVisible(false);
		} else if (name.equals("dispose")) { // �ѵ�ǰ�����ͷŵ�
			frame.dispose();
			Constants.select = true;
			Constants.isAlterSkinFrameOpen = true;
			Constants.isSeekFirendFrameOpen = true;
			Constants.isAlterHeadFrameOpen = true;
			Constants.isFriendMessageFrameOpen = true;
			Constants.isFriendMessageExtendsOpen = true;
			Constants.isApplyManageFrameOpen = true;
			Constants.isSelectPasswordFrameOpen = true;
		} else if (name.equals("min")) {
			frame.setExtendedState(JFrame.ICONIFIED); // ����JFrame���ڵ�״̬
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name.equals("task_min")) {
			UIUtil.change(e, this.getClass().getResource("/images/min_over.png"));
		} else if (name.equals("dispose")) {
			UIUtil.change(e, this.getClass().getResource("/images/exit_over.png"));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		name = CommonMethod.getLabelName(e);
		if (name.equals("task_min")) {
			UIUtil.change(e, this.getClass().getResource("/images/min_def.png"));
		} else if (name.equals("dispose")) {
			UIUtil.change(e, this.getClass().getResource("/images/exit_def.png"));
		}
	}
}
