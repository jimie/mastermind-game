package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
//import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import layout.TableLayout;

public class ViewGUI implements IView, Observer {
	private IController controller;
	private IModel model;
	
	private CanvasPanel mainPanel;
	private JTextField[][] result;
	private Circle chooseCircleMenu;
	private JFrame frame;
	private int[] resultInput = new int[4];
	
	public ViewGUI (IController controller, IModel model) {
		 this.controller = controller;
		 this.model = model;
		 model.addObserver(this);
	}

	
	@Override
	public void startView() {
		frame = new JFrame();
		frame.setSize(370, 586);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Mastermind");
		frame.setLocationRelativeTo(null);
		
		mainPanel = new CanvasPanel();
		
		
		mainPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent event) {
	        	controller.dragged(event);
	        }
	    });
		mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mousePressed(MouseEvent event) {
	        	controller.pressed(event);
	        }
	    });
		mainPanel.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseReleased(MouseEvent event) {
	        	controller.released(event);
	        }
	    });
		
		double size[][] =
        {{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 10},
         {60, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 20}};

		mainPanel.setLayout(new TableLayout(size));		
		
		JLabel[] label = new JLabel[10];
		for (int i = 0; i < 10; i++) {
			label[i] = new JLabel(""+(i+1));
			Font font = new Font("Dialog",Font.PLAIN, 20);
			label[i].setFont(font);
			mainPanel.add(label[i], 2+" "+(10-i));
		}

		result = new JTextField[2][10];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				result[i][j] = new JTextField();
				result[i][j].setEnabled(false);
				Font font = new Font("Dialog",Font.PLAIN, 30);
				result[i][j].setFont(font);
				mainPanel.add(result[i][j], (8+i)+" "+(10-j));
			}
		}
		
		//��������� � �����
		JLabel code = new JLabel("���: ");
		Font font = new Font("Dialog",Font.PLAIN, 12);
		code.setFont(font);
		mainPanel.add(code, 2+" "+0);
		frame.add(mainPanel);
		createMenu();
		frame.setVisible(true);
		
	}
	
	public void createMenu() {
		JMenuBar menu = new JMenuBar();
		frame.setJMenuBar(menu);
		JMenu fileMenu = new JMenu("����");
		JMenu helpMenu = new JMenu("������");
		menu.add(fileMenu);
		menu.add(helpMenu);
		
		JMenuItem tryAgain = fileMenu.add("������ �������");
		fileMenu.addSeparator();
		JMenuItem exit = fileMenu.add("�����");
		
		
		JMenuItem rules = helpMenu.add("��� ������");
		
		tryAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				controller.startAgain();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		rules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				showRules();
			}
		});

	}

	public void showAbout() {
		JFrame about = new JFrame();
		JPanel panel = new JPanel();
		about.add(panel);
		about.setSize(300, 200);
		about.setLocationRelativeTo(null);
		about.setUndecorated(true);
		about.setVisible(true);
		
		JTextArea text = new JTextArea();
		text.setEditable(false);
		text.setText("123");
		panel.setLayout(new BorderLayout());
		panel.add(text);
	}


	public void showRules() {
		final JFrame about = new JFrame();
		JPanel panel = new JPanel();
		about.add(panel);
		about.setSize(320, 180);
		about.setLocationRelativeTo(null);
		about.setUndecorated(true);
		about.setVisible(true);
		
		JTextArea text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setText("��������� ���������� ������������������ �� 4 ������ ������� ������� -" +
				" ��� ���, ������� ����� �������." +
				" �������������� ������ �� ��������������� �������, ����� �����" +
				" � ���� ������ ��������� ��������� �������. ������ ����� - ������� ������� ������� " +
				"�� ����� �������. ������ ����� - ������� ������� �������, �� ����� �� �� ����� " +
				"�������." +
				" ����� ������ 10 ������� ��� ����������. Good luck.");
		panel.setLayout(new BorderLayout());
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				about.setVisible(false);
			}
		});
		panel.add(text, BorderLayout.CENTER);
		panel.add(ok, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable o, Object arg) {
		int k = model.getCount();
		result[0][k-1].setText(""+model.getBulls());
		result[1][k-1].setText(""+model.getCows());
		controller.isWin();
	}
	



	@Override
	public void repaint() {
	frame.repaint();		
	}


	@Override
	public Circle getChooseCircleMenu() {
		// TODO Auto-generated method stub
		return chooseCircleMenu;
	}
	@Override
	public void setChooseCircleMenu(Circle c) {
		chooseCircleMenu = c;
	}
	public Circle getCircleMenu(int i) {
		return mainPanel.getCircleMenu(i);
	}
	public Circle getCircleInput( int i, int j) {
		return mainPanel.getCircleInput(i, j);
	}
	public Circle getCircleResult(int i) {
		return mainPanel.getCircleResult(i);
	}

	@Override
	public void paintOverCircle(int i, int j, Color color) {
		mainPanel.paintOverInputCircle(i, j, color);
		
	}
	public void paintOverCircle(int i, Color color) {
		mainPanel.paintOverResultCircle(i, color);
		
	}


	@Override
	public void addResult(int i, int numberByColor) {
		resultInput[i] = numberByColor;
		
	}


	@Override
	public int[] getResultInput() {
		// TODO Auto-generated method stub
		return resultInput;
	}
	public void showWin() {
		JOptionPane.showMessageDialog(null, "�� ������� ��� �� " + model.getCount() + "-�� �������", 
				"���������", JOptionPane.INFORMATION_MESSAGE);

	}


	@Override
	public void showFail() {
		JOptionPane.showMessageDialog(null, "�� �� ������� ���.", 
				"�� ���������", JOptionPane.INFORMATION_MESSAGE);
	}


	@Override
	public void eraseWindows() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 10; j++) {
				result[i][j].setText("");
				result[i][j].setText("");
			}
		}
	}
}
