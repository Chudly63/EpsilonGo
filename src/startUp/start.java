package startUp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import screens.*;

public class start extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame f;


	public start() {
		gui();
	}
	
	
    private JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Welcome to Epsilon Go");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 40));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(220,179,92));
        return titlePanel;
    }
    
    
    private JPanel createImgPanel(){
        JPanel imgPanel = new JPanel();
        JLabel imgLabel = new JLabel();
        
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/startImg.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
        
        imgLabel.setIcon(icon);
        imgPanel.add(imgLabel);
        imgPanel.setBackground(new Color(220,179,92));
        imgPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return imgPanel;
    }
	
	public void gui() {
		f = new JFrame("EpsilonGo");
        try{
            f.setIconImage(ImageIO.read(new File("assets/img/center-b.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
		f.setSize(600,400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
        
        
		

		f.add(createTitlePanel(),BorderLayout.CENTER);
		f.add(createImgPanel(),BorderLayout.SOUTH);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		f.dispose();
		home h = new home();
		
	}
	
	public static void main(String[] args) {  
		new start();
		
	}  

}
