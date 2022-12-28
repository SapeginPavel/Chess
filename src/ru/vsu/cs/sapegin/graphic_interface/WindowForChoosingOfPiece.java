package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.Colors;
import ru.vsu.cs.sapegin.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class WindowForChoosingOfPiece extends JWindow {
    private final JButton knightButton = new JButton();
    private final JButton bishopButton = new JButton();
    private final JButton rookButton = new JButton();
    private final JButton queenButton = new JButton();

    private final JFrame frameWithBoard;
    private Controller controller = null;

    private final JPanel mainPanel = new JPanel();

    public WindowForChoosingOfPiece(JFrame frameWithBoard) throws HeadlessException {
        this.frameWithBoard = frameWithBoard;
        int sizeOfWindow = 220;
        setSize(new Dimension(sizeOfWindow, sizeOfWindow));
        this.setLocation(frameWithBoard.getWidth() / 2 - sizeOfWindow / 2, frameWithBoard.getHeight() / 2 - sizeOfWindow / 2);
        mainPanel.setLayout(new GridLayout(2, 2));
        mainPanel.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(10, 10, 10, 10), "Выберите фигуру:", TitledBorder.CENTER, TitledBorder.TOP, getFont(), Color.BLACK));
        add(mainPanel);

        //todo: как-то переписать, чтобы чары передавались по-другому, более ООП, более официально
        knightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPieceAndCloseWindow('k');
            }
        });
        bishopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPieceAndCloseWindow('B'); //Bishop.getCharNameOfPiece()
            }
        });
        rookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPieceAndCloseWindow('R');
            }
        });
        queenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPieceAndCloseWindow('Q');
            }
        });
    }

    public void setPieceAndCloseWindow(char piece) {
        controller.getGame().setPieceByChar(piece, controller.getPawnCell());
        frameWithBoard.setEnabled(true);
        this.dispose();
    }

    public void setController(Controller graphicInterfaceController) {
        this.controller = graphicInterfaceController;
    }

    public void initElements(Colors color) {
        String colorPath = (color == Colors.WHITE ? "/white" : "/black");
        knightButton.add(new JLabel(new ImageIcon(Objects.requireNonNull(GraphicApp.class.getResource("images" + colorPath + "/Knight.png")))));
        bishopButton.add(new JLabel(new ImageIcon(Objects.requireNonNull(GraphicApp.class.getResource("images" + colorPath + "/Bishop.png")))));
        rookButton.add(new JLabel(new ImageIcon(Objects.requireNonNull(GraphicApp.class.getResource("images" + colorPath + "/Rook.png")))));
        queenButton.add(new JLabel(new ImageIcon(Objects.requireNonNull(GraphicApp.class.getResource("images" + colorPath + "/Queen.png")))));
        List<JButton> listOfButtons = List.of(knightButton, bishopButton, rookButton, queenButton);
        for (JButton b : listOfButtons) {
            b.setSize(new Dimension(80, 80));
            b.setBackground(Color.pink);
            mainPanel.add(b);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
        frameWithBoard.setEnabled(false);
    }
}
