package com.paulohdsousa.vocemeconhece;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class    MainActivity extends BaseActivity {


    ImageButton btnJogar;
    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.navigation_home;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnJogar= (ImageButton)findViewById(R.id.btnJogar);
        btnJogar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, JogarActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }
}
