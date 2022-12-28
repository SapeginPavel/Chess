package ru.vsu.cs.sapegin.chessFigures;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.Colors;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {
    public Pawn(Colors color, Cell cell) {
        super(color, cell);
        charNameOfPiece = 'P';
    }

    public boolean canMove(Cell cellTo) {
        if (color == Colors.WHITE) {
            if (cell.getLetter() != cellTo.getLetter()) {
                return cellTo.getNumber() - cell.getNumber() == 1 && Math.abs(cellTo.getLetter() - cell.getLetter()) == 1 && !cellTo.isEmpty() && cellTo.getColorOfPiece() == Colors.BLACK;
            } else if (cellTo.getNumber() - cell.getNumber() == 1 && cellTo.isEmpty()) {
                return true;
            } else return cell.getNumber() == 2 && cellTo.getNumber() - cell.getNumber() == 2 && cellTo.isEmpty();
        } else {
            if (cell.getLetter() != cellTo.getLetter()) {
                return cellTo.getNumber() - cell.getNumber() == -1 && Math.abs(cellTo.getLetter() - cell.getLetter()) == 1 && !cellTo.isEmpty() && cellTo.getColorOfPiece() == Colors.WHITE;
            } else if (cellTo.getNumber() - cell.getNumber() == -1 && cellTo.isEmpty()) {
                return true;
            } else return cell.getNumber() == 7 && cellTo.getNumber() - cell.getNumber() == -2 && cellTo.isEmpty();
        }
    }

    @Override
    public List<Cell> getAvailableCells(Board board) {
        List<Cell> availableCells = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Cell to = board.getCellByCoordinates(j, i);
                if (canMove(to)) {
                    availableCells.add(to);
                }
            }
        }
        return availableCells;
    }
}
