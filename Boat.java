//Patrick Hunner - hunne007

public class Boat{
    int length;
    boolean horizontal;
    Cell[] cellArray;

    public Boat(int length, boolean horizontal){
        this.length = length;
        this.horizontal = horizontal;
        this.cellArray = null;
    }

    public int getLength(){
        return length;
    }

    public boolean getHorizontal(){
        return horizontal;
    }

    public Cell[] getCellArray(){
        return cellArray;
    }

    public void setCellArray(Cell[] newArray){
        cellArray = newArray;
    }
}