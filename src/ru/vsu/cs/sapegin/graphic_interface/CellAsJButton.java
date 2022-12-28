package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class CellAsJButton extends JButton {
    private final int letter;
    private final int number;
    private CellState cellState = CellState.DEFAULT;

    private static final Dimension DIMENSION_OF_CELL = new Dimension(82, 82);

    public static Dimension getDimensionOfCell() {
        return DIMENSION_OF_CELL;
    }

    @Override
    public String toString() {
        return "CellAsJButton{" +
                "letter=" + letter +
                ", number=" + number +
                ", cellState=" + cellState +
                '}';
    }

    public CellAsJButton(BoardAsJPanel boardAsJPanel, int letter, int number, Controller controller) {
        this.letter = letter;
        this.number = number;

        this.setSize(DIMENSION_OF_CELL);
        this.setBackground(defineBackground());

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board board = controller.getBoard();
                if (getCellState() == CellState.DEFAULT) {
                    setCellState(CellState.IS_SELECTED);
                    boardAsJPanel.setCurrentCellAsJButton(getCell());
                    controller.displayAvailableCells(board.getCellByCoordinates(getCell().getLetter(), getCell().getNumber()));
                } else if (getCellState() == CellState.IS_SELECTED) {
                    setCellState(CellState.DEFAULT);
                    boardAsJPanel.setCurrentCellAsJButton(null);
                    controller.unDisplayAvailableCells(board.getCellByCoordinates(getCell().getLetter(), getCell().getNumber()));
                } else if (getCellState() == CellState.IS_AVAILABLE) {
                    controller.setWasChanged(List.of(boardAsJPanel.getCurrentCellAsJButton(), getCell()));
                    controller.getGame().move(
                        new Move(board.getCellByCoordinates(
                        boardAsJPanel.getCurrentCellAsJButton().getLetter(),
                        boardAsJPanel.getCurrentCellAsJButton().getNumber()),
                        board.getCellByCoordinates(getCell().getLetter(), getCell().getNumber())));
                    boardAsJPanel.setCurrentCellAsJButton(null);
                    boardAsJPanel.setDefaultCellStateForAllExceptCurrent();
                }
                updateBackground(); //эта клетка перерисовывается
                revalidate();
                repaint();
            }
        });
    }

    public CellAsJButton getCell() {
        return this;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
        updateBackground();
        revalidate();
        repaint();
    }

    private Color defineBackground() {
        if ((letter + number) % 2 == 0) {
            return (new Color(98, 58, 58));
        } else {
            return (Color.white);
        }
    }

    public void updateBackground() {
        Color c = cellState.getColor();
        if (c == Color.BLACK) {
            c = defineBackground();
        }
        setBackground(c);
    }

    public int getLetter() {
        return letter;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CellAsJButton)) return false;
        CellAsJButton that = (CellAsJButton) o;
        return letter == that.letter && number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, number);
    }
}
