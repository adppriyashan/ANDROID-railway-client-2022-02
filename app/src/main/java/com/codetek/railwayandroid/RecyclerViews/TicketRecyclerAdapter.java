package com.codetek.railwayandroid.RecyclerViews;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codetek.railwayandroid.Models.Ticket;
import com.codetek.railwayandroid.R;

import java.util.ArrayList;

public class TicketRecyclerAdapter extends RecyclerView.Adapter<TicketRecyclerAdapter.ViewHolder>{

    private ArrayList<Ticket> tickets=new ArrayList<Ticket>();

    public TicketRecyclerAdapter(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_dashboard_ticket, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketRecyclerAdapter.ViewHolder holder, int position) {
        holder.getTitle().setText("Turn No :"+Integer.toString(tickets.get(position).getTurn()));
        holder.getSubtitle().setText(tickets.get(position).getStart() + " To "+tickets.get(position).getEnd());
        holder.getStatus().setText((tickets.get(position).getStatus()==1)?"ACTIVE":"NOT AVAILABLE");
        holder.getStatus().setTextColor((tickets.get(position).getStatus()==1)?Color.parseColor("#65a743"):Color.parseColor("#FF0000"));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title,subtitle,status;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.row_ticket_title);
            subtitle =  view.findViewById(R.id.row_ticket_subtitle);
            status = view.findViewById(R.id.row_ticket_status);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getSubtitle() {
            return subtitle;
        }

        public TextView getStatus() {
            return status;
        }

    }
}
