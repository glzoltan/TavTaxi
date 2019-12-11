package com.example.tavtaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelfTripsAdapter extends  RecyclerView.Adapter<SelfTripsAdapter.SelfTripsViewHolder> {

    private Context selfContext;
    private List<Fire_Trip> selfTripList;

    public SelfTripsAdapter(Context selfContext, List<Fire_Trip> selfTripList) {
        this.selfContext = selfContext;
        this.selfTripList = selfTripList;
    }

    @NonNull
    @Override
    public SelfTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(selfContext);
        View view = inflater.inflate(R.layout.self_list_layout,null);
        return new SelfTripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelfTripsViewHolder holder, int position) {

            Fire_Trip trip = selfTripList.get(position);
            holder.from.setText(trip.getFrom());
        holder.to.setText(trip.getWhere());
        holder.when.setText(trip.getWhen());

    }

    @Override
    public int getItemCount() {
        return selfTripList.size();
    }

    class SelfTripsViewHolder extends RecyclerView.ViewHolder{

        TextView from,to,when;
        public SelfTripsViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.textViewFrom);
            to = itemView.findViewById(R.id.textViewTo);
            when = itemView.findViewById(R.id.textViewWhen);
        }
    }
}
