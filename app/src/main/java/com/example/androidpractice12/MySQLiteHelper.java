package com.example.androidpractice12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // 数据库名称和版本
    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1;

    // 表名和列名
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";

    // 创建表的 SQL 语句
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY, " + // 将用户名设为主键，避免重复
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE + " TEXT)";

    // 删除表的 SQL 语句
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_USERS;

    public MySQLiteHelper(@Nullable Context context) {
        // 调用父类构造函数
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 在数据库第一次创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // 当数据库版本升级时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // --- 数据操作方法 ---

    // 添加用户
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_PHONE, user.getPhone());

        // insert 方法返回新行的 ID，如果出错则返回 -1
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    // 获取所有用户
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                userList.add(new User(username, password, phone));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // 删除用户
    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete 方法返回被删除的行数
        int result = db.delete(TABLE_USERS, COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
        return result > 0;
    }
}