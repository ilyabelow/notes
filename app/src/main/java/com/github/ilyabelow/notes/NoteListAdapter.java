package com.github.ilyabelow.notes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteHolder> {

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    private final List<Note> data;
    private final ArrayList<Integer> colors = new ArrayList<>();
    private final OnItemClickListener listener;

    public NoteListAdapter(Context context, List<Note> data, OnItemClickListener listener) {
        // I cannot create list of colors with { .. }, too bad
        colors.add(context.getColor(com.google.android.material.R.color.design_default_color_on_primary));
        colors.add(context.getColor(R.color.green));
        colors.add(context.getColor(R.color.blue));
        colors.add(context.getColor(R.color.orange));
        colors.add(context.getColor(R.color.purple));
        colors.add(context.getColor(R.color.red));
        this.listener = listener;
        this.data = data;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R.layout.list_item,parent,false);
        return new NoteHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bindTo(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView textTextView;
        private final View markerView;
        private final TextView dateTextView;

        private int id;

        public NoteHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleText);
            textTextView = itemView.findViewById(R.id.textText);
            markerView = itemView.findViewById(R.id.marker);
            dateTextView = itemView.findViewById(R.id.dateText);
            itemView.findViewById(R.id.listItem).setOnClickListener(view -> listener.onItemClick(id));
        }

        public void bindTo(Note n) {
            id = n.getId();
            titleTextView.setText(n.getTitle());
            textTextView.setText(n.getText());
            dateTextView.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(n.getDate()));
            if (n.getColor() != 0) {
                markerView.setVisibility(View.VISIBLE);
                markerView.setBackgroundColor(colors.get(n.getColor()));
            } else {
                markerView.setVisibility(View.GONE);
            }
        }
    }
}
