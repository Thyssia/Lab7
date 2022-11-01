package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras();

        DetailsFragment dFrag = new DetailsFragment();
        dFrag.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentLocation, dFrag)
                .commit();
    }
}
