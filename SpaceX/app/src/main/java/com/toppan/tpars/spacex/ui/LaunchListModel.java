package com.toppan.tpars.spacex.ui;

import androidx.annotation.NonNull;

import com.toppan.tpars.spacex.data.model.Launch;
import com.toppan.tpars.spacex.data.network.ApiInterface;
import com.toppan.tpars.spacex.data.prefs.PreferencesInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchListModel implements MainActivityContract.Model {
    private final ApiInterface apiService;
    private final PreferencesInterface preferencesInterface;

    public LaunchListModel(ApiInterface apiService, PreferencesInterface preferencesInterface) {
        this.apiService = apiService;
        this.preferencesInterface = preferencesInterface;
    }

    @Override
    public void getLaunches(MainActivityContract.OnResponseListener onResponseListener) {
        Call<List<Launch>> call = apiService.getLaunchList();
        call.enqueue(new Callback<List<Launch>>() {
            @Override
            public void onResponse(@NonNull Call<List<Launch>> call,
                                   @NonNull Response<List<Launch>> response) {
                onResponseListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Launch>> call, @NonNull Throwable t) {
                onResponseListener.onFailure(t);
            }
        });
    }

    @Override
    public void setRemovedItemId(String id) {
        preferencesInterface.setRemovedItemId(id);
    }

    @Override
    public List<String> getRemovedItemId() {
        return preferencesInterface.getRemovedItemId();
    }
}
