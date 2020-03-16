package board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GoBoard implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<List<Space>> spaces;
    private List<Integer> pieces;
    private List<Integer> captures;
    private List<Double> scores;
    private int sideLength;
    private int currentPlayer;
    private HashSet<Space> visited;

    private double komi;
    private String blackName;
    private String whiteName;
    private boolean passLastTurn;


    public GoBoard(int sideLength, double komi, String blackName, String whiteName){
        this.sideLength = sideLength;
        this.currentPlayer = 1;
        this.komi = komi;
        this.blackName = blackName;
        this.whiteName = whiteName;
        this.pieces = new ArrayList<Integer>();
        this.captures = new ArrayList<Integer>();

        this.captures.add(Integer.valueOf(0));
        this.captures.add(Integer.valueOf(0));
        this.pieces.add(Integer.valueOf(sideLength * sideLength / 2 + 1));
        this.pieces.add(Integer.valueOf(sideLength * sideLength / 2));
        
        this.passLastTurn = false;

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
        this.passLastTurn = false;
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
    
    public List<Double> getScores() {
    	return this.scores;
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

    public String getPlayerName(int player){
        return player == 1 ? this.blackName : this.whiteName;
    }

    public double getKomi(){
        return this.komi;
    }
    
    public boolean isPassLastTurn() {
    	return this.passLastTurn;
    }
    
    public void setPassLastTurn(boolean passLastTurn) {
    	this.passLastTurn = passLastTurn;
    }
    
    public List<Space> getNeighbors(int x, int y){
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
        
        return neighbors;
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
            List<Space> neighbors = this.getNeighbors(x,  y);
            
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
    
    
    public HashSet<Integer> findOwners(int x, int y) {
    	Space currentSpace = getSpace(x,y);
    	this.visited.add(currentSpace);
    	HashSet<Integer> found = new HashSet<Integer>();
    	
    	if(currentSpace.getValue() != 0) {
    		return null;
    	}
    	
    	List<Space> neighbors = this.getNeighbors(x, y);
    	
    	//Find all player tokens surrounding this territory
    	for(Space neighbor : neighbors) {
    		if(neighbor.getValue() == 0 && !this.visited.contains(neighbor)) {
    			this.visited.add(neighbor);
    			found.addAll(this.findOwners(neighbor.getX(), neighbor.getY()));
    		}
    		else if(neighbor.getValue() == 1 || neighbor.getValue() == 2) {
    			found.add(Integer.valueOf(neighbor.getValue()));
    		}
    		/*
    		else if(neighbor.getValue() == 4 || neighbor.getValue() == 5) {
    			found.add(Integer.valueOf(neighbor.getValue() - 3));
    		}
    		*/
    	}
    	
    	return found;
    	
    	
    }
    //Traverse the current board and mark what territory is captuered by each player
    // 4 = captured by black
    // 5 = captured by white
    // 0 = captured by nobody
    public void markCapturedTerritory() {
    	for(int i = 0; i < this.spaces.size(); i++){
            for(int j = 0; j < this.spaces.size(); j++){
                this.visited = new HashSet<Space>();
                HashSet<Integer> found = this.findOwners(i,j);
                if(found != null && found.size() > 1) {
                	//Territory is touched by both players, no one owns is
                	this.visited.forEach(visitedSpace -> visitedSpace.setValue(0));
                }
                else if(found != null && found.size() == 1) {
                	//Territory is touched by only one player, they own it
                	Integer owner = found.iterator().next();
                	this.visited.forEach(visitedSpace -> visitedSpace.setValue(owner + 3));
                }
            }
        }
    }
    
    public int countSpacesWithValue(int value) {
    	int count = 0;
    	for(Space space : this.getAllSpaces()) {
    		if(space.getValue() == value) {
    			count++;
    		}
    	}
    	return count;
    }

<<<<<<< HEAD:src/board/GoBoard.java
    public void updateScores(){
        this.scores = new ArrayList<Double>();
        
        this.markCapturedTerritory();
        double blackScore = this.countSpacesWithValue(4) + this.captures.get(0);
        double whiteScore = this.countSpacesWithValue(5) + this.captures.get(1) + this.komi;
        
        this.scores.add(blackScore);
        this.scores.add(whiteScore);
=======
    public List<Double> getScores(){
        ArrayList<Double> scores = new ArrayList<Double>();
        //TODO: Implement Scoring

        //Dummy Values
        scores.add(0.0);
        scores.add(0 + this.komi);
        return scores;
>>>>>>> 4d5fd497d12ce4669a9ed7df4d7724829042aa73:GoBoard.java
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