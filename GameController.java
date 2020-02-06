import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GameController extends Controller implements ActionListener{
    private GoBoard board;
    private GameView view;
    private int currentPlayer = 1;

    public GameController(Model model){
        board = (GoBoard)model;
    }

    public void setView(View view){
        this.view = (GameView)view;
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
        else if(e.getActionCommand().equals("Reset")){
            board.resetBoard();
            currentPlayer = 1;
            view.updateBoardPanel(board, board.getAllSpaces());
        }
        else{
            int otherPlayer = currentPlayer == 1 ? 2 : 1;
            int x = Integer.parseInt(e.getActionCommand().split(":")[0]);
            int y = Integer.parseInt(e.getActionCommand().split(":")[1]);
            List<Space> spacesToUpdate = new ArrayList<Space>();
            List<Space> captures;
            Space selected = board.getSpace(x,y);

            selected.setValue(currentPlayer);
            captures = board.highlightCaptures(otherPlayer);

            if(board.getLiberties(x,y).size() == 0 && captures.size() == 0){
                System.out.println("Suicide");
                playPieceSound(3);
                selected.setValue(0);
                return;
            }
            playPieceSound(1);

            ((JButton) e.getSource()).setEnabled(false);

            currentPlayer = otherPlayer;
            board.updateLiberties();

            spacesToUpdate.add(selected);
            spacesToUpdate.addAll(captures);
            view.updateBoardPanel(board, spacesToUpdate); 
            
        }
    }   
}