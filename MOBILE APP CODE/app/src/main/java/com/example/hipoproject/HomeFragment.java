package com.example.hipoproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class HomeFragment extends Fragment {

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 🔹 Card Data (ke Control)
        View cardData = view.findViewById(R.id.cardData);
        if (cardData != null) {
            cardData.setOnClickListener(v ->
                    Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_controlFragment)
            );
        }

        // 🔹 Card Active Plant (ke Plant Info)
        View cardActivePlant = view.findViewById(R.id.cardActivePlant);
        if (cardActivePlant != null) {
            cardActivePlant.setOnClickListener(v ->
                    Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_plantInfoFragment)
            );
        }

        return view;
    }
}
