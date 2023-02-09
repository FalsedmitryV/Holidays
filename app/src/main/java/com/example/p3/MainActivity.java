package com.example.p3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3.model.ListHolidaysInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataFromInternet.AsyncResponse, MyAdapter.ListItemClickListener
{
    private static final String TAG = "MainActivity";
    private Toast toast;
    private ListHolidaysInfo listHolidaysInfo;
    //private TextView listOfHolidays;
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            URL url = new URL("https://calendarific.com/api/v2/holidays?api_key=f23942345087a9224ae8e8893c49f7fb98a6d52c&country=RU&year=2021");

            new GetDataFromInternet(this).execute(url);

        } catch(MalformedURLException e){
            e.printStackTrace();
        }

        // listOfHolidays = findViewById(R.id.listOfHolidays);
    }

    @Override
    public void proccessFinish(String output)
    {
        Log.d(TAG, "proccessFinish: " + output);
        // listOfHolidays.setText(output);

        try{
            JSONObject outputJSON = new JSONObject(output);
            JSONObject responseJSON = outputJSON.getJSONObject("response");
            JSONArray array = responseJSON.getJSONArray("holidays");
            int length = array.length();

            listHolidaysInfo = new ListHolidaysInfo(length);
            ArrayList<String> namesHolidays = new ArrayList<>();

            for(int i = 0; i < length; i++)
            {
                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");

                JSONObject ob_date = obj.getJSONObject("date");
                String data_iso = ob_date.getString("iso");

                namesHolidays.add(name);
                Log.d(TAG, "proccessFinish:" + name + " " + data_iso);
                listHolidaysInfo.addHoliday(name, data_iso, i);
            }

           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, namesHolidays);
            ListView listHolidays = findViewById(R.id.listHolidays);

            listHolidays.setAdapter(adapter); */

            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new MyAdapter(listHolidaysInfo, length, this));


        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        CharSequence text = listHolidaysInfo.ListHolidaysInfo[clickedItemIndex].getHoliday_name(); //listHolidaysInfo[clickedItemIndex].getHoliday_name();
        int duration = Toast.LENGTH_SHORT;
        if(toast != null)
        {
            toast.cancel();
        }

        toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}