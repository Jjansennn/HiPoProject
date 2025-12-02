package com.example.hipoproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlantInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_info, container, false);

        ImageView imgPlant = view.findViewById(R.id.imgPlant);
        TextView tvPlantName = view.findViewById(R.id.tvPlantName);
        TextView tvPlantStatus = view.findViewById(R.id.tvPlantStatus);

        Bundle args = getArguments();
        if (args != null) {
            tvPlantName.setText(args.getString("plantName"));
            tvPlantStatus.setText(args.getString("plantStatus"));
            imgPlant.setImageResource(args.getInt("plantImage"));
        }

        // Tombol back
        view.findViewById(R.id.btnBack).setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
