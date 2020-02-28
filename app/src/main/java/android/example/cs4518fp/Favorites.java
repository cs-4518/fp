package android.example.cs4518fp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import static android.content.Context.MODE_PRIVATE;

class Favorites {
    private static final String FILE_NAME = "favorites.txt";
    private static ArrayList<ChordProgression> favorites = new ArrayList<>();

    static ArrayList<ChordProgression> getFavorites() {
        return favorites;
    }

    static boolean contains(ChordProgression chordProgression) {
        for (ChordProgression cp : favorites) {
            if (cp.equals(chordProgression)) {
                return true;
            }
        }

        return false;
    }

    private static String condenseChordProgression(ChordProgression chord) {
        StringBuilder fin = new StringBuilder();
        ArrayList<String> chords = chord.getChords();

        if (chords == null || chord.getProgression() == null)
            return "";

        if (chords.size() == 0) {
            return "";
        } else if (chords.size() == 1) {
            fin.append(chords.get(0));
            fin.append(';');
        }

        for (int j = 0; j < chords.size() - 1; j++) {
            fin.append(chords.get(j));
            fin.append(',');
        }
        fin.append(chords.get(chords.size() - 1));
        fin.append(';');

        return fin.toString();
    }

    static void addFavorite(ChordProgression chordProgression, Context context) {
        if (chordProgression.getProgression() == null || chordProgression.getChords() == null)
            return;

        boolean contained = false;
        for (ChordProgression cp : favorites) {
            if (cp.equals(chordProgression)) {
                contained = true;
            }
        }

        if (!contained) {
            favorites.add(chordProgression);
        }
        updateFile(context);
    }

    static void removeFavorite(ChordProgression chordProgression, Context context) {
        Iterator<ChordProgression> iterator = favorites.iterator();
        while (iterator.hasNext()) {
            ChordProgression cp = iterator.next();
            if (cp.equals(chordProgression)) {
                iterator.remove();
            }
        }
        updateFile(context);
    }

    private static void updateFile(Context context) {
        StringBuilder fin = new StringBuilder();
        for (int i = 0; i < favorites.size(); i++) {
            ChordProgression cp = favorites.get(i);
            if (cp.getChords() != null && cp.getProgression() != null) {
                fin.append(condenseChordProgression(cp));
            }
        }
        String contents = fin.toString();

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(contents.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void loadFavoritesFromFile(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            parseFavorites(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void parseFavorites(String input) {
        ArrayList<ChordProgression> newFavorites = new ArrayList<>();
        StringTokenizer bigTokens = new StringTokenizer(input, ";");

        while (bigTokens.hasMoreTokens()) {
            StringTokenizer smallTokens = new StringTokenizer(bigTokens.nextToken(), ",");
            ArrayList<String> chords = new ArrayList<>();
            while (smallTokens.hasMoreTokens()) {
                chords.add(smallTokens.nextToken());
            }

            boolean qualityControl = true;
            for (String str : chords) {
                if (str.trim().equals("")) {
                    qualityControl = false;
                }
            }

            if (qualityControl) {
                newFavorites.add(new ChordProgression(chords));
            }
        }

        favorites = newFavorites;
    }
}
