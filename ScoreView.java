import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

public class ScoreView extends View {

    private JPanel createTopPanel(){
        JPanel top = new JPanel();
        JLabel label = new JLabel("Game Over");
        top.add(label);
        return top;
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

        JLabel score = new JLabel("Score: " + scores.get(0) + " to " + scores.get(1));

        JLabel playerOne = new JLabel(currentBoard.getPlayerName(1) + " Captured: " + currentBoard.getCaptures(1));
        JLabel playerTwo = new JLabel(currentBoard.getPlayerName(2) + " Captured: " + currentBoard.getCaptures(2));
        
        winner.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoresPanel.add(winner);
        score.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoresPanel.add(score);
        playerOne.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoresPanel.add(playerOne);
        playerTwo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoresPanel.add(playerTwo);
        

        return scoresPanel;
    }

    private JPanel createBottomPanel(){
        JPanel bottom = new JPanel();
        JButton newGame = new JButton("Play Again");
        JButton exit = new JButton("Exit");
        bottom.add(newGame);
        bottom.add(exit);
        return bottom;
    }

    public void showView(GoBoard currentBoard){
        JFrame jFrame = new JFrame("EpsilonGo - Scores");
        try{
            jFrame.setIconImage(ImageIO.read(new File("assets/img/center-b.png")));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(this.createTopPanel(), BorderLayout.NORTH);
        jPanel.add(this.createBottomPanel(), BorderLayout.SOUTH);
        jPanel.add(this.createScorePanel(currentBoard), BorderLayout.CENTER);

        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}