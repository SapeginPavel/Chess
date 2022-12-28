package ru.vsu.cs.sapegin.sdk;

import ru.vsu.cs.sapegin.*;
import ru.vsu.cs.sapegin.chessFigures.*;

import java.io.IOException;

abstract public class AbstractGame {
    protected Board board;
    protected Player whitePlayer;
    protected Player blackPlayer;
    protected Player mainPlayer;
    protected final Controller controller;
    protected GameState gameStateOf;

    public AbstractGame(Controller controller) {
        this.controller = controller;
    }

    public GameState getGameStateOf() {
        return gameStateOf;
    }

    public void setGameStateOf(GameState gameStateOf) {
        this.gameStateOf = gameStateOf;
    }

    abstract public void startGame() throws IOException;

    abstract protected void play() throws IOException;

    abstract public void move(Move moving);

    protected boolean canGameContinue(GameState gameState) {
        return gameState == GameState.WHITE_TO_MOVE || gameState == GameState.BLACK_TO_MOVE || gameState == GameState.SHAH_FOR_WHITE || gameState == GameState.SHAH_FOR_BLACK;
    }

    public void setPieceByChar(char piece, Cell cell) {
        Colors color = cell.getPiece().getColor();
        if (piece == 'B') {
            new Bishop(color, cell);
        }
        if (piece == 'k') {
            new Knight(color, cell);
        }
        if (piece == 'Q') {
            new Queen(color, cell);
        }
        if (piece == 'R') {
            new Rook(color, cell);
        }
        if (piece == 'K') {
            new King(color, cell);
        }
        if (piece == 'P') {
            new Pawn(color, cell);
        }
    }

    abstract public void continueGame();
}
