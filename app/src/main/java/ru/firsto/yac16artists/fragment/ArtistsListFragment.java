package ru.firsto.yac16artists.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.firsto.yac16artists.Consts;
import ru.firsto.yac16artists.R;
import ru.firsto.yac16artists.activity.ArtistsDetailActivity;
import ru.firsto.yac16artists.adapter.ArtistsListAdapter;
import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.database.table.ArtistTable;
import ru.firsto.yac16artists.api.loader.ApiLoader;
import ru.firsto.yac16artists.api.loader.ApiLoaderCallback;
import ru.firsto.yac16artists.api.loader.ApiLoaderResult;
import ru.firsto.yac16artists.api.request.ArtistListRequest;
import ru.firsto.yac16artists.api.response.ListResponse;

public class ArtistsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    @InjectView(R.id.refresh)
    public SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(android.R.id.list)
    public ListView listView;

    private ArtistsListAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.inject(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);

        loadArtistsList();

        Bundle args = getArguments();
        if (args != null) {

        }
    }

    private void loadArtistsList() {
        swipeRefreshLayout.setRefreshing(true);
        loaderManager.initLoader(Consts.LoaderId.GET_ARTISTS_LIST,
                new ApiLoaderCallback<ListResponse<ArtistTable>>() {
                    @Override
                    public Loader<ApiLoaderResult<ListResponse<ArtistTable>>> onCreateLoader(int id, Bundle args) {
                        return new ApiLoader<>(getActivity(), new ArtistListRequest());
                    }

                    @Override
                    public void onSuccess(ListResponse<ArtistTable> result) {
                        if (!result.isEmpty())
                            loaderManager.destroyLoader(Consts.LoaderId.GET_ARTISTS_LIST);
                        setList(result.result);
                    }

                    @Override
                    public void onFail(ApiErrorResponse error) {
                        loaderManager.destroyLoader(Consts.LoaderId.GET_ARTISTS_LIST);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(getTag(), error.toString());
                    }
                });
    }

    private void setList(List<ArtistTable> result) {
        swipeRefreshLayout.setRefreshing(false);

        if (result == null || result.size() == 0) {
            Toast.makeText(getActivity(), "No result", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArtistsListAdapter(getActivity(), R.layout.item_artists_list_layout, result);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setItemsCanFocus(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArtistTable artistTable = adapter.getItem((int) id);
        Intent details = new Intent(getActivity(), ArtistsDetailActivity.class);
        details.putExtra(Consts.ExtraKeys.ARTIST, artistTable);
        startActivity(details);
    }

    @Override
    public void onRefresh() {
        loadArtistsList();
    }
}
