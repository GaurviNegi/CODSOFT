import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    static Scanner sc = new Scanner(System.in);


//function that is invoked by main to start the game
    public static int numberGame(){
        Random rad= new Random();
        int n  = rad.nextInt(1,100);
        int score= startGame(n);
        return score;
    }



//function invoked by numberGame to evaluate the users score
    private static int startGame(int n) {
        int i;
        System.out.println("GUIDELINES:  GUESS THE NUMBER (1-100) IN MAX 10 ATTEMPTS AND GET YOU SCORE ACCORDING TO " + "IT");

        for (i=1;i<=10;i++){
                System.out.println("make the guess - ");
                int a = sc.nextInt();
                if (a == n) {
                    System.out.println("the guess is correct");
                    break;
                } else if (a > n) {
                    System.out.println("oops...too high");
                } else {
                    System.out.println("oops...too low");
                }
        }
        System.out.println("SCORE is " + (10-i+1));
        System.out.println("the correct ans is "+ n);
        return (10-i+1);
    }



//main function here multiple rounds can be played by the user and the game is started
    public static void main(String [] args){
      int rounds = 0;
      int score = 0;
      String wish;
      do{
          rounds++;
          score+=  numberGame();
          System.out.println("want to play again ?\n press 0 - play \n press any key - quit");
          wish = sc.next();
      }while(wish.equals("0") && rounds<=5);

      System.out.println("GAME ENDS....................... \nyour total score is "+ score/rounds);

    }
}
