package ru.vsu.cs.sapegin.client;

import ru.vsu.cs.sapegin.*;
import ru.vsu.cs.sapegin.graphic_interface.BoardAsJPanel;
import ru.vsu.cs.sapegin.graphic_interface.CellAsJButton;
import ru.vsu.cs.sapegin.sdk.AbstractGame;
import ru.vsu.cs.sapegin.sdk.AdditionalCommands;
import ru.vsu.cs.sapegin.sdk.GameCommand;
import ru.vsu.cs.sapegin.sdk.ToStringUtils;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkGame extends AbstractGame {

    private final RSocket rSocket;
    //todo: переписать контроллер (наследование)
    ClientController controller;

    public NetworkGame(Player mainPlayer, String server, int port, ClientController clientController) throws IOException {
        super(clientController);
        this.mainPlayer = mainPlayer;
        clientController.setNetworkGame(this);
        this.controller = clientController;
        rSocket = RSocketConnector.connectWith(TcpClientTransport.create(server, port)).block();
    }

    @Override
    public void startGame() throws IOException {
        board = new Board();
        play();
        controller.displayBoard(board);
    }

    @Override
    protected void play() {
        String commandToStart = GameCommand.START.getComm() + "," + mainPlayer.getColor().getColorStr();
        pusher(commandToStart);
    }

    protected void pusher(String command) {
        rSocket.requestResponse(DefaultPayload.create(command))
                .map(Payload::getDataUtf8)
                .doOnNext(s -> {
                    try {
                        handler2(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).subscribe(); //подписываемся, а не блокируем
//                .block(); //говорит ждать
    }

    private void handler2 (String response) throws IOException {
        String[] responses = response.split(",");
        if (responses[0].equals(AdditionalCommands.list.getComm())) {
            if (responses[1].equals(AdditionalCommands.enabled.getComm())) {
                controller.setEnabledCells(board, ToStringUtils.getListOfCellsFromStr(board, responses[2]), true);
            } else if (responses[1].equals(AdditionalCommands.available.getComm())) {
                controller.displayAvailableCells(ToStringUtils.getListOfCellsFromStr(board, responses[2]));
            }
        } else if (responses[0].equals(GameCommand.UPDATE_PAWN.getComm())) {
            Cell cell = ToStringUtils.getCellFromString(board, responses[1]);
            controller.choosePieceInsteadOfPawn(this, mainPlayer.getColor(), cell);
        } else if (responses[0].equals(GameCommand.MOVE.getComm())) {
            Cell cellFrom = getCellFromStr(responses[1]);
            Cell cellTo = getCellFromStr(responses[2]);
            cellFrom.getPiece().moveTo(cellTo);
            if (cellTo.getPiece().getCharNameOfPiece() != responses[3].charAt(0)) {
                setPieceByCharOnly(responses[3].charAt(0), cellTo);
//                controller.setWasChanged(convertListOfCellsToListOfCellsAsJButton(List.of(cellTo))); //возможно, лишнее
            }
            controller.setWasChanged(convertListOfCellsToListOfCellsAsJButton(List.of(cellFrom, cellTo)));
            controller.displayBoard(board);
            pusher(GameCommand.MOVE_COMPLETED.getComm());
        } else if (responses[0].equals(GameCommand.END.getComm())) {
            controller.displayBoard(board);
            controller.handleGameState(GameState.valueOf(responses[1]));
        }
    }

    private Cell getCellFromStr(String cell) {
        return ToStringUtils.getCellFromString(board, cell);
    }

    private List<CellAsJButton> convertListOfCellsToListOfCellsAsJButton(List<Cell> cells) {
        List<CellAsJButton> cellsAsJButton = new ArrayList<>();
        BoardAsJPanel b = controller.getGraphicApp().getBoardAsJPanel();
        for (Cell c : cells) {
            cellsAsJButton.add(b.getCellAsJPanelByCoordinates(c.getLetter(), c.getNumber()));
        }
        return cellsAsJButton;
    }

    @Override
    public void move(Move moving) {
        moving.getCellFrom().getPiece().moveTo(moving.getCellTo());
        String strMoving = GameCommand.MOVE.getComm() + "," + ToStringUtils.cellToString(moving.getCellFrom()) + "," + ToStringUtils.cellToString(moving.getCellTo());
        pusher(strMoving);
    }

    //todo: можно прописать как раз внутренний метод, который переопределять
    @Override
    public void setPieceByChar(char piece, Cell cell) {
        setPieceByCharOnly(piece, cell);
        controller.displayCellAsJButton(cell.getLetter(), cell.getNumber());
        pusher(GameCommand.UPDATE_PAWN.getComm() + "," + ToStringUtils.cellToString(cell) + "," + cell.getPiece().getCharNameOfPiece());
    }

    public void setPieceByCharOnly(char piece, Cell cell) {
        super.setPieceByChar(piece, cell);
    }

    @Override
    public void continueGame() {

    }
}
