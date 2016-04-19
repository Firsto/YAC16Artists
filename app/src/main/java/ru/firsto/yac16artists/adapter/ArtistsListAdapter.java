package ru.firsto.yac16artists.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.firsto.yac16artists.R;
import ru.firsto.yac16artists.api.database.table.ArtistTable;

public class ArtistsListAdapter extends ArrayAdapter<ArtistTable> {

    protected LayoutInflater inflater;

    public ArtistsListAdapter(Context context, int textViewResourcesId, List<ArtistTable> objects) {
        super(context, textViewResourcesId, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_artists_list_layout, parent, false);
                holder = new ViewHolder((ViewGroup) convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ArtistTable item = getItem(position);
            fillHolder(holder, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    protected void fillHolder(ViewHolder holder, final ArtistTable item) {
        holder.artistName.setText(item.name);
        if (!TextUtils.isEmpty(item.genres.toString())) {
            holder.artistGenre.setText(item.genres.toString().replace('[', '\0').replace(']', '\0'));
        }
        holder.artistAlbums.setText(getContext().getString(R.string.albums, item.albums, item.tracks));

        Uri uri = Uri.parse(item.smallCover);
        if (uri != null) {
            Picasso.with(super.getContext())
                    .load(uri)
                    .placeholder(android.R.drawable.picture_frame)
                    .fit()
                    .into(holder.avatar);
        }
    }

    public static class ViewHolder {
        ImageView avatar;
        TextView artistName, artistGenre, artistAlbums;
        ViewGroup container;

        public ViewHolder(ViewGroup container) {
            this.container = container;
            avatar = (ImageView) container.findViewById(R.id.artistPhoto);
            artistName = (TextView) container.findViewById(R.id.artistName);
            artistGenre = (TextView) container.findViewById(R.id.artistGenre);
            artistAlbums = (TextView) container.findViewById(R.id.artistAlbums);
        }
    }

}
