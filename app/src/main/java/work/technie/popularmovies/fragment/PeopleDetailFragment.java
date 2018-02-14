package work.technie.popularmovies.fragment;
/*
 * Copyright (C) 2017 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import work.technie.popularmovies.Constants;
import work.technie.popularmovies.R;
import work.technie.popularmovies.asyntask.FetchPeopleDetail;
import work.technie.popularmovies.data.MovieContract;
import work.technie.popularmovies.utils.AsyncResponse;
import work.technie.popularmovies.utils.PaletteTransformation;
import work.technie.popularmovies.utils.Utility;

import static work.technie.popularmovies.Constants.PEOPLE_COLUMNS;
import static work.technie.popularmovies.Constants.PEOPLE_COL_BIOGRAPHY;
import static work.technie.popularmovies.Constants.PEOPLE_COL_BIRTHDAY;
import static work.technie.popularmovies.Constants.PEOPLE_COL_HOMEPAGE;
import static work.technie.popularmovies.Constants.PEOPLE_COL_NAME;
import static work.technie.popularmovies.Constants.PEOPLE_COL_PLACE_OF_BIRTH;

/**
 * Created by anupam on 19/12/16.
 */

public class PeopleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AsyncResponse {
    private static final int PEOPLE_DETAILS_LOADER = 0;
    private static final String DARK_MUTED_COLOR = "dark_muted_color";
    private static final String MUTED_COLOR = "muted_color";
    private View rootView;
    private String people_Id;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private int dark_muted_color;
    private int muted_color;
    private boolean mTwoPane;


    private void updateDetailList() {
        FetchPeopleDetail fetchTask = new FetchPeopleDetail(getActivity());
        fetchTask.response = this;
        fetchTask.execute(people_Id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(DARK_MUTED_COLOR) && savedInstanceState.containsKey(MUTED_COLOR)) {
            dark_muted_color = savedInstanceState.getInt(DARK_MUTED_COLOR);
            muted_color = savedInstanceState.getInt(MUTED_COLOR);
        } else {
            dark_muted_color = 0;
            muted_color = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();

        String transitionName = "";

        if (arguments != null) {
            people_Id = arguments.getString(Intent.EXTRA_TEXT);
            transitionName = arguments.getString("TRANS_NAME");
            mTwoPane = arguments.getBoolean(Intent.ACTION_SCREEN_ON);
        }
        rootView = inflater.inflate(R.layout.fragment_people, container, false);

        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView.findViewById(R.id.profile_img).setTransitionName(transitionName);
        }

        if (dark_muted_color != 0 && muted_color != 0 && !mTwoPane) {
            changeColor(getActivity());
        }

        loadData(rootView);
        return rootView;
    }

    private void loadData(final View rootView) {

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.detail_people_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utility.hasNetworkConnection(getActivity())) {
                    getActivity().getContentResolver().delete(MovieContract.People.buildPeopleUriWithPeopleId(people_Id), null, null);
                    updateDetailList();
                } else {
                    Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        int count = 0;

        Uri Uri = MovieContract.People.buildPersonUri();
        Cursor cursor = getActivity().getContentResolver().query(Uri, Constants.PEOPLE_DETAILS_COLUMNS_MIN,
                MovieContract.People.ID + " = ? ",
                new String[]{people_Id},
                null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }

        if (count == 0) {
            if (!Utility.hasNetworkConnection(getActivity())) {
                Toast.makeText(getContext(), "Network Not Available!", Toast.LENGTH_LONG).show();
            } else {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });
                updateDetailList();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(PEOPLE_DETAILS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(DARK_MUTED_COLOR, dark_muted_color);
        outState.putInt(MUTED_COLOR, muted_color);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != people_Id) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            switch (id) {
                case PEOPLE_DETAILS_LOADER:
                    return new CursorLoader(
                            getActivity(),
                            MovieContract.People.buildPeopleUriWithPeopleId(people_Id),
                            PEOPLE_COLUMNS,
                            null,
                            null,
                            null
                    );
            }
        }
        return null;
    }

    private void defaultShow() {
        rootView.findViewById(R.id.profile_img).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.birthday_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.place_of_birth_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.biography_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.homepage_title).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.tmdb_profile).setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor data) {
        swipeRefreshLayout.setRefreshing(false);
        if (!data.moveToFirst()) {
            return;
        }
        switch (loader.getId()) {
            case PEOPLE_DETAILS_LOADER:

                defaultShow();
                String name = data.getString(PEOPLE_COL_NAME).trim();
                collapsingToolbar.setTitle(name.isEmpty() || name.equalsIgnoreCase("null") ? "-" : name);

                String biography = data.getString(PEOPLE_COL_BIOGRAPHY).trim();
                ((TextView) rootView.findViewById(R.id.biography))
                        .setText(biography.isEmpty() || biography.equalsIgnoreCase("null") ? "-" : biography);

                String place_of_birth = data.getString(PEOPLE_COL_PLACE_OF_BIRTH).trim();
                ((TextView) rootView.findViewById(R.id.place_of_birth))
                        .setText(place_of_birth.isEmpty() || place_of_birth.equalsIgnoreCase("null") ? "-" : place_of_birth);

                String homepage = data.getString(PEOPLE_COL_HOMEPAGE).trim();
                ((TextView) rootView.findViewById(R.id.homepage_profile))
                        .setText(homepage.isEmpty() || homepage.equalsIgnoreCase("null") ? "-" : homepage);

                String birthDate = data.getString(PEOPLE_COL_BIRTHDAY).trim();

                if (!birthDate.isEmpty() && !birthDate.equalsIgnoreCase("null")) {
                    DateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = null;
                    try {
                        date = inputFormatter.parse(birthDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());

                    ((TextView) rootView.findViewById(R.id.birthday))
                            .setText(date != null ? formatter.format(date) : "-");
                } else {
                    ((TextView) rootView.findViewById(R.id.birthday))
                            .setText("-");
                }

                final String PROFILE_BASE_URL = "http://image.tmdb.org/t/p/w185";
                final String profileURL = data.getString(Constants.PEOPLE_COL_PROFILE_PATH).trim();

                if (!profileURL.isEmpty() && !profileURL.equalsIgnoreCase("null")) {
                    final ImageView profile_img = (ImageView) rootView.findViewById(R.id.profile_img);
                    Picasso.with(getActivity())
                            .load(PROFILE_BASE_URL + profileURL)
                            .fit().centerCrop()
                            .transform(PaletteTransformation.instance())
                            .into(profile_img, new Callback.EmptyCallback() {
                                @Override
                                public void onSuccess() {
                                    if (!mTwoPane) {
                                        Bitmap bitmap = ((BitmapDrawable) profile_img.getDrawable()).getBitmap();
                                        Palette palette = PaletteTransformation.getPalette(bitmap);
                                        changeSystemToolbarColor(palette);
                                    }
                                }
                            });

                    profile_img.setAdjustViewBounds(true);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown Loader");
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onFinish(boolean isData) {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void changeSystemToolbarColor(Palette palette) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Activity mActivity = getActivity();
            if (mActivity != null) {
                dark_muted_color = palette.getDarkMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryDark));
                muted_color = palette.getMutedColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
                changeColor(mActivity);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void changeColor(Activity mActivity) {
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mActivity.getWindow().setStatusBarColor(dark_muted_color);
        collapsingToolbar.setContentScrimColor(muted_color);
    }
}
