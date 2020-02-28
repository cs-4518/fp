package android.example.cs4518fp;

import java.util.ArrayList;

class ChordProgression {
    private final String mProgression;

    ChordProgression(ArrayList<String> chords) {
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

    ChordProgression() {
        mProgression = null;
    }

    String getProgression() {
        return mProgression;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof ChordProgression)) {
            return false;
        }

        return ((ChordProgression) o).getProgression().equals(mProgression);
    }
}
