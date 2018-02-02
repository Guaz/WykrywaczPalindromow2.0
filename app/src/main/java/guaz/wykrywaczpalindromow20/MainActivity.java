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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.listView)
    ListView listView;

    private DbHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myDb = new DbHelper(this);
        final ArrayList<String> data = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

        myDb.deleteAll();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wpisString = editText.getText().toString();
                boolean isPalindrom;
                //Wywołanie isPalindrome
                isPalindrom = isPalindrome(wpisString);

                if (isPalindrom == true) {
                    textView.setText("Wyrazenie jest palindromem");
                } else {
                    textView.setText("Wyrazenie nie jest palindromem");
                }
                boolean isInserted = myDb.insertData(wpisString, isPalindrom);

                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Wpis zapisano.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Wpis nie udało się zapisać.", Toast.LENGTH_SHORT).show();
                }

                myDb.getReadableDatabase();
                Cursor c = myDb.getData();
                String id = "";
                String content = "";
                String result;
                int resultInt = 0;

                while(c.moveToNext()) {
                    id = c.getString(0);
                    content = c.getString(1);
                    resultInt = c.getInt(2);
                }

                if (resultInt == 1) {
                    result = "palindrom";
                } else {
                    result = "nie palindrom";
                }

                data.add(id + " - " + content + " - " + result);
                adapter.notifyDataSetChanged();
                }
        }
    );

    }
    public boolean isPalindrome(String check){
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