public class Main{
    static GoBoard board = new GoBoard(9, 6.5, "Alex", "Joe");
    static private GameController gameController = new GameController(board);
    static GameView view = new GameView(gameController);

    public static void main(String [] args){
        gameController.setView(view);
        view.showView(board);
        board.updateLiberties();
    }

}