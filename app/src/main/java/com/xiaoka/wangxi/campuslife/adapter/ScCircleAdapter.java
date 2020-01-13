package com.xiaoka.wangxi.campuslife.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScCircleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> integers;
    private Context context;

    public ScCircleAdapter(Context context, List<Integer> integers) {
        this.context = context;
        this.integers = integers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 16, parent.getResources().getDisplayMetrics());
        final TextView textView = new TextView(parent.getContext());
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(textView.getContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return new RecyclerView.ViewHolder(textView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.itemView instanceof TextView) {
            ((TextView) holder.itemView).setText(String.valueOf(integers.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return integers.size();
    }
}
