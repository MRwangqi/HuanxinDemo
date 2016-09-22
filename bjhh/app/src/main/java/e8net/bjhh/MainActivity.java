package e8net.bjhh;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import e8net.bjhh.widget.AdViewPager;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);

        AdViewPager adViewPager= (AdViewPager) findViewById(R.id.viewpager);
        List<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adViewPager.setNum(list);


    }
}
