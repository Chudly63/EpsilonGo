import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class GameView {

    private HashMap<String, ArrayList<ImageIcon>> ICONS = new HashMap<String, ArrayList<ImageIcon>>();
    private String[] fileNames = {"north-west", "north", "north-east", "west", "center", "east", "south-west", "south", "south-east"};
    private Controller controller;
    private ArrayList<ArrayList<JButton>> boardButtons;

    JFrame jFrame = new JFrame("EpsilonGo");
    
    JPanel jPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    public GameView(Controller controller){
        this.controller = controller;
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        for(String fileName : fileNames){
            ImageIcon blank = new ImageIcon("assets/img/" + fileName + ".png");
            ImageIcon black = new ImageIcon("assets/img/" + fileName + "-b.png");
            ImageIcon white = new ImageIcon("assets/img/" + fileName + "-w.png");
            ArrayList<ImageIcon> currentIcons = new ArrayList<ImageIcon>();
            currentIcons.add(blank);
            currentIcons.add(white);
            currentIcons.add(black);
            ICONS.put(fileName, currentIcons);
        }
    }

    public void updateBoardPanel(GoBoard currentBoard){
        for(int i = 0; i < currentBoard.getBoardLength(); i++){
            for(int j = 0; j < currentBoard.getBoardLength(); j++){
                ImageIcon icon = new ImageIcon();
                if(i == 0){
                    if(j == 0){
                        icon = ICONS.get("north-west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("north-east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("north").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                else if (i == currentBoard.getBoardLength() - 1){
                    if(j == 0){
                        icon = ICONS.get("south-west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("south-east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("south").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                else{
                    if(j == 0){
                        icon = ICONS.get("west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("center").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance( 800 / currentBoard.getBoardLength(), 800 / currentBoard.getBoardLength(),  java.awt.Image.SCALE_SMOOTH ) ;  
                icon = new ImageIcon( newimg );
                this.boardButtons.get(i).get(j).setIcon(icon);
            }
        }
    }

    private void createBoardPanel(GoBoard currentBoard){
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        this.boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(currentBoard.getBoardLength(),currentBoard.getBoardLength()));

        for(int i = 0; i < currentBoard.getBoardLength(); i++){
            ArrayList<JButton> boardRow = new ArrayList<JButton>();
            for(int j = 0; j < currentBoard.getBoardLength(); j++){
                JButton select = new JButton();
                ImageIcon icon = new ImageIcon();
                if(i == 0){
                    if(j == 0){
                        icon = ICONS.get("north-west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("north-east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("north").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                else if (i == currentBoard.getBoardLength() - 1){
                    if(j == 0){
                        icon = ICONS.get("south-west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("south-east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("south").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                else{
                    if(j == 0){
                        icon = ICONS.get("west").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else if(j == currentBoard.getBoardLength() - 1){
                        icon = ICONS.get("east").get(currentBoard.getSpace(j,i).getValue());
                    }
                    else{
                        icon = ICONS.get("center").get(currentBoard.getSpace(j,i).getValue());
                    }
                }
                Image img = icon.getImage();
                Image newimg = img.getScaledInstance( 800 / currentBoard.getBoardLength(), 800 / currentBoard.getBoardLength(),  java.awt.Image.SCALE_SMOOTH ) ;  
                icon = new ImageIcon( newimg );
                select.setIcon(icon);
                select.setOpaque(false);
                select.setContentAreaFilled(false);
                select.setBorderPainted(false);
                select.addActionListener(this.controller);
                select.setActionCommand(j + ":" + i);
                this.boardPanel.add(select);
                boardRow.add(select);
            }
            this.boardButtons.add(boardRow);
        }
        this.boardPanel.setPreferredSize(new Dimension(800,800));
        jPanel.add(this.boardPanel, BorderLayout.CENTER);
    }

    public void showView(GoBoard currentBoard){
        jPanel.setLayout(new BorderLayout());


        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        quit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        file.add(quit);
        menuBar.add(file);
        jFrame.setJMenuBar(menuBar);


        JToolBar toolBar = new JToolBar();
        JButton quitBtn = new JButton("Q");
        quitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        toolBar.add(quitBtn);
        jPanel.add(toolBar, BorderLayout.NORTH);

        


        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        JButton leftButton = new JButton("Pass");

        left.add(leftButton);
        jPanel.add(left, BorderLayout.LINE_START);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JButton rightButton = new JButton("Pass");

        right.add(rightButton);
        jPanel.add(right, BorderLayout.LINE_END);

        createBoardPanel(currentBoard);

        jPanel.add(this.boardPanel, BorderLayout.CENTER);

        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}