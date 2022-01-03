package GameState;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;


public class MenuState implements State{
    private GameStateManager gsm;
    private int currentChoice;
    private String[] options = {
            "Play",
            "Instructions",
            "Exit"
    };

    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
    }

    @Override
    public void init() {}

    @Override
    public void update() {}

    @Override
    public void draw(TextGraphics g) {
        g.setBackgroundColor(TextColor.Factory.fromString("BLACK"));
        g.fillRectangle(new TerminalPosition(0,0), new TerminalSize(190, 50), ' ');

        g.putString(10,10,"Super Mario");

        for(int i = 0; i < options.length; i++){
            if(i == currentChoice){
                g.setForegroundColor(TextColor.Factory.fromString("RED"));
            }else{
                g.setForegroundColor(TextColor.Factory.fromString("WHITE"));
            }
            g.putString(10, 13 + i, options[i]);
        }
    }

    @Override
    public void keyPressed(KeyStroke key) {
        switch (key.getKeyType()){
            case Enter -> select();
            case ArrowUp -> {
                currentChoice--;
                if(currentChoice == -1){
                    currentChoice = options.length - 1;
                }
            }
            case ArrowDown-> {
                currentChoice++;
                if(currentChoice == options.length){
                    currentChoice = 0;
                }
            }
        }

    }

    @Override
    public void keyReleased() {}

    public void select(){
        switch (currentChoice){
            case 0:
                gsm.setState(currentChoice);
                System.out.println("start");
                break;
            case 1:
                gsm.setState(currentChoice);
                System.out.println("instruct");
                break;
            case 2:
                System.exit(0);
                break;
        }
    }
}
