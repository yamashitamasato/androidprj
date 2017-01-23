package com.example.masa.myapplicationtest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.*;
import static android.support.v7.appcompat.R.styleable.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);  //データベースオブジェクトの生成
        String qry1 = "CREATE TABLE IF NOT EXISTS site" + "(id INTEGER PRIMARY KEY, name STRING, time STRING, Category STRING)";  //テーブル作成のクエリ
        db.execSQL(qry1);
        String qry2 = "CREATE TABLE IF NOT EXISTS site1" + "(id INTEGER PRIMARY KEY,Category STRING)";  //テーブル作成のクエリ
        db.execSQL(qry2);
    }
    public void changeLabel(View view){
        Intent intent = new Intent(getApplication(), SubActivity.class);
        startActivity(intent);
    }
    public void listLabel(View view){
        Intent intent =new Intent(getApplication(), listActivity.class);
        startActivity(intent);
    }
    public void config(View view){
        Intent intent = new Intent(getApplication(),configActivity.class);
        startActivity(intent);
    }
}

