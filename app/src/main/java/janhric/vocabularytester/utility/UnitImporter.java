package janhric.vocabularytester.utility;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import janhric.vocabularytester.models.Phrase;
import janhric.vocabularytester.models.Unit;

/**
 * Created by Honza on 12/23/2018.
 */

public class UnitImporter {

    public int importPhrases(Uri uri, Context context, Unit unit) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream, "ISO-8859-2"));
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            Log.i("read line", line);
            String[] phrases = line.split(";", 2);
            if (phrases.length == 2) {
                Phrase newPhrase = new Phrase(phrases[1], phrases[0], unit);
                PhraseCRUD phraseCRUD = new PhraseCRUD(context);
                phraseCRUD.savePhrase(newPhrase);
                count++;
            }
        }
        inputStream.close();

        return count;
    }

    public int importAllUnits(Uri uri, Context context) throws IOException{
        return -1;
    }
}
