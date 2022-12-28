package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.chessFigures.*;

import java.util.List;

public class PrintConsole {
    private final char[][] boardLikeCharArray = new char[9][33];

    public void printBoard(Board board) {
        char[][] boardLikeCharArray = createCharBoard(board);
        printCharBoard(boardLikeCharArray);
    }

    public void tickAvailableCellsOnBoard(Board board, List<Cell> available) {
        char[][] boardLikeCharArray = createCharBoard(board);
        tickAvailable(available, boardLikeCharArray);
        printCharBoard(boardLikeCharArray);
    }

    private char[][] createCharBoard(Board board) {
        char[][] boardLikeCharArray = new char[9][33];
        for (int i = boardLikeCharArray.length - 1; i > 0; i--) {
            for (int j = 0; j < boardLikeCharArray[0].length; j++) {
                if (j % 4 == 1) {
                    boardLikeCharArray[i][j] = '[';
                } else if (j % 4 == 2) {
                    ChessPiece piece = board.getCellByCoordinates((j-1) / 4 + 1, 9 - i).getPiece();
                    if (piece == null) {
                        boardLikeCharArray[i][j] = ' ';
                    } else {
                        boardLikeCharArray[i][j] = piece.getCharNameOfColor();
                    }
                } else if (j % 4 == 3) {
                    if (boardLikeCharArray[i][j - 1] != ' ') { //то есть если там записан цвет фигуры
                        ChessPiece piece = board.getCellByCoordinates((j-1) / 4 + 1, 9 - i).getPiece();
                        boardLikeCharArray[i][j] = piece.getCharNameOfPiece();
                    } else {
                        boardLikeCharArray[i][j] = ' ';
                    }
                } else {
                    boardLikeCharArray[i][j] = ']';
                }
            }
        }
        //Цифры и буквы слева и сверху над самим полем (координаты)
        boardLikeCharArray[0][0] = ' ';
        for (int i = 1; i < boardLikeCharArray[0].length; i++) {
            if ((i - 1) % 4 == 1) {
                boardLikeCharArray[0][i] = (char) (97 + i / 4);
            } else {
                boardLikeCharArray[0][i] = ' ';
            }
        }
        for (int i = 1; i < boardLikeCharArray.length; i++) {
            boardLikeCharArray[9-i][0] = (char) (48 + i);
        }
        return boardLikeCharArray;
    }

    private void tickAvailable(List<Cell> available, char[][] boardLikeCharArray) {
        if (available == null) {
            return;
        }
        for (Cell cell : available) {
            boardLikeCharArray[9 - cell.getNumber()][2 + (cell.getLetter() - 1) * 4] = '*';
        }
    }

    private void printCharBoard(char[][] boardLikeCharArray) {
        System.out.println();
        for (int i = 0; i < boardLikeCharArray.length; i++) {
            for (int j = 0; j < boardLikeCharArray[0].length; j++) {
                System.out.print(boardLikeCharArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
