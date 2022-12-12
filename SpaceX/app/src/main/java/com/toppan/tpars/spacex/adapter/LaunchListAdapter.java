package com.toppan.tpars.spacex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toppan.tpars.spacex.R;
import com.toppan.tpars.spacex.data.model.Launch;
import com.toppan.tpars.spacex.helper.DateConverter;

import java.util.ArrayList;
import java.util.List;

public class LaunchListAdapter extends RecyclerView.Adapter<LaunchListAdapter.ViewHolder> implements Filterable {

    private final List<Launch> launchFilterList;
    private List<Launch> launchList;
    private LaunchFilter launchFilter;

    public LaunchListAdapter(List<Launch> launchList) {
        this.launchList = launchList;
        this.launchFilterList = launchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_launch_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Launch launch = launchList.get(position);
        holder.getTextViewDate().setText(DateConverter.convertDateToString(launch.getDate(),
                DateConverter.DATE_FORMAT));
        holder.getTextViewName().setText(launch.getName());
        holder.getTextViewRocket().setText(launch.getRocket());
        StringBuilder strCores = new StringBuilder();
        List<Launch.Core> cores = launch.getCores();
        if (cores != null && !cores.isEmpty()) {
            for (Launch.Core core : cores) {
                if (!strCores.toString().equals("")) {
                    strCores.append("\n\n");
                }
                strCores.append(core.toString());
            }
        }
        holder.getTextViewCores().setText(strCores);
    }

    @Override
    public int getItemCount() {
        return launchList.size();
    }

    @Override
    public Filter getFilter() {
        if (launchFilter == null) {
            launchFilter = new LaunchFilter();
        }
        return launchFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewDate;
        private final TextView textViewName;
        private final TextView textViewRocket;
        private final TextView textViewCores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.text_date);
            textViewName = itemView.findViewById(R.id.text_name);
            textViewRocket = itemView.findViewById(R.id.text_rocket);
            textViewCores = itemView.findViewById(R.id.text_cores);
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewRocket() {
            return textViewRocket;
        }

        public TextView getTextViewCores() {
            return textViewCores;
        }
    }

    private class LaunchFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Launch> filterList = new ArrayList<>();
                for (int i = 0; i < launchFilterList.size(); i++) {
                    if ((launchFilterList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        filterList.add(launchFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = launchFilterList.size();
                results.values = launchFilterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            launchList = (List<Launch>) results.values;
            notifyDataSetChanged();
        }

    }
}
