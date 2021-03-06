package superMario


import com.googlecode.lanterna.input.KeyType
import spock.lang.Specification
import superMario.blocks.BrickBlock
import superMario.elements.Block
import superMario.elements.Enemy
import superMario.elements.FinalFlag
import superMario.elements.Player
import superMario.elements.Position
import superMario.enemies.EnemyX
import superMario.enemies.EnemyY
import superMario.gameStates.GameStateManager
import superMario.gameStates.LevelState



class LevelStateSpockTest extends Specification{

    def 'Collision Block under Player'(){
        given:
        Block b = new BrickBlock(20,10)
        Player p = new Player(11,10)
        when:
            def bool = p.getBounds().intersects(b.getBounds())
        then:
            bool == true
    }

    def 'Collision Block above Player'(){
        given:
            Block b = new BrickBlock(10,10)
            Player p = new Player(14,10)
        when:
            def bool = p.getBounds().intersects(b.getBounds())
        then:
            bool == true
    }

    def 'Collision Block left Player'(){
        given:
            Block b = new BrickBlock(10,10)
            Player p = new Player(5,16)
        when:
            def bool = p.getBounds().intersects(b.getBounds())
        then:
            bool == true
    }

    def 'Collision Block right Player'(){
        given:
            Block b = new BrickBlock(10,10)
            Player p = new Player(5,4)
        when:
            def bool = p.getBounds().intersects(b.getBounds())
        then:
            bool == true
    }

    def 'Player collides with left block stays same position'(){
        GameStateManager gsm = new GameStateManager()
        given:
            LevelState level = new LevelState(gsm);
            Block b = new BrickBlock(10,2)
            level.getBlocks().add(b)
            Player p = new Player(5,10)
            FinalFlag flag = new FinalFlag(20,18)
            level.setFlag(flag)
            Position pPosition = new Position(p.getPosition().getX(),p.getPosition().getY())
            level.setPlayer(p)
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowLeft)
        when:
            level.keyPressed(key)
            level.update()
        then:
            p.getPosition().isEqual(pPosition)

    }

    def 'Player collides with right block stays same position'(){
        GameStateManager gsm = new GameStateManager()
        given:
            LevelState level = new LevelState(gsm)
            FinalFlag flag = new FinalFlag(20,50)
            level.setFlag(flag)
            Block b = new BrickBlock(10,19)
            level.getBlocks().add(b)
            Player p = new Player(5,10)
            Position pPosition = new Position(p.getPosition().getX(),p.getPosition().getY())
            level.setPlayer(p)
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowRight)
        when:
            level.keyPressed(key)
            level.update()
        then:
            p.getPosition().isEqual(pPosition)
    }

    def 'Player collides with ground block stays same position'(){
        GameStateManager gsm = new GameStateManager()
        given:
            LevelState level = new LevelState(gsm)
            FinalFlag flag = new FinalFlag(20,50)
            level.setFlag(flag)
            Block b = new BrickBlock(20,10)
            level.getBlocks().add(b)
            Player p = new Player(11,10)
            p.setVy(1)
            Position pPosition = new Position(p.getPosition().getX(),p.getPosition().getY())
            level.setPlayer(p)
        when:
            level.update()
        then:
            p.getPosition().isEqual(pPosition)
    }

    def 'Player collides with Enemy and loses a live'(){
        GameStateManager gsm = new GameStateManager()
        given:
            LevelState level = new LevelState(gsm)
            FinalFlag flag = new FinalFlag(20,50)
            level.setFlag(flag)
            Enemy e = new EnemyX(20,8)
            e.setVisible(true)
            level.getEnemies().add(e)
            Player p = new Player(15,14)
            level.setPlayer(p)
            int playerLives = p.getLives()

        when:
            level.update()

        then:
            p.getPosition().getY() == 0
            level.getLives() == playerLives - 1

    }

    def 'Player kills Enemy'(){
        GameStateManager gsm = new GameStateManager()

        given:
            LevelState level = new LevelState(gsm)
            FinalFlag flag = new FinalFlag(20,50)
            level.setFlag(flag)
            Enemy e = new EnemyY(29,8)
            e.setVisible(true)
            level.getEnemies().add(e)
            Player p = new Player(20,8)
            level.setPlayer(p)

        when:
            level.update()

        then:
            e.isVisible() == false

    }

    def 'Player wins'(){
        GameStateManager gsm = new GameStateManager()

        given:
            LevelState level = new LevelState(gsm)
            Player p = new Player(20,8)
            level.setPlayer(p)
            FinalFlag flag = new FinalFlag(20,18)
            level.setFlag(flag)
            def key = new com.googlecode.lanterna.input.KeyStroke(KeyType.ArrowRight)
        when:
            level.keyPressed(key)
            level.update()

        then:
            gsm.getCurrentState() == 4
    }

    def 'Player loses'(){
        GameStateManager gsm = new GameStateManager()
        given:
            LevelState level = new LevelState(gsm)
            Player p = new Player(20,8)
            level.setPlayer(p)
            FinalFlag flag = new FinalFlag(20,18)
            level.setFlag(flag)
            p.setLives(0)
        when:
            level.update()

        then:
            gsm.getCurrentState() == 3
    }

}
