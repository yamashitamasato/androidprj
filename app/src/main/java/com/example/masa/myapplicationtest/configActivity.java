package com.example.masa.myapplicationtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

/**
 * Created by masa on 2016/12/15.
 */

public class configActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void allsqldelete(View view){
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
        String qry0 = "DROP TABLE IF EXISTS site"; //テーブルリセットのクエリ
        db.execSQL(qry0);
        new AlertDialog.Builder(configActivity.this)
                .setTitle("データ全削除")
                .setMessage("データを全て削除しました。\nタイトルに戻ります。")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

    }
    public void categoryconf(View view){
        Intent intent =new Intent(getApplication(), categoryActivity.class);
        startActivity(intent);
    }
}
