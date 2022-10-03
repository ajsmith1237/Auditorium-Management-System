/*
    Code written by Ashton Smith
    ajs190019
 */

public class Seat {
    private int Row;
    private char Seat;
    private char Type;

    public Seat() {
    }

    // Overloaded Constructor
    public Seat(int row, char seat, char type) {
        Row = row;
        Seat = seat;
        Type = type;
    }

    // Setters
    public void setRow(int row) {
        Row = row;
    }

    public void setSeat(char seat) {
        Seat = seat;
    }

    public void setType(char type) {
        Type = type;
    }

    // getters
    public int getRow() { return Row; }

    public char getSeat() {
        return Seat;
    }

    public char getType() {
        return Type;
    }

}
