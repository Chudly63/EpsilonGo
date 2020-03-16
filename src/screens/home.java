package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import board.*;
import screens.GameView;

public class home extends JFrame{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameController controller;
	
	JFrame f;
	
	String l;
	
	public home() {
		gui();
		
	}
	
    private JPanel createTopPanel(){
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel();
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/titleImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		titleLabel.setIcon(icon);
        //titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 40));
        topPanel.add(titleLabel);
        topPanel.setBackground(new Color(220,179,92));
        return topPanel;
    }
    
    private Container createContentPane() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));

		
		//JButton b1 = new JButton(" New Game ");
		JButton b1 =new JButton();
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/newGameImg0.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		b1.setIcon(icon);
		b1.setBorder(null);
		b1.setAlignmentX(Component.CENTER_ALIGNMENT);
		b1.setAlignmentY(Component.CENTER_ALIGNMENT);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Point p = f.getLocation();
				f.dispose();
				NewGame ng = new NewGame(p);
			}
		});


		
		JButton b2 = new JButton();
        img = null;
		try {
			img = ImageIO.read(new File("assets/img/loadGameImg0.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b2.setIcon(icon);
		b2.setBorder(null);
		b2.setAlignmentX(Component.CENTER_ALIGNMENT);
		b2.setAlignmentY(Component.CENTER_ALIGNMENT);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				JFileChooser j = new JFileChooser("./savedGames/"); 
				int r = j.showOpenDialog(null);
				GoBoard g = null;
				
				if (r == JFileChooser.APPROVE_OPTION) { 
	                String l = j.getSelectedFile().getAbsolutePath();
	                System.out.print(l);
	                
	                try
	                {    
	                    FileInputStream file = new FileInputStream(l); 
	                    ObjectInputStream in = new ObjectInputStream(file); 
	                      

	                    g = (GoBoard)in.readObject(); 
	                      
	                    in.close(); 
	                    file.close(); 
	                      
	                    System.out.println("Object has been deserialized "); 
	                    
	                    f.dispose();
	                    
	    			    GoBoard loadBoard = g;
	    			    GameController gameController = new GameController(loadBoard);
	    			    GameView view = new GameView(gameController);
	    			    
	    			    
	    		        gameController.setView(view);
	    		        view.showView(loadBoard);
	 
	                } 
	                  
	                catch(IOException ex) 
	                { 
	                    System.out.println("IOException is caught"); 
	                } 
	                  
	                catch(ClassNotFoundException ex) 
	                { 
	                    System.out.println("ClassNotFoundException is caught"); 
	                } 
	            }
				
			}
		});
		

		
		JButton b3 = new JButton();
        img = null;
		try {
			img = ImageIO.read(new File("assets/img/rulesImg0.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b3.setIcon(icon);
		b3.setBorder(null);
		b3.setAlignmentX(Component.CENTER_ALIGNMENT);
		b3.setAlignmentY(Component.CENTER_ALIGNMENT);
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Point p = f.getLocation();
				rules r = new rules(p);
			}
		});

		
		JButton b4 = new JButton();
        img = null;
		try {
			img = ImageIO.read(new File("assets/img/exitImg1.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b4.setIcon(icon);
		b4.setBorder(null);
		b4.setAlignmentX(Component.CENTER_ALIGNMENT);
		b4.setAlignmentY(Component.CENTER_ALIGNMENT);
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				System.exit(0);
			}
		});
		
		
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b1);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b2);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b3);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b4);
		contentPane.add (Box.createVerticalGlue());
		
		contentPane.setBackground(new Color(220,179,92));
		return contentPane;
    	
    }
	

	private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				JFileChooser j = new JFileChooser("./savedGames/"); 
				int r = j.showSaveDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) { 
	                String s = j.getSelectedFile().getAbsolutePath();
	                System.out.print(s);
                	System.out.print(this.getClass());
	                
                	s = s +".ser";
	                
	            } 
				
			}
		});
        save.setEnabled(false);

        JMenuItem load = new JMenuItem("Load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				JFileChooser j = new JFileChooser("./savedGames/"); 
				int r = j.showOpenDialog(null);
				GoBoard g = null;
				
				if (r == JFileChooser.APPROVE_OPTION) { 
	                String l = j.getSelectedFile().getAbsolutePath();
	                System.out.print(l);
	                
	                try
	                {    
	                    FileInputStream file = new FileInputStream(l); 
	                    ObjectInputStream in = new ObjectInputStream(file); 
	                      

	                    g = (GoBoard)in.readObject(); 
	                      
	                    in.close(); 
	                    file.close(); 
	                      
	                    System.out.println("Object has been deserialized "); 
	                    
	                    f.dispose();
	                    
	    			    GoBoard loadBoard = g;
	    			    GameController gameController = new GameController(loadBoard);
	    			    GameView view = new GameView(gameController);
	    			    
	    			    
	    		        gameController.setView(view);
	    		        view.showView(loadBoard);
	 
	                } 
	                  
	                catch(IOException ex) 
	                { 
	                    System.out.println("IOException is caught"); 
	                } 
	                  
	                catch(ClassNotFoundException ex) 
	                { 
	                    System.out.println("ClassNotFoundException is caught"); 
	                } 
	            }
				
			}
		});
        
        JMenuItem reset = new JMenuItem("Reset");
        reset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        reset.addActionListener(this.controller);
        reset.setActionCommand("Reset");
        reset.setEnabled(false);


        file.add(save);
        file.add(load);
        file.add(reset);
        file.add(quit);


        JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Point p = f.getLocation();
				about r = new about(p);
				
			}
		});

        JMenuItem rules = new JMenuItem("Rules");
        rules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				Point p = f.getLocation();
				rules r = new rules(p);
			}
		});

        help.add(about);
        help.add(rules);

        menuBar.add(file);
        menuBar.add(help);
        return menuBar;
    }
	
	public void gui() {
		f = new JFrame("EpsilonGo");
        try{
            f.setIconImage(ImageIO.read(new File("assets/img/center-b.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
		f.setSize(900,600);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
		f.setJMenuBar(this.createMenu());
		f.add(createContentPane(), BorderLayout.CENTER);
		f.add(createTopPanel(), BorderLayout.NORTH);

		f.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {  
		new home();
	}  

}
