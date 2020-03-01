package android.example.cs4518fp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChordProgressionActivity extends AppCompatActivity {

    private RecyclerView mChordRecycler;
    private RecyclerView mScalesRecycler;
    private ArrayList<ChordProgression> mChords = null;
    private ArrayList<Scale> mScales = null;

    private final String[] chosenNotes = new String[3];
    private boolean help_visible = false;
    private String[][] allScales;

    private ConstraintLayout mHelpLayout;
    private TextView mHelpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord_progression);

        makeScales();

        mChordRecycler = findViewById(R.id.chordRecycler);
        mScalesRecycler = findViewById(R.id.scaleRecycler);

        mHelpLayout = findViewById(R.id.helpLayout);
        mHelpText = findViewById(R.id.helpText);
        mHelpText.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();

        TextView mStorageText = findViewById(R.id.storageText);

        chosenNotes[0] = "";
        chosenNotes[1] = "";
        chosenNotes[2] = "";

        if (extras != null) {
            chosenNotes[0] = extras.getString("note1");
            chosenNotes[1] = extras.getString("note2");
            chosenNotes[2] = extras.getString("note3");

            mStorageText.setText(String.format("%s, %s, %s", chosenNotes[0], chosenNotes[1], chosenNotes[2]));

            if (chosenNotes[2] == null) {
                chosenNotes[2] = "";
                mStorageText.setText(String.format("%s, %s", chosenNotes[0], chosenNotes[1]));
            }
            if (chosenNotes[1] == null) {
                chosenNotes[1] = "";
                mStorageText.setText(chosenNotes[0]);
            }
            if (chosenNotes[0] == null) {
                chosenNotes[0] = "";
                mStorageText.setText("");
            }

        }

        findChords(chosenNotes);

        initializeRecyclerViews();

        Favorites.loadFavoritesFromFile(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (help_visible) {
            toggleHelp(null);
        }

        initializeRecyclerViews();
    }

    private static ArrayList<ChordProgression> removeDuplicates(ArrayList<ChordProgression> arr) {
        ArrayList<ChordProgression> fin = new ArrayList<>();
        for (ChordProgression cp : arr) {
            boolean contained = false;
            for (ChordProgression c : fin) {
                if (c.equals(cp)) {
                    contained = true;
                }
            }

            if (!contained) {
                fin.add(cp);
            }
        }

        return fin;
    }

    private void initializeRecyclerViews() {
        mChordRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mChordsLayoutManager = new LinearLayoutManager(this);
        mChordRecycler.setLayoutManager(mChordsLayoutManager);
        RecyclerView.Adapter mChordsAdapter;

        if (mChords != null) {
            mChords = removeDuplicates(mChords);

            mChordsAdapter = new ChordProgressionAdapter(mChords, this);
            mChordsAdapter.notifyDataSetChanged();
        } else {
            mChordsAdapter = new ChordProgressionAdapter(this, getString(R.string.chord_prog_default));
            mChordsAdapter.notifyDataSetChanged();
        }
        mChordRecycler.setAdapter(mChordsAdapter);


        mScalesRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mScalesLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mScalesAdapter;

        if (mScales != null)
            mScalesAdapter = new ScaleAdapter(mScales);
        else
            mScalesAdapter = new ScaleAdapter();

        mScalesRecycler.setLayoutManager(mScalesLayoutManager);
        mScalesRecycler.setAdapter(mScalesAdapter);
    }

    private void findChords(String[] note) {
        ArrayList<ChordProgression> chords = new ArrayList<>();
        ArrayList<Scale> scales = new ArrayList<>();

        boolean check1, check2, check3;
        int I = 0;
        int IV = 3;
        int V = 4;
        int VI = 5;
        for (int i = 0; i < 15; i++) {
            check1 = false;
            check2 = false;
            check3 = false;

            for (int j = 0; j < 7; j++) {
                if ((note[0]).contains("/")) {
                    if (note[0].contains("A♭") && allScales[i][j].contains("A♭")) {
                        I = 0;
                        IV = 3;
                        V = 4;
                        VI = 5;
                        check1 = true;
                    } else if (note[0].contains("B♭") && allScales[i][j].contains("B♭")) {
                        I = 1;
                        IV = 4;
                        V = 5;
                        VI = 6;
                        check1 = true;
                    } else if (note[0].contains("C♯") && allScales[i][j].contains("C♯")) {
                        I = 2;
                        IV = 5;
                        V = 6;
                        VI = 0;
                        check1 = true;
                    } else if (note[0].contains("D♯") && allScales[i][j].contains("D♯")) {
                        I = 3;
                        IV = 6;
                        V = 0;
                        VI = 1;
                        check1 = true;
                    } else if (note[0].contains("E♭") && allScales[i][j].contains("E♭")) {
                        I = 4;
                        IV = 0;
                        V = 1;
                        VI = 2;
                        check1 = true;
                    } else if (note[0].contains("F♯") && allScales[i][j].contains("F♯")) {
                        I = 5;
                        IV = 1;
                        V = 2;
                        VI = 3;
                        check1 = true;
                    } else if (note[0].contains("G♯") && allScales[i][j].contains("G♯")) {
                        I = 6;
                        IV = 2;
                        V = 3;
                        VI = 4;
                        check1 = true;
                    }
                } else if (note[0].equals(allScales[i][j])) {
                    if (note[0].contains("A")) {
                        I = 0;
                        IV = 3;
                        V = 4;
                        VI = 5;
                    } else if (note[0].contains("B")) {
                        I = 1;
                        IV = 4;
                        V = 5;
                        VI = 6;
                    } else if (note[0].contains("C")) {
                        I = 2;
                        IV = 5;
                        V = 6;
                        VI = 0;
                    } else if (note[0].contains("D")) {
                        I = 3;
                        IV = 6;
                        V = 0;
                        VI = 1;
                    } else if (note[0].contains("E")) {
                        I = 4;
                        IV = 0;
                        V = 1;
                        VI = 2;
                    } else if (note[0].contains("F")) {
                        I = 5;
                        IV = 1;
                        V = 2;
                        VI = 3;
                    } else {
                        I = 6;
                        IV = 2;
                        V = 3;
                        VI = 4;

                    }
                    check1 = true;

                }

                if ((note[1]).contains("/")) {
                    if (note[1].contains("A♭") && allScales[i][j].contains("A♭")) {
                        check2 = true;
                    } else if (note[1].contains("B♭") && allScales[i][j].contains("B♭")) {
                        check2 = true;
                    } else if (note[1].contains("C♯") && allScales[i][j].contains("C♯")) {
                        check2 = true;
                    } else if (note[1].contains("D♯") && allScales[i][j].contains("D♯")) {
                        check2 = true;
                    } else if (note[1].contains("E♭") && allScales[i][j].contains("E♭")) {
                        check2 = true;
                    } else if (note[1].contains("F♯") && allScales[i][j].contains("F♯")) {
                        check2 = true;
                    } else if (note[1].contains("G♯") && allScales[i][j].contains("G♯")) {
                        check2 = true;
                    }
                } else if (!note[1].equals("") && note[1].equals(allScales[i][j])) {
                    check2 = true;
                }

                if ((note[2]).contains("/")) {
                    if (note[2].contains("A♭") && allScales[i][j].contains("A♭")) {
                        check3 = true;
                    } else if (note[2].contains("B♭") && allScales[i][j].contains("B♭")) {
                        check3 = true;
                    } else if (note[2].contains("C♯") && allScales[i][j].contains("C♯")) {
                        check3 = true;
                    } else if (note[2].contains("D♯") && allScales[i][j].contains("D♯")) {
                        check3 = true;
                    } else if (note[2].contains("E♭") && allScales[i][j].contains("E♭")) {
                        check3 = true;
                    } else if (note[2].contains("F♯") && allScales[i][j].contains("F♯")) {
                        check3 = true;
                    } else if (note[2].contains("G♯") && allScales[i][j].contains("G♯")) {
                        check3 = true;
                    }
                } else if (!note[2].equals("") && note[2].equals(allScales[i][j])) {
                    check3 = true;
                }

            }
            if (!note[1].equals("") && note[2].equals("") & check1 && check2) {
                scales.add(new Scale(allScales[i][7]));

                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][V], allScales[i][VI] + "m", allScales[i][IV]))));
                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][IV], allScales[i][V]))));
            } else if (!note[1].equals("") && !note[2].equals("") && check1 && check2 && check3) {
                scales.add(new Scale(allScales[i][7]));

                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][V], allScales[i][VI] + "m", allScales[i][IV]))));
                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][IV], allScales[i][V]))));
            } else if (note[1].equals("") && note[2].equals("") && check1) {
                scales.add(new Scale(allScales[i][7]));

                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][V], allScales[i][VI] + "m", allScales[i][IV]))));

                chords.add(new ChordProgression(new ArrayList<>(
                        Arrays.asList(allScales[i][I], allScales[i][IV], allScales[i][V]))));
            }
        }
        note[0] = "";
        note[1] = "";
        note[2] = "";
        if (chords.size() > 0)
            mChords = chords;
        if (scales.size() > 0)
            mScales = scales;
    }

    public void toggleHelp(View view) {
        TextView storageText = findViewById(R.id.storageText);
        TextView mNotesTitle = findViewById(R.id.notes);

        if (help_visible) {
            help_visible = false;
            mHelpLayout.setVisibility(View.INVISIBLE);
            storageText.setVisibility(View.VISIBLE);
            mNotesTitle.setVisibility(View.VISIBLE);

        } else {
            help_visible = true;
            mHelpText.scrollTo(0, 0);
            mHelpLayout.setVisibility(View.VISIBLE);
            storageText.setVisibility(View.INVISIBLE);
            mNotesTitle.setVisibility(View.INVISIBLE);

        }
    }

    private void makeScales() {
        String[] cMajor = new String[]{"A", "B", "C", "D", "E", "F", "G", "C Major"};
        String[] gMajor = new String[]{"A", "B", "C", "D", "E", "F♯", "G", "G Major"};
        String[] dMajor = new String[]{"A", "B", "C♯", "D", "E", "F♯", "G", "D Major"};
        String[] aMajor = new String[]{"A", "B", "C♯", "D", "E", "F♯", "G♯", "A Major"};
        String[] eMajor = new String[]{"A", "B", "C♯", "D♯", "E", "F♯", "G♯", "E Major"};
        String[] bMajor = new String[]{"A♯", "B", "C♯", "D♯", "E", "F♯", "G♯", "B Major"};
        String[] fSharpMajor = new String[]{"A♯", "B", "C♯", "D♯", "E♯", "F♯", "G♯", "F Sharp Major"};
        String[] cSharpMajor = new String[]{"A♯", "B♯", "C♯", "D♯", "E♯", "F♯", "G♯", "C Sharp Major"};

        String[] fMajor = new String[]{"A", "B♭", "C", "D", "E", "F", "G", "F Major"};
        String[] bFlatMajor = new String[]{"A", "B♭", "C", "D", "E♭", "F", "G", "B Flat Major"};
        String[] eFlatMajor = new String[]{"A♭", "B♭", "C", "D", "E♭", "F", "G", "E Flat Major"};
        String[] aFlatMajor = new String[]{"A♭", "B♭", "C", "D♭", "E♭", "F", "G", "A Flat Major"};
        String[] dFlatMajor = new String[]{"A♭", "B♭", "C", "D♭", "E♭", "F", "G♭", "D Flat Major"};
        String[] gFlatMajor = new String[]{"A♭", "B♭", "C♭", "D♭", "E♭", "F", "G♭", "G Flat Major"};
        String[] cFlatMajor = new String[]{"A♭", "B♭", "C♭", "D♭", "E♭", "F♭", "G♭", "C Flat Major"};

        allScales = new String[][]{cMajor, gMajor, dMajor, aMajor, eMajor,
                bMajor, fSharpMajor, cSharpMajor, fMajor, bFlatMajor, eFlatMajor, aFlatMajor,
                dFlatMajor, gFlatMajor, cFlatMajor};
    }

    public void goToFavorites(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);

        startActivity(intent);
    }
}
