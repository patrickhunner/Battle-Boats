//Patrick Hunner - hunne007

public class Board {
    private Cell cellArr[][];
    private Boat[] boatArr;
    private int totalShots;
    private int turns;
    private int shipsRemaining;

    public Board(int x, int y) {
        this.totalShots = 0;
        this.turns = 1;
        this.shipsRemaining = 5;
        this.cellArr = new Cell[x][y];
        this.boatArr = new Boat[5];
    }

    public Boat[] getBoatArr() {
        return boatArr;
    }

    public int getShipsRemaining(){
        return shipsRemaining;
    }

    public Cell[][] getCellArr() {
        return cellArr;
    }

    public void setCellArr(int x, int y, Cell cell) {
        cellArr[y][x] = cell;
    }

    public void setBoatArr() { //start with largest boat for fastest placement algorithm
        boatArr[4] = new Boat(2, true);
        boatArr[3] = new Boat(3, false);
        boatArr[2] = new Boat(3, true);
        boatArr[1] = new Boat(4, true);
        boatArr[0] = new Boat(5, false);
    }

    public int getTurns(){
        return turns;
    }

    public void increaseTurns(){
        turns++;
    }

    public void placeBoats() {
        int boatsPlaced = 0;
        while (boatsPlaced < 5) { //loop until all boats have been placed
            boolean place = true;
            Boat currBoat = boatArr[boatsPlaced];
            int x = (int) Math.floor(Math.random() * 10);
            int y = (int) Math.floor(Math.random() * 10);
            if (currBoat.getHorizontal()) { //boat is horizontal
                if (x + currBoat.getLength() - 1 <= 9) { //within board bounds
                    for(int i = 0; i < currBoat.getLength(); i++){ //check if a boat is already in any of these positions
                        if(cellArr[y][x + i].getStatus() == 'B'){
                            place = false;
                        }
                    }
                    if(place == true) { //we're clear to place a boat and put it in the cell array
                        boatsPlaced++;
                        Cell[] array = new Cell[currBoat.getLength()];
                        for (int i = 0; i < currBoat.getLength(); i++) {
                            cellArr[y][x + i].setStatus('B');
                            array[i] = cellArr[y][x + i];
                        }
                        currBoat.setCellArray(array);
                    }
                }
            }
            else { //boat is vertical
                if (y + currBoat.getLength() - 1 <= 9) { //within board bounds
                    for(int i = 0; i < currBoat.getLength(); i++){ //check if a boat is already in any of these positions
                        if(cellArr[y + i][x].getStatus() == 'B'){
                            place = false;
                        }
                    }
                    if(place == true) { //we're clear to place a boat and put it in the cell array
                        boatsPlaced++;
                        Cell[] array = new Cell[currBoat.getLength()];
                        for (int i = 0; i < currBoat.getLength(); i++) {
                            cellArr[y + i][x].setStatus('B');
                            array[i] = cellArr[y + i][x];
                        }
                        currBoat.setCellArray(array);
                    }
                }
            }
        }
    }

    public String fire(int x, int y) {
        if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
            if (cellArr[y][x].getStatus() == 'B') {
                cellArr[y][x].setStatus('H');
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < boatArr[i].getLength(); j++){
                        if(boatArr[i].cellArray[j].getRow() == x && boatArr[i].cellArray[j].getColumn() == y){
                            Boat currBoat = boatArr[i];
                            for(int z = 0; z < currBoat.getLength(); z++){
                                if(currBoat.cellArray[z].getStatus() != 'H'){
                                    return "HIT!";
                                }
                            }
                            shipsRemaining--;
                            return "SHIP SUNK!";
                        }
                    }
                }
            }
            if (cellArr[y][x].getStatus() == 'H') { //cell already hit
                return "Already Hit";
            } else if (cellArr[y][x].getStatus() == 'M') { //cell already shot at
                increaseTurns();
                return "Penalty. We're skipping your next turn!";
            } else {
                cellArr[y][x].setStatus('M');
                return "MISS!";
            }
        } else { //invalid input
            increaseTurns();
            return "Penalty. We're skipping your next turn!";
        }
    }

    public String scan(int x, int y) {
        String orientation;
        if(cellArr[y][x].getStatus() == '-'){ //nothing there
            System.out.println(fire(x,y));
            return "No boat here";
        }
        else{
            for(int i = 0; i < 5; i++){ //determine which boat is in this location
                Boat currentBoat = boatArr[i];
                for(int j = 0; j < currentBoat.getLength() - 1; j++){
                    if(x == currentBoat.cellArray[j].getRow() && y == currentBoat.cellArray[j].getColumn()){
                        System.out.println(fire(x,y)); //fire and find length and orientation
                        if(currentBoat.getHorizontal() == true){ //convert character orientation to string
                            orientation = "horizontally";
                        }
                        else{
                            orientation = "vertically";
                        }
                        String scanned = "The boat here is " + currentBoat.getLength() + " units long and placed " + orientation;
                        return scanned;
                    }
                }
            }
        }
        return "error"; //one if statement will always be accessed, simply for intellij program
    }
}