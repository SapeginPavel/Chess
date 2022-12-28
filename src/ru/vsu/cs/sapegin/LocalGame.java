package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.sdk.AbstractGame;

public class LocalGame extends AbstractGame {
    Player whitePlayer;
    Player blackPlayer;

    public Board getBoard() {
        return board;
    }

    public LocalGame(Player whitePlayer, Player blackPlayer, Controller controller) {
        super(controller);
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public void startGame() {
        board = new Board();
        mainPlayer = whitePlayer;
        play();
    }

    protected void play() {
        controller.displayBoard(board);
        GameState gameState = GameLogic.getGameState(board, mainPlayer); //////////// ТУТ
        controller.handleGameState(gameState);
        setGameStateOf(gameState);
        if (!canGameContinue(gameState)) {
            return;
        }
        mainPlayer.makeMove(this, board, controller);
    }

    public void move(Move moving) {
        System.out.println("Ход: " + moving.getCellFrom() + " to " + moving.getCellTo());
        moving.getCellFrom().getPiece().moveTo(moving.getCellTo());
        controller.saveTheMove(mainPlayer.getColor(), moving);
        if (GameLogic.isPawnReachedBorder(moving.getCellTo())) {
            mainPlayer.choosePeaceInsteadOfPawn(this, moving, controller);
        } else {
            continueGame();
        }
    }

    protected boolean canGameContinue(GameState gameState) {
        return gameState == GameState.WHITE_TO_MOVE || gameState == GameState.BLACK_TO_MOVE || gameState == GameState.SHAH_FOR_WHITE || gameState == GameState.SHAH_FOR_BLACK;
    }

    public void setPieceByChar(char piece, Cell cell) {
        super.setPieceByChar(piece, cell);
        continueGame();
    }

    public void continueGame() {
        mainPlayer = (mainPlayer == whitePlayer ? blackPlayer : whitePlayer);
        play();
    }
}
