package com.example.tavtaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripsAdapter extends  RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    private Context Context;
    private List<Fire_Trip> TripList;

    public TripsAdapter(Context Context, List<Fire_Trip> TripList) {
        this.Context = Context;
        this.TripList = TripList;
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View view = inflater.inflate(R.layout.one_list_item,null);
        return new TripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {

        Fire_Trip trip = TripList.get(position);
        holder.from.setText(trip.getFROM());
        holder.to.setText(trip.getWHERE());
        holder.when.setText(trip.getWHERE());

    }

    @Override
    public int getItemCount() {
        return TripList.size();
    }

    class TripsViewHolder extends RecyclerView.ViewHolder{

        TextView from,to,when;
        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.from_tv);
            to = itemView.findViewById(R.id.when_vt);
            when = itemView.findViewById(R.id.when_vt);
        }
    }
}
