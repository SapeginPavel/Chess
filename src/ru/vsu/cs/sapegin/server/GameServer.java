package ru.vsu.cs.sapegin.server;

import io.rsocket.Payload;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Mono;
import ru.vsu.cs.sapegin.*;
import ru.vsu.cs.sapegin.sdk.AdditionalCommands;
import ru.vsu.cs.sapegin.sdk.GameCommand;
import ru.vsu.cs.sapegin.sdk.ToStringUtils;

import java.io.IOException;
import java.util.List;

public class GameServer {

    private final int port;
    private Player player;
    private SimpleBot bot;
    private LocalGame game = null;
    private final ServerController serverController;

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(11111);
        server.start();
    }

    public GameServer(int port) {
        this.port = port;
        serverController = new ServerController();
    }

    public void start() {
        System.out.printf("Server started on: %d%n", port);
        RSocketServer.create(SocketAcceptor.forRequestResponse(this::requestHandler2)) //тута меняем на новое
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bindNow(TcpServerTransport.create(port)).onClose().block();
    }

    protected Mono<Payload> requestHandler2(Payload p) {
        String command = p.getDataUtf8();
        String[] commands = command.split(",");
        String response = "";
        if (commands[0].equals(GameCommand.START.getComm())) {
            startGame(commands[1]);
            response = getEnabledCellsAsStr();
        } else {
            if (commands[0].equals(GameCommand.DISPLAY_AVAILABLE_CELLS.getComm())) {
                response = getAvailableCellsAsStr(commands[1]);
            } else if (commands[0].equals(GameCommand.MOVE.getComm())) {
                Cell cellFrom = getCellFromStr(commands[1]);
                Cell cellTo = getCellFromStr(commands[2]);
                game.move(new Move(cellFrom, cellTo));
                Cell pawnReachedBorder = player.getPawnReachedBorder();
                if (pawnReachedBorder != null) {
                    response = GameCommand.UPDATE_PAWN.getComm() + "," + ToStringUtils.cellToString(pawnReachedBorder);
                } else {
                    response = further();
                }
            } else if (commands[0].equals(GameCommand.UPDATE_PAWN.getComm())) {
                updatePawn2(commands[1], commands[2]);
                response = further();
            } else if (commands[0].equals(GameCommand.MOVE_COMPLETED.getComm())) {
                GameState gameState = game.getGameStateOf();
                if (gameState == GameState.CHECKMATE ||
                    gameState == GameState.STALEMATE_FOR_BLACK ||
                    gameState == GameState.STALEMATE_FOR_WHITE) {
                    response = endGame();
                } else {
                    response = getEnabledCellsAsStr();
                }
            }
        }
        return Mono.just(DefaultPayload.create(response));
    }

    private String further() {
        String response;
        if (!hasBotMoved()) {
            response = endGame();
        } else {
            response = getMovesAsStr2();
        }
        return response;
    }

    private void startGame(String color) {
        Colors colorOfPlayer = color.equals(Colors.WHITE.getColorStr()) ? Colors.WHITE : Colors.BLACK;
        Colors colorOfBot = color.equals(Colors.WHITE.getColorStr()) ? Colors.BLACK : Colors.WHITE;
        player = new Player(colorOfPlayer);
        bot = new SimpleBot(colorOfBot);
        game = new LocalGame(player, bot, serverController);
        game.startGame();
    }

    private String endGame() {
        return GameCommand.END.getComm() + "," + game.getGameStateOf();
    }

    private Cell getCellFromStr(String cellAsStr) {
        return ToStringUtils.getCellFromString(game.getBoard(), cellAsStr);
    }

    private void updatePawn2(String cellStr, String charStr) {
        Cell cell = ToStringUtils.getCellFromString(game.getBoard(), cellStr);
        game.setPieceByChar(charStr.charAt(0), cell);
    }

    private String getEnabledCellsAsStr() {
        List<Cell> enable = GameLogic.getListOfCellsThatCanBeFrom(game.getBoard(), player);
        return ToStringUtils.convertListOfCellsToString(enable, AdditionalCommands.enabled.getComm());
    }

    private String getAvailableCellsAsStr(String cellFromAsStr) {
        Cell cellFrom = ToStringUtils.getCellFromString(game.getBoard(), cellFromAsStr);
        List<Cell> availableCells = GameLogic.getListOfCellsThatCanBeTo(game.getBoard(), cellFrom);
        return ToStringUtils.convertListOfCellsToString(availableCells, AdditionalCommands.available.getComm());
    }

    private boolean hasBotMoved() {
        int index = player.getColor().equals(Colors.WHITE) ? 1 : 0;
        Move move = serverController.getMove(index);
        return move.getCellFrom() != null && move.getCellTo() != null;
    }

    private String getMovesAsStr2() {
        int index = player.getColor().equals(Colors.WHITE) ? 1 : 0;
        Move move = serverController.popMove(index);
        char charOfPiece = move.getCellTo().getPiece().getCharNameOfPiece();
        return  GameCommand.MOVE.getComm() + "," +
                ToStringUtils.cellToString(move.getCellFrom()) + "," +
                ToStringUtils.cellToString(move.getCellTo()) + "," + charOfPiece;
    }
}
