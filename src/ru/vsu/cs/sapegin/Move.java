package ru.vsu.cs.sapegin;

public class Move {
    private Cell cellFrom;
    private Cell cellTo;

    public Move(Cell cellFrom, Cell cellTo) {
        this.cellFrom = cellFrom;
        this.cellTo = cellTo;
    }

    public Cell getCellFrom() {
        return cellFrom;
    }

    public Cell getCellTo() {
        return cellTo;
    }
}
