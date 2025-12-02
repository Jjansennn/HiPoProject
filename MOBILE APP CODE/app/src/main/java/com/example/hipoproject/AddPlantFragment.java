package com.example.hipoproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.fragment.app.Fragment;

public class AddPlantFragment extends Fragment {

    private ImageView imagePlant;
    private ImageView btnBack;
    private EditText nameInput, plantingDateInput, growthDurationInput, harvestDateInput, plantCountInput;
    private Button btnCancel, btnSave;

    public AddPlantFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_plant, container, false);

        // 🔹 Inisialisasi semua view
        imagePlant = view.findViewById(R.id.imagePlant);
        nameInput = view.findViewById(R.id.etName);
        plantingDateInput = view.findViewById(R.id.etDate);
        growthDurationInput = view.findViewById(R.id.etDuration);
        harvestDateInput = view.findViewById(R.id.etHarvest);
        plantCountInput = view.findViewById(R.id.etCount);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());


        // 🔹 Klik tombol pilih gambar (sementara hanya tampilkan Toast)
        imagePlant.setOnClickListener(v ->
                Toast.makeText(getContext(), "Select plant image", Toast.LENGTH_SHORT).show()
        );

        // 🔹 Klik tombol cancel (kembali ke fragment sebelumnya)
        btnCancel.setOnClickListener(v -> requireActivity().onBackPressed());

        // 🔹 Klik tombol save (ambil semua input dan tampilkan log / toast)
        btnSave.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String date = plantingDateInput.getText().toString().trim();
            String duration = growthDurationInput.getText().toString().trim();
            String harvest = harvestDateInput.getText().toString().trim();
            String count = plantCountInput.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || duration.isEmpty() || harvest.isEmpty() || count.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // 🔹 Buat bundle untuk kirim data ke fragment berikutnya
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("date", date);
                bundle.putString("duration", duration);
                bundle.putString("harvest", harvest);
                bundle.putString("count", count);

                // 🔹 Navigasi ke PlantInfoFragment
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_addPlantFragment_to_plantInfoFragment, bundle);
            }
        });


        return view;
    }
}
