package com.example.mycashdebt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int add;
    TotalCash totalCash = new TotalCash();
    EditText debtEditText, proceedsEditText, spendingEditText;
    TextView debtTextView, proceedsTextView, spendingTextView;


    private final static String DATABASE_NAME = "Test";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "Money";

    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "OperationName";
    private final static String COLUMN_VALUE = "Value";

    private final static int NUM_COLUMN_ID = 0; //номера столбцов для использования курсора
    private final static int NUM_COLUMN_NAME = 1;
    private final static int NUM_COLUMN_VALUE = 2;

    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         debtEditText = findViewById(R.id.debtEditText);
         proceedsEditText = findViewById(R.id.proceedsEditText);
         spendingEditText = findViewById(R.id.spendingEditText);
         debtTextView = findViewById(R.id.debtTextView);
         proceedsTextView = findViewById(R.id.proceedsTextView);
         spendingTextView = findViewById(R.id.spendingTextView);
        totalCash.upgrade();
        OpenHelper openHelper = new OpenHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
        database = openHelper.getWritableDatabase();
    }
    public void addDebt(View view){
        add=Integer.parseInt(debtEditText.getText().toString());
        String viewText = debtTextView.getText().toString();
        viewText = viewText.replaceAll("rub", "");
        debtTextView.setText(add+Integer.parseInt(viewText)+"rub");
        debtEditText.setText(null);
        insert(debtTextView, debtEditText, "debt");
        totalCash.upgrade();

    }
    public void addProceeds(View view){
        add=Integer.parseInt(proceedsEditText.getText().toString());
        String viewText = proceedsTextView.getText().toString();
        viewText = viewText.replaceAll("rub", "");
        proceedsTextView.setText(add+Integer.parseInt(viewText)+"rub");
        proceedsEditText.setText(null);
        insert(proceedsTextView, proceedsEditText, "proceeds");
        totalCash.upgrade();
    }
    public void addSpending(View view){
        add=Integer.parseInt(spendingEditText.getText().toString());
        String viewText = spendingTextView.getText().toString();
        viewText = viewText.replaceAll("rub", "");
        spendingTextView.setText(add+Integer.parseInt(viewText)+"rub");
        spendingEditText.setText(null);
        insert(spendingTextView, spendingEditText, "spending");
        totalCash.upgrade();

    }
    public void insert(TextView tv, EditText et, String TABLE_NAME){
        if (!String.valueOf(debtEditText.getText()).equals("")|| !String.valueOf(proceedsEditText.getText()).equals("")||!String.valueOf(spendingTextView.getText()).equals("")) {
            ContentValues cv = new ContentValues(); //позволяет добавлять данные для записи
            cv.put(COLUMN_NAME, tv.getText().toString());
            cv.put(COLUMN_VALUE, Long.valueOf(et.getText().toString()));
            long count = database.insert(TABLE_NAME, null, cv);
            Toast.makeText(this, String.valueOf(count), Toast.LENGTH_SHORT).show();
        }
    }
    public int selectAll(String TABLE_NAME){
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<cashData> people = new ArrayList<>();
        int sum = 0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(NUM_COLUMN_ID);
                String name = TABLE_NAME;
                int value = cursor.getInt(NUM_COLUMN_VALUE);
                cashData cd = new cashData(id, name, value);
                people.add(cd);
                sum += value;
            } while (cursor.moveToNext());
        }
        return sum;

    }
    class TotalCash{
        void upgrade(){
            int spending = selectAll("spending");
            int debt = selectAll("debt");
            int proceeds = selectAll("proceeds");
            int total = -Math.max(spending,debt)+proceeds;
            TextView totalCash = findViewById(R.id.totalCash);

            if (total<0){
                totalCash.setTextColor(getResources().getColor(R.color.colorNotStonks));
                totalCash.setText("-"+total*-1+"rub");
            }
            else{
                totalCash.setTextColor(getResources().getColor(R.color.colorStonks));
                totalCash.setText("+"+total+"rub");
            }
        }
    }
}
