package com.toca.jaime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.api.InjectContent;
import com.processor.jaime.R;

public class MainActivity extends Activity {

    private final static String SALUDO_KEY = "one_cms_saludo_key";

    @InjectContent(key = SALUDO_KEY)
    TextView mTvHello;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvHello = (TextView) findViewById(R.id.textView);
        InitContent.injectMainActivityContent(this);
    }
}
