package kr.ac.cnu.cse.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SAVEDPREFS = "SavedPrefsFile";
    public static final String NAME = "NAME";
    public static final String PASSWORD = "PASSWORD";

    private EditText field_name;
    private EditText filed_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field_name = (EditText) findViewById(R.id.name);
        filed_pass = (EditText) findViewById(R.id.password);
        SharedPreferences settings = getSharedPreferences(SAVEDPREFS, 0);
        String name = settings.getString(NAME, "");
        String password = settings.getString(PASSWORD, "");
        field_name.setText(name);
        filed_pass.setText(password);

        final Button saveButton = findViewById(R.id.savebtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                SharedPreferences settings = getSharedPreferences(SAVEDPREFS, Context.MODE_PRIVATE);
                settings.edit()
                        .putString(NAME, field_name.getText().toString())
                        .putString(PASSWORD, filed_pass.getText().toString())
                        .commit();
            }
        });

        final Button readButton = findViewById(R.id.readbtn);
        readButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                SharedPreferences saved_settings = getSharedPreferences(SAVEDPREFS, Context.MODE_PRIVATE);
                String savedname = saved_settings.getString(NAME, null);
                String savedpassword = saved_settings.getString(PASSWORD, null);

                Toast.makeText(MainActivity.this, savedname + ":" + savedpassword, Toast.LENGTH_SHORT).show();
            }
        });

    }



}
