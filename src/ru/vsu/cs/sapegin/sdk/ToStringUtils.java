package ru.vsu.cs.sapegin.sdk;

import ru.vsu.cs.sapegin.Board;
import ru.vsu.cs.sapegin.Cell;

import java.util.ArrayList;
import java.util.List;

public class ToStringUtils {
    public static String convertListOfCellsToString(List<Cell> cells, String state) {
        StringBuilder sb = new StringBuilder();
        sb.append(AdditionalCommands.list.getComm());
        sb.append(",");
        sb.append(state);
        sb.append(",");
        for (Cell c : cells) {
            sb.append(c.getLetter());
            sb.append(c.getNumber());
            sb.append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static List<Cell> getListOfCellsFromStr(Board board, String strOfCells) {
        String[] splitted = strOfCells.split(";");
        List<Cell> cells = new ArrayList<>();
        for (String s : splitted) {
            int let = Integer.parseInt(s.substring(0,1));
            int num = Integer.parseInt(s.substring(1,2));
            cells.add(board.getCellByCoordinates(let, num));
        }
        return cells;
    }

    public static String cellToString(Cell cell) {
        String res;
        if (cell == null) {
            res = "null";
        } else {
            res = cell.getLetter() + "" + cell.getNumber();
        }
        return res;
    }

    public static Cell getCellFromString(Board board, String str) {
        int let = Integer.parseInt(str.substring(0,1));
        int num = Integer.parseInt(str.substring(1,2));
        return board.getCellByCoordinates(let, num);
    }
}
