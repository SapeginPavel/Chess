package ru.vsu.cs.sapegin;

public enum Colors {
    WHITE("white"),
    BLACK("black");

    String colorStr;

    Colors(String colorStr) {
        this.colorStr = colorStr;
    }

    public String getColorStr() {
        return colorStr;
    }
}
