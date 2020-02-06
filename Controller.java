import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Controller implements ActionListener{
    View view;
    Model model;

    public Controller(){
    }

    public Controller(Model model){
        this.model = model;
    }

    public void setView(View view){
        this.view = view;
    }

    public View getView(){
        return this.view;
    }

    public abstract void actionPerformed(ActionEvent e);
}