import Element.Player
import GameState.GameStateManager
import GameState.LevelState
import com.googlecode.lanterna.input.KeyType
import spock.lang.Specification

class PlayerSpockTest extends Specification{
    Player player = new Player(10,0)
    Player playerMock = Mock(Player)

    def 'ArrowLeft Test'(){
        given:
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowLeft)
            int x = player.getPosition().getX()
        when:
            player.keyPressed(key)
            player.update()
        then:
            player.getIsLeft() == true
            player.getVx() == -3
            player.getPosition().getX() == x - 3
    }

    def 'ArrowRight Test'(){
        given:
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowRight)
            int x = player.getPosition().getX()
        when:
            player.keyPressed(key)
            player.update()
        then:
            player.getIsLeft() == false
            player.getVx() == 3
            player.getPosition().getX() == x + 3
    }

    def 'ArrowUp/Jump Test'(){
        given:
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowUp)
            int vy = player.getVy()
        when:
            player.keyPressed(key)
        then:
            player.getVy() == vy - player.getJumpStrength()
            player.getIsJumping() == true
    }

    def 'no doubleJump'(){
        given:
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowUp)
        when:
            player.update()
        then:
            player.getIsJumping() == false
        when:
            player.keyPressed(key)
        then:
            player.getIsJumping()
    }
}
