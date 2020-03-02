package android.example.cs4518fp;

import java.util.ArrayList;

class ChordProgression {
    private final ArrayList<String> mChords;
    private final String mProgression;

    ChordProgression(ArrayList<String> chords) {
        if (chords.size() == 0) {
            mProgression = null;
            mChords = null;
            return;
        }

        mChords = chords;

        StringBuilder str = new StringBuilder();
        if (chords.size() > 1) {
            for (int i = 0; i < chords.size() - 1; i++) {
                str.append(chords.get(i));
                str.append(" - ");
            }
        }
        str.append(chords.get(chords.size() - 1));

        mProgression = str.toString();
    }

    ArrayList<String> getChords() {
        return mChords;
    }

    ChordProgression() {
        mProgression = null;
        mChords = null;
    }

    String getProgression() {
        return mProgression;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || mProgression == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof ChordProgression)) {
            return false;
        }

        boolean b = false;
        try {
            b = ((ChordProgression) o).getProgression().equals(mProgression);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return b;
    }
}
