package com.example.simpledb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    TextView name;
    TextView roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = findViewById(R.id.roll);
        name = findViewById(R.id.name);
        db = openOrCreateDatabase("Student", Context.MODE_PRIVATE, null);
        db.execSQL("Create table if not exists students (roll varchar, name varchar);");
    }
    public void insert(View view)
    {   db.execSQL("insert into students values ('"+roll.getText()+"','"+name.getText()+"');");
        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
    }
    public void delete(View view)
    {
        db.execSQL("delete from students where (roll='"+roll.getText()+"')");
        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
    }
    public void show(View view)
    {
        Cursor c= db.rawQuery("SELECT * from students",null);
        TextView tv = findViewById(R.id.display);
        tv.setText("");
        while(c.moveToNext())
        {
            tv.append(c.getString(0)+" "+ c.getString(1)+"\n");
        }
        c.close();
    }
}
