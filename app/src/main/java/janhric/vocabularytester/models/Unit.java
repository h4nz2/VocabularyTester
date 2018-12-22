package janhric.vocabularytester.models;

import java.io.Serializable;
import java.util.List;

import janhric.vocabularytester.utility.PhraseCRUD;

/**
 * Created by Honza on 12/21/2018.
 */

public class Unit implements Serializable{
    public static final int MODE_MANAGE = 1;
    public static final int MODE_PRACTISE = 2;

    public static final String TABLE = "unit";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    public Unit() {}

    public Unit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private int id;
    private String name;
    private List<Phrase> phrases;

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
