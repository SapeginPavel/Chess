package ru.vsu.cs.sapegin.graphic_interface;

import ru.vsu.cs.sapegin.Controller;

import javax.swing.*;
import java.awt.*;

public class BoardAsJPanel extends JPanel {

    private CellAsJButton[][] cellAsJButtons;
    private CellAsJButton currentCellAsJButton = null;
    Controller graphicInterfaceController;

    public BoardAsJPanel() {
        tunePanel();
//        setPreferredSize(getDimensionForBoard());
//        setMinimumSize(getDimensionForBoard());
//        setMaximumSize(getDimensionForBoard());
    }

    public void createBoardAsJPanel(Controller graphicInterfaceController) {
        this.graphicInterfaceController = graphicInterfaceController;
        cellAsJButtons = createCellsAsJButton();
        arrangeCellAsJButton();
    }

    private void tunePanel() {
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(9, 9));
//        this.setSize(getDimensionForBoard());
//        this.setMinimumSize(getDimensionForBoard());
        this.setMaximumSize(getDimensionForBoard());
        this.setPreferredSize(getDimensionForBoard());
    }

    public CellAsJButton getCurrentCellAsJButton() {
        return currentCellAsJButton;
    }

    public void setCurrentCellAsJButton(CellAsJButton currentCellAsJButton) {
        this.currentCellAsJButton = currentCellAsJButton;
        setDefaultCellStateForAllExceptCurrent();
    }

    //наверное, тут не надо перерисовывать, но попробую
    public void setDefaultCellStateForAllExceptCurrent() {
        for (int num = 1; num < 9; num++) {
            for (int let = 1; let < 9; let++) {
                CellAsJButton cell = getCellAsJPanelByCoordinates(let, num);
                if (!cell.equals(currentCellAsJButton)) {
                    if (cell.getCellState() != CellState.DEFAULT) {
                        cell.setCellState(CellState.DEFAULT);
//                        cell.revalidate();
//                        cell.repaint();
                    }
                }
            }
        }
    }

    private CellAsJButton[][] createCellsAsJButton() {
        CellAsJButton[][] cellAsJButtons = new CellAsJButton[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cellAsJButtons[i][j] = new CellAsJButton(this, j + 1, 8 - i, graphicInterfaceController);
            }
        }
        return cellAsJButtons;
    }

    public CellAsJButton[][] getCellAsJButton() {
        return cellAsJButtons;
    }

    private void arrangeCellAsJButton() { //arrange - расположить
        char let = 'a';
        int num = 8;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 0) {
                    if (j == 0) {
                        add(new JLabel(""));
                    } else {
                        JLabel label = new JLabel();
                        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                        label.setText(Character.toString(let));
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        label.setVerticalAlignment(SwingConstants.BOTTOM);
                        add(label);
                        let++;
                    }
                } else {
                    if (j == 0) {
                        JLabel label = new JLabel();
                        label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
                        label.setText((num--) + "  ");
                        label.setHorizontalAlignment(SwingConstants.RIGHT);
                        label.setVerticalAlignment(SwingConstants.CENTER);
                        label.setSize(new Dimension(10, 10));
                        add(label);
                    } else {
                        add(cellAsJButtons[i - 1][j - 1]);
                    }
                }
            }
        }
    }

    public void setEnabledCellAsJButton() {
        for (CellAsJButton[] cellAsJButton : cellAsJButtons) {
            for (int j = 0; j < cellAsJButtons.length; j++) {
                cellAsJButton[j].setEnabled(false);
            }
        }
    }

    public Dimension getDimensionForBoard() {
        int h = CellAsJButton.getDimensionOfCell().height;
        int w = CellAsJButton.getDimensionOfCell().width;
        return new Dimension(w * 9, h * 9);
    }

    public CellAsJButton getCellAsJPanelByCoordinates(int letter, int number) {
        return cellAsJButtons[8 - number][letter - 1];
    }
}
