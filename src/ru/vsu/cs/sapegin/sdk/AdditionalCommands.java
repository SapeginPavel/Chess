package ru.vsu.cs.sapegin.sdk;

public enum AdditionalCommands {
    list("list"),
    available("available"),
    enabled("enabled");

    private String comm;

    public String getComm() {
        return comm;
    }

    AdditionalCommands(String comm) {
        this.comm = comm;
    }
}
