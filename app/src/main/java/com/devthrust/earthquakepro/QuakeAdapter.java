package com.devthrust.earthquakepro;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class QuakeAdapter extends ArrayAdapter<Earthquakes> {


    public QuakeAdapter(@NonNull Context context, int resource, @NonNull List<Earthquakes> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquakes earthEarthquakes = getItem(position);
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list, parent,false );
        }
        Date date = new Date(earthEarthquakes.getDate());
        TextView mMagnitude = listItemView.findViewById(R.id.tv_magnitude);
        TextView mPlace = listItemView.findViewById(R.id.tv_place);
        TextView mDaTe = listItemView.findViewById(R.id.tv_date);
        TextView mTime = listItemView.findViewById(R.id.tv_time);
        TextView mOffsetPlace = listItemView.findViewById(R.id.tv_offset_place);
        GradientDrawable magnitudeCircle = (GradientDrawable) mMagnitude.getBackground();
        int magnitudeColour = ContextCompat.getColor(getContext(),getMagnitudeColour(earthEarthquakes.getMagnitude()));

        mMagnitude.setText(earthEarthquakes.getMagnitude()+ "");
        mPlace.setText(earthEarthquakes.getPlace());
        mOffsetPlace.setText(formattedString(earthEarthquakes.getPlace()));
        mDaTe.setText(formatDate(date));
        mTime.setText(formatTime(date));
        magnitudeCircle.setColor(magnitudeColour);

        return listItemView;
    }
    int getMagnitudeColour(double magnitude){
        int colour = 0;
        if (magnitude>= 0 && magnitude <2){
            colour = R.color.magnitude1;
        }else if (magnitude>=2 && magnitude<3){
            colour = R.color.magnitude2;
        }else if (magnitude>=3 && magnitude<4){
            colour = R.color.magnitude3;
        }else if (magnitude>=4 && magnitude<5){
            colour = R.color.magnitude4;
        }else if (magnitude>=5 && magnitude<6){
            colour = R.color.magnitude5;
        }else if (magnitude>=6 && magnitude<7){
            colour = R.color.magnitude6;
        }else if (magnitude>=7 && magnitude<8){
            colour = R.color.magnitude7;
        }else if (magnitude>=8 && magnitude<9){
            colour = R.color.magnitude8;
        }else if (magnitude>=9 && magnitude<10){
            colour = R.color.magnitude9;
        }else if (magnitude>=10){
            colour = R.color.magnitude10plus;
        }

        return colour;
    }

    String formatDate(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD MMM, YYYY");
        String date = simpleDateFormat.format(dateObject);
       return date;
    }
    String formatTime(Date dateObject){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        String time = simpleDateFormat.format(dateObject);
        return time;
    }
    String formattedString(String string){
        String str = string;
        return  str;
    }
}
