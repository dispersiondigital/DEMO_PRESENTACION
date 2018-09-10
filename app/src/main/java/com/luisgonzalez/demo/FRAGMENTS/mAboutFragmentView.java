package com.luisgonzalez.demo.FRAGMENTS;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dispersiondigital.demo.CORE.LoginActivity;
import com.dispersiondigital.smartclaritydemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mAboutFragmentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mAboutFragmentView extends Fragment {


    private Button logOutButton;
    public mAboutFragmentView() {
        // Required empty public constructor
    }

    public static mAboutFragmentView newInstance() {
        mAboutFragmentView fragment = new mAboutFragmentView();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.m_about_fragment_view, container, false);
        this.logOutButton = (Button)rootView.findViewById(R.id.button_logout);
        this.logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.show(getActivity());
                getActivity().finish();

            }
        });


        return rootView;
    }

}
