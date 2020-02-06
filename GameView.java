import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
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

public class GameView extends View{

    private HashMap<String, ArrayList<ImageIcon>> ICONS = new HashMap<String, ArrayList<ImageIcon>>();
    private String[] fileNames = {"north-west", "north", "north-east", "west", "center", "east", "south-west", "south", "south-east", "dot"};
    private GameController controller;
    private ArrayList<ArrayList<JButton>> boardButtons;

    JPanel glassPanel = new JPanel();

    public GameView(GameController controller){
        this.controller = controller;
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        for(String fileName : fileNames){
            ArrayList<ImageIcon> currentIcons = new ArrayList<ImageIcon>();
            currentIcons.add(new ImageIcon("assets/img/" + fileName + ".png"));
            currentIcons.add(new ImageIcon("assets/img/" + fileName + "-b.png"));
            currentIcons.add(new ImageIcon("assets/img/" + fileName + "-w.png"));
            currentIcons.add(new ImageIcon("assets/img/" + fileName + "-r.png"));
            ICONS.put(fileName, currentIcons);
        }
    }

    private ImageIcon getBoardButtonIcon(int x, int y, int value, int length){
        ImageIcon icon = new ImageIcon();
        //Top Row
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
        //Bottom Row
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
            //Left Wall
            if(x == 0){
                icon = ICONS.get("west").get(value);
            }
            //Right Wall
            else if(x == length - 1){
                icon = ICONS.get("east").get(value);
            }
            else{
                //Dot locations
                int gap = length / 7 + 1;
                if((x == length / 2 || x == gap || x == length - (gap+1)) && 
                    (y == length / 2 || y == gap || y == length - (gap+1))){
                    icon = ICONS.get("dot").get(value);
                }
                //Intersections
                else
                    icon = ICONS.get("center").get(value);
            }
        }
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance( 800 / length, 800 / length,  java.awt.Image.SCALE_SMOOTH ) ;  
        icon = new ImageIcon( newimg );
        return icon;
    }

    public void updateBoardPanel(GoBoard currentBoard, List<Space> updatedSpaces){
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
    }

    private JPanel createBoardPanel(GoBoard currentBoard){
        this.boardButtons = new ArrayList<ArrayList<JButton>>();
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(currentBoard.getBoardLength(),currentBoard.getBoardLength()));

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
                boardPanel.add(select);
                boardRow.add(select);
            }
            this.boardButtons.add(boardRow);
        }
        boardPanel.setPreferredSize(new Dimension(800,800));
        boardPanel.setSize(boardPanel.getPreferredSize());
        return boardPanel;
    }

    private JMenuBar createMenu(){
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
        return menuBar;
    }

    private JToolBar createToolBar(){
        JToolBar toolBar = new JToolBar();
        JButton quitBtn = new JButton("Q");
        quitBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        toolBar.add(quitBtn);
        JButton resetBtn = new JButton("R");
        resetBtn.addActionListener(this.controller);
        resetBtn.setActionCommand("Reset");
        toolBar.add(resetBtn);
        return toolBar;
    }

    private JPanel createPlayerPanel(){
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        JButton pass = new JButton("Pass");

        playerPanel.add(pass);
        return playerPanel;
    }

    private void createGlassPanel(){
        this.glassPanel.setOpaque(false);
        this.glassPanel.setVisible(false);
        this.glassPanel.addMouseListener(new MouseAdapter() {});
        this.glassPanel.setFocusable(true);
        this.glassPanel.setPreferredSize(new Dimension(800,800));
        this.glassPanel.setSize(this.glassPanel.getPreferredSize());
    }

    private JLayeredPane createLayeredPane(JPanel boardPanel, JPanel glassPanel){
        JLayeredPane layeredPanel = new JLayeredPane();
        layeredPanel.setPreferredSize(new Dimension(800,800));
        layeredPanel.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPanel.add(glassPanel, JLayeredPane.PALETTE_LAYER);
        return layeredPanel;
    }

    public void showView(GoBoard currentBoard){
        JFrame jFrame = new JFrame("EpsilonGo");
        jFrame.setJMenuBar(this.createMenu());
        
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(this.createToolBar(), BorderLayout.NORTH);
        jPanel.add(createPlayerPanel(), BorderLayout.LINE_START);
        jPanel.add(createPlayerPanel(), BorderLayout.LINE_END);

        createGlassPanel();
        jPanel.add(createLayeredPane(createBoardPanel(currentBoard), this.glassPanel), BorderLayout.CENTER);

        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}