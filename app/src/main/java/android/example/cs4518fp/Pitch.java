package android.example.cs4518fp;

class Pitch {
    private final String note;
    private final float basePitch;

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
