package com.example.hipoproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class PlantFragment extends Fragment {

    private RecyclerView recyclerActive, recyclerPast;
    private PlantAdapter activeAdapter, pastAdapter;
    private List<Plant> activeList, pastList;
    private SearchView searchView;
    private Button btnAddPlant;

    private PlantViewModel plantViewModel;

    public PlantFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plants, container, false);

        // === Inisialisasi komponen ===
        searchView = view.findViewById(R.id.searchView);
        recyclerActive = view.findViewById(R.id.recyclerActive);
        recyclerPast = view.findViewById(R.id.recyclerPast);
        btnAddPlant = view.findViewById(R.id.btnAddPlant);

        // === Styling SearchView ===
        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            searchPlate.setBackgroundResource(R.drawable.bg_search_bar);
        }

        int submitAreaId = searchView.getContext().getResources()
                .getIdentifier("android:id/submit_area", null, null);
        View submitArea = searchView.findViewById(submitAreaId);
        if (submitArea != null) {
            submitArea.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        }
        searchView.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        searchView.setBackgroundResource(R.drawable.bg_search_bar);

        // === Recycler setup ===
        recyclerActive.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerPast.setLayoutManager(new LinearLayoutManager(getContext()));

        activeList = new ArrayList<>();
        pastList = new ArrayList<>();

        activeAdapter = new PlantAdapter(activeList);
        pastAdapter = new PlantAdapter(pastList);

        recyclerActive.setAdapter(activeAdapter);
        recyclerPast.setAdapter(pastAdapter);

        // === Inisialisasi ViewModel ===
        plantViewModel = new ViewModelProvider(requireActivity()).get(PlantViewModel.class);

        // Load dummy data hanya sekali jika ViewModel masih kosong
        if (plantViewModel.getActivePlants().getValue().isEmpty()) {
            plantViewModel.addActivePlant(new Plant("Pok Choy", "9 days until harvest", 80, R.drawable.ic_pokchoy,null,null));
            plantViewModel.addActivePlant(new Plant("Lettuce", "60 days until harvest", 50, R.drawable.ic_lettuce,null,null));
            plantViewModel.addPastPlant(new Plant("Spinach", "Harvested 10 days ago", 100, R.drawable.ic_spinach,null,null));
        }

        // Observe data ViewModel
        plantViewModel.getActivePlants().observe(getViewLifecycleOwner(), plants -> {
            activeList.clear();
            activeList.addAll(plants);
            activeAdapter.updateList(activeList);
        });

        plantViewModel.getPastPlants().observe(getViewLifecycleOwner(), plants -> {
            pastList.clear();
            pastList.addAll(plants);
            pastAdapter.updateList(pastList);
        });

        // === Klik item untuk buka PlantInfoFragment ===
        activeAdapter.setOnItemClickListener(plant -> {
            Bundle bundle = new Bundle();
            bundle.putString("plantName", plant.getName());
            bundle.putInt("plantImage", plant.getImageRes());
            bundle.putString("plantStatus", plant.getStatus());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_plantFragment_to_plantInfoFragment, bundle);
        });

        pastAdapter.setOnItemClickListener(plant -> {
            Bundle bundle = new Bundle();
            bundle.putString("plantName", plant.getName());
            bundle.putInt("plantImage", plant.getImageRes());
            bundle.putString("plantStatus", plant.getStatus());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_plantFragment_to_plantInfoFragment, bundle);
        });

        // === Fitur pencarian ===
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterPlants(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPlants(newText);
                return true;
            }
        });

        // === Tombol Add Plant ===
        btnAddPlant.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_plantFragment_to_addPlantFragment);
        });

        return view;
    }

    private void filterPlants(String text) {
        List<Plant> filteredActive = new ArrayList<>();
        List<Plant> filteredPast = new ArrayList<>();

        for (Plant plant : activeList) {
            if (plant.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredActive.add(plant);
            }
        }
        for (Plant plant : pastList) {
            if (plant.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredPast.add(plant);
            }
        }

        activeAdapter.updateList(filteredActive);
        pastAdapter.updateList(filteredPast);
    }
}
