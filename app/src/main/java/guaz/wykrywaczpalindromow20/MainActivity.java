package guaz.wykrywaczpalindromow20;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DbHelper(this);
        Button btn = (Button) findViewById(R.id.button);
        //Zmienne do ListView
        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList<String> data = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        //Wyczyszczenie listy
        myDb.deleteAll();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText wpisanyTekst = (EditText) findViewById(R.id.editText);
                Editable wpis = wpisanyTekst.getText();
                String wpisString = wpis.toString();
                boolean czyPalindrom;
                //Wywołanie isPalindrome
                czyPalindrom = isPalindrome(wpisString);
                TextView poleTekstowe = (TextView) findViewById(R.id.textView);

                if (czyPalindrom == true) {
                    poleTekstowe.setText("Wyrazenie jest palindromem");
                } else {
                    poleTekstowe.setText("Wyrazenie nie jest palindromem");
                }
                boolean isInserted = myDb.insertData(wpisString, czyPalindrom);

                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Wpis zapisano.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Wpis nie udało się zapisać.", Toast.LENGTH_SHORT).show();
                }

                myDb.getReadableDatabase();
                Cursor c = myDb.getData();
                String id = "";
                String content = "";
                String result = "";
                int resultInt = 0;

                while(c.moveToNext()) {
                    id = c.getString(0);
                    content = c.getString(1);
                    resultInt = c.getInt(2);
                }

                if (resultInt == 1) {
                    // Prawda
                    result = "palindrom";
                } else {
                    // Falsz
                    result = "nie palindrom";
                }

                data.add(id + " - " + content + " - " + result);
                adapter.notifyDataSetChanged();
                }
        }
    );

    // Sprawdzenie czy tekst jest Palindromem
    }
    public static boolean isPalindrome(String check){
        boolean palindrom = false;
        check = check.replaceAll("\\s+","");
        char[] tablica = check.toCharArray();

        int i, j;
        for (i = 0, j = (check.length() - 1); i<check.length(); i++, j--)
            if (tablica[i] != tablica[j]) {
                return false;
            } else {
                palindrom = true;
            }
        return palindrom;
    }
}