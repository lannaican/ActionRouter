package com.baby.jojo.demo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.star.router.ActionRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionRouter.init();
        ActionRouter.getInstance()
                .with(this)
                .go("course/abc");
    }
}