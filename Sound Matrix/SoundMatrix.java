import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.io.*;
import javax.sound.sampled.*;
import java.util.*;


public class SoundMatrix extends JPanel implements ActionListener, ChangeListener{
	private JFrame frame;
	private JButton buttons[][];
	ArrayList<JButton[][]> psongs;
	private String names[] = { "A1", "B1", "C1", "D1", "E1", "F1", "G1", "A2", "B2", "C2", "D2", "E2", "F2", "G2"};
	private GridLayout buttonGrid;
	private JPanel buttonPanel, bottomPanel, topPanel, choosePanel, columnPanel;
    Clip clip,clip2;
    int cols = 4;
    JButton playButton, randButton, clearButton;
    JLabel colLabel;
    JSlider slider;
    int multiplier = 5;
    JComboBox<String> cb;
    int currentSong = 0;



	
	public SoundMatrix() {
		frame=new JFrame("Sound Matrix");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,150);
		frame.setLayout(new BorderLayout());
		buttonGrid = new GridLayout(14,cols);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(buttonGrid);
		
		psongs = new ArrayList<JButton[][]>();

		
		setPreBuilt();
		cols = 4;
		createButtons();
		
		
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		bottomPanel.add(playButton, BorderLayout.CENTER);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		bottomPanel.add(clearButton, BorderLayout.WEST);
		
		randButton = new JButton("Randomize");
		randButton.addActionListener(this);
		bottomPanel.add(randButton, BorderLayout.EAST);
		
		slider=new JSlider(JSlider.HORIZONTAL,1,10,5);
		slider.addChangeListener(this);
		bottomPanel.add(slider, BorderLayout.SOUTH);
		
		
		choosePanel = new JPanel();
		choosePanel.setLayout(new BorderLayout());
		JLabel lbl = new JLabel("Choose a default song");
	    lbl.setVisible(true);

	    choosePanel.add(lbl, BorderLayout.WEST);


	    cb = new JComboBox<String>();
	    cb.addItem("Custom");
	    cb.addItem("Mary Had a Little Lamb");
	    cb.addItem("Let it Go");
	    cb.addItem("Call Me Maybe");
	    cb.addActionListener(this);

	    cb.setVisible(true);
	    choosePanel.add(cb, BorderLayout.CENTER);

	    
	    columnPanel = new JPanel();
	    columnPanel.setLayout(new BorderLayout());
	    JButton plusButton = new JButton("+");
	    plusButton.addActionListener(this);
	    JButton minusButton = new JButton("-");
	    minusButton.addActionListener(this);
	    colLabel = new JLabel("Columns: "+cols);
	    
	    columnPanel.add(plusButton, BorderLayout.EAST);
	    columnPanel.add(minusButton,BorderLayout.WEST);
	    columnPanel.add(colLabel, BorderLayout.CENTER);
	    
	    topPanel = new JPanel();
	    topPanel.setLayout(new BorderLayout());
	    topPanel.add(choosePanel, BorderLayout.NORTH);
	    topPanel.add(columnPanel, BorderLayout.EAST);
	    
	    JButton saveButton = new JButton("Save Current Song");
	    saveButton.addActionListener(this);
	    topPanel.add(saveButton, BorderLayout.WEST);
	    
		
		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.add(topPanel, BorderLayout.NORTH);

		frame.setSize(1000,600);
		frame.setVisible(true);
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if (e.getSource() == cb) {
			currentSong = ((JComboBox)e.getSource()).getSelectedIndex();
			chooseExistingSongs();
		}else if (e.getActionCommand().equals("+")) {
			cols++;
			createButtons();
			colLabel.setText("Columns: "+cols);
		}else if (e.getActionCommand().equals("-")) {
			if (cols>0) {
				cols--;
				createButtons();
				colLabel.setText("Columns: "+cols);
			}
		}else if (e.getActionCommand().equals("Save Current Song")) {
			saveSong();
		}else if (e.getActionCommand().equals("Clear")) {
			for (int i=0; i<cols; i++) {
				for (int j=0; j<buttons.length; j++) {
					buttons[j][i].setSelected(false);
				}
			}
		}else if (e.getActionCommand().equals("Randomize")) {
			for (int i=0; i<cols; i++) {
				for (int j=0; j<buttons.length; j++) {
					int randNumber = (int)(Math.random()*3);
					if (randNumber==0) {
						buttons[j][i].setSelected(true);
					}else {
						buttons[j][i].setSelected(false);
					}
				}
			}
		}else if ((e.getActionCommand().equals("Play"))) {
			for (int c=0; c<cols; c++) {
				System.out.println("hello");
				ArrayList<JButton> listtosend = new ArrayList<JButton>();
				for (int k=0; k<buttons.length; k++) {
					System.out.println(c+" "+k);
					if (((JButton)buttons[k][c]).isSelected()) {
						listtosend.add(buttons[k][c]);
					}
					
					
				}
				playSounds(listtosend);
				
				try {
					Thread.sleep(100*multiplier);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			try {
				Thread.sleep(100*multiplier);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
				System.out.println(e.getActionCommand());
				if (((JButton)e.getSource()).isSelected()==true) {
					((JButton)e.getSource()).setSelected(false);
				}else ((JButton)e.getSource()).setSelected(true);
			
		}

		 
		 
		
	}
	
	
	
	public static void main(String args[] )
	{
		SoundMatrix application = new SoundMatrix();
	}
	
	
	public void playSounds(ArrayList<JButton> list) {
		
		ArrayList<Clip> listOfClips = new ArrayList<Clip>();
		for (int i=0; i<list.size(); i++) {
			Clip clippy = null;
			try {  
			    clippy = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, (AudioSystem.getAudioInputStream(new File(list.get(i).getActionCommand() + ".wav"))).getFormat()));
				clippy.open(AudioSystem.getAudioInputStream(new File(list.get(i).getActionCommand() + ".wav")));
				clippy.addLineListener(new LineListener()
		        {
		            @Override
		            public void update(LineEvent event)
		            {
		                if (event.getType() == LineEvent.Type.STOP)
		                    clip.close();
		            }
		        });
		        listOfClips.add(clippy);

			}
			catch (Exception e1) {
			}
			
			
			//loop through all the buttons
			//for every button, create a clip
			//use if statements to determine which string to use
			//play the clips
		}
		
		for (int i=0; i<listOfClips.size(); i++) {
			listOfClips.get(i).start();
		}
		
		
		
	}
	
	public void createButtons() {
		
		buttonPanel.removeAll();
		buttons = new JButton [14][cols];
		
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttons[count][c] = new JButton(names[count]);
				buttons[count][c].addActionListener(this);
				buttonPanel.add(buttons[count][c]);
			}
		}
		buttonPanel.updateUI();
		
	}
	
	
	
	public void chooseExistingSongs() {
		buttonPanel.removeAll();
		cols = (psongs.get(currentSong))[0].length;
		buttons = psongs.get(currentSong);
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttonPanel.add(buttons[count][c]);
			}
		}		
		buttonPanel.updateUI();
	    colLabel.setText("Columns: "+cols);

		
	}
	
	public void saveSong() {
		psongs.add(buttons);
		cb.addItem("Song "+(psongs.size()-1));
		
	}
	
	public void setPreBuilt() {
		
		/////////////////custom
		
		buttons = new JButton[14][4];
		cols = 4;
		
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttons[count][c] = new JButton(names[count]);
				buttons[count][c].addActionListener(this);
			}
		}
		psongs.add(buttons);
		
		//////////////////mary had a little lamb
		
		buttons = new JButton [14][30];
		cols = 30;
		
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttons[count][c] = new JButton(names[count]);
				buttons[count][c].addActionListener(this);
			}
		}
		buttons[4][0].setSelected(true);
		buttons[3][1].setSelected(true);
		buttons[2][2].setSelected(true);
		buttons[3][3].setSelected(true);
		buttons[4][4].setSelected(true);
		buttons[4][5].setSelected(true);
		buttons[4][6].setSelected(true);
		buttons[3][8].setSelected(true);
		buttons[3][9].setSelected(true);
		buttons[3][10].setSelected(true);
		buttons[4][12].setSelected(true);
		buttons[6][13].setSelected(true);
		buttons[6][14].setSelected(true);
		buttons[4][16].setSelected(true);
		buttons[3][17].setSelected(true);
		buttons[2][18].setSelected(true);
		buttons[3][19].setSelected(true);
		buttons[4][20].setSelected(true);
		buttons[4][21].setSelected(true);
		buttons[4][22].setSelected(true);
		buttons[4][23].setSelected(true);
		buttons[3][24].setSelected(true);
		buttons[3][25].setSelected(true);
		buttons[4][26].setSelected(true);
		buttons[3][27].setSelected(true);
		buttons[2][28].setSelected(true);
		
		psongs.add(buttons);

		//////////////////let it go
		buttons = new JButton [14][30];
		cols = 30;
		
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttons[count][c] = new JButton(names[count]);
				buttons[count][c].addActionListener(this);
			}
		}
		buttons[4][1].setSelected(true);
		buttons[5][2].setSelected(true);
		buttons[6][3].setSelected(true);
		buttons[3][8].setSelected(true);
		buttons[8][9].setSelected(true);
		buttons[7][10].setSelected(true);
		buttons[6][15].setSelected(true);
		buttons[5][16].setSelected(true);
		buttons[4][17].setSelected(true);
		buttons[4][18].setSelected(true);
		buttons[4][19].setSelected(true);
		buttons[4][21].setSelected(true);
		buttons[5][22].setSelected(true);
		buttons[6][23].setSelected(true);
		buttons[7][25].setSelected(true);
		buttons[6][26].setSelected(true);
		
		psongs.add(buttons);
		
		
		////////////////call me maybe

		buttons = new JButton [14][30];
		cols = 30;
		
		for (int count = 0; count<names.length; count++) {
			for (int c=0; c<cols; c++) 
			{
				buttons[count][c] = new JButton(names[count]);
				buttons[count][c].addActionListener(this);
			}
		}
		buttons[6][0].setSelected(true);
		buttons[1][2].setSelected(true);
		buttons[3][3].setSelected(true);
		buttons[6][4].setSelected(true);
		buttons[3][5].setSelected(true);
		
		buttons[1][9].setSelected(true);
		buttons[1][10].setSelected(true);
		buttons[3][11].setSelected(true);
		buttons[8][12].setSelected(true);
		buttons[6][13].setSelected(true);
		
		buttons[6][17].setSelected(true);
		buttons[8][18].setSelected(true);
		buttons[9][19].setSelected(true);
		buttons[8][20].setSelected(true);
		buttons[6][21].setSelected(true);
		
		buttons[6][25].setSelected(true);
		buttons[8][26].setSelected(true);
		buttons[7][27].setSelected(true);
		buttons[7][28].setSelected(true);
		buttons[6][29].setSelected(true);		
		psongs.add(buttons);


	}




	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println("fdghjk");
		multiplier = ((JSlider)(e.getSource())).getValue();
	}
	


	

	

	
	
	

}
