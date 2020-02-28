package android.example.cs4518fp;

import java.util.ArrayList;
import java.util.Iterator;

class Favorites {
    private static ArrayList<ChordProgression> favorites = new ArrayList<>();

    static void addFavorite(ChordProgression chordProgression) {
        boolean contained = false;
        for (ChordProgression cp : favorites) {
            if (cp.equals(chordProgression)) {
                contained = true;
            }
        }

        if (!contained) {
            favorites.add(chordProgression);
        }
    }

    static void removeFavorite(ChordProgression chordProgression) {
        Iterator<ChordProgression> iterator = favorites.iterator();
        while (iterator.hasNext()) {
            ChordProgression cp = iterator.next();
            if (cp.equals(chordProgression)) {
                iterator.remove();
            }
        }
    }

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
}
