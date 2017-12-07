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

public class ParkingInfo extends AppCompatActivity {
    BarChart barChart;
    LotUpdater updater;
    int[] averages;
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

        updater = new LotUpdater();

        if (getIntent().hasExtra("com.example.amandaabalos.bobcatparkingapp.lotrequest")) {
            ParkingLot to_display = (ParkingLot)getIntent().getExtras().getSerializable("com.example.amandaabalos.bobcatparkingapp.lotrequest");
            //2nd screen is passed the appropriate parking lot object. need to access and display its info
            TextView name = (TextView)findViewById(R.id.lotname);
            name.setText(to_display.getName());

            TextView permits = (TextView)findViewById(R.id.permitlist);
            permits.setText(to_display.getPermitsComma());

            TextView dist = (TextView)findViewById(R.id.distance);
            dist.setText(Double.toString(to_display.getDist()));

            TextView cap = (TextView)findViewById(R.id.current);
            //cap.setText(Integer.toString(to_display.curr_capacity));
            cap.setText(": " + Integer.toString(to_display.curr_capacity) + "/" + Integer.toString(to_display.getMax_capacity()));

            //Bar graph class to display day/capacity info from database.
            barChart = (BarChart)findViewById(R.id.bargraph);
            barChart.setDrawBarShadow(false);
            barChart.setDrawValueAboveBar(true);
            barChart.setDrawGridBackground(true);

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

            /*  //This code will be used with the database, it essentially queries the database for a day's average, populates an array
                //with each hourly average, and initializes the bar graph with those values
            averages = updater.update(to_display, "Monday");
            for(int i = 0; i < averages.size(); i++){
                entries.add(new BarEntry((i+1), float(averages[i]));
             }
             */
            BarDataSet entry_set = new BarDataSet(entries, "Average Capacity");
            entry_set.setColors(ColorTemplate.COLORFUL_COLORS);

            //Sets x-axis values. currently there are only 5 days of the week.
            String[] days = new String[]{"8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM"};
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new XAxisValueFormatter(days));
            BarData data = new BarData(entry_set);
            //visual configurations go here. for documentation: https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started
            data.setBarWidth(0.2f);

            barChart.setData(data);

        }
    }
}
