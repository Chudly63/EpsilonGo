import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoBoard extends Model implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<List<Space>> spaces;
    private List<Integer> pieces;
    private List<Integer> captures;
    private int sideLength;
    private int currentPlayer;
    private HashSet<Space> visited;


    //9, 15, or 19
    public GoBoard(int sideLength){
        this.sideLength = sideLength;
        this.currentPlayer = 1;
        this.pieces = new ArrayList<Integer>();
        this.captures = new ArrayList<Integer>();

        this.captures.add(Integer.valueOf(0));
        this.captures.add(Integer.valueOf(0));
        this.pieces.add(Integer.valueOf(sideLength * sideLength / 2 + 1));
        this.pieces.add(Integer.valueOf(sideLength * sideLength / 2));

        this.spaces = new ArrayList<List<Space>>();
        for(int i = 0; i < sideLength; i++){
            List<Space> column = new ArrayList<Space>();
            for(int j = 0; j < sideLength; j++){
                column.add(new Space(i, j, 0));
            }
            this.spaces.add(column);
        }
    }

    public void resetBoard(){
        for(List<Space> row : this.spaces){
            for(Space space : row){
                space.setValue(0);
            }
        }
        this.pieces.set(0, Integer.valueOf(this.sideLength * this.sideLength / 2 + 1));
        this.pieces.set(1, Integer.valueOf(this.sideLength * this.sideLength / 2));
        this.captures.set(0, Integer.valueOf(0));
        this.captures.set(1, Integer.valueOf(0));
        this.currentPlayer = 1;
    }



    public List<List<Space>> getSpaces() {
        return this.spaces;
    }

    public List<Space> getAllSpaces() {
        List<Space> spaces = new ArrayList<Space>();
        for(List<Space> row : this.spaces){
            for(Space space : row){
                spaces.add(space);
            }
        }
        return spaces;
    }

    public Space getSpace(int x, int y){
        return this.spaces.get(x).get(y);
    }

    public void setSpace(int x, int y, Space space){
        this.spaces.get(x).set(y, space);
    }

    public int getPieces(int player){
        return this.pieces.get(player - 1);
    }

    public int getCaptures(int player){
        return this.captures.get(player - 1);
    }

    public void setPieces(int player, int pieces){
        this.pieces.set(player - 1, Integer.valueOf(pieces));
    }

    public void setCaptures(int player, int captures){
        this.captures.set(player - 1, Integer.valueOf(captures));
    }

    public void addPieces(int player, int pieces){
        this.pieces.set(player - 1, Integer.valueOf(getPieces(player) + pieces));
    }

    public void addCaptures(int player, int captures){
        this.captures.set(player - 1, Integer.valueOf(getCaptures(player) + captures));
    }

    public int getBoardLength(){
        return this.spaces.size();
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }

    public int getOtherPlayer(){
        return this.currentPlayer == 1 ? 2 : 1;
    }

    public void changePlayer(){
        this.currentPlayer = this.getOtherPlayer();
    }

    public void updateLiberties(){
        for(int i = 0; i < this.spaces.size(); i++){
            for(int j = 0; j < this.spaces.size(); j++){
                this.visited = new HashSet<Space>();
                this.getSpace(i,j).setLiberties(this.getLiberties(i,j));
            }
        }
    }

    public HashSet<Space> getLiberties(int x, int y){
        Space currentSpace = getSpace(x,y);
        HashSet<Space> liberties = new HashSet<Space>();
        if (currentSpace.getValue() == 0){
            return liberties;
        }
        else{
            List<Space> neighbors = new ArrayList<Space>();

            //Get north
            if(y != 0 && !this.visited.contains(this.getSpace(x, y - 1))){
                neighbors.add(this.getSpace(x, y - 1));
            }
            //Get south
            if(y != getBoardLength() - 1 && !this.visited.contains(this.getSpace(x, y + 1))){
                neighbors.add(this.getSpace(x, y + 1));
            }
            //Get west
            if(x != 0 && !this.visited.contains(this.getSpace(x - 1, y))){
                neighbors.add(this.getSpace(x - 1, y));
            }
            //Get east
            if(x != getBoardLength() - 1 && !visited.contains(this.getSpace(x + 1, y))){
                neighbors.add(this.getSpace(x + 1, y));
            }
            for (Space neighbor : neighbors){
                if(neighbor.getValue() == currentSpace.getValue()){
                    this.visited.add(neighbor);
                    liberties.addAll(this.getLiberties(neighbor.getX(), neighbor.getY()));
                }
                else if (neighbor.getValue() == 0){
                    liberties.add(neighbor);
                }
            }
        }
        return liberties;
    }

    public List<Space> highlightCaptures(int capturedPlayer){
        ArrayList<Space> capturesFound = new ArrayList<Space>();
        for(List<Space> row : this.spaces){
            for(Space space : row){
                this.visited = new HashSet<Space>();
                if(space.getValue() == capturedPlayer && getLiberties(space.getX(), space.getY()).size() == 0){
                    space.setValue(3);
                    capturesFound.add(space);
                }
            }
        }
        return capturesFound;
    }

    public List<Space> collectCaptures(){
        ArrayList<Space> captures = new ArrayList<Space>();
        for(List<Space> row : this.spaces){
            for(Space space : row){
                if(space.getValue() == 3){
                    space.setValue(0);
                    captures.add(space);
                }
            }
        }
        return captures;
    }

    public String toString(){
        String rebound = "\nCurrent Board: \n";
        for(int i = 0; i < this.spaces.size(); i++){
            for(int j = 0; j < this.spaces.size(); j++){
                rebound += this.spaces.get(j).get(i).getValue() + " ";
            }
            rebound += "\n";
        }

        rebound += "\nLiberties: \n";
        for(int i = 0; i < this.spaces.size(); i++){
            for(int j = 0; j < this.spaces.size(); j++){
                rebound += this.spaces.get(j).get(i).getLiberties().size() + " ";
            }
            rebound += "\n";
        }

        return rebound;
    }
}