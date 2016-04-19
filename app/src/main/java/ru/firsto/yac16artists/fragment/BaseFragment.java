package ru.firsto.yac16artists.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import ru.firsto.yac16artists.api.loader.LoaderManager;

public class BaseFragment extends Fragment {
    protected LoaderManager loaderManager;
    protected Bundle outState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        loaderManager = new LoaderManager(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (outState == null) {
            outState = new Bundle();
        }
        onSaveInstanceState(outState);
        hideSoftInput();
    }

    @Override
    public void onStop() {
        loaderManager.destroyAllLoaders();
        super.onStop();
    }

    @Override
    public void onDetach() {
        loaderManager.destroy();
        super.onDetach();
    }

    protected void hideSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getActivity().getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
