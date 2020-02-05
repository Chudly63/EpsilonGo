import java.io.Serializable;
import java.util.HashSet;

public class Space implements Serializable {
    private int x;
    private int y;
    
    private int value;
    private HashSet<Space> liberties;

    public Space(){
        this.value = 0;
    }

    public Space(int x, int y, int value){
        this.x = x;
        this.y = y;
        this.value = value;
        this.liberties = new HashSet<Space>();
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getValue(){
        return this.value;
    }

    public HashSet<Space> getLiberties(){
        return this.liberties;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setLiberties(HashSet<Space> liberties){
        this.liberties = liberties;
    }

    public void addLiberty(Space liberty){
        this.liberties.add(liberty);
    }

}