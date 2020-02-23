import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameView extends View{

    private HashMap<String, ArrayList<ImageIcon>> ICONS = new HashMap<String, ArrayList<ImageIcon>>();
    private String[] fileNames = {"north-west", "north", "north-east", "west", "center", "east", "south-west", "south", "south-east", "dot"};
    private GameController controller;
    private ArrayList<ArrayList<JButton>> boardButtons;
    private ArrayList<HashMap<String, JComponent>> playerComponents = new ArrayList<HashMap<String, JComponent>>(); 

    JPanel glassPanel = new JPanel();
    JPanel blackPanel;
    JPanel whitePanel;

    JLabel currentPlayerLabel;

    
    Icon black = new ImageIcon("assets/img/black.png");
    Icon white = new ImageIcon("assets/img/white.png");

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

    private JPanel createBottomPanel(){
        JPanel bottomPanel = new JPanel();
        this.currentPlayerLabel = new JLabel("Current Turn: Black");
        bottomPanel.add(this.currentPlayerLabel);
        return bottomPanel;
    }

    private JPanel createPlayerPanel(int player, GoBoard currentBoard){
        HashMap<String, JComponent> components = new HashMap<String, JComponent>();
        HashMap<TextAttribute, Integer> underlineAttributes = new HashMap<TextAttribute, Integer>();
        underlineAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        JPanel playerPanel = new JPanel();
        playerPanel.setPreferredSize(new Dimension(200, 800));
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        JLabel playerLabel = player == 1 ? new JLabel("Black") : new JLabel("White");
        playerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playerLabel.setFont(new Font(playerLabel.getFont().getName(), Font.BOLD, 20));
        playerPanel.add(playerLabel);
        playerPanel.add(Box.createRigidArea(new Dimension(20,20)));

        JLabel pieces = new JLabel("Stones Left");
        JLabel captures = new JLabel("Stones Captured");

        JLabel piecesCounter = new JLabel("x " + currentBoard.getPieces(player));
        JLabel capturesCounter = new JLabel("x " + currentBoard.getCaptures(player));
        if(player == 1){
            piecesCounter.setIcon(this.black);
            capturesCounter.setIcon(this.white);
        }
        else{
            piecesCounter.setIcon(this.white);
            capturesCounter.setIcon(this.black);
        }
        pieces.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        pieces.setFont(new Font(pieces.getFont().getName(), Font.BOLD, 16).deriveFont(underlineAttributes));
        playerPanel.add(pieces);
        piecesCounter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playerPanel.add(piecesCounter);
        playerPanel.add(Box.createRigidArea(new Dimension(20,20)));
        captures.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        captures.setFont(new Font(pieces.getFont().getName(), Font.BOLD, 16).deriveFont(underlineAttributes));
        playerPanel.add(captures);
        capturesCounter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        playerPanel.add(capturesCounter);
        playerPanel.add(Box.createRigidArea(new Dimension(20,20)));

        JButton pass = new JButton("Pass");
        pass.setActionCommand("Pass");
        pass.addActionListener(this.controller);
        pass.setAlignmentX(JButton.CENTER_ALIGNMENT);
        playerPanel.add(pass);

        components.put("Pieces", piecesCounter);
        components.put("Captures", capturesCounter);
        components.put("Pass", pass);
        this.playerComponents.add(components);

        return playerPanel;
    }

    public void updatePlayerPanel(int player, GoBoard currentBoard){
        System.out.println(player + "");
        HashMap<String, JComponent> components = this.playerComponents.get(player - 1);
        JLabel pieces = (JLabel)components.get("Pieces");
        JLabel captures = (JLabel)components.get("Captures");
        pieces.setText("x " + currentBoard.getPieces(player));
        captures.setText("x " + currentBoard.getCaptures(player));

    }

    public void setActivePlayer(int activePlayer){
        //Enable active players pass button
        HashMap<String, JComponent> components = this.playerComponents.get(activePlayer - 1);
        JButton pass = (JButton)components.get("Pass");
        pass.setEnabled(true);

        //Disable inactive player's pass button
        int inavtivePlayer = activePlayer == 1 ? 2 : 1;
        components = this.playerComponents.get(inavtivePlayer - 1);
        pass = (JButton)components.get("Pass");
        pass.setEnabled(false);

        //Display active player
        String currentPlayerText = activePlayer == 1 ? "Current Player: Black" : "Current Player: White";
        this.currentPlayerLabel.setText(currentPlayerText);
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
        this.blackPanel = createPlayerPanel(1, currentBoard);
        this.whitePanel = createPlayerPanel(2, currentBoard);
        jPanel.add(this.blackPanel, BorderLayout.LINE_START);
        jPanel.add(this.whitePanel, BorderLayout.LINE_END);
        jPanel.add(this.createBottomPanel(), BorderLayout.SOUTH);

        createGlassPanel();
        jPanel.add(createLayeredPane(createBoardPanel(currentBoard), this.glassPanel), BorderLayout.CENTER);

        this.setActivePlayer(currentBoard.getCurrentPlayer());
        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setVisible(true);
    }

}