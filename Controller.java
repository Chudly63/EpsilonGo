public class Controller{
    static GoBoard board = new GoBoard(9);
    public static void main(String [] args){
        board.setSpace(1,2,new Space(1,2,2));
        board.setSpace(1,1,new Space(1,1,2));
        board.setSpace(2,1,new Space(2,1,2));

        board.setSpace(5,3,new Space(5,3,1));
        board.setSpace(5,4,new Space(5,4,1));
        board.setSpace(5,5,new Space(5,5,1));
        board.setSpace(5,6,new Space(5,6,1));

        board.setSpace(6,1,new Space(6,1,1));
        board.setSpace(8,1,new Space(8,1,1));
        board.setSpace(7,0,new Space(7,0,1));
        board.setSpace(7,2,new Space(7,2,1));
        board.setSpace(7,1,new Space(7,1,2));
        board.updateLiberties();
        System.out.println(board.toString());
    }
}