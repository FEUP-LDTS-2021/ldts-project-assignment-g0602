package superMario

import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import spock.lang.Specification
import superMario.gameStates.GameStateManager
import superMario.gameStates.LevelSelectState
import superMario.gameStates.LevelState

class LevelSelectStateSpockTest extends Specification{
    LevelSelectState ls
    GameStateManager gsm = new GameStateManager()

    def setup(){
        ls = new LevelSelectState(gsm)
        gsm.setState(1)
    }

    def ' Basic test input ArrowDown'(){
        given:
            def key = new KeyStroke(KeyType.ArrowDown)
            int choice = ls.getCurrentChoice()

        when:
            ls.keyPressed(key)

        then:
            ls.getCurrentChoice() == choice + 1
    }

    def 'Bottom position test input ArrowDown'(){
        given:
            def key = new KeyStroke(KeyType.ArrowDown)
            ls.setCurrentChoice(3)

        when:
            ls.keyPressed(key)
        then:
            ls.getCurrentChoice() == 1
    }

    def 'Basic test input ArrowUp'(){
        given:
            def key = new KeyStroke(KeyType.ArrowUp)
            ls.setCurrentChoice(3)
            int choice = ls.getCurrentChoice()

        when:
            ls.keyPressed(key)

        then:
            ls.getCurrentChoice() == choice - 1
    }


    def 'Top position test input ArrowUp'(){
        given:
            def key = new KeyStroke(KeyType.ArrowUp)
            ls.setCurrentChoice(1)

        when:
            ls.keyPressed(key)

        then:
            ls.getCurrentChoice() == 3
    }

    def 'test input Enter'(){
        given:
            def key = new KeyStroke(KeyType.Enter)

        when:
            ls.keyPressed(key)

        then:
            gsm.getCurrentState() == 5

    }
}
