package com.example.masa.myapplicationtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by masa on 2016/11/29.
 */
public class SubActivity extends Activity {

    CalendarView cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadActivity();
    }
    private void loadActivity(){
        setContentView(R.layout.activity_next);
        Spinner sp =(Spinner)this.findViewById(R.id.spinner);
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);  //データベースオブジェクトの生成
        String qry3 = "SELECT * FROM site1";  //データ選択のクエリ
        Cursor cr = db.rawQuery(qry3, null);  //クエリ結果をカーソルで受け取り
        List<String> list = new ArrayList<String>();
        while(cr.moveToNext()){  //カーソルを一つづつ動かしデータを取得
            int i = cr.getColumnIndex("id");  //データをテーブルの要素ごとに取得
            int c = cr.getColumnIndex("Category");
            int id = cr.getInt(i);

            String Category = cr.getString(c);
            list.add(Category);
        }
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);  //テキストをリストビュー、スピナーに渡すためのアレイアダプター
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //アレイアダプターにドロップダウンリストを設定
        sp.setAdapter(ad);  //スピナーにアレイアダプターを登録

        cal = (CalendarView) findViewById(R.id.calendarView);

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                TextView tv= (TextView) findViewById(R.id.editText3);
                tv.setText(year+"年"+(month+1)+"月"+dayOfMonth+"日");
            }
        });
    }


    public void todosave(View view){
        TextView tv= (TextView) findViewById(R.id.editText);
        String Title = tv.getText().toString();
        TextView tv1= (TextView) findViewById(R.id.editText3);
        String limit = tv1.getText().toString();
        Spinner sp = (Spinner)findViewById(R.id.spinner);
        String category = (String)sp.getSelectedItem();
        String str = "data/data/" + getPackageName() + "/Sample.db";  //データベースの保存先の指定
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
        String qry2 ="INSERT INTO site(name, time, Category) VALUES ('"+Title+"', '"+limit+"', '"+category+"')";
        db.execSQL(qry2);
        db.close();

        new AlertDialog.Builder(SubActivity.this)
                .setTitle("title")
                .setMessage("保存しました。\nまだ続けますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadActivity();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whhich){
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

}

