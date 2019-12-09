package com.example.tavtaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
        holder.when.setText(trip.getWHEN());
        holder.states.setText(trip.getFreeStates());
        holder.phone.setText(trip.getPhoneNumber());
        ArrayList<String> cts=trip.getCities();
        String item = "";
        for(int i=0;i < cts.size();i++){
            item = item + cts.get(i);
            if(i != cts.size()-1){
                item= item + ", ";
            }
        }
        holder.cities.setText(item);

    }

    @Override
    public int getItemCount() {
        return TripList.size();
    }

    class TripsViewHolder extends RecyclerView.ViewHolder{

        TextView from,to,when,cities;
        TextView phone,states;
        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);

            from = itemView.findViewById(R.id.from_tv);
            to = itemView.findViewById(R.id.where_tv);
            when = itemView.findViewById(R.id.when_vt);
            states =itemView.findViewById(R.id.free_tv);
            cities = itemView.findViewById(R.id.cities_tv);
            phone=itemView.findViewById(R.id.textPhone);

        }
    }
}
