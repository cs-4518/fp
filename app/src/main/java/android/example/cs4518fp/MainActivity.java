package android.example.cs4518fp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {

    private TextView mPitchText;
    private TextView mNoteText;
    private Pitch[] pitches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TEST", "reached point 1");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("TEST", "reached point 2");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    123);
        } else {
            Log.d("TEST", "reached point 3");
        }
        Log.d("TEST", "reached point 4");

        AudioDispatcher dispatcher =
                AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        mPitchText = findViewById(R.id.pitchText);
        mNoteText = findViewById(R.id.noteText);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
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

    public void processPitch(float inputPitch) {
        mNoteText.setText(String.format(Locale.getDefault(), "%s", ""));

        if (inputPitch < 16 || inputPitch > 8000) {
            mPitchText.setText(String.format(Locale.getDefault(), "%s", "No pitch detected"));
            return;
        }

        mPitchText.setText(String.format(Locale.getDefault(), "%f", inputPitch));

        for (Pitch pitch : pitches) {
            if (pitch.matches(inputPitch, (float) (inputPitch * 0.02))) {
                mNoteText.setText(pitch.getNote());
            }
        }
    }

    private void createPitches() {
        pitches = new Pitch[12];

        pitches[0] = new Pitch("B#/C", 16.35f);
        pitches[1] = new Pitch("C#/D♭", 17.32f);
        pitches[2] = new Pitch("D", 18.35f);
        pitches[3] = new Pitch("D#/E♭", 19.45f);
        pitches[4] = new Pitch("E/F♭", 20.60f);
        pitches[5] = new Pitch("E#/F", 21.83f);
        pitches[6] = new Pitch("F#/G♭", 23.12f);
        pitches[7] = new Pitch("G", 24.50f);
        pitches[8] = new Pitch("G#/A♭", 25.96f);
        pitches[9] = new Pitch("A", 27.50f);
        pitches[10] = new Pitch("A#/B♭", 29.14f);
        pitches[11] = new Pitch("B/C♭", 30.87f);
    }

    private class Pitch {
        String note;
        float basePitch;

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
