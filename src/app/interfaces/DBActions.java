package app.interfaces;

import java.util.HashMap;
import java.util.Map;

public interface DBActions {
    public static Map<Integer, Object> fetch() {
        return new HashMap<>();
    }
    
    public static void insert() {}
    public static void update(int id, String col) {}
    public static void delete(int id) {}
}