package com.example.masa.myapplicationtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.view.View;
import android.widget.*;

/**
 * Created by masa on 2016/12/05.
 */

public class listActivity extends AppCompatActivity{

    ListView lv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();
    }
    private void loadActivity(){
        setContentView(R.layout.activity_list);
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_list);
        setContentView(ll);

        ListView lv = new ListView(this);
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);  //データベースオブジェクトの生成

        String qry3 = "SELECT * FROM site";  //データ選択のクエリ

        Cursor cr = db.rawQuery(qry3, null);  //クエリ結果をカーソルで受け取り
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);  //結果出力のリスト

        while(cr.moveToNext()){  //カーソルを一つづつ動かしデータを取得
            int i = cr.getColumnIndex("id");  //データをテーブルの要素ごとに取得
            int n = cr.getColumnIndex("name");
            int p = cr.getColumnIndex("time");
            int c = cr.getColumnIndex("Category");
            int id = cr.getInt(i);
            String name = cr.getString(n);
            String time = cr.getString(p);
            String Category = cr.getString(c);
            String row = "予定:" + name + "\n" +"締め切り日: "+ time+"\nCategory:"+ Category;
            ad.add(row);
        }
        lv.setAdapter(ad);
        ll.addView(lv);
        db.close();  //データベースのクローズ
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(listActivity.this)
                        .setTitle("確認")
                        .setMessage("予定は終了しましたか")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView list =(ListView)parent;
                                String selectedItem = (String) list.getItemAtPosition(position);
                                String Item[] = selectedItem.split("\n");
                                String name[]=Item[0].split(":");
                                String time[]=Item[1].split(":");
                                String Category[]=Item[2].split(":");
                                /*Toast.makeText(getApplicationContext(), name[1],
                                        Toast.LENGTH_LONG).show();*/
                                String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
                                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
                                String qry2 ="DELETE FROM site where name = '"+name[1]+"' ";
                                db.execSQL(qry2);
                                db.close();
                                loadActivity();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which){
                            }
                        })
                        .show();
            }
        });
    }
}
