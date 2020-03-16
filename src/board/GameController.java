package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import screens.GameView;
import screens.ScoreView;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GameController implements ActionListener{
    private GoBoard board;
    private GameView view;

    public GameController(GoBoard model){
        board = model;
    }

    public void setView(GameView view){
        this.view = view;
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

        if(e.getActionCommand().equals("Update")){
            List<Space> collectedSpaces = board.collectCaptures();
            playPieceSound(2);
            board.addCaptures(board.getOtherPlayer(), collectedSpaces.size());
            board.updateLiberties();
            view.updateBoardPanel(board, collectedSpaces);
            view.updatePlayerPanel(board.getOtherPlayer(), board);
            view.setSaved(false);
        }
        else if(e.getActionCommand().equals("Reset")){
        	if(JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the game to the beginning?", "Reset?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                board.resetBoard();
                view.updateBoardPanel(board, board.getAllSpaces());
                view.updatePlayerPanel(1, board);
                view.updatePlayerPanel(2, board);
                view.setActivePlayer(board.getCurrentPlayer(), this.board);
                board.setPassLastTurn(false);
                view.setSaved(true);
        	}
        }
        else if(e.getActionCommand().equals("Save")){
        	JFileChooser j = new JFileChooser("./savedGames/"); 
			int r = j.showSaveDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) { 
                String s = j.getSelectedFile().getAbsolutePath();
                System.out.print(s);
            	
            	s = s +".ser";
            	
            	try
                {    
                    FileOutputStream file = new FileOutputStream(s); 
                    ObjectOutputStream out = new ObjectOutputStream(file); 
                      
                    out.writeObject(this.board); 
                      
                    out.close(); 
                    file.close(); 
                      
                    view.setSaved(true);
          
                } 
                  
                catch(IOException ex) 
                { 
                    ex.printStackTrace();
                } 
            	
			}
        }
        else if(e.getActionCommand().equals("Load")){
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
                      
                    
                    view.jFrame.dispose();
                    
    			    GoBoard loadBoard = g;
    			    GameController gameController = new GameController(loadBoard);
    			    GameView view = new GameView(gameController);
    			    
    			    
    		        gameController.setView(view);
    		        view.showView(loadBoard);
    		        view.setSaved(false);
 
                } 
                  
                catch(IOException ex) 
                { 
                    ex.printStackTrace();
                } 
                  
                catch(ClassNotFoundException ex) 
                { 
                    ex.printStackTrace();
                } 
            } 
			
        }
        else if(e.getActionCommand().equals("Pass")){
            if(board.isPassLastTurn()){
                this.board.updateScores();
                this.view.updateBoardPanel(this.board, this.board.getAllSpaces());
                this.view.setEnabled(false);
                ScoreView scoreView = new ScoreView(this.board, this.view);
                scoreView.showView();
                return;
            }
            this.changePlayer();
            board.setPassLastTurn(true);
            view.setSaved(false);
        }
        else{
            int x = Integer.parseInt(e.getActionCommand().split(":")[0]);
            int y = Integer.parseInt(e.getActionCommand().split(":")[1]);
            List<Space> spacesToUpdate = new ArrayList<Space>();
            List<Space> captures;

            if(board.getPieces(board.getCurrentPlayer()) == 0){
                playPieceSound(3);
                this.view.displayMessage("Illegal Move: You are out of pieces!");
                return;
            }

            Space selected = board.getSpace(x,y);

            selected.setValue(board.getCurrentPlayer());
            captures = board.highlightCaptures(board.getOtherPlayer());

            if(board.getLiberties(x,y).size() == 0 && captures.size() == 0){
                playPieceSound(3);
                selected.setValue(0);
                this.view.displayMessage("Illegal Move: Placing your piece there would result in it being captured!");
                return;
            }
            board.addPieces(board.getCurrentPlayer(), -1);
            playPieceSound(1);
            board.setPassLastTurn(false);

            ((JButton) e.getSource()).setEnabled(false);
            view.updatePlayerPanel(board.getCurrentPlayer(), board);
            this.changePlayer();
            board.updateLiberties();

            spacesToUpdate.add(selected);
            spacesToUpdate.addAll(captures);
            view.updateBoardPanel(board, spacesToUpdate); 
            view.setSaved(false);
        }
    }   
}