import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView {

    private HashMap<String, ArrayList<ImageIcon>> ICONS = new HashMap<String, ArrayList<ImageIcon>>();
    private String[] fileNames = {"north-west", "north", "north-east", "west", "center", "east", "south-west", "south", "south-east", "dot"};
    private Controller controller;
    private ArrayList<ArrayList<JButton>> boardButtons;

    JFrame jFrame = new JFrame("EpsilonGo");
    
    JPanel jPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel glassPanel = new JPanel();

    public GameView(Controller controller){
        this.controller = controller;
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        for(String fileName : fileNames){
            ImageIcon blank = new ImageIcon("assets/img/" + fileName + ".png");
            ImageIcon black = new ImageIcon("assets/img/" + fileName + "-b.png");
            ImageIcon white = new ImageIcon("assets/img/" + fileName + "-w.png");
            ImageIcon red = new ImageIcon("assets/img/" + fileName + "-r.png");
            ArrayList<ImageIcon> currentIcons = new ArrayList<ImageIcon>();
            currentIcons.add(blank);
            currentIcons.add(black);
            currentIcons.add(white);
            currentIcons.add(red);
            ICONS.put(fileName, currentIcons);
        }
    }

    private ImageIcon getBoardButtonIcon(int x, int y, int value, int length){
        ImageIcon icon = new ImageIcon();
        if(y == 0){
            if(x == 0){
                icon = ICONS.get("north-west").get(value);
            }
            else if(x == length - 1){
                icon = ICONS.get("north-east").get(value);
            }
            else{
                icon = ICONS.get("north").get(value);
            }
        }
        else if (y == length - 1){
            if(x == 0){
                icon = ICONS.get("south-west").get(value);
            }
            else if(x == length - 1){
                icon = ICONS.get("south-east").get(value);
            }
            else{
                icon = ICONS.get("south").get(value);
            }
        }
        else{
            if(x == 0){
                icon = ICONS.get("west").get(value);
            }
            else if(x == length - 1){
                icon = ICONS.get("east").get(value);
            }
            else{
                int gap = length / 7 + 1;
                if((x == length / 2 || x == gap || x == length - (gap+1)) && 
                    (y == length / 2 || y == gap || y == length - (gap+1))){
                    icon = ICONS.get("dot").get(value);
                }
                else
                    icon = ICONS.get("center").get(value);
            }
        }
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance( 800 / length, 800 / length,  java.awt.Image.SCALE_SMOOTH ) ;  
        icon = new ImageIcon( newimg );
        return icon;
    }

    public GoBoard updateBoardPanel(GoBoard currentBoard, List<Space> updatedSpaces){
        boolean highlightFound = false;
        for(Space space : updatedSpaces){
            ImageIcon icon = this.getBoardButtonIcon(space.getX(), space.getY(), space.getValue(), currentBoard.getBoardLength());
            JButton spaceButton = this.boardButtons.get(space.getY()).get(space.getX());
            spaceButton.setIcon(icon);
            spaceButton.setDisabledIcon(icon);
            switch(space.getValue()){
                case 3:
                    highlightFound = true;
                case 1:
                case 2:
                    spaceButton.setEnabled(false);
                    break;
                case 0:
                    spaceButton.setEnabled(true);
            }
        }
        this.glassPanel.setVisible(highlightFound);
        if(highlightFound){
            Timer timer = new Timer(1000, controller);
            timer.setActionCommand("Update");
            timer.setRepeats(false);
            timer.start();
        }
        return currentBoard;
    }

    private void createBoardPanel(GoBoard currentBoard){
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        this.boardPanel = new JPanel();
        this.boardPanel.setLayout(new GridLayout(currentBoard.getBoardLength(),currentBoard.getBoardLength()));

        for(int i = 0; i < currentBoard.getBoardLength(); i++){
            ArrayList<JButton> boardRow = new ArrayList<JButton>();
            for(int j = 0; j < currentBoard.getBoardLength(); j++){
                JButton select = new JButton();
                select.setIcon(this.getBoardButtonIcon(j, i, currentBoard.getSpace(j,i).getValue(), currentBoard.getBoardLength()));
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

        this.glassPanel.setOpaque(false);
        this.glassPanel.setVisible(false);
        this.glassPanel.addMouseListener(new MouseAdapter() {});
        this.glassPanel.setFocusable(true);
        this.glassPanel.setPreferredSize(new Dimension(800,800));
        this.glassPanel.setSize(this.glassPanel.getPreferredSize());

        JLayeredPane top = new JLayeredPane();
        top.setPreferredSize(new Dimension(800,800));
        this.boardPanel.setSize(this.boardPanel.getPreferredSize());
        top.add(this.boardPanel, JLayeredPane.DEFAULT_LAYER);
        top.add(this.glassPanel, JLayeredPane.PALETTE_LAYER);

        jPanel.add(top, BorderLayout.CENTER);


        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}