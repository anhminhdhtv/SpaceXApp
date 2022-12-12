package com.toppan.tpars.spacex.ui;

import com.toppan.tpars.spacex.data.model.Launch;

import java.util.List;

public interface MainActivityContract {
    interface Model {
        void getLaunches(OnResponseListener onResponseListener);
        void setRemovedItemId(String id);
        List<String> getRemovedItemId();
    }

    interface View {
        void displayLaunchList(List<Launch> launchList);
    }

    interface Presenter {
        void getLaunches();
        void setRemovedItemId(String id);
    }

    interface OnResponseListener {
        void onSuccess(List<Launch> launchList);

        void onFailure(Throwable t);
    }

}
