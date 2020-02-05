import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoBoard implements Serializable {
    private List<List<Space>> spaces;

    private HashSet<Space> visited;


    //9, 15, or 19
    public GoBoard(int sideLength){

        this.spaces = new ArrayList<List<Space>>();
        for(int i = 0; i < sideLength; i++){
            List<Space> column = new ArrayList<Space>();
            for(int j = 0; j < sideLength; j++){
                column.add(new Space(i, j, 0));
            }
            this.spaces.add(column);
        }
    }

    public List<List<Space>> getSpaces(){
        return this.spaces;
    }

    public Space getSpace(int x, int y){
        return this.spaces.get(x).get(y);
    }

    public void setSpace(int x, int y, Space space){
        this.spaces.get(x).set(y, space);
    }

    public int getBoardLength(){
        return this.spaces.size();
    }

    public void updateLiberties(){
        for(int i = 0; i < this.spaces.size(); i++){
            for(int j = 0; j < this.spaces.size(); j++){
                this.visited = new HashSet<Space>();
                System.out.println("Space: " + i + "," + j);
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
                    System.out.println("FOUND LIBERTY");
                    liberties.add(neighbor);
                }
            }
        }

        return liberties;
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