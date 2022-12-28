package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.graphic_interface.CellAsJButton;
import ru.vsu.cs.sapegin.graphic_interface.CellState;
import ru.vsu.cs.sapegin.sdk.AbstractGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract public class Controller {
    protected Scanner scanner = new Scanner(System.in); //public - private

    protected AbstractGame abstractGame = null;
    protected Cell pawnCell = null;
    protected Board board = null;
    protected Move[] moves = {new Move(null, null), new Move(null, null)}; //может, проще

    protected final List<CellAsJButton> wasChanged = new ArrayList<>();

    public Board getBoard() {
        return board;
    }

    public Cell getPawnCell() {
        return pawnCell;
    }

    public AbstractGame getGame() {
        return abstractGame;
    }

    public void displayBoard(Board board) {
        this.board = board;
    }

    abstract public void setEnabledCells(Board board, List<Cell> cellsForChoosing, boolean isEnabled);

    abstract public void changeEnabledCells(Board board, List<Cell> cells, boolean isEnabled);

    public void displayAvailableCells(Board board, List<Cell> availableCells) { //как-то неприятно болтается метод
    }

    abstract public Cell getCell(Board board, List<Cell> cellsForChoosing);

    abstract public Cell getCellFrom(Board board, List<Cell> cellsForChoosing);

    abstract public Cell getCellTo(Board board, List<Cell> cellsForChoosing);

    abstract public void choosePieceInsteadOfPawn(AbstractGame localGame, Colors color, Cell cell);

    abstract public void handleGameState(GameState gameState);

    public void makeMove(AbstractGame abstractGame, Board board, Player player) {
        this.board = board;
        this.abstractGame = abstractGame;
        setEnabledCells(board, GameLogic.getListOfCellsThatCanBeFrom(board, player), true);
    }

    abstract public void setWasChanged(List<CellAsJButton> list);

    abstract public void displayAvailableCells(Cell from);

    abstract public void unDisplayAvailableCells(Cell from);

    public void saveTheMove(Colors color, Move move) {
        if (color == Colors.WHITE) {
            moves[0] = move;
        } else {
            moves[1] = move;
        }
    }

    public Move popMove(int index) {
        Move testMove = new Move(moves[index].getCellFrom(), moves[index].getCellTo());
        moves[index] = new Move(null, null);
        return testMove;
    }

    public Move getMove(int index) {
        return moves[index];
    }
}
