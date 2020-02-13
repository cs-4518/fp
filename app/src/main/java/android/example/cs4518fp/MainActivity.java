package android.example.cs4518fp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private String storage1 = "";
    private String storage2 = "";
    private String storage3 = "";

    private TextView mPitchText;
    private TextView mNoteText;
    private TextView mStorageText;
    private SeekBar mPitchSeekBar;
    private Pitch[] pitches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        }

        AudioDispatcher dispatcher =
                AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        mPitchText = findViewById(R.id.pitchText);
        mNoteText = findViewById(R.id.noteText);
        mStorageText = findViewById(R.id.storageText);
        mPitchSeekBar = findViewById(R.id.pitchSeekBar);

        mPitchSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mPitchSeekBar.setMax(NUM_PITCHES - 1);

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
    }

    private void processPitch(float inputPitch) {
        if (inputPitch < 16 || inputPitch > 8000) {
            mPitchText.setText(String.format(Locale.getDefault(), "%s", "No pitch detected"));
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
        pitches[1] = new Pitch("C#/D♭", 17.32f);
        pitches[2] = new Pitch("D", 18.35f);
        pitches[3] = new Pitch("D#/E♭", 19.45f);
        pitches[4] = new Pitch("E", 20.60f);
        pitches[5] = new Pitch("F", 21.83f);
        pitches[6] = new Pitch("F#/G♭", 23.12f);
        pitches[7] = new Pitch("G", 24.50f);
        pitches[8] = new Pitch("G#/A♭", 25.96f);
        pitches[9] = new Pitch("A", 27.50f);
        pitches[10] = new Pitch("A#/B♭", 29.14f);
        pitches[11] = new Pitch("B", 30.87f);
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
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
        if(storage1.equals("")) {
            storage1 = pitches[mPitchSeekBar.getProgress()].getNote();
            mStorageText.setText(storage1);
        } else if (storage2.equals("")) {
            storage2 = pitches[mPitchSeekBar.getProgress()].getNote();
            mStorageText.setText(String.format("%s | %s", storage1, storage2));
        } else  if (storage3.equals("")){
            storage3 = pitches[mPitchSeekBar.getProgress()].getNote();
            mStorageText.setText(String.format("%s | %s | %s", storage1, storage2, storage3));
        } else {
            storage3 = storage2;
            storage2 = storage1;
            storage1 = pitches[mPitchSeekBar.getProgress()].getNote();
            mStorageText.setText(String.format("%s | %s | %s", storage1, storage2, storage3));
        }
    }

    public void clear(View view) {
        storage1 = storage2 = storage3 = "";
        mStorageText.setText("");
    }

    private class Pitch {
        final String note;
        final float basePitch;

        Pitch(String note, float basePitch) {
            this.note = note;
            this.basePitch = basePitch;
        }

        boolean matches(float pitch, float tolerance) {
            float tempPitch = basePitch;
            while (tempPitch < 8000) {
                if (pitch >= (tempPitch - tolerance) && pitch <= (tempPitch + tolerance))
                    return true;
                tempPitch *= 2;
            }
            return false;
        }

        String getNote() {
            return note;
        }
    }

}
