package ru.vsu.cs.sapegin.server;

import ru.vsu.cs.sapegin.*;
import ru.vsu.cs.sapegin.graphic_interface.CellAsJButton;
import ru.vsu.cs.sapegin.sdk.AbstractGame;
import ru.vsu.cs.sapegin.sdk.GameCommand;
import ru.vsu.cs.sapegin.sdk.ToStringUtils;

import java.io.IOException;
import java.util.List;

public class ServerController extends Controller {

    private Cell whitePawnReachedBorder = null;
    private Cell blackPawnReachedBorder = null;

    @Override
    public void displayBoard(Board board) {
        this.board = board;

    }

    @Override
    public void setEnabledCells(Board board, List<Cell> cellsForChoosing, boolean isEnabled) {

    }

    @Override
    public void changeEnabledCells(Board board, List<Cell> cells, boolean isEnabled) {

    }

    @Override
    public Cell getCell(Board board, List<Cell> cellsForChoosing) {
        return null;
    }

    @Override
    public Cell getCellFrom(Board board, List<Cell> cellsForChoosing) {
        return null;
    }

    @Override
    public Cell getCellTo(Board board, List<Cell> cellsForChoosing) {
        return null;
    }

    @Override
    public void choosePieceInsteadOfPawn(AbstractGame localGame, Colors color, Cell cell) {
    }

    @Override
    public void handleGameState(GameState gameState) {

    }

    @Override
    public void makeMove(AbstractGame localGame, Board board, Player player) {
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
