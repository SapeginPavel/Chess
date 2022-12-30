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
    int session_id = -1;

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
        String finalCommand = session_id + "," + command;
        rSocket.requestResponse(DefaultPayload.create(finalCommand))
                .map(Payload::getDataUtf8)
                .doOnNext(s -> {
                    try {
                        handler(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).subscribe(); //подписываемся, а не блокируем
//                .block(); //говорит ждать
    }

    private void handler(String response) throws IOException {
        String[] responses = response.split(",");
        System.out.println(response);
        if (responses[1].equals(GameCommand.SET_SESSION_ID.getComm()) && session_id == -1) {
            session_id = Integer.parseInt(responses[0]);
            pusher(GameCommand.SUCCESS.getComm());
        } else if (Integer.parseInt(responses[0]) == session_id){
            if (responses[1].equals(AdditionalCommands.list.getComm())) {
                if (responses[2].equals(AdditionalCommands.enabled.getComm())) {
                    controller.setEnabledCells(board, ToStringUtils.getListOfCellsFromStr(board, responses[3]), true);
                } else if (responses[2].equals(AdditionalCommands.available.getComm())) {
                    controller.displayAvailableCells(ToStringUtils.getListOfCellsFromStr(board, responses[3]));
                }
            } else if (responses[1].equals(GameCommand.UPDATE_PAWN.getComm())) {
                Cell cell = ToStringUtils.getCellFromString(board, responses[2]);
                controller.choosePieceInsteadOfPawn(this, mainPlayer.getColor(), cell);
            } else if (responses[1].equals(GameCommand.MOVE.getComm())) {
                Cell cellFrom = getCellFromStr(responses[2]);
                Cell cellTo = getCellFromStr(responses[3]);
                cellFrom.getPiece().moveTo(cellTo);
                if (cellTo.getPiece().getCharNameOfPiece() != responses[4].charAt(0)) {
                    setPieceByCharOnly(responses[4].charAt(0), cellTo);
                }
                controller.setWasChanged(convertListOfCellsToListOfCellsAsJButton(List.of(cellFrom, cellTo)));
                controller.displayBoard(board);
                pusher(GameCommand.MOVE_COMPLETED.getComm());
            } else if (responses[1].equals(GameCommand.END.getComm())) {
                controller.displayBoard(board);
                controller.handleGameState(GameState.valueOf(responses[2]));
            }
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
