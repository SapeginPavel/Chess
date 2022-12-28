package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.List;

public class King extends ChessPiece {
    public King(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'K';
    }

    //король не может подходить ещё и к другому королю!
    @Override
    public boolean canMove(Cell cellTo) {
        return (Math.abs(cell.getNumber() - cellTo.getNumber()) <= 1 && Math.abs(cell.getLetter() - cellTo.getLetter()) <= 1);
    }

    @Override
    public List<Cell> getAvailableCells(Board board) {
        return super.getAvailableCells(board);
    }
}
