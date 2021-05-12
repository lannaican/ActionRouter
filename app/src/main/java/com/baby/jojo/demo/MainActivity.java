package com.baby.jojo.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.star.router.JoJoRouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JoJoRouter.init();
        JoJoRouter.getInstance()
                .with(this)
                .go("RouteURL", "babybus://course-en/QmPackagePlanInScreen?aaa");
    }
}