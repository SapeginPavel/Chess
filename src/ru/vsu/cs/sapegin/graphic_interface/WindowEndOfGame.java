package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.sdk.AbstractGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowEndOfGame extends JWindow {

    private final JPanel mainPanel = new JPanel();
    private String endGameStatus = "Выберите действие:";

    public WindowEndOfGame(JFrame frameWithBoard) {
        //        setSize(new Dimension(400, 80));
        Dimension dimensionOfWindow = new Dimension(400, 80);
        setSize(dimensionOfWindow);
        this.setLocation(frameWithBoard.getWidth() / 2 - dimensionOfWindow.width / 2, frameWithBoard.getHeight() / 2 - dimensionOfWindow.height / 2);
        mainPanel.setLayout(new GridLayout(1,2));

        JButton exitButton = new JButton("Выйти");
        exitButton.setBackground(Color.pink);
        mainPanel.add(exitButton);
        JButton continueGameButton = new JButton("Продолжить");
        continueGameButton.setBackground(Color.pink);
        mainPanel.add(continueGameButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameWithBoard.dispose();
                getWindow().dispose();
            }
        });
        continueGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                frameWithBoard.dispose();
                getWindow().dispose();
                //todo: новая игра
//                AbstractGame localGame = new AbstractGame(new GraphicInterfaceController(new GraphicApp()));
//                localGame.startGame();
//                getWindow().dispose();
            }
        });
    }

    public void setEndGameStatus(String endGameStatus) {
        this.endGameStatus = endGameStatus;
        mainPanel.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10, 10, 10, 10), endGameStatus, TitledBorder.CENTER, TitledBorder.TOP, new Font("Times New Roman", Font.PLAIN, 18), Color.BLACK));
        add(mainPanel);
        revalidate();
        repaint();
    }

    public JWindow getWindow() {
        return this;
    }
}
