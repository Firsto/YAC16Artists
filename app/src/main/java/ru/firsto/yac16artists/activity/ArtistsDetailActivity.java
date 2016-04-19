package ru.firsto.yac16artists.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.firsto.yac16artists.Consts;
import ru.firsto.yac16artists.R;
import ru.firsto.yac16artists.view.TopCropImageView;
import ru.firsto.yac16artists.api.database.table.ArtistTable;

public class ArtistsDetailActivity extends AppCompatActivity {

    @InjectView(R.id.artistPhotoBig)
    TopCropImageView artistPhotoBig;
    @InjectView(R.id.artistGenre)
    TextView artistGenre;
    @InjectView(R.id.artistAlbums)
    TextView artistAlbums;
    @InjectView(R.id.artistDescription)
    TextView artistDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_detail);
        ButterKnife.inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArtistTable artist = getIntent().getParcelableExtra(Consts.ExtraKeys.ARTIST);
        setTitle(artist.name);

        Uri uri = Uri.parse(artist.bigCover);
        if (uri != null) {
            Picasso.with(this)
                    .load(uri)
                    .placeholder(android.R.drawable.picture_frame)
                    .into(artistPhotoBig);
        }

        if (!TextUtils.isEmpty(artist.genres.toString())) {
            artistGenre.setText(artist.genres.toString().replace('[', '\0').replace(']', '\0'));
        }
        artistAlbums.setText(getString(R.string.albums_detail, artist.albums, artist.tracks));
        artistDescription.setText(artist.description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
