package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.List;

public class Rook extends ChessPiece {
    public Rook(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'R';
    } //ладья

    @Override
    public boolean canMove(Cell cellTo) {
        return (cell.getNumber() == cellTo.getNumber() || cell.getLetter() == cellTo.getLetter());
    }

    @Override
    public List<Cell> getAvailableCells(Board board) {
        return super.getAvailableCells(board);
    }
}
