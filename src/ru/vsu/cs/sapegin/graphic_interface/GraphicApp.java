package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.Colors;
import ru.vsu.cs.sapegin.LocalGame;
import ru.vsu.cs.sapegin.Player;
import ru.vsu.cs.sapegin.client.ClientController;
import ru.vsu.cs.sapegin.client.NetworkGame;
import ru.vsu.cs.sapegin.sdk.AbstractGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GraphicApp {
    private JFrame frame;
    private BoardAsJPanel boardAsJPanel;
    private final JLabel labelGameState = new JLabel();
    private final WindowForChoosingOfPiece windowForChoosingOfPiece;
    private final WindowEndOfGame windowEndOfGame;

    private final JButton buttonLocalGame = new JButton("Local");
    private final JButton buttonServerGame = new JButton("Server");

    public GraphicApp() throws HeadlessException {
        createWindow();
        initElements();
        windowForChoosingOfPiece = new WindowForChoosingOfPiece(frame);
        windowEndOfGame = new WindowEndOfGame(frame);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);

        buttonLocalGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                AbstractGame localGame = new LocalGame(new Player(Colors.WHITE), new Player(Colors.BLACK), new GraphicInterfaceController(new GraphicApp()));
                try {
                    localGame.startGame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonServerGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    AbstractGame networkGame = new NetworkGame(new Player(Colors.WHITE), "localhost", 11111, new ClientController(new GraphicApp()));
                    networkGame.startGame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public BoardAsJPanel getBoardAsJPanel() {
        return boardAsJPanel;
    }

    public WindowForChoosingOfPiece getWindowForChoosingOfPiece() {
        return windowForChoosingOfPiece;
    }

    public WindowEndOfGame getWindowEndOfGame() {
        return windowEndOfGame;
    }

    public void setTextOnLabelGameState(String whoseMove) {
        labelGameState.setText(whoseMove);
    }

    private void createWindow() {
        frame = new JFrame();
        Dimension displayDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(displayDimension);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initElements() {
        boardAsJPanel = new BoardAsJPanel();
        frame.setLayout(new FlowLayout());
        frame.add(boardAsJPanel);
        frame.add(buttonLocalGame);
        frame.add(buttonServerGame);
//        Box generalBox = new Box(BoxLayout.PAGE_AXIS);
//        generalBox.add(boardAsJPanel);
//        labelGameState.setFont(new Font("Times New Roman", Font.PLAIN, 30));
//        generalBox.add(labelGameState);
//        frame.add(generalBox);
    }
}
