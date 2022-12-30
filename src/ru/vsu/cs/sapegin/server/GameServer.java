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
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private final int port;
    List<ClientSession> sessions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer(11111);
        server.start();
    }

    public GameServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.printf("Server started on: %d%n", port);
        RSocketServer.create(SocketAcceptor.forRequestResponse(this::requestHandler)) //тута меняем на новое
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .bindNow(TcpServerTransport.create(port)).onClose().block();
    }

    protected Mono<Payload> requestHandler(Payload p) {
        String command = p.getDataUtf8();
        String[] commands = command.split(",");
        System.out.println("Получаем: " + command);
        int session_id = Integer.parseInt(commands[0]);
        if (session_id > -1 && session_id < sessions.size()) {
            System.out.println("заходим");
            return sessions.get(session_id).requestHandler(p);
        } else {
            ClientSession cls = new ClientSession(sessions.size(), commands[2]);
            sessions.add(cls);
            cls.run();
            return cls.requestHandler(p);
        }
    }
}
