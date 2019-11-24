package com.example.calc_simpler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView op1;
    TextView op2;
    TextView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        op1= findViewById(R.id.op1);
        op2= findViewById(R.id.op2);
        res= findViewById(R.id.res);
    }
    public void add(View view) {
        res.setText("");
        float val1=Float.parseFloat(op1.getText()+ "");
        float val2=Float.parseFloat(op2.getText()+"");
        res.setText((val1+val2)+"");
    }
}
