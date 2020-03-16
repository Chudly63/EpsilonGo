package screens;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import board.GoBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ScoreView{
	private JFrame jFrame;
	private GameView gameView;
	private GoBoard board;
	
	public ScoreView(GoBoard board, GameView gameView) {
		this.board = board;
		this.gameView = gameView;
	}

    private JPanel createTopPanel(){
        JPanel top = new JPanel();
        JLabel label = new JLabel("Game Over");

        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 24));
        top.add(label);
        top.setBackground(new Color(220,179,92));
        return top;
    }
    
    private JPanel createBreakdownPanel(GoBoard currentBoard) {
    	JPanel breakdownPanel = new JPanel();
    	breakdownPanel.setLayout(new BoxLayout(breakdownPanel, BoxLayout.X_AXIS));

    	breakdownPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
    	
    	JLabel captures = new JLabel("<html><div style='text-align: center;'><p style=\"text-decoration: underline;\">Captures</p>" + currentBoard.getPlayerName(1) + ": " + currentBoard.getCaptures(1) + "<br/>" + 
    								currentBoard.getPlayerName(2) + ": " + currentBoard.getCaptures(2) + "</div></html>");

        captures.setToolTipText("The number of enemy pieces that each player removed from the board");
    	JLabel territory = new JLabel("<html><div style='text-align: center;'><p style=\"text-decoration: underline;\">Territory</p>" + currentBoard.getPlayerName(1) + ": " + currentBoard.countSpacesWithValue(4) + "<br/>" +
    								currentBoard.getPlayerName(2) + ": " + currentBoard.countSpacesWithValue(5) + "</div></html>");

        territory.setToolTipText("The number of empty spaces that each player has completely surrounded. (Marked with circles on the board)");
    	JLabel komi = new JLabel("<html><div style='text-align: center;'><p style=\"text-decoration: underline;\">Komi</p>" + currentBoard.getPlayerName(1) + ": 0<br/>" + 
    							currentBoard.getPlayerName(2) + ": " + currentBoard.getKomi() + "</div></html>");

        komi.setToolTipText("The number of points awarded to " + currentBoard.getPlayerName(2) + " to compensate for going second");
    	breakdownPanel.add(Box.createHorizontalGlue());
    	breakdownPanel.add(captures);
    	breakdownPanel.add(territory);
    	breakdownPanel.add(komi);
    	breakdownPanel.setBackground(new Color(220,179,92));
    	
    	return breakdownPanel;
    }

    private JPanel createScorePanel(GoBoard currentBoard){
        List<Double> scores = currentBoard.getScores();
        
        JPanel scoresPanel = new JPanel();
        scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));

        JLabel winner = new JLabel();
        if(scores.get(0) > scores.get(1)){
            winner.setText(currentBoard.getPlayerName(1) + " Wins!");
        }
        else{
            winner.setText(currentBoard.getPlayerName(2) + " Wins!");
        }

        winner.setFont(new Font(winner.getFont().getName(), Font.BOLD, 20));

        JLabel score = new JLabel("Score: " + scores.get(0) + " to " + scores.get(1));
        score.setToolTipText("A player's score is the sum of their captures, territory, and komi");

        
        winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoresPanel.add(winner);
        score.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        score.setFont(new Font(score.getFont().getName(), Font.BOLD, 16));
        scoresPanel.add(score);
        
        scoresPanel.add(this.createBreakdownPanel(currentBoard));
        
        scoresPanel.setBackground(new Color(220,179,92));
        return scoresPanel;
    }

    private JPanel createBottomPanel(){
        JPanel bottom = new JPanel();
        JButton newGame = new JButton();
        BufferedImage img = null;
		try {
			img = ImageIO.read(new File("assets/img/scorePlayAgain.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		ImageIcon icon=new ImageIcon(img);
		newGame.setIcon(icon);
		newGame.setBorder(null);
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.resetBoard();
				gameView.updateBoardPanel(board, board.getAllSpaces());
				jFrame.dispose();
				gameView.setEnabled(true);
				gameView.toFront();
				gameView.setActivePlayer(board.getCurrentPlayer(), board);
				gameView.updatePlayerPanel(1, board);
				gameView.updatePlayerPanel(2, board);
			}
		});
        JButton exit = new JButton();
        img = null;
		try {
			img = ImageIO.read(new File("assets/img/scoreExit.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		icon=new ImageIcon(img);
		exit.setIcon(icon);
		exit.setBorder(null);
        exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameView.dispose();
				jFrame.dispose();
				home home = new home();
			}
		});
        
        
        bottom.add(newGame);
        bottom.add(exit);
        bottom.setBackground(new Color(220,179,92));
        return bottom;
    }

    public void showView(){
        jFrame = new JFrame("EpsilonGo - Scores");
        jFrame.setSize(200,200);
        jFrame.setLocationRelativeTo(null);
        jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                gameView.dispose();
            }
        });
        try{
            jFrame.setIconImage(ImageIO.read(new File("assets/img/center-b.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(220,179,92));
        jPanel.setLayout(new BorderLayout());
        jPanel.add(this.createTopPanel(), BorderLayout.NORTH);
        jPanel.add(this.createBottomPanel(), BorderLayout.SOUTH);
        jPanel.add(this.createScorePanel(this.board), BorderLayout.CENTER);
        
        jFrame.setResizable(false);
        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}