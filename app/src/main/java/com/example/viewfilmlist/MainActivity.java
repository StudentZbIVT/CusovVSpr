package com.example.viewfilmlist;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText Name = findViewById(R.id.namefilm);
        Spinner type = findViewById(R.id.typefilm);
        Spinner sp = findViewById(R.id.typeop);
        Spinner rate = findViewById(R.id.rate);
        ListView listfilm = findViewById(R.id.ListFilm);
        listfilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TextView nameView,typeView, rateView;
                    nameView = view.findViewById(R.id.nameView);
                    typeView = view.findViewById(R.id.typefilm);
                    rateView = view.findViewById(R.id.ratef);

                ArrayAdapter adapterType = (ArrayAdapter) type.getAdapter();
                int typePosition = adapterType.getPosition(typeView.getText().toString());

                ArrayAdapter adapterRate = (ArrayAdapter) rate.getAdapter();
                int RatePosition = adapterType.getPosition(rateView.getText().toString());

                Name.setText(""+nameView.getText().toString());
                type.setSelection(typePosition);
                rate.setSelection(RatePosition);
                sp.setSelection(2);
            }
        });
    }
    private void Pospehov(Cursor query)
    {
       // textView.setText("");

        ArrayList<Films> filmsArr = new ArrayList<Films>();
        ListView filmList = findViewById(R.id.ListFilm);

        while (query.moveToNext()) {
            String name = query.getString(0);
            String typefilm = query.getString(1);
            String Ratefilm = query.getString(2);

            filmsArr.add(new Films(name, typefilm, Ratefilm));
        }

        FilmeAdapter adapter = new FilmeAdapter(this, R.layout.listfilms_item, filmsArr);
        filmList.setAdapter(adapter);

        query.close();
    }
    public void onClick(View view){
        EditText Name = findViewById(R.id.namefilm);
        Spinner type = findViewById(R.id.typefilm);
        Spinner sp = findViewById(R.id.typeop);
        Spinner rate = findViewById(R.id.rate);
        ListView listfilm = findViewById(R.id.ListFilm);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("VFT.db", MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS films (namefilm TEXT, typefilm TEXT,ratefilm INTEGER , PRIMARY KEY(namefilm),UNIQUE(namefilm))");

        Cursor query = db.rawQuery("SELECT * FROM films ;", null);

          switch (sp.getSelectedItem().toString()) {
              case ("Поиск"):
                  if(Name.length()>0&&type.getSelectedItem().toString().length()==0){
                  Cursor ser = db.rawQuery("SELECT * FROM films WHERE namefilm LIKE '%"+Name.getText().toString()+"%';", null);
                      Pospehov(ser);
                  }
                  else if(type.getSelectedItem().toString().length()>0&&Name.length()==0){
                      Cursor ser = db.rawQuery("SELECT * FROM films WHERE typefilm LIKE '%"+type.getSelectedItem().toString()+"%';", null);
                      Pospehov(ser);
                  }
                  else if(type.getSelectedItem().toString().length()>0&&Name.length()>0){
                      Cursor ser = db.rawQuery("SELECT * FROM films WHERE typefilm LIKE '%"+type.getSelectedItem().toString()+"%' AND namefilm LIKE '%"+Name.getText().toString()+"%' ;", null);
                      Pospehov(ser);
                  }
                  else {
                      Pospehov(query);
                  }
                  break;
              case ("Добавить"):
                  db.execSQL("INSERT OR IGNORE INTO films VALUES ('" + Name.getText().toString() + "', '" + type.getSelectedItem().toString() + "','" + rate.getSelectedItem().toString() + "');");
                  Pospehov(query);
                  break;
              case ("Изменить"):
                  db.execSQL("UPDATE films SET typefilm ='" + type.getSelectedItem().toString() + "'WHERE namefilm = '" + Name.getText().toString() + "';");
                  db.execSQL("UPDATE films SET ratefilm =" + rate.getSelectedItem().toString() + "  WHERE namefilm = '" + Name.getText().toString() + "';");
                  Pospehov(query);
                  break;
              case ("Удалить"):
                 db.execSQL("DELETE FROM films WHERE namefilm ='" + Name.getText().toString() + "';");
                  Pospehov(query);
                  break;
              default:
                  break;
          }
        db.close();
    }
}