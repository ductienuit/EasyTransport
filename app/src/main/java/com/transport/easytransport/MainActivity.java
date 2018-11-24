package com.transport.easytransport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.transport.easytransport.ui.main.MainFragment;
import com.transport.easytransport.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // This ViewModel is scoped to child Fragments of this Activity.
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}