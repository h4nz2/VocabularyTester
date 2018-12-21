package janhric.vocabularytester.models;

/**
 * Created by Honza on 12/21/2018.
 */

public class Unit {
    public static final String TABLE = "unit";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public Unit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
