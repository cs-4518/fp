package android.example.cs4518fp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ChordProgressionActivity extends AppCompatActivity {
    String[] cMajor;
    String[] fMajor = new String[8];
    String[] dMajor = new String[8];
    String[] aMajor = new String[8];
    String[] bMajor = new String[8];
    String[] gMajor = new String[8];
    String[] eMajor = new String[8];

    String[] bFlatMajor = new String[8];
    String[] eFlatMajor = new String[8];
    String[] aFlatMajor = new String[8];
    String[] cFlatMajor = new String[8];
    String[] gFlatMajor = new String[8];
    String[] dFlatMajor = new String[8];

    String[] fSharpMajor = new String[8];
    String[] cSharpMajor = new String[8];
    String[][] allScales;

    String[] note = new String[3];

    TextView tvChords;
    TextView tvScales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chord_progression);
        cMajor = new String[]{"A", "B", "C", "D", "E", "F", "G", "C Major"};
        gMajor = new String[]{"A", "B", "C", "D", "E", "F♯", "G", "G Major"};
        dMajor = new String[]{"A", "B", "C♯", "D", "E", "F♯", "G", "D Major"};
        aMajor = new String[]{"A", "B", "C♯", "D", "E", "F♯", "G♯", "A Major"};
        eMajor = new String[]{"A", "B", "C♯", "D♯", "E", "F♯", "G♯", "E Major"};
        bMajor = new String[]{"A♯", "B", "C♯", "D♯", "E", "F♯", "G♯", "B Major"};
        fSharpMajor = new String[]{"A♯", "B", "C♯", "D♯", "E♯", "F♯", "G♯", "F Sharp Major"};
        cSharpMajor = new String[]{"A♯", "B♯", "C♯", "D♯", "E♯", "F♯", "G♯", "C Sharp Major"};


        fMajor = new String[]{"A", "B♭", "C", "D", "E", "F", "G", "F Major"};
        bFlatMajor = new String[]{"A", "B♭", "C", "D", "E♭", "F", "G", "B Flat Major"};
        eFlatMajor = new String[]{"A♭", "B♭", "C", "D", "E♭", "F", "G", "E Flat Major"};
        aFlatMajor = new String[]{"A♭", "B♭", "C", "D♭", "E♭", "F", "G", "A Flat Major"};
        dFlatMajor = new String[]{"A♭", "B♭", "C", "D♭", "E♭", "F", "G♭", "D Flat Major"};
        gFlatMajor = new String[]{"A♭", "B♭", "C♭", "D♭", "E♭", "F", "G♭", "G Flat Major"};
        cFlatMajor = new String[]{"A♭", "B♭", "C♭", "D♭", "E♭", "F♭", "G♭", "C Flat Major"};

        allScales = new String[][]{cMajor, gMajor, dMajor, aMajor, eMajor,
                bMajor, fSharpMajor, cSharpMajor, fMajor, bFlatMajor, eFlatMajor, aFlatMajor,
                dFlatMajor, gFlatMajor, cFlatMajor};


        tvChords = findViewById(R.id.chords);
        tvScales = findViewById(R.id.scales);
        Bundle extras = getIntent().getExtras();
        note[0] = "";
        note[1] = "";
        note[2] = "";

        if (extras != null) {
            note[0] = extras.getString("note1");
            note[1] = extras.getString("note2");
            note[2] = extras.getString("note3");
        }
        findChords(note);
    }


    public void findChords(String[] note) {
        boolean check1, check2, check3;
        int I = 0;
        int II = 1;
        int III = 2;
        int IV = 3;
        int V = 4;
        int VI = 5;
        int VII = 6;
        tvChords.setText("");
        tvScales.setText("");
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
                /*tvScales.append(allScales[i][7] + " (" + allScales[i][0] + "-" + allScales[i][1] + "-"
                        + allScales[i][2] + "-" + allScales[i][3] + "-" + allScales[i][4]
                        + "-" + allScales[i][5] + "-" + allScales[i][6] + ")\n");*/
                tvScales.append(allScales[i][7] + "\n");
                tvChords.append(allScales[i][I] + "-" + allScales[i][V] + "-" + allScales[i][VI] + "m-" + allScales[i][IV] + "\n");
                tvChords.append(allScales[i][I] + "-" + allScales[i][IV] + "-" + allScales[i][V] + "\n");
            } else if (!note[1].equals("") && !note[2].equals("") && check1 && check2 && check3) {
                tvScales.append(allScales[i][7] + "\n");

                tvChords.append(allScales[i][I] + "-" + allScales[i][V] + "-" + allScales[i][VI] + "m-" + allScales[i][IV] + "\n");
                tvChords.append(allScales[i][I] + "-" + allScales[i][IV] + "-" + allScales[i][V] + "\n");
            } else if (note[1].equals("") && note[2].equals("") && check1) {
                tvScales.append(allScales[i][7] + "\n");

                tvChords.append(allScales[i][I] + "-" + allScales[i][V] + "-" + allScales[i][VI] + "m-" + allScales[i][IV] + "\n");
                tvChords.append(allScales[i][I] + "-" + allScales[i][IV] + "-" + allScales[i][V] + "\n");
            }


        }
        note[0] = "";
        note[1] = "";
        note[2] = "";
    }
}
