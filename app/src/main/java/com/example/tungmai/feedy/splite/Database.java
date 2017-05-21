package com.example.tungmai.feedy.splite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.tungmai.feedy.models.ItemFeedyList;
import com.example.tungmai.feedy.models.ItemPrepare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by TungMai on 5/14/2017.
 */

public class Database {

    private static final String DB_NAME = "feedy.sqlite";
    private static final String PATH = Environment.getDataDirectory() +
            "/data/com.example.tungmai.feedy/databases/" + DB_NAME;
    private static final String TABLE_NAME = "prepare";
    private static final String TABLE_NAME_FEEDY = "feedy";
    private static final String NAME_FEEDY = "name_feedy";
    private static final String IMAGE_FEEDY = "image_feedy";
    private static final String NAME_ID = "name_id";
    private static final String NAME_USER = "name_user";
    private static final String NAME_IMAGE = "name_image";

    private static final String PREPARE_ID = "id";
    private static final String CONTENT = "content";
    private static final String QUANTITY = "quatity";
    private static final String UNIT = "unit";
    private static final String CHECK = "check_prepare";

    private Context context;
    private SQLiteDatabase database;

    public Database(Context context) {
        this.context = context;
        copyDataBase(context);
    }

    private void openDatabase() {
        database = context.openOrCreateDatabase(DB_NAME, context.MODE_PRIVATE, null);
    }

    private void closeDatabase() {
        database.close();

    }

    private void copyDataBase(Context context) {
        try {
            File file = new File(PATH);
            if (file.exists()) {
//                Log.e("fsda", "aafdsa");
                return;
            }
//            Log.e("fsda", "ffff");
            File parent = file.getParentFile();
            parent.mkdirs();
            FileOutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = context.getAssets().open(DB_NAME);
            byte[] b = new byte[1024];
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                count = inputStream.read(b);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ItemPrepare> getDataPrepare() {
        openDatabase();
        ArrayList<ItemPrepare> hocSinh = new ArrayList<ItemPrepare>();
//        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(sql, null);
        int indexId = cursor.getColumnIndex(PREPARE_ID);
        int indexContent = cursor.getColumnIndex(CONTENT);
        int indexQuantity = cursor.getColumnIndex(QUANTITY);
        int indexUnit = cursor.getColumnIndex(UNIT);
        int indexCheck = cursor.getColumnIndex(CHECK);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indexId);
            String content = cursor.getString(indexContent);
            String quantity = cursor.getString(indexQuantity);
            String unit = cursor.getString(indexUnit);
            String checkTemp = cursor.getString(indexCheck);
            boolean check = false;
            if (checkTemp.equals("true")) check = true;
            else check = false;
            ItemPrepare hocSinhs = new ItemPrepare(id, content, quantity, unit, check);
            hocSinh.add(hocSinhs);
            cursor.moveToNext();
        }
        closeDatabase();
        return hocSinh;
    }

    public void insertDataPrepare(ArrayList<ItemPrepare> arrItemPrepares) {
        openDatabase();
        for (int i = 0; i < arrItemPrepares.size(); i++) {
            ItemPrepare itemListView = arrItemPrepares.get(i);
            ContentValues contentValues = new ContentValues();
            contentValues.put(PREPARE_ID, itemListView.getId());
            contentValues.put(CONTENT, itemListView.getContent());
            contentValues.put(QUANTITY, itemListView.getQuantity());
            contentValues.put(UNIT, itemListView.getUnit());
            contentValues.put(CHECK, "false");
            database.insert(TABLE_NAME, null, contentValues);
        }
        closeDatabase();
    }

    public void insertDataFeedy(String idFeedy, String nameFeedy, String imageFeedy, String idUser, String nameUser, String imageUser) {
        openDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PREPARE_ID, idFeedy);
        contentValues.put(NAME_FEEDY, nameFeedy);
        contentValues.put(IMAGE_FEEDY, imageFeedy);
        contentValues.put(NAME_ID, idUser);
        contentValues.put(NAME_USER, nameUser);
        contentValues.put(NAME_IMAGE, imageUser);
        database.insert(TABLE_NAME_FEEDY, null, contentValues);
        closeDatabase();
    }

    public void deleteDataPrepare() {
        openDatabase();
        database.delete(TABLE_NAME, null, null);
        closeDatabase();
    }

    public void updateDatePrepare(String idPrepare, boolean value) {
        openDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CHECK, value + "");

        String where = "id=?";
        String[] whereArgs = {idPrepare};

        database.update(TABLE_NAME, cv, where, whereArgs);
        closeDatabase();
    }

    public ArrayList<ItemFeedyList> getDataListFeedy() {
        openDatabase();
        ArrayList<ItemFeedyList> hocSinh = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME_FEEDY;
        Cursor cursor = database.rawQuery(sql, null);
        int indexId = cursor.getColumnIndex(PREPARE_ID);
        int indexNameFeedy = cursor.getColumnIndex(NAME_FEEDY);
        int indexImageFeedy = cursor.getColumnIndex(IMAGE_FEEDY);
        int indexNameId = cursor.getColumnIndex(NAME_ID);
        int indexNameUser = cursor.getColumnIndex(NAME_USER);
        int indexNameImage = cursor.getColumnIndex(NAME_IMAGE);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String id = cursor.getString(indexId);
            String nameFeedy = cursor.getString(indexNameFeedy);
            String imageFeedy = cursor.getString(indexImageFeedy);
            String nameId = cursor.getString(indexNameId);
            String nameUser = cursor.getString(indexNameUser);
            String nameImage = cursor.getString(indexNameImage);
            ItemFeedyList hocSinhs = new ItemFeedyList(id, imageFeedy, nameFeedy, nameId, nameImage, nameUser);
            hocSinh.add(hocSinhs);
            cursor.moveToNext();
        }
        closeDatabase();
        return hocSinh;
    }

    public void deleteDataFeedy(String id) {
        openDatabase();
        String where = "id=?";
        String[] whereArgs = {id};
        database.delete(TABLE_NAME_FEEDY, where, whereArgs);
        closeDatabase();
    }
}
