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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class about {
	JFrame f;
	JPanel panel;
	JPanel panelc;


	public about(Point p) {
		gui(p);
	}
	
	
    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(220,179,92));
        return titlePanel;
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
			}
		});
		

		contentPane.add(Box.createRigidArea(new Dimension(50,0)));
		contentPane.add(Box.createRigidArea(new Dimension(50,0)));
		contentPane.add(Box.createRigidArea(new Dimension(50,0)));
		contentPane.add(Box.createRigidArea(new Dimension(80,0)));
		contentPane.add(b1);
	
		
		return contentPane;
    	
    }
    
    private JPanel createCP() {
    	
		panel = new JPanel();
		panel.setBackground(new Color(220,179,92));
		panelc = new JPanel();
		panelc.setBackground(new Color(220,179,92));

				

		
		JLabel l2 = new JLabel();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/aboutImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		l2.setIcon(icon);
		l2.setHorizontalAlignment(JLabel.CENTER);
		l2.setVerticalAlignment(JLabel.CENTER);
		
		BorderLayout layout = new BorderLayout();
		panel.setLayout(layout);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		

	    panelc.add(Box.createRigidArea(new Dimension(0,250)));
		//panelc.add(l3,BorderLayout.WEST);
	    //panelc.add(Box.createRigidArea(new Dimension(20,0)));
		panelc.add(l2,BorderLayout.EAST);
		//panel.add(createTopPanel(),BorderLayout.NORTH);
		panel.add(panelc, BorderLayout.CENTER);
		return panel;
    	
    }
	
   
    
	public void gui(Point p) {
		f = new JFrame("About Epsilon Go");
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
		f.setResizable(false);
		
		JLabel j = new JLabel("About info coming soon!");
		
		
		JPanel panel = new JPanel();
		
		
		//f.add(createTitlePanel(),BorderLayout.NORTH);
		f.add(createCP(),BorderLayout.CENTER);
		panel.add(createSouthCP());
		
		panel.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
		panel.setBackground(new Color(220,179,92));
		f.add(panel,BorderLayout.SOUTH);

		f.setVisible(true);
		
		
	}
}
