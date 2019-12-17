package com.example.tavtaxi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    private Context Context;
    private List<Fire_Trip> TripList;
    public static final String SHARED_PREFS="sharedPrefs";
    public TripsAdapter(Context Context, List<Fire_Trip> TripList) {
        this.Context = Context;
        this.TripList = TripList;
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Context);
        View view = inflater.inflate(R.layout.one_list_item, null);

        return new TripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripsViewHolder holder, int position) {

        final Fire_Trip trip = TripList.get(position);
        SharedPreferences sharedPreferences = Context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        String activityfrom = sharedPreferences.getString("activityfrom","");
        if(activityfrom.equals("menu"))
        {
            holder.buttonModify.setVisibility(View.VISIBLE);
            holder.buttonCall.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.buttonModify.setVisibility(View.INVISIBLE);
            holder.buttonCall.setVisibility(View.VISIBLE);
        }
        holder.from.setText(trip.getFrom());
        holder.to.setText(trip.getWhere());
        holder.when.setText(trip.getWhen());
        holder.states.setText(trip.getFreeStates());
        holder.phone.setText(trip.getPhoneNumber());
        holder.name.setText(trip.getName());
        holder.tripid.setText(trip.getId());
        ArrayList<String> cts = trip.getCities();
        String item = "";
        for (int i = 0; i < cts.size(); i++) {
            item = item + cts.get(i);
            if (i != cts.size() - 1) {
                item = item + ", ";
            }
        }
        holder.cities.setText(item);
        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Context,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    String phone = holder.phone.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    Context.startActivity(intent);
                    return;
                }

            }
        });
        holder.buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Context.getApplicationContext(), Offer_transport.class);
                intent.putExtra("id",trip.getId());
                intent.putExtra("from", trip.getFrom());
                intent.putExtra("where",trip.getWhere());
                intent.putExtra("when",trip.getWhen());
                intent.putExtra("states",trip.getFreeStates());
                intent.putExtra("phone",trip.getPhoneNumber());
                intent.putExtra("username",trip.getName());
                Context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return TripList.size();
    }

    class TripsViewHolder extends RecyclerView.ViewHolder{

        TextView from,to,when,cities,name,tripid;
        TextView phone,states;
        Button buttonCall,buttonModify;
        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.username);
            tripid=itemView.findViewById(R.id.tripid);
            from = itemView.findViewById(R.id.from_tv);
            to = itemView.findViewById(R.id.where_tv);
            when = itemView.findViewById(R.id.when_vt);
            states =itemView.findViewById(R.id.free_tv);
            cities = itemView.findViewById(R.id.cities_tv);
            phone=itemView.findViewById(R.id.textPhone);
            buttonCall=itemView.findViewById(R.id.call_btn);
            buttonModify=itemView.findViewById(R.id.modify_btn);

        }
    }
}
