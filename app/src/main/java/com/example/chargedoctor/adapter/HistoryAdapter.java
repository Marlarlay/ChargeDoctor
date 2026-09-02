package com.example.chargedoctor.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chargedoctor.R;
import com.example.chargedoctor.model.HistoryItem;
import com.example.chargedoctor.HistoryDetailActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryItem> historyList;

    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate;
        TextView txtProduct;
        TextView txtConfidence;
        Button btnStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtProduct = itemView.findViewById(R.id.txtProduct);
            txtConfidence = itemView.findViewById(R.id.txtConfidence);
            btnStatus = itemView.findViewById(R.id.btnStatus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        HistoryItem item = historyList.get(position);

        holder.txtDate.setText(
                item.getDate()
        );

        holder.txtProduct.setText(
                item.getCableName()
        );

        holder.txtConfidence.setText(
                holder.itemView.getContext().getString(R.string.health)
                        + item.getHealth()
                        + "%"
        );

        holder.btnStatus.setText(
                item.getStatus()
        );

        if (item.getStatus().equals("安全")) {
            holder.btnStatus.setText(R.string.safe);

            holder.btnStatus.setBackgroundColor(
                    Color.parseColor("#22C55E")
            );

        } else if (item.getStatus().equals("注意")) {
            holder.btnStatus.setText(R.string.warning);

            holder.btnStatus.setBackgroundColor(
                    Color.parseColor("#FACC15")
            );

            holder.btnStatus.setTextColor(
                    Color.BLACK
            );

        } else {
            holder.btnStatus.setText(R.string.danger);

            holder.btnStatus.setBackgroundColor(
                    Color.parseColor("#EF4444")
            );
        }

        holder.btnStatus.setAllCaps(false);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    HistoryDetailActivity.class
            );

            intent.putExtra("date", item.getDate());
            intent.putExtra("health", item.getHealth());
            intent.putExtra("current", item.getCurrent());
            intent.putExtra("temp", item.getTemp());
            intent.putExtra("status", item.getStatus());
            intent.putExtra("cableName", item.getCableName());

            v.getContext().startActivity(intent);

        });

    }



    @Override
    public int getItemCount() {
        return historyList.size();
    }
}