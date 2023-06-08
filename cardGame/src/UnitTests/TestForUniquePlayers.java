package UnitTests;

import Models.Game;
import Models.Player;
import junit.framework.AssertionFailedError;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class TestForUniquePlayers {

    @Test
    public void testForUniquePlayers(){
        ArrayList<Player> players = new ArrayList<>();
        int numOfPs = 5;//lets say 5
        // In order to fail the method comment the for loop and run the while loop
        // In order to run the method successfully uncomment the for loop and comment the while loop
//        for(int i = 1;i<=numOfPs;i++){
//                Player p = new Player(i);
//                players.add(p);
//
//            }
        while(numOfPs > 0){
            int idx = 1;
            Player p = new Player(idx++);
            players.add(p);
            numOfPs--;
        }
        HashMap<Integer,Integer> map = new HashMap<>();
        // we will check the values of map if the players are not unique then it will throw error


        for(Player p : players){
            map.put(p.getId(),map.getOrDefault(p.getId(),0)+1);
        }
        for(int i = 0;i<map.size();i++){
            System.out.println(map.get(i));
        }
        for(int v : map.values()){
            Assert.assertTrue(v == 1);
        }
    }
}
