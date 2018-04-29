package me.haeki.ledcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        ImageButton bb = findViewById(R.id.backButton);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Display the fragment as the main content.
        //getFragmentManager().beginTransaction()
        //        .replace(android.R.id.content, new SettingsFragment())
        //        .commit();
    }
}
