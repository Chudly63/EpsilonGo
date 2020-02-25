import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GameController extends Controller{
    private GoBoard board;
    private GameView view;
    private boolean passLastTurn = false;

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

    public void changePlayer(){
        this.board.changePlayer();
        this.view.setActivePlayer(this.board.getCurrentPlayer(), this.board);
    }
 
    public void actionPerformed(ActionEvent e){
        System.out.println(e.getActionCommand());

        if(e.getActionCommand().equals("Update")){
            List<Space> collectedSpaces = board.collectCaptures();
            playPieceSound(2);
            board.addCaptures(board.getOtherPlayer(), collectedSpaces.size());
            board.updateLiberties();
            view.updateBoardPanel(board, collectedSpaces);
            view.updatePlayerPanel(board.getOtherPlayer(), board);
        }
        else if(e.getActionCommand().equals("Reset")){
            board.resetBoard();
            view.updateBoardPanel(board, board.getAllSpaces());
            view.updatePlayerPanel(1, board);
            view.updatePlayerPanel(2, board);
            view.setActivePlayer(board.getCurrentPlayer(), this.board);
            passLastTurn = false;
        }
        else if(e.getActionCommand().equals("Pass")){
            if(passLastTurn){
                System.out.println("Game Ends");
                System.out.println(this.board.getScores());
                ScoreView scoreView = new ScoreView();
                scoreView.showView(this.board);
                return;
            }
            this.changePlayer();
            passLastTurn = true;
        }
        else{
            int x = Integer.parseInt(e.getActionCommand().split(":")[0]);
            int y = Integer.parseInt(e.getActionCommand().split(":")[1]);
            List<Space> spacesToUpdate = new ArrayList<Space>();
            List<Space> captures;

            if(board.getPieces(board.getCurrentPlayer()) == 0){
                System.out.println("Out of pieces");
                playPieceSound(3);
                return;
            }

            Space selected = board.getSpace(x,y);

            selected.setValue(board.getCurrentPlayer());
            captures = board.highlightCaptures(board.getOtherPlayer());

            if(board.getLiberties(x,y).size() == 0 && captures.size() == 0){
                System.out.println("Suicide");
                playPieceSound(3);
                selected.setValue(0);
                return;
            }
            board.addPieces(board.getCurrentPlayer(), -1);
            playPieceSound(1);
            passLastTurn = false;

            ((JButton) e.getSource()).setEnabled(false);
            view.updatePlayerPanel(board.getCurrentPlayer(), board);
            this.changePlayer();
            board.updateLiberties();

            spacesToUpdate.add(selected);
            spacesToUpdate.addAll(captures);
            view.updateBoardPanel(board, spacesToUpdate); 
            
        }
    }   
}