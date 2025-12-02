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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hipoproject.R;

public class EditControlFragment extends Fragment {

    private EditText inputPhMin, inputPhMax, inputNutMin, inputNutMax, inputWaterMin, inputWaterMax;
    private Button btnCancel, btnSave;
    private ImageView btnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_control, container, false);

        inputPhMin = view.findViewById(R.id.editPhMin);
        inputPhMax = view.findViewById(R.id.editPhMax);
        inputNutMin = view.findViewById(R.id.editNutrientMin);
        inputNutMax = view.findViewById(R.id.editNutrientMax);
        inputWaterMin = view.findViewById(R.id.editWaterMin);
        inputWaterMax = view.findViewById(R.id.editWaterMax);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());


        // Load existing data
        loadData();

        btnCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        btnSave.setOnClickListener(v -> {
            saveData();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void loadData() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("NutrientPrefs", Context.MODE_PRIVATE);
        String[] ph = prefs.getString("phRange", "5.5 - 6.5 ph").replace(" ph", "").split(" - ");
        String[] nut = prefs.getString("nutrientRange", "560 - 650 ppm").replace(" ppm", "").split(" - ");
        String[] water = prefs.getString("waterLevelRange", "10 - 13 cm").replace(" cm", "").split(" - ");

        if (ph.length == 2) {
            inputPhMin.setText(ph[0].trim());
            inputPhMax.setText(ph[1].trim());
        }
        if (nut.length == 2) {
            inputNutMin.setText(nut[0].trim());
            inputNutMax.setText(nut[1].trim());
        }
        if (water.length == 2) {
            inputWaterMin.setText(water[0].trim());
            inputWaterMax.setText(water[1].trim());
        }
    }

    private void saveData() {
        String phMin = inputPhMin.getText().toString();
        String phMax = inputPhMax.getText().toString();
        String nutMin = inputNutMin.getText().toString();
        String nutMax = inputNutMax.getText().toString();
        String waterMin = inputWaterMin.getText().toString();
        String waterMax = inputWaterMax.getText().toString();

        SharedPreferences prefs = requireActivity().getSharedPreferences("NutrientPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phRange", phMin + " - " + phMax + " ph");
        editor.putString("nutrientRange", nutMin + " - " + nutMax + " ppm");
        editor.putString("waterLevelRange", waterMin + " - " + waterMax + " cm");
        editor.apply();
    }
}
