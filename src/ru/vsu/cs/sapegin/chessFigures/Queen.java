package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.List;

public class Queen extends ChessPiece {
    public Queen(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'Q';
    }

    @Override
    public boolean canMove(Cell cellTo) {
        if ((cell.getNumber() == cellTo.getNumber() || cell.getLetter() == cellTo.getLetter())) {
            return true;
        } //like rook

        if (Math.abs(cell.getNumber() - cellTo.getNumber()) == Math.abs(cell.getLetter() - cellTo.getLetter())) {
            return true;
        } //like bishop

        return false;
    }

    @Override
    public List<Cell> getAvailableCells(Board board) {
        return super.getAvailableCells(board);
    }
}
