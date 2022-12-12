package com.toppan.tpars.spacex.ui;

import android.widget.Filter;
import android.widget.Filterable;

import com.toppan.tpars.spacex.data.model.Launch;
import com.toppan.tpars.spacex.data.network.ApiInterface;
import com.toppan.tpars.spacex.data.prefs.PreferencesInterface;

import java.util.List;

public class LaunchListPresenter implements MainActivityContract.Presenter, Filterable {
    private final MainActivityContract.View launchListView;
    private final MainActivityContract.Model launchListModel;

    public LaunchListPresenter(MainActivityContract.View launchListView, ApiInterface apiInterface,
                               PreferencesInterface preferencesInterface) {
        this.launchListView = launchListView;
        launchListModel = new LaunchListModel(apiInterface, preferencesInterface);
    }

    @Override
    public void getLaunches() {
        launchListModel.getLaunches(new MainActivityContract.OnResponseListener() {
            @Override
            public void onSuccess(List<Launch> responseData) {
                List<String> removedIdList = launchListModel.getRemovedItemId();
                if (removedIdList != null) {
                    removeHiddenItems(responseData, removedIdList);
                }
                launchListView.displayLaunchList(responseData);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void setRemovedItemId(String id) {
        launchListModel.setRemovedItemId(id);
    }

    private List<Launch> removeHiddenItems(List<Launch> launchList, List<String> removedIdList) {
        for (int i = 0; i < launchList.size(); i++) {
            if (removedIdList.contains(launchList.get(i).getId())) {
                launchList.remove(i);
                i--;
            }
        }
        return launchList;
    }


    @Override
    public Filter getFilter() {
        return null;
    }
}
