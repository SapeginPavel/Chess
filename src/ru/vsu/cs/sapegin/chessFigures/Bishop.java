package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.List;

public class Bishop extends ChessPiece {
    private static char charNameOfPieceTest;
    //Потому что если сделать static в главном классе (chessPiece), то там все фигуры становятся ладьями

    public Bishop(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'B';
        charNameOfPieceTest = charNameOfPiece;
    }

    public static char getCharNameOfPieceTest() {
        return charNameOfPieceTest;
    }

    @Override
    public boolean canMove(Cell cellTo) {
        return Math.abs(cell.getNumber() - cellTo.getNumber()) == Math.abs(cell.getLetter() - cellTo.getLetter());
    }

    @Override
    public List<Cell> getAvailableCells(Board board) {
        return super.getAvailableCells(board);
    }
}
