package com.example.hipoproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class PlantViewModel extends ViewModel {

    private final MutableLiveData<List<Plant>> activePlants = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Plant>> pastPlants = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Plant>> getActivePlants() {
        return activePlants;
    }

    public LiveData<List<Plant>> getPastPlants() {
        return pastPlants;
    }

    public void addActivePlant(Plant plant) {
        List<Plant> current = activePlants.getValue();
        current.add(plant);
        activePlants.setValue(current);
    }

    public void addPastPlant(Plant plant) {
        List<Plant> current = pastPlants.getValue();
        current.add(plant);
        pastPlants.setValue(current);
    }
}
