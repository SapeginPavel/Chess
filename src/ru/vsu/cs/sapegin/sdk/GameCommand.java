package ru.vsu.cs.sapegin.sdk;

public enum GameCommand {

    START("start"),
    END("end"),

    MOVE("move"),
    UPDATE_PAWN("update_pawn"),
    MOVE_COMPLETED("move_completed"),
    DISPLAY_AVAILABLE_CELLS("display_available_cells"),
    SET_SESSION_ID("set_session_id"),
    SUCCESS("success");

    private String comm;

    public String getComm() {
        return comm;
    }

    GameCommand(String comm) {
        this.comm = comm;
    }
}
