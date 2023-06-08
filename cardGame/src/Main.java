import Controllers.GameController;
import Exceptions.PlayerCntException;
import Models.Game;
import Models.GameState;
import Models.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PlayerCntException {

        GameController controller = new GameController();
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the number of Players");
            int numOfPlayers = sc.nextInt();
            if(numOfPlayers < 2 || numOfPlayers > 4){
                //No of Players Validation
                System.out.println("Sorry Invalid Input please enter in-between 2 to 4 after restarting from mains");
                System.exit(0);
            }
            ArrayList<Player> players = new ArrayList<>();
            GameState state = GameState.IN_PROGRESS;
            for(int i = 1;i<=numOfPlayers;i++){
                Player p = new Player(i);
                players.add(p);
            }

            Game game = controller.startGame(numOfPlayers,players);
            // Triggered obj of game from main
            while(controller.checkState(game).equals(GameState.IN_PROGRESS)){
                // now we will first distribute 5 cards among all players
                //then we will create discard and draw piles
                // then game will begin following turns of each player

                if(controller.checkState(game).equals(GameState.DRAW)){
                    System.out.println("----- Result : GAME DRAW! Draw pile empty! -----");
                }
                controller.distributeCards(game);
                controller.createDiscardAndDrawPile(game);
                controller.beginGame(players,game);
            }
        }catch(Exception e){
            System.out.println("Something went wrong");

        }
    }
}