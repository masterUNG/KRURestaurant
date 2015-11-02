package appewtc.masterung.krurestaurant;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private ManageTABLE objManageTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connected Database
        //objManageTABLE = new ManageTABLE(this);

        //Tester
        //testAddValue();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method

    private void synJSONtoSQLite() {

        //1. Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);


        int intTimes = 1;
        while (intTimes <= 2) {


            //2. Create InputStream

            //3. Create JSON String

            //4. Update to SQLite


            intTimes += 1;
        }   // while

    }   // synJSONtoSQLite

    private void testAddValue() {
        objManageTABLE.addNewUser("testUser", "testPass", "testName");
        objManageTABLE.addNewFood("testFood", "testSource", "testPrice");
    }

}   // Main Class
