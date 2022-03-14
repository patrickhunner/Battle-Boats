//Patrick Hunner - hunne007

public class Cell{
    private int row;
    private int column;
    private char status;

    public Cell(int row, int column){
        this.row = row;
        this.column = column;
        this.status = '-';
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public char getStatus(){
        return status;
    }

    public void setStatus(char newStatus){
        status = newStatus;
    }
}