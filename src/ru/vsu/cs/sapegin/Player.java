package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.sdk.AbstractGame;

public class Player {
    private final Colors color;
    protected Cell pawnReachedBorder = null;

    public Player(Colors color) { //игрок определённого цвета за определённой игрой
        this.color = color;
    }

    public Colors getColor() {
        return color;
    }

    public Cell getPawnReachedBorder() {
        Cell c = pawnReachedBorder;
        pawnReachedBorder = null;
        return c;
    }

    public void makeMove(AbstractGame game, Board board, Controller controller) {
        controller.makeMove(game, board, this);
    }

    public void choosePeaceInsteadOfPawn(AbstractGame game, Move moving, Controller controller) {
        controller.choosePieceInsteadOfPawn(game, getColor(), moving.getCellTo());
        pawnReachedBorder = moving.getCellTo();
    }
}
