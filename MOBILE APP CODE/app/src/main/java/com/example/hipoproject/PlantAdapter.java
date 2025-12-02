package com.example.hipoproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private List<Plant> plantList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Plant plant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PlantAdapter(List<Plant> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Pastikan ini layout RecyclerView item yang benar
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plant plant = plantList.get(position);

        // Set nama dan status tanaman
        holder.tvName.setText(plant.getName());
        holder.tvStatus.setText(plant.getStatus());

        // Set progress dan persentase
        holder.progressBar.setProgress(plant.getProgress());
        holder.tvPercentage.setText(plant.getProgress() + "%");

        // Set gambar tanaman
        holder.imgPlant.setImageResource(plant.getImageRes());

        // Listener klik item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(plant);
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public void updateList(List<Plant> newList) {
        plantList = newList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus, tvPercentage;
        ProgressBar progressBar;
        ImageView imgPlant;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textPlantName);
            tvStatus = itemView.findViewById(R.id.textPlantDesc);
            tvPercentage = itemView.findViewById(R.id.textPercentage);
            progressBar = itemView.findViewById(R.id.progressCircle);
            imgPlant = itemView.findViewById(R.id.imagePlant);
        }
    }
}
