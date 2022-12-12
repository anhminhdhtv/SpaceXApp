package com.toppan.tpars.spacex.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toppan.tpars.spacex.R;
import com.toppan.tpars.spacex.adapter.LaunchListAdapter;
import com.toppan.tpars.spacex.data.model.Launch;
import com.toppan.tpars.spacex.data.network.ApiClient;
import com.toppan.tpars.spacex.data.network.ApiInterface;
import com.toppan.tpars.spacex.data.prefs.AppPreferences;
import com.toppan.tpars.spacex.data.prefs.PreferencesInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private MainActivityContract.Presenter presenter;
    private LaunchListAdapter launchListAdapter;
    private List<Launch> launchList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.searchView);
        setupViews();
        setupPresenter();
        presenter.getLaunches();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void displayLaunchList(List<Launch> launchList) {
        this.launchList.addAll(launchList);
        launchListAdapter.notifyDataSetChanged();
    }

    private void setupViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        launchList = new ArrayList<>();
        launchListAdapter = new LaunchListAdapter(launchList);
        recyclerView.setAdapter(launchListAdapter);
        setSwipeToDeleteItem();

        searchView.setActivated(true);
        searchView.setQueryHint("Type your keyword here");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                launchListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setSwipeToDeleteItem() {
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                        @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                showDialogAskToDeleteItem(position);
            }
        });
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void showDialogAskToDeleteItem(int itemPosition) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("Do you want remove this item?")
                .setPositiveButton("No", (dialogInterface, i) ->
                        launchListAdapter.notifyItemChanged(itemPosition))
                .setNegativeButton("Yes", (dialogInterface, i) -> {
                    Launch launch = launchList.get(itemPosition);
                    presenter.setRemovedItemId(launch.getId());
                    launchList.remove(itemPosition);
                    launchListAdapter.notifyItemRemoved(itemPosition);
                })
                .create()
                .show();
    }

    private void setupPresenter() {
        ApiInterface apiService = ApiClient.getInstance(ApiClient.BASE_URL).create(ApiInterface.class);
        PreferencesInterface preferences = new AppPreferences(getApplicationContext());
        presenter = new LaunchListPresenter(this, apiService, preferences);
    }
}