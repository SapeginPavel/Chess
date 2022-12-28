package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.graphic_interface.CellAsJButton;
import ru.vsu.cs.sapegin.sdk.AbstractGame;

import java.util.List;

public class ConsoleController extends Controller {

    PrintConsole printConsole = new PrintConsole();

    @Override
    public Cell getCell(Board board, List<Cell> cellsForChoosing) {
        Cell cell = null;
        while (cell == null) {
            cell = getCellFromString(scanner.nextLine(), board);
        }
        while (!cellsForChoosing.contains(cell)) {
            cell = getCellFromString(scanner.nextLine(), board);
        }
        return cell;
    }

    private Cell getCellFromString(String str, Board board) {
        if (str.length() != 2 || str.charAt(0) < 97 || str.charAt(0) > 105 || str.charAt(1) < 49 || str.charAt(1) > 56) {
            return null;
        }
        int letter = str.charAt(0) - 96;
        int number = str.charAt(1) - 48;
        return board.getCellByCoordinates(letter, number);
    }

    @Override
    public Cell getCellFrom(Board board, List<Cell> cellsForChoosing) {
        System.out.println("Откуда = ");
        return getCell(board, cellsForChoosing);
    }

    @Override
    public Cell getCellTo(Board board, List<Cell> cellsForChoosing) {
        System.out.println("Куда = ");
        return getCell(board, cellsForChoosing);
    }

    @Override
    public void choosePieceInsteadOfPawn(AbstractGame localGame, Colors color, Cell cell) {
        while (true) { //cell.getPiece() instanceof Pawn
            System.out.println("Выберите фигуру (введите первую букву): ");
            char piece = scanner.nextLine().charAt(0);
            System.out.println();
            if (piece == 'B' || piece == 'K' || piece == 'Q' || piece == 'R') {
                localGame.setPieceByChar(piece, cell);
            }
        }
    }

    @Override
    public void displayBoard(Board board) {
        printConsole.printBoard(board);
    }

    @Override
    public void setEnabledCells(Board board, List<Cell> cellsForChoosing, boolean isEnabled) {

    }

    @Override
    public void changeEnabledCells(Board board, List<Cell> cells, boolean isEnabled) {

    }

    public void displayAvailableCells(Board board, List<Cell> availableCells) {
        printConsole.tickAvailableCellsOnBoard(board, availableCells);
    }

    @Override
    public void handleGameState(GameState gameState) {
        System.out.println(gameState.getWhatItMeans());
        System.out.println();
        System.out.println();
    }

    @Override
    public void makeMove(AbstractGame localGame, Board board, Player player) {
//        Cell cellFrom = player.chooseCellFrom(GameLogic.getListOfCellsThatCanBeFrom(board, player));
        Cell cellFrom = getCellFrom(board, GameLogic.getListOfCellsThatCanBeFrom(board, player));
        List<Cell> available = GameLogic.getAvailableCellsWithCheckForShah(board, cellFrom.getPiece());
        displayAvailableCells(board, available);

//        List<Cell> choose = GameLogic.getListOfCellsThatCanBeTo(board, cellFrom);
//        choose.add(cellFrom);
//        Cell cellTo = player.chooseCellTo(choose);
        Cell cellTo = getCellTo(board, GameLogic.getListOfCellsThatCanBeTo(board, cellFrom));
        if (cellFrom.equals(cellTo)) {
            displayBoard(board);
            makeMove(localGame, board, player);
        }
        localGame.move(new Move(cellFrom, cellTo));

    }

    @Override
    public void setWasChanged(List<CellAsJButton> list) {

    }

    @Override
    public void displayAvailableCells(Cell from) {

    }

    @Override
    public void unDisplayAvailableCells(Cell from) {

    }
}
