package android.example.cs4518fp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChordProgressionAdapter extends RecyclerView.Adapter<ChordProgressionAdapter.ChordProgressionViewHolder> {
    private final ArrayList<ChordProgression> mChordProgressions;
    private final Context mContext;
    private String mErrorMessage = "No Chord Progressions Found";

    static class ChordProgressionViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;
        final ImageButton mButton;
        boolean selected = false;

        ChordProgressionViewHolder(View itemView) {
            super(itemView);
            mButton = itemView.findViewById(R.id.button);
            mTextView = itemView.findViewById(R.id.textView);
        }
    }

    ChordProgressionAdapter(ArrayList<ChordProgression> chordProgressions, Context context) {
        mChordProgressions = chordProgressions;
        mContext = context;
    }

    ChordProgressionAdapter(Context context, String errorMessage) {
        mContext = context;
        mChordProgressions = new ArrayList<>();
        mChordProgressions.add(new ChordProgression());
        mErrorMessage = errorMessage;
    }

    @NonNull
    @Override
    public ChordProgressionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chord_progression, parent, false);
        return new ChordProgressionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChordProgressionViewHolder holder, final int position) {
        final ChordProgression currentItem = mChordProgressions.get(position);

        if (Favorites.contains(currentItem)) {
            holder.selected = true;
            holder.mButton.setImageResource(R.drawable.star_full);
        }

        if (currentItem.getProgression() != null) {
            holder.mTextView.setText(currentItem.getProgression());

            holder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.selected) {
                        Toast.makeText(mContext, "Removed " + currentItem.getProgression() + " from favorites",
                                Toast.LENGTH_SHORT).show();
                        holder.mButton.setImageResource(R.drawable.star_empty);
                        holder.selected = false;
                        Favorites.removeFavorite(currentItem, mContext);
                    } else {
                        Toast.makeText(mContext, "Added " + currentItem.getProgression() + " to favorites",
                                Toast.LENGTH_SHORT).show();
                        holder.mButton.setImageResource(R.drawable.star_full);
                        holder.selected = true;
                        Favorites.addFavorite(currentItem, mContext);
                    }
                }
            });
        } else {
            holder.mTextView.setText(mErrorMessage);
            holder.mButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mChordProgressions.size();
    }

}
