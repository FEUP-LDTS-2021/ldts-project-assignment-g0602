package superMario.gameStates;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;


public class MenuState implements State{
    private GameStateManager gsm;
    private int currentChoice = 1;
    private String[] options = {
            "Play",
            "Instructions",
            "Exit"
    };

    public void setCurrentChoice(int currentChoice) {
        this.currentChoice = currentChoice;
    }

    public String[] getOptions(){
        return options;
    }

    public int getCurrentChoice() {
        return currentChoice;
    }

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

        g.putString(65,10,"Super Mario", SGR.BOLD);

        for(int i = 0; i < options.length; i++){
            if(i + 1 == currentChoice){
                g.setForegroundColor(TextColor.Factory.fromString("RED"));
            }else{
                g.setForegroundColor(TextColor.Factory.fromString("WHITE"));
            }
            g.putString(65, 13 + i, options[i], SGR.BOLD);
        }
    }

    @Override
    public void keyPressed(KeyStroke key) {
        switch (key.getKeyType()){
            case Enter -> select();
            case ArrowUp -> {
                currentChoice--;
                if(currentChoice == 0){
                    currentChoice = options.length;
                }
            }
            case ArrowDown-> {
                currentChoice++;
                if(currentChoice == options.length + 1){
                    currentChoice = 1;
                }
            }
        }

    }

    @Override
    public void keyReleased() {}

    public void select(){
        switch (currentChoice){
            case 1,2:
                gsm.setState(currentChoice);
                break;
            case 3:
                System.exit(0);
                break;
        }
    }
}
