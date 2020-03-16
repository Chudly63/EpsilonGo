package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class rules extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame f;
	JPanel panel;
	JPanel panelc;
	
	JButton b1;
	JButton b2;
	JButton b3;
	
	JLabel l;
	JLabel l2;
	JLabel l3;
	int r = 0;
	
	ArrayList<String> rulesImg = new ArrayList<String>();
	
	
	
	private Container createContentPane(String l) {
		Container contentPane = new Container();
	
		
		


		if(l == "Close") {
			b1 = new JButton();
			b1.setAlignmentX(Component.CENTER_ALIGNMENT);
			b1.setAlignmentY(Component.CENTER_ALIGNMENT);
			
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("assets/img/closeImg.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
			ImageIcon icon=new ImageIcon(img);
			b1.setIcon(icon);
			b1.setBorder(null);
			contentPane.setLayout(new GridLayout(1,4));
			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					f.dispose();
				}
			});
			
			contentPane.add(Box.createRigidArea(new Dimension(250,0)));
			contentPane.add(Box.createRigidArea(new Dimension(250,0)));
			contentPane.add(Box.createRigidArea(new Dimension(250,0)));
			contentPane.add(Box.createRigidArea(new Dimension(250,0)));
			contentPane.add(Box.createRigidArea(new Dimension(250,0)));
			contentPane.add(b1);
		}
		else if(l == ">") {
			b2 = new JButton();
			b2.setAlignmentX(Component.CENTER_ALIGNMENT);
			b2.setAlignmentY(Component.CENTER_ALIGNMENT);
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("assets/img/rightImg.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
			ImageIcon icon=new ImageIcon(img);
			b2.setIcon(icon);
			b2.setBorder(null);
			contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					if(r<rulesImg.size()-1) {
					
						b3.setEnabled(true);
						r = r + 1;
						
						if(r==rulesImg.size()-1) {
							b2.setEnabled(false);
							
						}
						
						
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File(rulesImg.get(r).toString()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				        
						ImageIcon icon=new ImageIcon(img);
						l2.setIcon(icon);
					}
					
				}
			});
			
			contentPane.add (Box.createVerticalGlue());
			contentPane.add(b2);
			contentPane.add (Box.createVerticalGlue());
			
		}
		else if(l == "<") {
			
			b3 = new JButton();
			b3.setEnabled(false);
			b3.setAlignmentX(Component.CENTER_ALIGNMENT);
			b3.setAlignmentY(Component.CENTER_ALIGNMENT);
			
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("assets/img/leftImg.png"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
			ImageIcon icon=new ImageIcon(img);
			b3.setIcon(icon);
			b3.setBorder(null);
			contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					if(r>0) {
						b2.setEnabled(true);
						r = r - 1;
						
						if(r==0) {
							b3.setEnabled(false);
							
						}
						
						BufferedImage img = null;
						try {
							img = ImageIO.read(new File(rulesImg.get(r).toString()));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				        
						ImageIcon icon=new ImageIcon(img);
						l2.setIcon(icon);
					}
					
				}
			});
			
			contentPane.add (Box.createVerticalGlue());
			contentPane.add(b3);
			contentPane.add (Box.createVerticalGlue());
			
		}
		
		

		
		contentPane.setBackground(new Color(220,179,92));
		return contentPane;
    	
    }

	
    private JPanel createTopPanel(){
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Rules of Go");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        topPanel.add(titleLabel);
        topPanel.setBackground(new Color(220,179,92));
        return topPanel;
    }
    
    
    private JPanel createCPanel() {
    	rulesImg.add("assets/img/rulesIntro.png");
    	rulesImg.add("assets/img/rulesLib.png");
    	rulesImg.add("assets/img/rulesCap.png");
    	rulesImg.add("assets/img/rulesIM.png");
    	//rulesImg.add("assets/img/rulesKo.png");
    	rulesImg.add("assets/img/rulesEnd.png");
    	
		panel = new JPanel();
		panel.setBackground(new Color(220,179,92));
		panelc = new JPanel();
		panelc.setBackground(new Color(220,179,92));

				

		
		l2 = new JLabel();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(rulesImg.get(0).toString()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		l2.setIcon(icon);
		l2.setHorizontalAlignment(JLabel.CENTER);
		l2.setVerticalAlignment(JLabel.CENTER);
		
		l3 = new JLabel("images go here");
		l3.setHorizontalAlignment(JLabel.CENTER);
		l3.setVerticalAlignment(JLabel.CENTER);
		
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		

	    panelc.add(Box.createRigidArea(new Dimension(0,250)));
		//panelc.add(l3,BorderLayout.WEST);
	    //panelc.add(Box.createRigidArea(new Dimension(20,0)));
		panelc.add(l2,BorderLayout.EAST);
		//panel.add(createTopPanel(),BorderLayout.NORTH);
		panel.add(createContentPane("<"), BorderLayout.WEST);
		panel.add(panelc, BorderLayout.CENTER);
		panel.add(createContentPane(">"),BorderLayout.EAST);
		panel.add(createContentPane("Close"), BorderLayout.SOUTH);
		return panel;
    	
    }
	
	public rules(Point p) {
		
		gui(p);
		
	}
	
	
	
	public void gui(Point p) {
		f = new JFrame("Rules of Go");
        try{
            f.setIconImage(ImageIO.read(new File("assets/img/center-w.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
		f.setSize(600,400);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		try {
			Point pt = new Point();
			pt = p;
			f.setLocation(pt);
		}
		catch(Exception e) {
			System.out.print("welp");
		}
		
		
		
		f.add(createCPanel());
		f.setVisible(true);
		
		
	}
	
}
