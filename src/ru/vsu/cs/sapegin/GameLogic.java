package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.chessFigures.ChessPiece;
import ru.vsu.cs.sapegin.chessFigures.Knight;
import ru.vsu.cs.sapegin.chessFigures.Pawn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameLogic {

    private static boolean isPathClear(Board board, Cell cellFrom, Cell cellTo) {
        int sign = 1;
        if (cellFrom.getNumber() == cellTo.getNumber()) {
            if (cellFrom.getLetter() < cellTo.getLetter()) {
                sign = -1;
            }
            for (int i = 1; i < Math.abs(cellFrom.getLetter() - cellTo.getLetter()); i++) {
                if (!board.getCellByCoordinates(cellFrom.getLetter() - i * sign, cellFrom.getNumber()).isEmpty()) {
                    return false;
                }
            }
            return true;
        } else if (cellFrom.getLetter() == cellTo.getLetter()) {
            if (cellFrom.getNumber() < cellTo.getNumber()) {
                sign = -1;
            }
            for (int i = 1; i < Math.abs(cellFrom.getNumber() - cellTo.getNumber()); i++) {
                if (!board.getCellByCoordinates(cellFrom.getLetter(), cellFrom.getNumber() - i * sign).isEmpty()) {
                    return false;
                }
            }
            return true;
        } else if (Math.abs(cellFrom.getNumber() - cellTo.getNumber()) == Math.abs(cellFrom.getLetter() - cellTo.getLetter())) {
            int signOfNums, signOfLetters;
            signOfNums = cellTo.getNumber() < cellFrom.getNumber() ? 1 : -1;
            signOfLetters = cellTo.getLetter() < cellFrom.getLetter() ? 1 : -1;
            for (int i = 1; i < Math.abs(cellFrom.getNumber() - cellTo.getNumber()); i++) {
                if (!board.getCellByCoordinates(cellTo.getLetter() + i * signOfLetters, cellTo.getNumber() + i * signOfNums).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean canMoveOnBoard(Board board, ChessPiece piece, Cell cellTo) {
        if (!cellTo.isEmpty()) {
            if (piece.getColor() == cellTo.getColorOfPiece() || piece.getCell().equals(cellTo)) {
                return false;
            }
        }
        if (piece.canMove(cellTo)) {
            if (piece instanceof Knight || isPathClear(board, piece.getCell(), cellTo)) {
                return !willBeShahAfterThisMove(board, piece, cellTo);
            }
        }
        return false;
    }

    /* Доступные клетки с учётом возможного шаха */

    public static List<Cell> getAvailableCellsWithCheckForShah(Board board, ChessPiece piece) {
        List<Cell> availableCells = getPotentialAvailableCells(board, piece);
        if (availableCells == null) { //переписать
            return null;
        }
        List<Cell> resAvailable = new ArrayList<>();
        for (Cell cellTo : availableCells) {
            if (!willBeShahAfterThisMove(board, piece, cellTo)) {
                resAvailable.add(cellTo);
            }
        }
        return resAvailable;
    }

    /* Без проверки на шах */

    public static List<Cell> getPotentialAvailableCells(Board board, ChessPiece piece) {
//        if (piece == null) {
//            return null;
//        }
        List<Cell> available = piece.getAvailableCells(board); //список вообще доступных для фигуры клеток
        if (available == null) {
            return null;
        }
        List<Cell> resAvailable = new ArrayList<>();
        for (Cell cellTo : available) {
            if (cellTo.isEmpty() || cellTo.getPiece().getColor() != piece.getColor()) { //клетка, куда идём, пустая или с фигурой другого цвета
                if (piece instanceof Knight || isPathClear(board, piece.getCell(), cellTo)) { //путь чистый
                    resAvailable.add(cellTo);
                }
            }
        }
        return resAvailable;
    }

    /* Проверка на шах при таком ходе */

    private static boolean willBeShahAfterThisMove(Board board, ChessPiece piece, Cell cellTo) {
        boolean isShah;
        ChessPiece tempPiece = cellTo.getPiece();
        Cell cellFrom = piece.getCell();
        piece.moveTo(cellTo);
        //если будет шах своему королю при таком ходе
        isShah = isShah(board, piece.getColor());
        piece.moveTo(cellFrom);
        cellTo.setPiece(tempPiece);
        return isShah;
    }

    private static Set<Cell> getAttackedCellsOfThisColor(Board board, Colors pieceColor) {
        Set<Cell> attackedCells = new HashSet<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece piece = board.getCellByCoordinates(j, i).getPiece();
                if (piece != null && piece.getColor() != pieceColor) {
                    List<Cell> attacked = getPotentialAvailableCells(board, piece);
                    if (attacked != null) {
                        if (piece instanceof Pawn) {
                            //удалить все c, для которых верно "f.getCell().getLetter() == c.getLetter()"
                            attacked.removeIf(c -> piece.getCell().getLetter() == c.getLetter());
                        }
                        attackedCells.addAll(attacked);
                    }
                }
            }
        }
        return attackedCells;
    }

    public static boolean isShah(Board board, Colors pieceColor) {
        if (pieceColor == Colors.WHITE) {
            return getAttackedCellsOfThisColor(board, pieceColor).contains(board.whiteKing.getCell());
        } else {
            return getAttackedCellsOfThisColor(board, pieceColor).contains(board.blackKing.getCell());
        }
    }

    public static boolean noneCanMove(Board board, Colors pieceColor) {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) { //если хотя бы для одной фигуры есть ход, то не мат
                Cell current = board.getCellByCoordinates(j, i);
                if (!current.isEmpty() && current.getPiece().getColor() == pieceColor && !getAvailableCellsWithCheckForShah(board, current.getPiece()).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean canIsItCellFrom(Board board, Cell cellFrom, Player player) {
        if (cellFrom.isEmpty()) {
            return false;
        }
        List<Cell> available = getAvailableCellsWithCheckForShah(board, cellFrom.getPiece());
        return !cellFrom.isEmpty() && cellFrom.getColorOfPiece() == player.getColor() && available != null && !available.isEmpty();
    }

    public static List<Cell> getListOfCellsThatCanBeFrom(Board board, Player player) {
        List<Cell> cellsThatCanBeFrom = new ArrayList<>();
        for (int num = 1; num < 9; num++) {
            for (int let = 1; let < 9; let++) {
                Cell cellFrom = board.getCellByCoordinates(let, num);
                if (canIsItCellFrom(board, cellFrom, player)) {
                    cellsThatCanBeFrom.add(cellFrom);
                }
            }
        }
        return cellsThatCanBeFrom;
    }

    public static List<Cell> getListOfCellsThatCanBeTo(Board board, Cell cellFrom) {
        List<Cell> cellsThatCanBeTo = new ArrayList<>();
        for (int num = 1; num < 9; num++) {
            for (int let = 1; let < 9; let++) {
                Cell cellTo = board.getCellByCoordinates(let, num);
                if (canMoveOnBoard(board, cellFrom.getPiece(), cellTo)) {
                    cellsThatCanBeTo.add(cellTo);
                }
            }
        }
        return cellsThatCanBeTo;
    }

    public static GameState getGameState(Board board, Player player) {
        if (isShah(board, player.getColor())) {
            if (noneCanMove(board, player.getColor())) { //если мат белым
                return player.getColor() == Colors.WHITE ? GameState.STALEMATE_FOR_WHITE : GameState.STALEMATE_FOR_BLACK;
            }
            return player.getColor() == Colors.WHITE ? GameState.SHAH_FOR_WHITE : GameState.SHAH_FOR_BLACK;
        } else if (noneCanMove(board, player.getColor())) { //если не шах, но ходить никто не может
            return GameState.CHECKMATE;
        }
        return player.getColor() == Colors.WHITE ? GameState.WHITE_TO_MOVE : GameState.BLACK_TO_MOVE;
    }

    public static boolean isPawnReachedBorder(Cell cell) {
        if (cell == null) {
            return false;
        }
        return cell.getPiece() instanceof Pawn && (cell.getNumber() == 1 || cell.getNumber() == 8);
    }
}
