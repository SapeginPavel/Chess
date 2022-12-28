package ru.vsu.cs.sapegin.client;

import ru.vsu.cs.sapegin.Cell;
import ru.vsu.cs.sapegin.graphic_interface.GraphicApp;
import ru.vsu.cs.sapegin.graphic_interface.GraphicInterfaceController;
import ru.vsu.cs.sapegin.sdk.GameCommand;
import ru.vsu.cs.sapegin.sdk.ToStringUtils;

public class ClientController extends GraphicInterfaceController {

    NetworkGame networkGame;

    public ClientController(GraphicApp graphicApp) {
        super(graphicApp);
    }

    public NetworkGame getGame() {
        return networkGame;
    }

    public void setNetworkGame(NetworkGame networkGame) {
        this.networkGame = networkGame;
    }

    @Override
    public void displayAvailableCells(Cell from) {
        String request = GameCommand.DISPLAY_AVAILABLE_CELLS.getComm() + "," + ToStringUtils.cellToString(from);
        networkGame.pusher(request);
    }
}
