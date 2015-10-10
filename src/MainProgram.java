// Software: 8 - Puzzle Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright 2015 (c) Hy Truong Son. All rights reserved.

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MainProgram {
	
	static int N = 3;
	
	static JFrame MainFrame;
	static JPanel StartPanel;
	static JPanel FinishPanel;
	static JPanel ControlPanel;
	static JButton RunButton;
	static JButton SettingButton;
	static JButton AboutButton;
	
	static int widthBox = 40;
	static int heightBox = 25;
	static int widthButton = 200;
	static int heightButton = 25;
	static int marginX = 40;
	static int marginY = 40;
	static int paddingX = 5;
	static int paddingY = 5;
	
	static int widthStartPanel = N * widthBox + 2 * marginX;
	static int heightStartPanel = N * heightBox + 2 * marginY;
	static int widthFinishPanel = widthStartPanel;
	static int heightFinishPanel = heightStartPanel;
	static int widthControlPanel = widthButton + 2 * marginX;
	static int heightControlPanel = heightStartPanel;
	static int widthMainFrame = widthStartPanel + widthFinishPanel + widthControlPanel;
	static int heightMainFrame = heightStartPanel;
	
	static JComboBox StartBox[][] = new JComboBox[N][N];
	static JComboBox FinishBox[][] = new JComboBox[N][N];
	static String NumberData[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	static Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	static TitledBorder titleStartPanel = BorderFactory.createTitledBorder(loweredetched, "Start");
	static TitledBorder titleFinishPanel = BorderFactory.createTitledBorder(loweredetched, "Finish");
	static TitledBorder titleControlPanel = BorderFactory.createTitledBorder(loweredetched, "Control");
	static TitledBorder titleSettingPanel = BorderFactory.createTitledBorder(loweredetched, "Settings");
	static TitledBorder titleAnimationPanel = BorderFactory.createTitledBorder(loweredetched, "Animation");
	
	static JFrame SettingFrame;
	static JPanel SettingPanel;
	static JLabel AlgorithmLabel;
	static JComboBox AlgorithmBox;
	static JLabel AnimationLabel;
	static JComboBox AnimationBox;
	static JLabel SpeedLabel;
	static JSlider SpeedSlider;
	
	static int widthLabel = 100;
	static int heightLabel = 30;
	static int widthAlgorithmBox = 60;
	static int heightAlgorithmBox = 30;
	static int widthAnimationBox = 130;
	static int heightAnimationBox = 30;
	static int widthSettingFrame = widthLabel + widthAnimationBox + 2 * marginX;
	static int heightSettingFrame = 5 * marginY + 2 * marginY;
	
	static String AlgorithmData[] = {"BFS", "A*"};
	static String AnimationData[] = {"One by one", "Automatically"};
	
	static int AlgorithmType;
	static int AnimationType;
	static int Speed;
	
	static JFrame AnimationFrame;
	static JPanel AnimationPanel;
	static JTextField TextField[][] = new JTextField[N][N];
	static JTextField StepText;
	static JButton StopButton;
	static JButton BackButton;
	static JButton NextButton;
	static JProgressBar ProgressBar;
	
	static int widthText = 30;
	static int heightText = 30;
	static int widthStepText = 100;
	static int widthStopButton = 70;
	static int widthBackButton = widthStopButton;
	static int widthNextButton = widthStopButton;
	static int widthProgressBar = 200;
	static int heightProgressBar = 25;
	static int widthAnimationFrame = 2 * marginX + 3 * widthBackButton + widthProgressBar;
	static int heightAnimationFrame = 2 * marginY + N * heightText + (N - 1) * paddingY + heightLabel + heightButton;
	
	static int nSteps;
	static int Start[][] = new int [N][N];
	static int Finish[][] = new int [N][N];
	static int Step[][] = new int [N][N];
	
	public static void Init_MainFrame_GUI(){
		MainFrame = new JFrame();
		MainFrame.setTitle("8 - Puzzle Solver");
		MainFrame.setSize(widthMainFrame, heightMainFrame);
		MainFrame.setResizable(false);
		MainFrame.setLayout(null);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int PositionX = 0;
		
		StartPanel = new JPanel();
		StartPanel.setBounds(PositionX, 0, widthStartPanel, heightStartPanel);
		StartPanel.setLayout(null);
		titleStartPanel.setTitleJustification(TitledBorder.RIGHT);
		StartPanel.setBorder(titleStartPanel);
		MainFrame.add(StartPanel);
		
		int number = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				StartBox[i][j] = new JComboBox(NumberData);
				int x = PositionX + marginX + i * (widthBox + paddingX);
				int y = marginY + j * (heightBox + paddingY);
				StartBox[i][j].setBounds(x, y, widthBox, heightBox);
				StartBox[i][j].setSelectedIndex(number);
				StartPanel.add(StartBox[i][j]);
				number++;
			}
		
		PositionX += widthStartPanel;
		
		FinishPanel = new JPanel();
		FinishPanel.setBounds(PositionX, 0, widthFinishPanel, heightFinishPanel);
		FinishPanel.setLayout(null);
		titleFinishPanel.setTitleJustification(TitledBorder.RIGHT);
		FinishPanel.setBorder(titleFinishPanel);
		
		number = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				FinishBox[i][j] = new JComboBox(NumberData);
				int x = PositionX + marginX + i * (widthBox + paddingX);
				int y = marginY + j * (heightBox + paddingY);
				FinishBox[i][j].setBounds(x, y, widthBox, heightBox);
				FinishBox[i][j].setSelectedIndex(number);
				MainFrame.add(FinishBox[i][j]);
				number++;
			}
		MainFrame.add(FinishPanel);
		
		PositionX += widthFinishPanel;
		
		ControlPanel = new JPanel();
		ControlPanel.setBounds(PositionX, 0, widthControlPanel, heightControlPanel);
		ControlPanel.setLayout(null);
		titleControlPanel.setTitleJustification(TitledBorder.RIGHT);
		ControlPanel.setBorder(titleControlPanel);
		
		int PositionY = marginY;
		RunButton = new JButton("Search and Animation");
		RunButton.setBounds(PositionX + marginX, PositionY, widthButton, heightButton);
		MainFrame.add(RunButton);
		
		PositionY += heightButton + paddingY;
		SettingButton = new JButton("Settings");
		SettingButton.setBounds(PositionX + marginX, PositionY, widthButton, heightButton);
		MainFrame.add(SettingButton);
		
		PositionY += heightButton + paddingY;
		AboutButton = new JButton("About Program");
		AboutButton.setBounds(PositionX + marginX, PositionY, widthButton, heightButton);
		MainFrame.add(AboutButton);
		
		MainFrame.add(ControlPanel);
		
		MainFrame.setLocationRelativeTo(null);
		MainFrame.setVisible(true);
	}
	
	public static void Init_SettingFrame_GUI(){
		SettingFrame = new JFrame();
		SettingFrame.setTitle("");
		SettingFrame.setSize(widthSettingFrame, heightSettingFrame);
		SettingFrame.setResizable(false);
		SettingFrame.setLayout(null);
		
		SettingPanel = new JPanel();
		SettingPanel.setBounds(0, 0, widthSettingFrame, heightSettingFrame);
		SettingPanel.setLayout(null);
		titleSettingPanel.setTitleJustification(TitledBorder.RIGHT);
		SettingPanel.setBorder(titleSettingPanel);
		SettingFrame.add(SettingPanel);
		
		int PositionY = marginY;
		
		AlgorithmLabel = new JLabel("Algorithm:");
		AlgorithmLabel.setBounds(marginX, PositionY, widthLabel, heightLabel);
		AlgorithmLabel.setOpaque(true);
		SettingPanel.add(AlgorithmLabel);
		
		AlgorithmBox = new JComboBox(AlgorithmData);
		AlgorithmBox.setBounds(marginX + widthLabel, PositionY, widthAlgorithmBox, heightAlgorithmBox);
		SettingPanel.add(AlgorithmBox);
		
		PositionY += 2 * heightLabel;
		
		AnimationLabel = new JLabel("Animation:");
		AnimationLabel.setBounds(marginX, PositionY, widthLabel, heightLabel);
		AnimationLabel.setOpaque(true);
		SettingPanel.add(AnimationLabel);
		
		AnimationBox = new JComboBox(AnimationData);
		AnimationBox.setBounds(marginX + widthLabel, PositionY, widthAnimationBox, heightAnimationBox);
		SettingPanel.add(AnimationBox);
		
		PositionY += 2 * heightLabel;
		
		SpeedLabel = new JLabel("Speed delay (0 - 1,000 ms):");
		SpeedLabel.setBounds(marginX, PositionY, 2 * widthLabel, heightLabel);
		SpeedLabel.setOpaque(true);
		SettingPanel.add(SpeedLabel);
		
		PositionY += heightLabel;
		
		SpeedSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 1000, 300);
		SpeedSlider.setMajorTickSpacing(10);
		SpeedSlider.setBounds(marginX, PositionY, widthSettingFrame - 2 * marginY, 30);
		SettingPanel.add(SpeedSlider);
		
		SettingFrame.setLocationRelativeTo(null);
	}
	
	public static void Init_AnimationFrame_GUI(){
		AnimationFrame = new JFrame();
		AnimationFrame.setTitle("");
		AnimationFrame.setSize(widthAnimationFrame, heightAnimationFrame);
		AnimationFrame.setResizable(false);
		AnimationFrame.setLayout(null);
		
		AnimationPanel = new JPanel();
		AnimationPanel.setBounds(0, 0, widthAnimationFrame, heightAnimationFrame);
		AnimationPanel.setLayout(null);
		titleAnimationPanel.setTitleJustification(TitledBorder.RIGHT);
		AnimationPanel.setBorder(titleAnimationPanel);
		AnimationFrame.add(AnimationPanel);
		
		int PositionY = marginY;
		int marginX = (widthAnimationFrame - N * widthText - (N - 1) * paddingX) / 2;
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				TextField[i][j] = new JTextField("...");
				int x = marginX + i * (widthText + paddingX);
				int y = marginY + j * (heightText + paddingY);
				TextField[i][j].setBounds(x, y, widthText, heightText);
				TextField[i][j].setEditable(false);
				TextField[i][j].setHorizontalAlignment(JTextField.CENTER);
				AnimationPanel.add(TextField[i][j]);
			}
		
		PositionY += N * heightText + (N + 1) * paddingY;
		marginX = (widthAnimationFrame - widthStepText) / 2;
		
		StepText = new JTextField("...");
		StepText.setBounds(marginX, PositionY, widthStepText, heightText);
		StepText.setEditable(false);
		StepText.setHorizontalAlignment(JTextField.CENTER);
		AnimationPanel.add(StepText);
		
		PositionY += heightText + 2 * paddingY;
		marginX = (widthAnimationFrame - 3 * widthNextButton - widthProgressBar - 2 * paddingX) / 2;
		int PositionX = marginX;
		
		StopButton = new JButton("Stop");
		StopButton.setBounds(PositionX, PositionY, widthStopButton, heightButton);
		AnimationPanel.add(StopButton);
		
		PositionX += widthStopButton + paddingX;
		
		BackButton = new JButton("Back");
		BackButton.setBounds(PositionX, PositionY, widthBackButton, heightButton);
		AnimationPanel.add(BackButton);
		
		PositionX += widthBackButton + paddingX;
		
		NextButton = new JButton("Next");
		NextButton.setBounds(PositionX, PositionY, widthNextButton, heightButton);
		AnimationPanel.add(NextButton);
		
		PositionX += widthNextButton + paddingX;
		
		ProgressBar = new JProgressBar();
		ProgressBar.setBounds(PositionX, PositionY, widthProgressBar, heightProgressBar);
		ProgressBar.setValue(0);
		ProgressBar.setStringPainted(true);
		ProgressBar.setBorderPainted(true);
		AnimationPanel.add(ProgressBar);
		
		AnimationFrame.setLocationRelativeTo(null);
	}
	
	public static boolean getTable(JComboBox ComboBox[][], int Table[][]){
		boolean used[] = new boolean [N * N];
		for (int i = 0; i < N * N; i++) used[i] = false;
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				Table[i][j] = ComboBox[i][j].getSelectedIndex();
				if (used[Table[i][j]]) return false;
				used[Table[i][j]] = true;
			}
		
		return true;
	}
	
	public static void show_AnimationFrame(int currentStep){
		if (AlgorithmType == 0)
			BFS.getStep(currentStep, Step);
		else
			Astar.getStep(currentStep, Step);
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				if (Step[i][j] == 0)
					TextField[i][j].setBackground(Color.orange);
				else
					TextField[i][j].setBackground(Color.green);
				TextField[i][j].setText(Integer.toString(Step[i][j]));
			}
		
		StepText.setText("Step " + Integer.toString(currentStep + 1));
		ProgressBar.setValue(100 * (currentStep + 1) / nSteps);
	}
	
	public static void Process_AnimationFrame() throws InterruptedException{
		AlgorithmType = AlgorithmBox.getSelectedIndex();
		AnimationType = AnimationBox.getSelectedIndex();
		Speed = SpeedSlider.getValue();
		
		if (!getTable(StartBox, Start)){
			JOptionPane.showMessageDialog(MainFrame, "Wrong Start!", "Notice", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!getTable(FinishBox, Finish)){
			JOptionPane.showMessageDialog(MainFrame, "Wrong Finish!", "Notice", JOptionPane.ERROR_MESSAGE);
			return;
		}
	
		if (AlgorithmType == 0){
			long StartTime = System.currentTimeMillis();
			nSteps = BFS.Search(Start, Finish);
			long FinishTime = System.currentTimeMillis();
			long RunTime = FinishTime - StartTime;
			
			if (nSteps == -1){
				JOptionPane.showMessageDialog(MainFrame, 
						"BFS algorithm: There is no solution!\n" +
						"Run time: " + RunTime + " ms.");
				return;
			}else
				JOptionPane.showMessageDialog(MainFrame, 
						"BFS algorithm found a solution!\n" +
						"Number of steps: " + nSteps + ".\n" +
						"Run time: " + RunTime + " ms.");
		}else{
			long StartTime = System.currentTimeMillis();
			nSteps = Astar.Search(Start, Finish);
			long FinishTime = System.currentTimeMillis();
			long RunTime = FinishTime - StartTime;
			
			if (nSteps == -1){
				JOptionPane.showMessageDialog(MainFrame, 
						"A* algorithm: There is no solution!\n" +
						"Run time: " + RunTime + " ms.");
				return;
			}else
				JOptionPane.showMessageDialog(MainFrame, 
						"A* algorithm found a solution!\n" +
						"Number of steps: " + nSteps + ".\n" +
						"Run time: " + RunTime + " ms.");
		}
		
		AnimationFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		AnimationFrame.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent AnEvent){
			 }
		});
		
		if (AnimationType == 0){
			BackButton.setEnabled(true);
			NextButton.setEnabled(true);
			StopButton.setEnabled(true);
		}else{
			BackButton.setEnabled(false);
			NextButton.setEnabled(false);
			StopButton.setEnabled(false);
		}
		
		AnimationFrame.setVisible(true);
		
		if (AnimationType == 0){
			JEventQueue Events = new JEventQueue();
			Events.listenTo(StopButton, "Stop");
			Events.listenTo(BackButton, "Back");
			Events.listenTo(NextButton, "Next");
			
			int currentStep = 0;
			show_AnimationFrame(currentStep);
			
			while (true){
				EventObject AnEvent = Events.waitEvent();
				String name = Events.getName(AnEvent);
				
				if (name.equals("Stop")){
					AnimationFrame.setVisible(false);
					return;
				}
				
				if (name.equals("Back"))
					if (currentStep > 0){
						currentStep--;
						show_AnimationFrame(currentStep);
					}
				
				if (name.equals("Next"))
					if (currentStep < nSteps - 1){
						currentStep++;
						show_AnimationFrame(currentStep);
					}
			}
		}else{
			for (int currentStep = 0; currentStep < nSteps; currentStep++){
				show_AnimationFrame(currentStep);
				if (currentStep < nSteps - 1)
					Thread.sleep(Speed);
			}
			
			StopButton.setEnabled(true);
			JEventQueue Events = new JEventQueue();
			Events.listenTo(StopButton, "Stop");
			
			while (true){
				EventObject AnEvent = Events.waitEvent();
				String name = Events.getName(AnEvent);
				
				if (name.equals("Stop")){
					AnimationFrame.setVisible(false);
					return;
				}
			}
		}
	}
	
	public static void Process_MainFrame() throws InterruptedException{
		JEventQueue Events = new JEventQueue();
		Events.listenTo(RunButton, "Run");
		Events.listenTo(SettingButton, "Setting");
		Events.listenTo(AboutButton, "About");
		
		while (true){
			EventObject AnEvent = Events.waitEvent();
			String name = Events.getName(AnEvent);
			
			if (name.equals("Run")){
				Process_AnimationFrame();
			}
			
			if (name.equals("Setting")){
				SettingFrame.setVisible(true);
			}
			
			if (name.equals("About"))
				JOptionPane.showMessageDialog(MainFrame, 
						"Software: 8 - Puzzle Solver\n" +
						"Author: Hy Truong Son\n" +
						"Major: BSc. Computer Science\n" +
						"Class: 2013 - 2016\n" +
						"Institution: Eotvos Lorand University\n" +
						"Email: sonpascal93@gmail.com\n" +
						"Website: http://people.inf.elte.hu/hytruongson/\n" +
						"Copyright ©2013-2016, Hy Truong Son. All rights reserved.");
		}
	}
	
	public static void main(String args[]) throws InterruptedException{
		System.out.println("Software: 8 - Puzzle Solver");
		System.out.println("Author: Hy Truong Son");
		System.out.println("Major: BSc. Computer Science");
		System.out.println("Class: 2013 - 2016");
		System.out.println("Institution: Eotvos Lorand University");
		System.out.println("Email: sonpascal93@gmail.com");
		System.out.println("Website: http://people.inf.elte.hu/hytruongson/");
		System.out.println("Copyright ©2013-2016, Hy Truong Son. All rights reserved.");
		
		Init_MainFrame_GUI();
		Init_SettingFrame_GUI();
		Init_AnimationFrame_GUI();
		Process_MainFrame();
	}
	
}
