package appewtc.masterung.krurestaurant;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private ManageTABLE objManageTABLE;
    private EditText userEditText, passEditText;
    private String userString, passString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Connected Database
        objManageTABLE = new ManageTABLE(this);

        //Tester
        //testAddValue();

        //Delete All Data
        deleteAllData();


        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    public void clickLogin(View view) {

        userString = userEditText.getText().toString().trim();
        passString = passEditText.getText().toString().trim();

        if (userString.equals("") || passString.equals("") ) {
            Toast.makeText(MainActivity.this, "Have Space", Toast.LENGTH_LONG).show();
        } else {

            checkUser();

        }

    }

    private void checkUser() {

        try {

            String[] strResult = objManageTABLE.searchUser(userString);

            if (passString.equals(strResult[2])) {

                Intent objIntent = new Intent(MainActivity.this, OrderActivity.class);
                objIntent.putExtra("Name", strResult[3]);
                startActivity(objIntent);
                finish();

            } else {
                Toast.makeText(MainActivity.this, "Password False" , Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "No This User in my Database", Toast.LENGTH_LONG).show();
        }

    }


    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText2);
    }

    private void deleteAllData() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("kru.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("foodTABLE", null, null);
    }

    private void synJSONtoSQLite() {

        //1. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        int intTimes = 1;
        while (intTimes <= 2) {


            //2. Create InputStream
            InputStream objInputStream = null;
            String strJSON = null;
            HttpPost objHttpPost = null;

            try {

                HttpClient objHttpClient = new DefaultHttpClient();

                switch (intTimes) {
                    case 1:
                        objHttpPost = new HttpPost("http://swiftcodingthai.com/kru/php_get_data_master.php");
                        break;
                    case 2:
                        objHttpPost = new HttpPost("http://swiftcodingthai.com/kru/php_get_data_food.php");
                        break;
                    default:
                        objHttpPost = new HttpPost("http://swiftcodingthai.com/kru/php_get_data_master.php");
                        break;

                }   // switch

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();

            } catch (Exception e) {
                Log.d("kru", "InputStream ==> " + e.toString());
            }


            //3. Create JSON String
            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null ) {
                    objStringBuilder.append(strLine);
                }   // while

                objInputStream.close();
                strJSON = objStringBuilder.toString();


            } catch (Exception e) {
                Log.d("kru", "JSON ==> " + e.toString());
            }

            //4. Update to SQLite
            try {

                JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {

                    final JSONObject object = objJsonArray.getJSONObject(i);

                    switch (intTimes) {
                        case 1:

                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            objManageTABLE.addNewUser(strUser, strPassword, strName);

                            break;
                        case 2:

                            String strFood = object.getString("Food");
                            String strSource = object.getString("Source");
                            String strPrice = object.getString("Price");
                            objManageTABLE.addNewFood(strFood, strSource, strPrice);

                            break;
                    }   // switch

                }   // for

            } catch (Exception e) {
                Log.d("kru", "Update ==> " + e.toString());
            }


            intTimes += 1;
        }   // while

    }   // synJSONtoSQLite

    private void testAddValue() {
        objManageTABLE.addNewUser("testUser", "testPass", "testName");
        objManageTABLE.addNewFood("testFood", "testSource", "testPrice");
    }

}   // Main Class
