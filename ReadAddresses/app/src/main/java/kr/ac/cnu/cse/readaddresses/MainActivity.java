package kr.ac.cnu.cse.readaddresses;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void readAddresses(View view) {
        // Show all the addresses sorted by friend's name
        String URL = "content://kr.ac.cnu.cse.myaddressbook.provider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name");
        String result = "AddressProvider Results:";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" +
                        c.getString(c.getColumnIndex("name")) +
                        " with id " + c.getString(c.getColumnIndex("id")) +
                        " has name " +  c.getString(c.getColumnIndex("name")) +
                        " has postaladdr " +
                        c.getString(c.getColumnIndex("postaladdr")) +
                        " has phone: " +  c.getString(c.getColumnIndex("phone"));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }

}
