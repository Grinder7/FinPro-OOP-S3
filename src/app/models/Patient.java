package app.models;

import java.util.HashMap;
import java.util.Map;

import app.interfaces.DBActions;

public class Patient extends Person implements DBActions {
    int handledByCaretakerId;

    public static Map<Integer, Object> fetch() {
        return new HashMap<>();
    }
}