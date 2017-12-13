package com.example.amandaabalos.bobcatparkingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;


public class ParkingInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    BarChart barGraph;
    LotUpdater updater;
    ParkingLot to_display;
    int[] averages;
    String[] days;


    //Don't modify this, code required to change the values of the x-axis
    class XAxisValueFormatter implements IAxisValueFormatter{
        private String[] xValues;
        public XAxisValueFormatter(String[] values){
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return xValues[(int)value];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_info);

        days = new String[]{getString(R.string.mon), getString(R.string.tue), getString(R.string.wed), getString(R.string.thu), getString(R.string.fri)};
        updater = new LotUpdater();

        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest")) {
            to_display = (ParkingLot)getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.lotrequest");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info
            TextView name = (TextView)findViewById(R.id.lotname);
            name.setText(to_display.getName());

            TextView permits = (TextView)findViewById(R.id.permitlist);
            permits.setText(to_display.getPermitsComma());

            TextView dist = (TextView)findViewById(R.id.distance);
            dist.setText(Double.toString(to_display.getDist()));

            TextView cap = (TextView)findViewById(R.id.current);
            //cap.setText(Integer.toString(to_display.curr_capacity));
            cap.setText(" : " + Integer.toString(to_display.curr_capacity) + "/" + Integer.toString(to_display.getMax_capacity()));

            //Bar graph class to display day/capacity info from database.
            barGraph = (BarChart)findViewById(R.id.bargraph);
            barGraph.setDrawBarShadow(false);
            barGraph.setDrawValueAboveBar(true);
            barGraph.setDrawGridBackground(true);

            //Sets x-axis values. currently there are only 5 days of the week.
            String[] times = new String[]{"8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM"};
            XAxis xAxis = barGraph.getXAxis();
            xAxis.setValueFormatter(new XAxisValueFormatter(times));

            fillBar("Monday");

            //Getting the instance of Spinner and applying OnItemSelectedListener on it
            Spinner daysSpinner = (Spinner) findViewById(R.id.daysSpinner);
            daysSpinner.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the bank name list
            ArrayAdapter daysAA = new ArrayAdapter(this, android.R.layout.simple_spinner_item, days);
            daysAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            daysSpinner.setAdapter(daysAA);

        }
    }
    //fill the bar chart with the given day's info
    public void fillBar(String day){
        //Sets bar values. this will be replaced with code that retrieves info from the database
        //for now, there are just test values
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 48f));//test entries
        entries.add(new BarEntry(2, 45f));
        entries.add(new BarEntry(3, 41f));
        entries.add(new BarEntry(4, 43f));
        entries.add(new BarEntry(5, 38f));
        entries.add(new BarEntry(6, 38f));
        entries.add(new BarEntry(7, 7f));
        entries.add(new BarEntry(8, 12f));
        entries.add(new BarEntry(9, 5f));
        entries.add(new BarEntry(10, 21f));
        entries.add(new BarEntry(11, 31f));
        entries.add(new BarEntry(12, 32f));
        entries.add(new BarEntry(13, 21f));

        //This code will be used with the database, it essentially queries the database for a day's average, populates an array
        // with each hourly average, and initializes the bar graph with those values
        /*
        averages = updater.update(to_display, day);
        for(int i = 0; i < 13; i++){
            entries.add(new BarEntry((i+1), averages[i]));
        } */


        BarDataSet entry_set = new BarDataSet(entries, "Average Capacity");
        entry_set.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data = new BarData(entry_set);

        //visual configurations go here. for documentation: https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
        data.setBarWidth(0.2f);

        barGraph.setData(data);
    }

    //Performing action onItemSelected and onNothing selected
    //@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        //empties the current bargraph and replaces it with the requested dataset
        BarData to_remove = barGraph.getBarData();
        to_remove.removeDataSet(0);
        if(item == days[0]){
            fillBar(days[0]);
        }
        else if(item == days[1]){
            fillBar(days[1]);
        }
        else if(item == days[2]){
            fillBar(days[2]);
        }
        else if(item == days[3]){
            fillBar(days[3]);
        }
        else if(item == days[4]){
            fillBar(days[4]);
        }

        barGraph.notifyDataSetChanged();
        barGraph.invalidate();
    }

    //@Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

