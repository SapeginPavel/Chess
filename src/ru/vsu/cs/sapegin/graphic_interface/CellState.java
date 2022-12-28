package ru.vsu.cs.sapegin.graphic_interface;

import java.awt.*;

public enum CellState {
    DEFAULT(Color.BLACK), //BLACK - метка дефолтного состояния
    IS_SELECTED(Color.CYAN),
    IS_AVAILABLE(Color.ORANGE);

    private Color color;

    CellState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
