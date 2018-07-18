package com.dididi.pocket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dididi.pocket_core.app.Pocket;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(Pocket.getApplicationContext(), "传入context成功",
                Toast.LENGTH_SHORT).show();
    }
}
