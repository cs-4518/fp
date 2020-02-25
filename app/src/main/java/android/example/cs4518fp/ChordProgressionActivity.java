package android.example.cs4518fp;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ChordProgressionActivity extends AppCompatActivity {

    private final String[] note = new String[3];
    private boolean help_visible = false;
    private String[][] allScales;

    private TextView mChordText;
    private TextView mScalesText;
    private ConstraintLayout mHelpLayout;
    private TextView mHelpText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord_progression);

        makeScales();

        mChordText = findViewById(R.id.chords);
        mScalesText = findViewById(R.id.scales);

        mHelpLayout = findViewById(R.id.helpLayout);
        mHelpText = findViewById(R.id.helpText);
        mHelpText.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();

        TextView mStorageText = findViewById(R.id.storageText);

        note[0] = "";
        note[1] = "";
        note[2] = "";

        if (extras != null) {
            note[0] = extras.getString("note1");
            note[1] = extras.getString("note2");
            note[2] = extras.getString("note3");

            mStorageText.setText(String.format("%s, %s, %s", note[0], note[1], note[2]));
        }
        findChords(note);
    }

    private void findChords(String[] note) {
        boolean check1, check2, check3;
        int I = 0;
        int II = 1;
        int III = 2;
        int IV = 3;
        int V = 4;
        int VI = 5;
        int VII = 6;
        mChordText.setText(getResources().getString(R.string.chord_prog_default));
        mScalesText.setText(getResources().getString(R.string.scales_default));
        for (int i = 0; i < 15; i++) {
            check1 = false;
            check2 = false;
            check3 = false;

            for (int j = 0; j < 7; j++) {
                if ((note[0]).contains("/")) {
                    if (note[0].contains("A♭") && allScales[i][j].contains("A♭")) {
                        I = 0;
                        II = 1;
                        III = 2;
                        IV = 3;
                        V = 4;
                        VI = 5;
                        VII = 6;
                        check1 = true;
                    } else if (note[0].contains("B♭") && allScales[i][j].contains("B♭")) {
                        I = 1;
                        II = 2;
                        III = 3;
                        IV = 4;
                        V = 5;
                        VI = 6;
                        VII = 0;
                        check1 = true;
                    } else if (note[0].contains("C♯") && allScales[i][j].contains("C♯")) {
                        I = 2;
                        II = 3;
                        III = 4;
                        IV = 5;
                        V = 6;
                        VI = 0;
                        VII = 1;
                        check1 = true;
                    } else if (note[0].contains("D♯") && allScales[i][j].contains("D♯")) {
                        I = 3;
                        II = 4;
                        III = 5;
                        IV = 6;
                        V = 0;
                        VI = 1;
                        VII = 2;
                        check1 = true;
                    } else if (note[0].contains("E♭") && allScales[i][j].contains("E♭")) {
                        I = 4;
                        II = 5;
                        III = 6;
                        IV = 0;
                        V = 1;
                        VI = 2;
                        VII = 3;
                        check1 = true;
                    } else if (note[0].contains("F♯") && allScales[i][j].contains("F♯")) {
                        I = 5;
                        II = 6;
                        III = 0;
                        IV = 1;
                        V = 2;
                        VI = 3;
                        VII = 4;
                        check1 = true;
                    } else if (note[0].contains("G♯") && allScales[i][j].contains("G♯")) {
                        I = 6;
                        II = 0;
                        III = 1;
                        IV = 2;
                        V = 3;
                        VI = 4;
                        VII = 5;
                        check1 = true;
                    }
                } else if (note[0].equals(allScales[i][j])) {
                    if (note[0].contains("A")) {
                        I = 0;
                        II = 1;
                        III = 2;
                        IV = 3;
                        V = 4;
                        VI = 5;
                        VII = 6;
                    } else if (note[0].contains("B")) {
                        I = 1;
                        II = 2;
                        III = 3;
                        IV = 4;
                        V = 5;
                        VI = 6;
                        VII = 0;
                    } else if (note[0].contains("C")) {
                        I = 2;
                        II = 3;
                        III = 4;
                        IV = 5;
                        V = 6;
                        VI = 0;
                        VII = 1;
                    } else if (note[0].contains("D")) {
                        I = 3;
                        II = 4;
                        III = 5;
                        IV = 6;
                        V = 0;
                        VI = 1;
                        VII = 2;
                    } else if (note[0].contains("E")) {
                        I = 4;
                        II = 5;
                        III = 6;
                        IV = 0;
                        V = 1;
                        VI = 2;
                        VII = 3;
                    } else if (note[0].contains("F")) {
                        I = 5;
                        II = 6;
                        III = 0;
                        IV = 1;
                        V = 2;
                        VI = 3;
                        VII = 4;
                    } else {
                        I = 6;
                        II = 0;
                        III = 1;
                        IV = 2;
                        V = 3;
                        VI = 4;
                        VII = 5;

                    }
                    check1 = true;

                }

                if ((note[1]).contains("/") && !note[1].equals("")) {
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

                if ((note[2]).contains("/") && !note[2].equals("")) {
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
                Log.d("testLoop", "testLoop");
                /*mScalesText.append(allScales[i][7] + " (" + allScales[i][0] + " - " + allScales[i][1] + " - "
                        + allScales[i][2] + " - " + allScales[i][3] + " - " + allScales[i][4]
                        + " - " + allScales[i][5] + " - " + allScales[i][6] + ")\n");*/
                mChordText.setText("");
                mScalesText.setText("");
                mScalesText.append(allScales[i][7] + "\n");
                mChordText.append(allScales[i][I] + " - " + allScales[i][V] + " - " + allScales[i][VI] + "m -" + allScales[i][IV] + "\n");
                mChordText.append(allScales[i][I] + " - " + allScales[i][IV] + " - " + allScales[i][V] + "\n");
            } else if (!note[1].equals("") && !note[2].equals("") && check1 && check2 && check3) {
                mChordText.setText("");
                mScalesText.setText("");
                mScalesText.append(allScales[i][7] + "\n");

                mChordText.append(allScales[i][I] + " - " + allScales[i][V] + " - " + allScales[i][VI] + "m -" + allScales[i][IV] + "\n");
                mChordText.append(allScales[i][I] + " - " + allScales[i][IV] + " - " + allScales[i][V] + "\n");
            } else if (note[1].equals("") && note[2].equals("") && check1) {
                mChordText.setText("");
                mScalesText.setText("");
                mScalesText.append(allScales[i][7] + "\n");

                mChordText.append(allScales[i][I] + " - " + allScales[i][V] + " - " + allScales[i][VI] + "m -" + allScales[i][IV] + "\n");
                mChordText.append(allScales[i][I] + " - " + allScales[i][IV] + " - " + allScales[i][V] + "\n");
            }


        }
        note[0] = "";
        note[1] = "";
        note[2] = "";
    }

    public void toggleHelp(View view) {
        if (help_visible) {
            help_visible = false;
            mHelpLayout.setVisibility(View.INVISIBLE);
        } else {
            help_visible = true;
            mHelpText.scrollTo(0, 0);
            mHelpLayout.setVisibility(View.VISIBLE);
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
}
