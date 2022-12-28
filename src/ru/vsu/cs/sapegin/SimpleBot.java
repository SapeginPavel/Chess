package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.sdk.AbstractGame;

import java.util.List;
import java.util.Random;

//Бот под графический интерфейс только
public class SimpleBot extends Player {
    public SimpleBot(Colors color) {
        super(color);
    }

    private Random random = new Random();

    @Override
    public void makeMove(AbstractGame game, Board board, Controller controller) {
        game.move(getRandomMove(board));
    }

    @Override
    public void choosePeaceInsteadOfPawn(AbstractGame game, Move moving, Controller controller) {
        game.setPieceByChar(getRandomPieceInsteadOfPawn(), moving.getCellTo());
        pawnReachedBorder = moving.getCellTo();
    }

    private Move getRandomMove(Board board) {
        List<Cell> listOfCellFrom = GameLogic.getListOfCellsThatCanBeFrom(board, this);
        Cell cellFrom = listOfCellFrom.get(random.nextInt(listOfCellFrom.size()));
        List<Cell> listOfCellTo = GameLogic.getListOfCellsThatCanBeTo(board, cellFrom);
        Cell cellTo = listOfCellTo.get(random.nextInt(listOfCellTo.size()));
        return new Move(cellFrom, cellTo);
    }

    private char getRandomPieceInsteadOfPawn() {
        char[] pieces = {'Q', 'R', 'B', 'k'};
        return pieces[random.nextInt(pieces.length)];
    }
}
