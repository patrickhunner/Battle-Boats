//Patrick Hunner - hunne007

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Board board;
    boolean debug;
    boolean missileUsed;
    boolean droneUsed;
    boolean scannerUsed;

    public Game(Board board) {
        this.board = board;
        this.debug = false;
        this.missileUsed = false;
        this.droneUsed = false;
        this.scannerUsed = false;
    }

    public String missile(){
        int hit = 0;
        int miss = 0;
        System.out.println("Enter a coordinate to fire the missile");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        int[] coord = validCoordinates(input);
        if (coord.length != 0){ //ensure the input was the correct format and has coordinates
            int[][] peripherals = {{coord[0],coord[1]},{coord[0] + 1,coord[1]},{coord[0],coord[1] + 1},{coord[0] - 1,coord[1]},{coord[0],coord[1] - 1}}; //four coordinates around the initial strike
            for(int i = 0; i < 5; i++){ //fire at all valid points around input
                if(board.fire(peripherals[i][0],peripherals[i][1]).equals("HIT!")){
                    hit++;
                }
                else{
                    miss++;
                }
            }
            String returned = "Miss: " + miss + ", Hit: " + hit;
            return returned;
        }
        else{
            return "Invalid Coordinate";
        }
    }

    public String droneDirection(){
        System.out.println("Enter r for row or c for column: ");
        while(true) { //only end when valid input is given
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            if (input.equals("c") || input.equals("r")) { //column
                System.out.println("Enter the index of the " + input + " you'd like to scan");
                while(true) { //end when valid input is given
                    try {
                        Scanner sn = new Scanner(System.in);
                        int input2 = sn.nextInt();
                        if (input2 <= 9 && input2 >= 0) {
                            return droneFind(input, input2);
                        } else {
                            System.out.println("Invalid index, please type in a number within the boundaries of the board.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input, please enter a number within the boundaries of the board.");
                    }
                }
            } else {
                System.out.println("Invalid input, please type in r for row or c for column.");
            }
        }
    }

    public String droneFind(String direction, int index){ //use once given the orientation and coordinates
        int numBoats = 0;
        if(direction.equals("r")) { //row
            for(int i = 0; i < 10; i++){
                if(this.board.getCellArr()[index][i].getStatus() == 'B'){
                    numBoats++;
                }
            }
        }
        else{ //column
            for(int i = 0; i < 10; i++){
                if(this.board.getCellArr()[i][index].getStatus() == 'B'){
                    numBoats++;
                }
            }
        }
        this.droneUsed = true;
        return "This " + direction + " contains " + numBoats + " targets";
    }

    public void initializeBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Cell newCell = new Cell(i, j);
                board.setCellArr(i, j, newCell);
            }
        }
    }

    public int[] validCoordinates(String input) { //check if coordinates are within board boundaries
        int[] nope = {};
        try {
            String coord[] = input.split(" ");
            int[] coords = {Integer.parseInt(coord[0]), Integer.parseInt(coord[1])};
            if (coords[0] < 0 || coords[0] > 9 || coords[1] < 0 || coords[1] > 9) {
                return nope;
            }
            return coords;
        } catch (NumberFormatException e) {
            return nope;
        }
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++) { //print out the board depending on debugging mode
            for (int j = 0; j < 10; j++) {
                if (j == 9) { //print new line
                    if (board.getCellArr()[i][j].getStatus() == 'B' && this.debug) { //boat with debug on
                        System.out.println(board.getCellArr()[i][j].getStatus());
                    } else if (board.getCellArr()[i][j].getStatus() == 'B' && !this.debug) { //boat with debug on
                        System.out.println("-");
                    } else {
                        System.out.println(board.getCellArr()[i][j].getStatus());
                    }
                } else { //print on the same line
                    if (board.getCellArr()[i][j].getStatus() == 'B' && this.debug) { //boat with debug on
                        System.out.print(board.getCellArr()[i][j].getStatus());
                    } else if (board.getCellArr()[i][j].getStatus() == 'B' && !this.debug) { //boat with debug on
                        System.out.print("-");
                    } else {
                        System.out.print(board.getCellArr()[i][j].getStatus());
                    }
                }
            }
        }
        return;
    }

    public boolean scanner() {
        System.out.println("Enter a coordinate to scan");
        while (true){ //ask until they enter a valid coordinate
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            int[] coords = validCoordinates(input);
            if (coords.length != 0) {
                System.out.println(board.scan(coords[0], coords[1]));
                return true;
            } else {
                System.out.println("Invalid input, your turn has been skipped");
                board.increaseTurns();
                return true;
            }
        }
    }

    public static void main(String[] args) {
        Board board = new Board(10, 10);
        Game thisGame = new Game(board);
        thisGame.initializeBoard();
        board.setBoatArr();
        board.placeBoats();
        System.out.println("At any point, type 'debugging' to put the game in debugging mode");
        while (true) {
            thisGame.printBoard();
            Scanner s = new Scanner(System.in);
            System.out.println("Pick a coordinate");
            String input = s.nextLine();
            int[] initialCoords = thisGame.validCoordinates(input); //check for valid coordinates
            if (initialCoords.length != 0) {
                System.out.println(board.fire(initialCoords[0], initialCoords[1]));
                thisGame.board.increaseTurns();
            }
            else if(input.equals("debugging")){
                thisGame.debug = true;
            }
            else if(input.equals("missile")){
                if(!thisGame.missileUsed){
                    String output = thisGame.missile();
                    if(!output.equals("Invalid Coordinate")){
                        thisGame.missileUsed = true;
                        thisGame.board.increaseTurns();
                    }
                    System.out.println(output);
                }
                else{
                    System.out.println("Missile already used");
                }
            }
            else if(input.equals("scanner")){
                if(!thisGame.scannerUsed){
                    if(thisGame.scanner()) {
                        thisGame.scannerUsed = true;
                        thisGame.board.increaseTurns();
                    }
                }
                else{
                    System.out.println("Scanner already used");
                }
            }
            else if(input.equals("drone")){
                if(!thisGame.droneUsed) {
                    System.out.println(thisGame.droneDirection());
                    thisGame.board.increaseTurns();
                }
                else{
                    System.out.println("Drone already used");
                }
            }
            else{
                System.out.println("Penalty. We're skipping a turn!");
                thisGame.board.increaseTurns();
                thisGame.board.increaseTurns();
            }
            System.out.println("This is turn number " + thisGame.board.getTurns());
            if(thisGame.board.getShipsRemaining() == 0){
                break;
            }
        }
        System.out.println("YOU WON IN " + thisGame.board.getTurns() + " TURNS!!!!!!!!!!");
    }
}