package com.jh.ui.listener;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.jh.common.Constants;

/**
 * �����ƶ�����
 * @author Administrator
 *
 */
public class FrameMoverListener extends MouseAdapter implements MouseMotionListener {

	private JFrame frame;
	// ��������һ�ε�λ��
	private Point lastPoint;
	
	public FrameMoverListener(JFrame frame) {
		this.frame = frame;
	}
	
	//��갴��ʱ����
	@Override
	public void mousePressed(MouseEvent e) {
		// ��ȡ��갴��ʱ�ڴ����е�λ��
		lastPoint = e.getLocationOnScreen();
	}

	// ����϶�ʱ����
	@Override
	public void mouseDragged(MouseEvent e) {
		Point point = e.getLocationOnScreen();
		if (!lastPoint.equals(point)) {
			int x = point.x - lastPoint.x;
			int y = point.y - lastPoint.y;
			Rectangle rectangle = frame.getBounds();
			rectangle.x = Constants.frameX = rectangle.x + x;
			rectangle.y = Constants.frameY = rectangle.y + y;
			frame.setBounds(rectangle);
			frame.repaint();
			lastPoint = point;
		}
			
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

}
