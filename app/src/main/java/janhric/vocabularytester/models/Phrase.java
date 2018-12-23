package janhric.vocabularytester.models;

import java.io.Serializable;

import janhric.vocabularytester.utility.PractiseConfig;

/**
 * Created by Honza on 12/21/2018.
 */

public class Phrase implements Serializable {
    // Labels table name
    public static final String TABLE = "phrase";
    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_CZECH = "czech";
    public static final String KEY_ENGLISH = "english";
    public static final String KEY_UNIT = "unit_id";

    private int id;
    private String czechPhrase;
    private String englishPhrase;
    private Unit unit;

    public Phrase(){}

    public Phrase(String czechPhrase, String englishPhrase, Unit unit) {
        this.czechPhrase = czechPhrase;
        this.englishPhrase = englishPhrase;
        this.unit = unit;
    }

    public Phrase(int id, String czechPhrase, String englishPhrase, Unit unit) {

        this.id = id;
        this.czechPhrase = czechPhrase;
        this.englishPhrase = englishPhrase;
        this.unit = unit;
    }

    public String getCzechPhrase() {
        return czechPhrase;
    }

    public void setCzechPhrase(String czechPhrase) {
        this.czechPhrase = czechPhrase;
    }

    public String getEnglishPhrase() {
        return englishPhrase;
    }

    public void setEnglishPhrase(String englishPhrase) {
        this.englishPhrase = englishPhrase;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public String getFromPhrase(PractiseConfig.Direction direction) {
        if (direction.equals(PractiseConfig.Direction.CZECH_TO_ENGLISH)) {
            return this.czechPhrase;
        } else {
            return this.englishPhrase;
        }
    }

    public String getToPhrase(PractiseConfig.Direction direction) {
        if (direction.equals(PractiseConfig.Direction.CZECH_TO_ENGLISH)) {
            return this.englishPhrase;
        } else {
            return this.czechPhrase;
        }
    }
}
