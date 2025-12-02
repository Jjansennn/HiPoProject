package com.example.hipoproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ControlFragment extends Fragment {

    private TextView textPhRange, textNutrientRange, textWaterLevelRange;
    private Button btnEdit;
    private LinearLayout btnPump, btnABMix, btnPhUp, btnPhDown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_control, container, false);

        // --- INIT VIEWS ---
        btnEdit = view.findViewById(R.id.btnEdit);
        textPhRange = view.findViewById(R.id.textPhRange);
        textNutrientRange = view.findViewById(R.id.textNutrientRange);
        textWaterLevelRange = view.findViewById(R.id.textWaterLevelRange);

        btnPump = view.findViewById(R.id.btnPump);
        btnABMix = view.findViewById(R.id.btnABMix);
        btnPhUp = view.findViewById(R.id.btnPhUp);
        btnPhDown = view.findViewById(R.id.btnPhDown);

        // --- LOAD DATA FROM PREFS ---
        loadSavedData();

        // --- BUTTON LISTENERS ---
        btnEdit.setOnClickListener(v -> {
            // Pindah ke EditControlFragment menggunakan NavController
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_controlFragment_to_editControlFragment);
        });

        // --- Contoh log (bisa disesuaikan nanti) ---
        btnPump.setOnClickListener(v -> addLog("Pump added"));
        btnABMix.setOnClickListener(v -> addLog("AB Mix added"));
        btnPhUp.setOnClickListener(v -> addLog("pH up added"));
        btnPhDown.setOnClickListener(v -> addLog("pH down added"));

        return view;
    }

    private void loadSavedData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("NutrientPrefs", Context.MODE_PRIVATE);
        String phRange = prefs.getString("phRange", "5.5 - 6.5 ph");
        String nutrientRange = prefs.getString("nutrientRange", "560 - 650 ppm");
        String waterLevelRange = prefs.getString("waterLevelRange", "10 - 13 cm");

        textPhRange.setText(phRange);
        textNutrientRange.setText(nutrientRange);
        textWaterLevelRange.setText(waterLevelRange);
    }

    private void addLog(String message) {
        android.util.Log.d("ControlLog", message);
    }
}
