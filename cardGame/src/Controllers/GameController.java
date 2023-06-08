package Controllers;

import Exceptions.PlayerCntException;
import Models.*;

import java.util.ArrayList;

public class GameController {

    void makeMove(Game game){

    }

    void getWinner(Game game){

    }
    public Game startGame(int cnt,ArrayList<Player> players) throws PlayerCntException {

       return Game.getBuilder().setPlayers(players).setPlayersCnt(cnt).build();
       // Initializing the game object
    }
    public GameController() {

    }
    public void distributeCards(Game game){
        game.dealCards(game.getPlayers());
    }
    public void createDiscardAndDrawPile(Game game){
        game.createPiles();
    }
    public GameState checkState(Game game){
        return game.getGameState();
    }
    public void beginGame(ArrayList<Player> players,Game game){
        game.gameBegin(players);
    }
}
