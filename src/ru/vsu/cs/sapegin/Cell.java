package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.chessFigures.ChessPiece;
import ru.vsu.cs.sapegin.graphic_interface.CellState;

public class Cell {

    private ChessPiece piece;
    private final int number, letter;
    CellState cellState = CellState.DEFAULT;

    public Cell(int number, int letter) {
        this.number = number;
        this.letter = letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return number == cell.number && letter == cell.letter;
    }

    @Override
    public int hashCode() {
        return (number - 1) * 8 + letter - 1;
    }

    @Override
    public String toString() {
        return "" + (char) (letter+96) + number;
//        return "Cell{" +
//                "number=" + number +
//                ", letter=" + letter +
//                ", Figure=" + figure +
//                '}';
    }

    public int getNumber() {
        return number;
    }

    public int getLetter() {
        return letter;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece p) {
        piece = p;
    }

    public void deletePiece() {
        piece = null;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Colors getColorOfPiece() {
        return piece.getColor();
    }
}
