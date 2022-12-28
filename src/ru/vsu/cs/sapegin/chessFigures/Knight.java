package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'k';
    }

    public char getChar() {
        return charNameOfPiece;
    }

    @Override
    public boolean canMove(Cell cellTo) {
        return (Math.abs(cell.getNumber() - cellTo.getNumber()) == 2 && Math.abs(cell.getLetter() - cellTo.getLetter()) == 1)
                || (Math.abs(cell.getLetter() - cellTo.getLetter()) == 2 && Math.abs(cell.getNumber() - cellTo.getNumber()) == 1);
    }


    @Override
    public List<Cell> getAvailableCells(Board board) {
        return super.getAvailableCells(board);
    }
}
