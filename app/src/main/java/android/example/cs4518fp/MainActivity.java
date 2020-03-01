package android.example.cs4518fp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_PITCHES = 12;
    private static final String PERSISTENT_FILE = "com.example.android.cs4518fp";

    private boolean help_visible = false;
    private boolean paused = false;

    private String storage1 = "";
    private String storage2 = "";
    private String storage3 = "";

    private TextView mPitchText;
    private TextView mNoteText;
    private TextView mStorageText1;
    private TextView mStorageText2;
    private TextView mStorageText3;
    private TextView mHelpText;
    private ConstraintLayout mHelpLayout;
    private SeekBar mPitchSeekBar;
    private Pitch[] pitches;
    private Button mPauseButton;

    private final String STRING_KEY = "note";
    private final String STRING_KEY2 = "note2";
    private final String STRING_KEY3 = "note3";
    private Button mClearButton;
    private Button mStoreButton;
    private Button mProgButton;


    private SharedPreferences mPreferences;
    private AudioDispatcher dispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        mPitchText = findViewById(R.id.pitchText);
        mNoteText = findViewById(R.id.noteText);
        mStorageText1 = findViewById(R.id.storageText1);
        mStorageText2 = findViewById(R.id.storageText2);
        mStorageText3 = findViewById(R.id.storageText3);

        mClearButton = findViewById(R.id.clearButton);
        mStoreButton = findViewById(R.id.storeButton);
        mProgButton = findViewById(R.id.chordProgressionButton);

        mHelpLayout = findViewById(R.id.helpLayout);

        mHelpText = findViewById(R.id.helpText);
        mHelpText.setMovementMethod(new ScrollingMovementMethod());

        mPitchSeekBar = findViewById(R.id.pitchSeekBar);
        mPitchSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mPitchSeekBar.setMax(NUM_PITCHES - 1);

        mPreferences = getSharedPreferences(PERSISTENT_FILE, MODE_PRIVATE);
        mPauseButton = findViewById(R.id.pauseButton);
        mPauseButton.setText(String.format("  %s  ", getString(R.string.stop_recording)));

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(@NonNull PitchDetectionResult res, AudioEvent e) {
                final float pitchInHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processPitch(pitchInHz);
                    }
                });
            }
        };
        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();

        createPitches();

        storage1 = mPreferences.getString(STRING_KEY, "");
        storage2 = mPreferences.getString(STRING_KEY2, "");
        storage3 = mPreferences.getString(STRING_KEY3, "");

        if (!storage1.equals("")) {
            if (!storage2.equals("")) {
                if (!(storage3 == null) && !storage3.equals("")) {
                    mStorageText1.setText(String.format("%s", storage1));
                    mStorageText2.setText(String.format("%s", storage2));
                    mStorageText3.setText(String.format("%s", storage3));
                } else {
                    mStorageText1.setText(String.format("%s", storage1));
                    mStorageText2.setText(String.format("%s", storage2));
                }
            } else {
                mStorageText1.setText(String.format("%s", storage1));
            }
        }
    }

    private void processPitch(float inputPitch) {
        if (help_visible) return;

        if (inputPitch < 16 || inputPitch > 8000) {
            mPitchText.setText(String.format(Locale.getDefault(), "%s", "0 Hz"));
            return;
        }

        mPitchText.setText(String.format(Locale.getDefault(), "%d%s", (int) inputPitch, " Hz"));

        for (int i = 0; i < pitches.length; i++) {
            Pitch pitch = pitches[i];
            if (pitch.matches(inputPitch, (float) (inputPitch * 0.02))) {
                mPitchSeekBar.setProgress(i);
                mNoteText.setText(pitch.getNote());
            }
        }
    }

    private void createPitches() {
        pitches = new Pitch[NUM_PITCHES];

        pitches[0] = new Pitch("C", 16.35f);
        pitches[1] = new Pitch("C♯/ D♭", 17.32f);
        pitches[2] = new Pitch("D", 18.35f);
        pitches[3] = new Pitch("D♯/ E♭", 19.45f);
        pitches[4] = new Pitch("E", 20.60f);
        pitches[5] = new Pitch("F", 21.83f);
        pitches[6] = new Pitch("F♯/ G♭", 23.12f);
        pitches[7] = new Pitch("G", 24.50f);
        pitches[8] = new Pitch("G♯/ A♭", 25.96f);
        pitches[9] = new Pitch("A", 27.50f);
        pitches[10] = new Pitch("A♯/ B♭", 29.14f);
        pitches[11] = new Pitch("B", 30.87f);
    }

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            try {
                mNoteText.setText(pitches[progress].getNote());
            } catch (ArrayIndexOutOfBoundsException e) {
                Toast.makeText(getApplicationContext(), "ArrayIndexOutOfBoundsException detected", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void store(View view) {
        String note = pitches[mPitchSeekBar.getProgress()].getNote();
        if (storage1.equals(note) || storage2.equals(note) || storage3.equals(note)) {
            Toast.makeText(getApplicationContext(), "That note is already stored", Toast.LENGTH_SHORT).show();
            return;
        }

        if (storage1.equals("")) {
            storage1 = note;
            mStorageText1.setText(storage1);
        } else if (storage2.equals("")) {
            storage2 = note;
            mStorageText2.setText(storage2);
        } else if (storage3.equals("")) {
            storage3 = note;
            mStorageText3.setText(storage3);
        } else {
            storage3 = storage2;
            storage2 = storage1;
            storage1 = note;
            mStorageText1.setText(storage1);
            mStorageText2.setText(storage2);
            mStorageText3.setText(storage3);
        }
    }

    public void clear(View view) {
        storage1 = storage2 = storage3 = "";
        mStorageText1.setText("");
        mStorageText2.setText("");
        mStorageText3.setText("");

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    public void toggleHelp(View view) {
        if (help_visible) {
            help_visible = false;
            mHelpLayout.setVisibility(View.INVISIBLE);
            mStoreButton.setVisibility(View.VISIBLE);
            mClearButton.setVisibility(View.VISIBLE);
            mProgButton.setVisibility(View.VISIBLE);
            mPitchText.setVisibility(View.VISIBLE);

        } else {
            help_visible = true;
            mHelpText.scrollTo(0, 0);
            mHelpLayout.setVisibility(View.VISIBLE);
            mStoreButton.setVisibility(View.INVISIBLE);
            mClearButton.setVisibility(View.INVISIBLE);
            mProgButton.setVisibility(View.INVISIBLE);
            mPitchText.setVisibility(View.INVISIBLE);
        }
    }


    public void goToChordProgressions(View view) {
        if (storage1 == null || storage1.equals("")) {
            Toast.makeText(getApplicationContext(), "Please store one or more notes first", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ChordProgressionActivity.class);
        String message1 = storage1;
        String message2 = storage2;
        String message3 = storage3;
        intent.putExtra("note1", message1);
        if (storage2 != null && !storage2.equals(""))
            intent.putExtra("note2", message2);
        if (storage3 != null && !storage3.equals(""))
            intent.putExtra("note3", message3);

        startActivity(intent);
    }

    public void pause(View view) {

        if (paused) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                        123);
            }
            dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);


            mPreferences = getSharedPreferences(PERSISTENT_FILE, MODE_PRIVATE);

            PitchDetectionHandler pdh = new PitchDetectionHandler() {
                @Override
                public void handlePitch(@NonNull PitchDetectionResult res, AudioEvent e) {
                    final float pitchInHz = res.getPitch();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processPitch(pitchInHz);
                        }
                    });
                }
            };
            AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
            dispatcher.addAudioProcessor(pitchProcessor);

            Thread audioThread = new Thread(dispatcher, "Audio Thread");
            audioThread.start();
            paused = false;
            mPauseButton.setText(String.format("  %s  ", getString(R.string.stop_recording)));
            mPitchText.setText(String.format(Locale.getDefault(), "%s", "0 Hz"));
        } else {
            try {
                if (!dispatcher.isStopped()) {
                    dispatcher.stop();
                    paused = true;
                    mPauseButton.setText(String.format("  %s  ", getString(R.string.start_recording)));
                    mPitchText.setText(String.format(Locale.getDefault(), "%s", getString(R.string.not_recording)));
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex, Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(STRING_KEY, storage1);
        preferencesEditor.putString(STRING_KEY2, storage2);
        preferencesEditor.putString(STRING_KEY3, storage3);
        preferencesEditor.apply();

    }
}
