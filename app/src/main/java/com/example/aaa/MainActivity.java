package com.example.aaa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    //    private LocationManager locationManager;
//    private LocationListener locationListener;
    Button btnSearch;
    EditText edDiaDiem;
    TextView tvCity, tvCountry, tvCencius, tvStatus, tvMay, tvMua, tvGio, tvDate;
    ImageView imgIcon;
    String City = "";
    String key = "211ff006de9aba9ddd122331f87cdf8b";
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> mangthoitiet;
//    private boolean checkRequiredPermissions(){
//        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
//        if(!EasyPermissions.hasPermissions(this,perms)){
//            EasyPermissions.requestPermissions(this,"ServiceDemo",20000,perms);
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Weather");     lv = (ListView) findViewById(R.id.listview);

        mangthoitiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(mangthoitiet, MainActivity.this);
        lv.setAdapter(customAdapter);
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//            double lat = location.getLatitude();
//            double lon = location.getLongitude();
//
//            Log.d("","onLocationChanged: "+lat+"lon: "+ lon);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//        }
//        checkRequiredPermissions();
//        String provider = LocationManager.GPS_PROVIDER;
//        locationManager.requestLocationUpdates(provider, 2000, 1, locationListener);


        addControls();
        addEvents();
        customAdapter.notifyDataSetChanged();
    }

    private void addControls() {
        edDiaDiem = findViewById(R.id.edSearch);
        btnSearch = findViewById(R.id.btnSearch);
        tvCity = findViewById(R.id.tvCity);
        tvCountry = findViewById(R.id.tvCountry);
        tvCencius = findViewById(R.id.tvCencius);
        tvStatus = findViewById(R.id.tvStatus);
        tvMay = findViewById(R.id.tvMay);
        tvMua = findViewById(R.id.tvMua);
        tvGio = findViewById(R.id.tvGio);
        imgIcon = findViewById(R.id.imgIcon);
        tvDate = findViewById(R.id.tvDate);
    }

    private void addEvents() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edDiaDiem.getText().toString().trim();
                if (city.equals("")) {
                    City = "Hanoi";
                    GetCurrentWeatherData(City);
                } else {
                    City = city;
                    GetCurrentWeatherData(City);
                }

            }
        });
    }

    public void GetCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=" + key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            tvCity.setText("Ten thanh pho :" + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l * (1000L));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = dateFormat.format(date);
                            tvDate.setText(Day);
                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonWeather = jsonArray.getJSONObject(0);
                            String status = jsonWeather.getString("main");
                            String icon = jsonWeather.getString("icon");
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/" + icon + ".png").resize(150, 150).into(imgIcon);
                            tvStatus.setText(status);
                            JSONObject jsonMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonMain.getString("temp");
                            String doAm = jsonMain.getString("humidity");
                            Double a = Double.valueOf(nhietDo);
                            nhietDo = String.valueOf(a.intValue());
                            tvCencius.setText(nhietDo + "oC");
                            tvMua.setText(doAm + "%");

                            JSONObject jsonWind = jsonObject.getJSONObject("wind");
                            String gio = jsonWind.getString("speed");
                            tvGio.setText(gio + "m/s");

                            JSONObject jsonClouds = jsonObject.getJSONObject("clouds");
                            String may = jsonClouds.getString("all");
                            tvMay.setText(may + "");

                            JSONObject jsonSys = jsonObject.getJSONObject("sys");
                            String country = jsonSys.getString("country");
                            tvCountry.setText("Quốc gia: " + country);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
    private void Get7DayData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast/daily?q=" + data + "&units=metric&cnt=7&appid=" + key;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1City = jsonObject.getJSONObject("city");
                            String name = jsonObject1City.getString("name");


                            JSONArray jsonArrayList = jsonObject.getJSONArray("List");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObject1List = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObject1List.getString("dt");

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l * (1000L));
                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                                String Day = dateFormat.format(date);

                                JSONObject jsonObject1Temp = jsonObject1List.getJSONObject("temp");
                                String max = jsonObject1Temp.getString("max");
                                String min = jsonObject1Temp.getString("min");
                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String nhietDoMax = String.valueOf(a.intValue());
                                String nhietDoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObject1List.getJSONArray("weather");
                                JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObject1Weather.getString("description");
                                String icon = jsonObject1Weather.getString("icon");

                                mangthoitiet .add(new ThoiTiet(Day, status, icon, nhietDoMax, nhietDoMin));
                            }
                            lv.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}

