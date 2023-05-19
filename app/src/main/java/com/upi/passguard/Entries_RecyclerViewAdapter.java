package com.upi.passguard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Entries_RecyclerViewAdapter extends RecyclerView.Adapter<Entries_RecyclerViewAdapter.MyViewHolder>{
    private final Entries_RecylerViewInterface entries_recylerViewInterface;
    Context context;
    List<VaultModel> entries;

    public Entries_RecyclerViewAdapter(Context context, List<VaultModel> entries, Entries_RecylerViewInterface entries_recylerViewInterface){
        this.context = context;
        this.entries = entries;
        this.entries_recylerViewInterface = entries_recylerViewInterface;
    }

    @NonNull
    @Override
    public Entries_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Giving a look to each row
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);

        return new Entries_RecyclerViewAdapter.MyViewHolder(view, entries_recylerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Entries_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to the views which are created in the recycler_view_row layout file
        // based on the position of the recycler view
        holder.tvTitle.setText(entries.get(position).getTitle());
        holder.tvUsername.setText(entries.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvUsername;

        public MyViewHolder(@NonNull View itemView, Entries_RecylerViewInterface entries_recylerViewInterface) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvUsername = itemView.findViewById(R.id.tvUsername);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(entries_recylerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            entries_recylerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
