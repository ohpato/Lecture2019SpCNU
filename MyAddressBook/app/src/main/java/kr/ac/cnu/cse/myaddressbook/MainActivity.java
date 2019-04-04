package kr.ac.cnu.cse.myaddressbook;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void deleteAllAddresses (View view) {
        String URL = "content://com.example.user.provider.Address/friends";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(
                friends, null, null);
        String countNum = "AddressProvider: "+ count +" records are deleted.";
        Toast.makeText(getBaseContext(),
                countNum, Toast.LENGTH_LONG).show();
    }

    public void addAddress(View view) {
        ContentValues values = new ContentValues();
        EditText emailText = (EditText)findViewById(R.id.email);
        EditText nameText = (EditText)findViewById(R.id.name);
        EditText postText = (EditText)findViewById(R.id.postaladdr);
        EditText phoneText = (EditText)findViewById(R.id.phone);

        values.put(AddressProvider.EMAIL,  emailText.getText().toString());
        values.put(AddressProvider.NAME, (nameText).getText().toString());
        values.put(AddressProvider.POSTALADDR, (postText).getText().toString());
        values.put(AddressProvider.PHONE, (phoneText).getText().toString());

        Uri uri = getContentResolver().insert( AddressProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "AddressProvider: " + uri.toString()
                + " inserted!", Toast.LENGTH_LONG).show();

        emailText.setText("");
        nameText.setText("");
        postText.setText("");
        phoneText.setText("");
    }

    public void showAllAddresses(View view) {
        String URL = "content://kr.ac.cnu.cse.myaddressbook.provider/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name"); // sorted by name
        String result = "AddressProvider Results:";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" +
                        c.getString(c.getColumnIndex(AddressProvider.NAME)) +
                        " with id " + c.getString(c.getColumnIndex(AddressProvider.ID)) +
                        " has name " +  c.getString(c.getColumnIndex(AddressProvider.NAME)) +
                        " has postaladdr " +  c.getString(c.getColumnIndex(AddressProvider.POSTALADDR)) +
                        " has phone: " + c.getString(c.getColumnIndex(AddressProvider.PHONE));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }




}
