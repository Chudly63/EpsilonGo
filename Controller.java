import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Controller implements ActionListener{
    static GoBoard board = new GoBoard(19);
    static GameView view = new GameView(new Controller());
    private int currentPlayer = 1;

    public static void main(String [] args){
        view.showView(board);
        board.updateLiberties();
    }

    public void playPieceSound(int sound){
        Clip clip;
        AudioInputStream audioInputStream;

        try{
            audioInputStream = AudioSystem.getAudioInputStream(new File("assets/sounds/piece" + sound + ".wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
    public void actionPerformed(ActionEvent e){
        System.out.println(e.getActionCommand());

        if(e.getActionCommand().equals("Update")){
            List<Space> collectedSpaces = board.collectCaptures();
            playPieceSound(2);
            board.updateLiberties();
            view.updateBoardPanel(board, collectedSpaces);
        }
        else{
            int otherPlayer = currentPlayer == 1 ? 2 : 1;
            int x = Integer.parseInt(e.getActionCommand().split(":")[0]);
            int y = Integer.parseInt(e.getActionCommand().split(":")[1]);
            List<Space> spacesToUpdate = new ArrayList<Space>();
            List<Space> captures;
            Space selected = board.getSpace(x,y);

            selected.setValue(currentPlayer);
            playPieceSound(1);
            captures = board.highlightCaptures(otherPlayer);

            if(board.getLiberties(x,y).size() == 0 && captures.size() == 0){
                System.out.println("Suicide");
                selected.setValue(0);
                return;
            }

            ((JButton) e.getSource()).setEnabled(false);

            currentPlayer = otherPlayer;
            board.updateLiberties();

            spacesToUpdate.add(selected);
            spacesToUpdate.addAll(captures);
            board = view.updateBoardPanel(board, spacesToUpdate); 
            
        }
    }   
}