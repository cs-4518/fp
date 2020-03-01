package android.example.cs4518fp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private boolean help_visible = false;
    private ConstraintLayout mHelpLayout;
    private TextView mHelpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mHelpLayout = findViewById(R.id.helpLayout);
        mHelpText = findViewById(R.id.helpText);
        mHelpText.setMovementMethod(new ScrollingMovementMethod());

        ArrayList<ChordProgression> favorites = Favorites.getFavorites();

        RecyclerView favoriteRecycler = findViewById(R.id.favoriteRecycler);

        favoriteRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mFavoriteLayoutManager = new LinearLayoutManager(this);
        favoriteRecycler.setLayoutManager(mFavoriteLayoutManager);
        RecyclerView.Adapter mFavoriteAdapter;

        if (favorites != null && favorites.size() > 0)
            mFavoriteAdapter = new ChordProgressionAdapter(favorites, this);
        else
            mFavoriteAdapter = new ChordProgressionAdapter(this, getString(R.string.favorite_default));

        favoriteRecycler.setAdapter(mFavoriteAdapter);
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
}
