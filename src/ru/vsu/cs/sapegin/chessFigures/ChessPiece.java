package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.ArrayList;
import java.util.List;

abstract public class ChessPiece {
    protected char charNameOfPiece;
    protected Colors color;
    protected Cell cell;

    public char getCharNameOfPiece() {
        return charNameOfPiece;
    }

    public char getCharNameOfColor() {
        return color == Colors.WHITE ? 'w' : 'b';
    }

    public Colors getColor() {
        return color;
    }

    public Cell getCell() {
        return cell;
    }

    public ChessPiece(Colors color, Cell cell) {
        this.color = color;
        this.cell = cell;
        cell.setPiece(this);
    }

    public boolean canMove(Cell cellTo) {
        return false;
    }

    public void moveTo(Cell cellTo) {
        cell.deletePiece(); //удаляем с предыдущей клетки фигуру
        cell = cellTo;
        cell.setPiece(this); //потому что фигура пришла на клетку и сказала ей, что пришла
    }

    public List<Cell> getAvailableCells(Board board) {
        List<Cell> availableCells = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Cell to = board.getCellByCoordinates(j, i);
                if (this.canMove(to)) { //to
                    availableCells.add(to);
                }
            }
        }
        return availableCells;
    }

    @Override
    public String toString() {
        return "ChessFigure{" +
                "color=" + color +
                '}';
    }
}
