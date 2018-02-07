package guaz.wykrywaczpalindromow20;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.imageView)
    ImageView imageView;

    private DbHelper myDb;
    private Boolean isPalindrom;
    private ArrayList<String> data;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getImage();

        myDb = new DbHelper(this);
        data = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        myDb.deleteAll();
    }

    @OnClick(R.id.button)
    public void onClick(View view){

        String wpisString = editText.getText().toString();

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

    @OnClick(R.id.imageView)
    public void refreshImage(View view){
        getImage();
    }

    public void getImage(){
        GlideApp.with(getBaseContext())
                .load("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Palindrom_TENET.svg/120px-Palindrom_TENET.svg.png")
                .fitCenter()
                .placeholder(R.drawable.bmp)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);

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