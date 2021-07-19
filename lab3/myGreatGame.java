package lab3;
import javax.swing.JTable;

import jdk.nashorn.api.tree.ForInLoopTree;
import lab3.JGaloInterface;

public class myGreatGame implements JGaloInterface{
     private int player;
     private int sum=0;
     private char[][] table;
     private char playervalue;
     private char winner;
/**
 * Class Constructor 
 * Creates a table where user inputs will be recorded
 * 
 * @param player player defines whose turn it is to play, even for 'O' odd for 'X'
 */


     public myGreatGame(int player){
          this.player=player;
          this.table= new char[3][3]; //creates an empty table 
          for (int i = 0; i < table.length; i++) {
               for (int j = 0; j < table.length; j++) {
                    table[i][j]=' ';
               }
          }
          if (player%2==0) {sum=-1;} // sum is a variable that adjusts the number of moves due to the fact this class receives 1 or 2 when its called
     }

     /**
      * Method to find the current player, also registers the last player to play
      * @return    Returns a char with the current player
      */

     public char getActualPlayer(){
        
          if  (player%2 ==0) {
               player++;
               playervalue='O';
               return 'O';
          }else{
               player++;
               playervalue='X';
               return 'X';
          }
     }

     /**
      * Method that declares the winner

      * @return     Returns a char with the winner, in case of a draw returns ' '
      */

     public  char checkResult(){
          if(winner!=' '){
               if  (player%2 ==0){
                    return 'X';
               }else{
                    return 'O';
               }
          }else{
          return winner;
          }
     }
     /**
      * Method that defines when a game is ended
      * Game ends when a win condition is found or there are no more possible moves without a winner
      * @return     returns true when a finishing condition is found
      */
     public  boolean isFinished(){
          
              if(player+sum>5){    //5 is the minimum amount of moves to find a winner 
                    if (
                    (table[0][0]==table[0][1] && table[0][1]==table[0][2] && table[0][2]!=' ')|| 
                    (table[1][0]==table[1][1] && table[1][1]==table[1][2] && table[1][2]!=' ')|| 
                    (table[2][0]==table[2][1] && table[2][1]==table[2][2] && table[2][2]!=' ')||
                    (table[0][0]==table[1][0] && table[1][0]==table[2][0] && table[2][0]!=' ')|| 
                    (table[0][1]==table[1][1] && table[1][1]==table[2][1] && table[2][1]!=' ')|| 
                    (table[0][2]==table[1][2] && table[1][2]==table[2][2] && table[2][2]!=' ')||
                    (table[0][0]==table[1][1] && table[1][1]==table[2][2] && table[2][2]!=' ')||
                    (table[0][2]==table[1][1] && table[1][1]==table[2][0] && table[2][0]!=' ')){
                         return true;
                    }else if(player+sum==10){     //10 is the maximum amount of moves
                         winner= ' ';
                         return true;
                    }else{
                         
                         return false;}
               }else{
                    return false;}
          
     }
     /**
      * Method that registers the player move in a table 
      * @param lin  the horizontal line of the table
      * @param col  the vertical line of the table
      * @return     returns true after the move is registered
      */ 
     public  boolean setJogada(int lin, int col){
          if (table[lin-1][col-1]==' '){
               table[lin-1][col-1] = playervalue;}
          
          return true;

     }



}