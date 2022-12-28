package ru.vsu.cs.sapegin;

import ru.vsu.cs.sapegin.graphic_interface.GraphicApp;
import ru.vsu.cs.sapegin.graphic_interface.GraphicInterfaceController;
import ru.vsu.cs.sapegin.sdk.AbstractGame;

import java.io.IOException;

public class Program {

    public static void main(String[] args) throws IOException {

//        Game game = new Game(new ConsoleController());
//        game.startGame();

        AbstractGame localGame = new LocalGame(new Player(Colors.WHITE), new Player(Colors.BLACK), new GraphicInterfaceController(new GraphicApp()));
        localGame.startGame();
    }
}
