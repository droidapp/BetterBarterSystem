package com.gaucow.betterbartersystem.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaucow.betterbartersystem.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class MainScreenFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public MainScreenFragment() {
    }

    public static MainScreenFragment newInstance() {
        return new MainScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //LayoutInflater newInflater = inflater.cloneInContext(new ContextThemeWrapper(getContext(), R.style.NewMaterialTheme));
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
