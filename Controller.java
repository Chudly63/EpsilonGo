import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Controller implements ActionListener{
    static GoBoard board = new GoBoard(19);
    static GameView view = new GameView(new Controller());
    private int currentPlayer = 1;

    public static void main(String [] args){
        view.showView(board);
        board.updateLiberties();
        System.out.println(board.toString());
    }

    public void actionPerformed(ActionEvent e){
        System.out.println(e.getActionCommand());
        JButton btn = (JButton) e.getSource();
        btn.setIcon(new ImageIcon("assets/img/center-w.png"));
        int x = Integer.parseInt(e.getActionCommand().split(":")[0]);
        int y = Integer.parseInt(e.getActionCommand().split(":")[1]);
        board.getSpace(x,y).setValue(currentPlayer);
        currentPlayer = currentPlayer == 1 ? 2 : 1;
        view.updateBoardPanel(board);
        board.updateLiberties();
        System.out.println(board);
    }   
}