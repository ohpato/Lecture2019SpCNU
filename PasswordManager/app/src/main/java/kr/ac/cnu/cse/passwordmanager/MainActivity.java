package kr.ac.cnu.cse.passwordmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {


    private EditText field_name;
    private EditText field_password;


    /* Shared preferences indexes */
    public static final String NAME = "NAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String IV = "IV";
    public static final String SAVEDPREFS = "SavedPrefsFile";

    private static final String keystr = "HelloCNUCSE!abcd"; /* 16 characters */
    private static final String CHARSET = "UTF-8";

    /* initial vector does not need to be kept secret,
    however must not be reused with the same key */
    private static final byte[] ivBytes = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
            0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences password_preferences =
                getSharedPreferences(SAVEDPREFS, Context.MODE_PRIVATE);

        /* Identify the UI components */
        field_name = (EditText) findViewById(R.id.name);
        field_password = (EditText) findViewById(R.id.password);

        /* Restore the preferences if they already exist*/
        String saved_name = password_preferences.getString(NAME, "");
        String saved_password = password_preferences.getString(PASSWORD, "");

        try {
            SecretKeySpec keyspec = AESHelperVer1.generateKey(keystr);
            if (saved_name != null) {
                byte[] savedname_bytes = saved_name.getBytes(CHARSET);
                byte[] decodedSavedNameBytes = Base64.decode(savedname_bytes, Base64.NO_WRAP);
                String decrypted_name = new String(AESHelperVer1.decrypt2(
                        keyspec, ivBytes, decodedSavedNameBytes));
                field_name.setText(decrypted_name);
            }
            if (saved_password != null) {
                byte[] savedpassword_bytes = saved_password.getBytes(CHARSET);

                byte[] decodedSavedPassBytes = Base64.decode(savedpassword_bytes, Base64.NO_WRAP);
                String decrypted_pass = new String(AESHelperVer1.decrypt2(
                        keyspec, ivBytes, decodedSavedPassBytes));
                field_password.setText(decrypted_pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        /* Save Button */
        final Button saveButton = findViewById(R.id.savebtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences password_preferences =
                        getSharedPreferences(SAVEDPREFS, Context.MODE_PRIVATE);

                try {
                    SecretKeySpec keyspec = AESHelperVer1.generateKey(keystr);

                    /* encrypt name */
                    String name_in = field_name.getText().toString();
                    if (name_in != null && !name_in.isEmpty()) {
                        byte[] encryptedNameBytes = AESHelperVer1.encrypt2(
                                keyspec, ivBytes, name_in.getBytes(CHARSET));
                        String encryptedNameString = Base64.encodeToString(
                                encryptedNameBytes, Base64.NO_WRAP);
                        password_preferences.edit().putString(NAME, encryptedNameString).commit();

                    }

                    /* encrypt password */
                    String password_in = field_password.getText().toString();
                    if (password_in != null && !password_in.isEmpty()) {
                        byte[] encryptedPassBytes = AESHelperVer1.encrypt2(
                                keyspec, ivBytes, password_in.getBytes(CHARSET));
                        String encryptedPassString = Base64.encodeToString(
                                encryptedPassBytes, Base64.NO_WRAP);
                        password_preferences.edit().putString(
                                PASSWORD, encryptedPassString).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        /* Check Preference Button */
        final Button checkPrefButton = findViewById(R.id.chkprfbtn);
        checkPrefButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences password_preferences =
                        getSharedPreferences(SAVEDPREFS, Context.MODE_PRIVATE);

                String savedname = password_preferences.getString(NAME, null);
                String savedpassword = password_preferences.getString(PASSWORD, null);
                try {
                    SecretKeySpec keyspec = AESHelperVer1.generateKey(keystr);

                    /* decrypt name */
                    byte[] savedname_bytes = savedname.getBytes(CHARSET);
                    byte[] decodedbytes_savedname =
                            Base64.decode(savedname_bytes, Base64.NO_WRAP);
                    String decrypted_name = new String(AESHelperVer1.decrypt2(
                            keyspec, ivBytes, decodedbytes_savedname));

                    /* decrypt password */
                    byte[] savedpass_bytes = savedpassword.getBytes(CHARSET);
                    byte[] decodedbytes_savedpass = Base64.decode(
                            savedpass_bytes, Base64.NO_WRAP);
                    String decrypted_pass = new String(AESHelperVer1.decrypt2(
                            keyspec, ivBytes, decodedbytes_savedpass));

                    Toast.makeText(MainActivity.this,
                            decrypted_name + ":" + decrypted_pass, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
