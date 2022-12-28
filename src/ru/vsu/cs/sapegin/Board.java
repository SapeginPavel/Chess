package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.chessFigures.*;

public class Board {
    Cell[][] cells = new Cell[8][8];
    King blackKing;
    King whiteKing;

    public Board() {
        int k = 0;
        for (int i = cells.length; i >= 1; i--) { //нумерация доски идёт снизу
            for (int j = 1; j <= cells.length; j++) {
                cells[k][j - 1] = new Cell(i, j);
            }
            k++;
        }
        setFiguresBeforeStart();
    }

    public Cell getCellByCoordinates(int letter, int number) {
        return cells[8 - number][letter - 1];
    }

    private void setFiguresBeforeStart() {
        new Rook(Colors.BLACK, cells[0][7]);
        new Knight(Colors.BLACK, cells[0][6]);
        new Bishop(Colors.BLACK, cells[0][5]);
        blackKing = new King(Colors.BLACK, cells[0][4]);
        new Queen(Colors.BLACK, cells[0][3]);
        new Bishop(Colors.BLACK, cells[0][2]);
        new Knight(Colors.BLACK, cells[0][1]);
        new Rook(Colors.BLACK, cells[0][0]);

        for (int i = 0; i < 8; i++) {
            new Pawn(Colors.BLACK, cells[1][i]);
        }

        new Pawn(Colors.WHITE, cells[1][1]); /////удалить

        for (int i = 0; i < 8; i++) {
            new Pawn(Colors.WHITE, cells[6][i]);
        }

        new Rook(Colors.WHITE, cells[7][7]);
        new Knight(Colors.WHITE, cells[7][6]);
        new Bishop(Colors.WHITE, cells[7][5]);
        whiteKing = new King(Colors.WHITE, cells[7][4]);
        new Queen(Colors.WHITE, cells[7][3]);
        new Bishop(Colors.WHITE, cells[7][2]);
        new Knight(Colors.WHITE, cells[7][1]);
        new Rook(Colors.WHITE, cells[7][0]);
    }
}
