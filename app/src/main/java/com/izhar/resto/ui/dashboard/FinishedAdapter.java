package com.izhar.resto.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.izhar.resto.R;
import com.izhar.resto.ui.objects.Request;

import java.util.List;

public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.Holder> {
    Context context;
    List<Request> requests;

    public FinishedAdapter(Context context, List<Request> requests) {
        this.context = context;
        this.requests = requests;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Request request = requests.get(position);
        holder.price.setText(request.getTotal());
        holder.table.setText(request.getName());
        holder.time.setText(request.getDateTime());
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView table, time, price;
        public Holder(@NonNull View itemView) {
            super(itemView);
            table = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);
        }
    }
}
