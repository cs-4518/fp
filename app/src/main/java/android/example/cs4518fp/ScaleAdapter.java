package android.example.cs4518fp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ScaleAdapter extends RecyclerView.Adapter<ScaleAdapter.ScaleViewHolder> {
    private final ArrayList<Scale> mScales;

    static class ScaleViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;

        ScaleViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
        }
    }

    ScaleAdapter(ArrayList<Scale> scales) {
        mScales = scales;
    }

    ScaleAdapter() {
        mScales = new ArrayList<>();
        mScales.add(new Scale());
    }

    @NonNull
    @Override
    public ScaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scale, parent, false);
        return new ScaleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScaleViewHolder holder, final int position) {
        final Scale currentItem = mScales.get(position);

        if (currentItem.getScale() != null)
            holder.mTextView.setText(currentItem.getScale());
        else
            holder.mTextView.setText(R.string.scales_default);
    }

    @Override
    public int getItemCount() {
        return mScales.size();
    }
}
