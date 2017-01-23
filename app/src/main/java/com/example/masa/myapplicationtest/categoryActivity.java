package com.example.masa.myapplicationtest;

/**
 * Created by masa on 2016/12/31.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.app.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.widget.*;


public class categoryActivity extends AppCompatActivity {

    ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadActivity();

    }
    private void loadActivity(){
        setContentView(R.layout.activity_category);
        LinearLayout ll = (LinearLayout) findViewById(R.id.activity_category);
        ListView lv = new ListView(this);
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);  //データベースオブジェクトの生成
        String qry3 = "SELECT * FROM site1";  //データ選択のクエリ
        Cursor cr = db.rawQuery(qry3, null);  //クエリ結果をカーソルで受け取り

        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);  //結果出力のリスト
        while(cr.moveToNext()){  //カーソルを一つづつ動かしデータを取得
            int i = cr.getColumnIndex("id");  //データをテーブルの要素ごとに取得
            int c = cr.getColumnIndex("Category");
            int id = cr.getInt(i);

            String Category = cr.getString(c);
            String row = "Category:"+ Category;
            ad.add(row);
        }

        lv.setAdapter(ad);
        ll.addView(lv);
        setContentView(ll);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(categoryActivity.this)
                        .setTitle("確認")
                        .setMessage("削除")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView list =(ListView)parent;
                                String selectedItem = (String) list.getItemAtPosition(position);
                                String Item[] = selectedItem.split(":");
                                String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
                                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
                                String qry2 ="DELETE FROM site1 where Category = '"+Item[1]+"' ";
                                db.execSQL(qry2);
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
        db.close();  //データベースのクローズ

    }

    public void add(View view){
        final EditText editView = new EditText(categoryActivity.this);
        new AlertDialog.Builder(categoryActivity.this)
                .setTitle("カテゴリの新規追加")
                .setView(editView)
                .setPositiveButton("追加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Toast.makeText(categoryActivity.this,
                                editView.getText().toString(),
                                Toast.LENGTH_LONG).show();*/
                        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
                        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
                        String qry2 ="INSERT INTO site1(Category) VALUES ('"+editView.getText().toString()+"')";
                        db.execSQL(qry2);
                        db.close();
                        loadActivity();
                    }
                })
                .show();
    }
}
