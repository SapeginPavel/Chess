package ru.vsu.cs.sapegin;

public enum GameState {
    WHITE_TO_MOVE("Ход белых"),
    BLACK_TO_MOVE("Ход чёрных"),
    SHAH_FOR_WHITE("Шах белым"),
    SHAH_FOR_BLACK("Шах чёрным"),
    CHECKMATE("Патовая ситуация"), //пат
    STALEMATE_FOR_WHITE("Мат белым"), //мат
    STALEMATE_FOR_BLACK("Мат чёрным"); //Мат чёрным

    private String whatItMeans;

    GameState(String whatItMeans) {
        this.whatItMeans = whatItMeans;
    }

    public String getWhatItMeans() {
        return whatItMeans;
    }
}
