package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.*;
import ru.vsu.cs.sapegin.chessFigures.Bishop;
import ru.vsu.cs.sapegin.chessFigures.ChessPiece;
import ru.vsu.cs.sapegin.sdk.AbstractGame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GraphicInterfaceController extends Controller {

    protected final GraphicApp graphicApp;
    protected Cell pawnCell = null;
    private final List<CellAsJButton> wasChanged = new ArrayList<>();

    public GraphicInterfaceController(GraphicApp graphicApp) {
        this.graphicApp = graphicApp;
        this.graphicApp.getBoardAsJPanel().createBoardAsJPanel(this);
        this.graphicApp.getWindowForChoosingOfPiece().setController(this);
        this.graphicApp.getFrame().revalidate();
        this.graphicApp.getFrame().repaint();
    }

    public Board getBoard() {
        return board;
    }

    public AbstractGame getGame() {
        return abstractGame;
    }

    public Cell getPawnCell() {
        return pawnCell;
    }

    public GraphicApp getGraphicApp() {
        return graphicApp;
    }

    @Override
    public void displayBoard(Board board) {
        this.board = board; //может, переместить куда-то глубже?
        CellAsJButton[][] cellAsJButtons = graphicApp.getBoardAsJPanel().getCellAsJButton();
        if (wasChanged.isEmpty()) {
            for (int num = 1; num <= cellAsJButtons.length; num++) {
                for (int let = 1; let <= cellAsJButtons.length; let++) {
                    displayCellAsJButton(let, num);
                }
            }
            this.graphicApp.getFrame().revalidate();
            this.graphicApp.getFrame().repaint();
        } else {
            for (CellAsJButton c : wasChanged) {
                displayCellAsJButton(c.getLetter(), c.getNumber());
            }
            wasChanged.clear();
        }
    }

    public void displayCellAsJButton(int let, int num) {
        ChessPiece piece = board.getCellByCoordinates(let, num).getPiece();
        graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).removeAll();
        if (piece != null) {
            String path = "";
            String colorPath = (piece.getCharNameOfColor() == 'w' ? "/white" : "/black");
            if (piece.getCharNameOfPiece() == 'P') {
                path = "/Pawn.png";
            }
            if (piece.getCharNameOfPiece() == 'R') {
                path = "/Rook.png";
            }
            if (piece.getCharNameOfPiece() == 'k') {
                path = "/Knight.png";
            }
            if (piece.getCharNameOfPiece() == 'K') {
                path = "/King.png";
            }
            if (piece.getCharNameOfPiece() == Bishop.getCharNameOfPieceTest()) {
                path = "/Bishop.png";
            }
            if (piece.getCharNameOfPiece() == 'Q') {
                path = "/Queen.png";
            }
            JLabel imgLabel = new JLabel(new ImageIcon(Objects.requireNonNull(GraphicApp.class.getResource("images" + colorPath + path))));
            graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).add(imgLabel);
        }
        graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).revalidate();
        graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).repaint();
    }

    public void setWasChanged(List<CellAsJButton> list) {
        wasChanged.addAll(list);
    }

    public void displayAvailableCells(Cell from) {
        List<Cell> availableCells = GameLogic.getListOfCellsThatCanBeTo(board, from);
        displayAvailableCells(availableCells);
    }

    public void displayAvailableCells(List<Cell> availableCells) {
        for (Cell cell : availableCells) {
            graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(cell.getLetter(), cell.getNumber()).setCellState(CellState.IS_AVAILABLE);
        }
        for (Cell c : availableCells) {
            graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(c.getLetter(), c.getNumber()).setCellState(CellState.IS_AVAILABLE);
        }
        changeEnabledCells(board, availableCells, true);
    }

    @Override
    public void unDisplayAvailableCells(Cell from) {
        List<Cell> availableCells = GameLogic.getListOfCellsThatCanBeTo(board, from);
        changeEnabledCells(board, availableCells, false);
        for (Cell c : availableCells) {
            graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(c.getLetter(), c.getNumber()).setCellState(CellState.DEFAULT);
        }
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

    public void setEnabledCells(Board board, List<Cell> cellsForChoosing, boolean isEnabled) {
        for (int num = 1; num < 9; num++) {
            for (int let = 1; let < 9; let++) {
                Cell cell = board.getCellByCoordinates(let, num);
                if (!cellsForChoosing.contains(cell)) {
                    graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).setEnabled(!isEnabled); //false
                } else {
                    graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).setEnabled(isEnabled); //true
                }
            }
        }
    }
    
    public void changeEnabledCells(Board board, List<Cell> cells, boolean isEnabled) {
        for (int num = 1; num < 9; num++) {
            for (int let = 1; let < 9; let++) {
                Cell cell = board.getCellByCoordinates(let, num);
                if (cells.contains(cell)) {
                    graphicApp.getBoardAsJPanel().getCellAsJPanelByCoordinates(let, num).setEnabled(isEnabled); //false
                }
            }
        }
    }

    @Override
    public void choosePieceInsteadOfPawn(AbstractGame localGame, Colors color, Cell cell) {
        graphicApp.getWindowForChoosingOfPiece().setVisible(true);
        graphicApp.getWindowForChoosingOfPiece().initElements(color);
        pawnCell = cell;
    }

    @Override
    public void handleGameState(GameState gameState) {
        if (gameState == GameState.CHECKMATE || gameState == GameState.STALEMATE_FOR_WHITE || gameState == GameState.STALEMATE_FOR_BLACK) {
            graphicApp.getBoardAsJPanel().setEnabledCellAsJButton();
            graphicApp.getWindowEndOfGame().setEndGameStatus(gameState.getWhatItMeans());
            graphicApp.getWindowEndOfGame().setVisible(true);
        }
    }

    @Override
    public void makeMove(AbstractGame localGame, Board board, Player player) {
        this.board = board;
        this.abstractGame = localGame;
        setEnabledCells(board, GameLogic.getListOfCellsThatCanBeFrom(board, player), true);
    }
}
