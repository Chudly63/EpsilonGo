package screens;

import java.awt.BorderLayout;
import java.awt.Color;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import board.*;

public class NewGame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	JFrame f;

	

	
	int BoardSize = 19;
	double Komi = 6.5;
	JTextField bs;
	JTextField k;
	JTextField p1;
	JTextField p2;
	

	
	public NewGame(Point p) {
		
		gui(p);
		
	}
	
	private Container createCenterCP(String l,String l2) {
		Container contentPane = new Container();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));

		
		
		JLabel titleLabel = new JLabel("Board Size:");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
        titleLabel.setToolTipText("The dimensions of the board to play on");
  
		JButton b1 = new JButton();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/upImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		b1.setIcon(icon);
		b1.setBorder(null);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if(BoardSize < 30 ) {
					BoardSize = BoardSize + 2;
					bs.setText(Integer.toString(BoardSize));
				}
			}
		});
		
		bs = new JTextField();
		bs.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
		bs.setBorder(new LineBorder(new Color(220,179,92)));
		bs.setBackground(new Color(220,179,92));
		bs.setText(Integer.toString(BoardSize));
		bs.setEditable(false);
		
		JButton b2 = new JButton();
		img = null;
		try {
			img = ImageIO.read(new File("assets/img/downImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b2.setIcon(icon);
		b2.setBorder(null);
	
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(BoardSize > 5 ) {
					BoardSize = BoardSize - 2;
					bs.setText(Integer.toString(BoardSize));
				}
			}
		});

		
		
		contentPane.add(titleLabel);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b1);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(bs);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b2);
		contentPane.add (Box.createVerticalGlue());
		
		contentPane.setBackground(new Color(220,179,92));
		return contentPane;
    	
    }
	
	private Container createEastCP(String l,String l2) {
		Container contentPane = new Container();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel("Komi:");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
        titleLabel.setToolTipText("Handicap given to Player 2 to compensate for Player 1 going first");

		
		JButton b1 = new JButton();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/upImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		b1.setIcon(icon);
		b1.setBorder(null);
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if(Komi < 30 ) {
					Komi = Komi + 1;
					k.setText(Double.toString(Komi));
				}
			}
		});
		
		k = new JTextField();
		
		k.setText(Double.toString(Komi));
		k.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
		k.setBorder(new LineBorder(new Color(220,179,92)));
		k.setBackground(new Color(220,179,92));
		k.setEditable(false);
		
		JButton b2 = new JButton();
		img = null;
		try {
			img = ImageIO.read(new File("assets/img/downImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b2.setIcon(icon);
		b2.setBorder(null);
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(Komi > 1 ) {
					Komi = Komi - 1;
					k.setText(Double.toString(Komi));
				}
			}
		});

		
		
		contentPane.add(titleLabel);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b1);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(k);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(b2);
		contentPane.add (Box.createVerticalGlue());
		
		contentPane.setBackground(new Color(220,179,92));
		return contentPane;
    	
    }
	
	private Container createWestCP() {
		Container contentPane = new Container();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		
		JLabel titleLabel = new JLabel("Player Names:");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
        titleLabel.setToolTipText("The names of the players to be used during the game");
        
        p1 = new JTextField("Black");
		p1.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
		//p1.setBorder(new LineBorder(new Color(220,179,92)));
		p1.setBackground(new Color(255,255,255));
		
        p2 = new JTextField("White");
		p2.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 15));
		//p1.setBorder(new LineBorder(new Color(220,179,92)));
		p2.setBackground(new Color(255,255,255));
		
		JTextField f1 = new JTextField();
		
		f1.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 2));
		f1.setBorder(new LineBorder(new Color(220,179,92)));
		f1.setBackground(new Color(220,179,92));
		f1.setEditable(false);
		
		JTextField f2 = new JTextField();
		
		f2.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 18));
		f2.setBorder(new LineBorder(new Color(220,179,92)));
		f2.setBackground(new Color(220,179,92));
		f2.setEditable(false);
        
        //JRadioButton j1 = new JRadioButton("AI Game");
        //j1.setOpaque(false);
        //j1.setEnabled(false);
        //JRadioButton j2 = new JRadioButton("2P Game");
        //j2.setOpaque(false);
        //j2.setSelected(true);
        //JRadioButton j3 = new JRadioButton("Tutorial");
        //j3.setOpaque(false);
        //j3.setEnabled(false);
        //ButtonGroup G = new ButtonGroup();
 

		//G.add(j1);
		//G.add(j2);
		//G.add(j3);
		
		contentPane.add(titleLabel);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(f1);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(p1);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(f2);
		contentPane.add (Box.createVerticalGlue());
		contentPane.add(p2);
		contentPane.add (Box.createVerticalGlue());
		//contentPane.add(j3);
		contentPane.add (Box.createVerticalGlue());
		
		contentPane.setBackground(new Color(220,179,92));
		return contentPane;
    	
    }
	
    private JPanel createTopPanel(){
        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("New Game");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 40));
        topPanel.add(titleLabel);
        topPanel.setBackground(new Color(220,179,92));
        return topPanel;
    }
    
    
    private Container createSouthCP() {
		Container contentPane = new Container();
	
		JButton b1 = new JButton();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/cancelImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		b1.setIcon(icon);
		b1.setBorder(null);
	
		contentPane.setLayout(new GridLayout(1,6));
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				f.dispose();
				home h = new home();
			}
		});
		
		JButton b2 = new JButton();
		img = null;
		try {
			img = ImageIO.read(new File("assets/img/startGameImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		b2.setIcon(icon);
		b2.setBorder(null);
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				f.dispose();
			    GoBoard board = new GoBoard(BoardSize, Komi, p1.getText(), p2.getText());
			    GameController gameController = new GameController(board);
			    GameView view = new GameView(gameController);
			    
		        gameController.setView(view);
		        view.showView(board);
			    

			}
		});
		

		contentPane.add(Box.createRigidArea(new Dimension(5,0)));
		contentPane.add(Box.createRigidArea(new Dimension(5,0)));
		contentPane.add(Box.createRigidArea(new Dimension(5,0)));
		contentPane.add(b2);
		
		contentPane.add(Box.createRigidArea(new Dimension(5,0)));

		contentPane.add(b1);
	
		
		return contentPane;
    	
    }
    
    
	
	public void gui(Point p) {
		f = new JFrame("EpsilonGo");
        try{
            f.setIconImage(ImageIO.read(new File("assets/img/center-b.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
		f.setVisible(true);
		f.setSize(900,600);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		/*try {
			Point pt = new Point();
			pt = p;
			f.setLocation(pt);
		}
		catch(Exception e) {
			System.out.print("welp");
		}
		
		*/
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		JPanel pc = new JPanel();
		JPanel pe = new JPanel();
		JPanel pw =new JPanel();
		JPanel panel = new JPanel();
		
		JPanel pn =new JPanel();
		JPanel ps =new JPanel();
		
		
		pc.add(createCenterCP("+2","-2"));
		pc.setBackground(new Color(220,179,92));
		pe.add(createEastCP("+1","-1"));
		pe.setBackground(new Color(220,179,92));
		pw.add(createWestCP());
		pw.setBackground(new Color(220,179,92));
		
		pn.add(createTopPanel());
		pn.setBackground(new Color(220,179,92));
		ps.add(createSouthCP());
		ps.setBackground(new Color(220,179,92));
		
		ps.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		pn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panel.add(pw,BorderLayout.WEST);
		panel.add(pc,BorderLayout.CENTER);
		panel.add(pe,BorderLayout.EAST);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(new Color(220,179,92));
		
		f.add(panel);
		f.add(pn, BorderLayout.NORTH);
		f.add(ps, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
		
		
	}
}
