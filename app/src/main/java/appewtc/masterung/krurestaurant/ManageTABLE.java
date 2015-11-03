package appewtc.masterung.krurestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by masterUNG on 11/2/15 AD.
 */
public class ManageTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;

    public static final String USER_TABLE = "userTABLE";
    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_SOURCE = "Source";
    public static final String COLUMN_PRICE = "Price";

    public ManageTABLE(Context context) {

        //Create & Connect Database
        objMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Search User
    public String[] searchUser(String strUser) {

        try {

            String[] strResult = null;
            Cursor objCursor = readSqLiteDatabase.query(USER_TABLE,
                    new String[]{COLUMN_ID, COLUMN_USER, COLUMN_PASSWORD, COLUMN_NAME},
                    COLUMN_USER + "=?",
                    new String[] {String.valueOf(strUser)},
                    null, null, null, null);
            if (objCursor != null) {
                if (objCursor.moveToFirst()) {

                    strResult = new String[4];
                    strResult[0] = objCursor.getString(0);
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);

                }   // if2
            }   // if1

            objCursor.close();
            return strResult;

        } catch (Exception e) {
            return null;
        }

       // return new String[0];
    }



    //Add New Value to UserTABLE
    public long addNewUser(String strUser, String strPassword, String strName) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_NAME, strName);

        return writeSqLiteDatabase.insert(USER_TABLE, null, objContentValues);
    }

    public long addNewFood(String strFood, String strSource, String strPrice) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FOOD, strFood);
        objContentValues.put(COLUMN_SOURCE, strSource);
        objContentValues.put(COLUMN_PRICE, strPrice);

        return writeSqLiteDatabase.insert(FOOD_TABLE, null, objContentValues);
    }


}   // Main Class
