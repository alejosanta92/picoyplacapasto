package com.example.alejandro.picoyplacapasto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private TextView tx_plca;
    private TextView proximo;
    private TextView tx_pico;
    private TextView tx_aviso;
    String text;

    private static final String[] placas = {"4 - 5", "6 - 7", "8 - 9", "0 - 1", "2 - 3"};
    private static final Integer[] diasFestivos = {1, 9, 79, 103, 104, 121, 149, 170, 177, 184, 201, 219, 233, 289, 310, 317, 342, 359};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String picoYplacaHoy;
        tx_plca = (TextView) findViewById(R.id.tx_plca);
        proximo = (TextView) findViewById(R.id.proximo);
        tx_pico = (TextView) findViewById(R.id.tx_pico);
        tx_aviso = (TextView) findViewById(R.id.tx_aviso);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tx_plca.setText(prefe.getString("placa", "0-1"));

        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);
        if (Arrays.asList(diasFestivos).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {
            picoYplacaHoy = "NO APLICA";
        } else {
            int j = diaAnio;
            while (j > 7) {
                j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? j - 11 : j - 6;
                localCalendar.set(Calendar.DAY_OF_YEAR, j);
            }
            picoYplacaHoy = placas[localCalendar.get(Calendar.DAY_OF_YEAR) - 2];
        }
        tx_pico.setText(" " + picoYplacaHoy + " ");

        localCalendar.set(Calendar.DAY_OF_YEAR, diaAnio);


    }
    public void elegir(View view) {
        Intent i = new Intent(this, miplaca.class );
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        tx_plca.setText(prefe.getString("placa", "0-1"));
        String pico = text;
        Date d = new Date();
        String picoYplacaHoy;
        Calendar localCalendar = Calendar.getInstance();
        int diaAnio = localCalendar.get(Calendar.DAY_OF_YEAR);
        int diaSemana = localCalendar.get(Calendar.DAY_OF_WEEK);

        if (Arrays.asList(diasFestivos).contains(diaAnio) || diaSemana == 1 || diaSemana == 7) {
            picoYplacaHoy = "NO";
        } else {
            int j = diaAnio;
            while (j > 7) {
                j = localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? j - 11 : j - 6;
                localCalendar.set(Calendar.DAY_OF_YEAR, j);
            }
            picoYplacaHoy = placas[localCalendar.get(Calendar.DAY_OF_YEAR) - 2];
        }
        if (picoYplacaHoy.equals(tx_plca.getText().toString()))
        {
            tx_aviso.setText("Tiene Pico y Placa ");
        }
        else {
            tx_aviso.setText("No Tiene Pico y Placa ");
        }

        boolean enc1 = false;

        int pos1 = 2;

        for (int i = 0; i < placas.length && !enc1; i++) {
            if (placas[i].equals(tx_plca.getText())) {
                enc1 = true;
                pos1 = pos1 + i;
            }
        }

        while (pos1 <= diaAnio) {
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            text = String.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK));


            if (text.equals("2")){
                pos1 = pos1 + 11;

            }
            else{
                pos1 = pos1 + 6;
            }

            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
        }
        if (Arrays.asList(diasFestivos).contains(pos1)) {
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
            text = String.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK));
            if (localCalendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY) {

                pos1 = pos1 + 11;
            } else {
                pos1 = pos1 + 6;
            }
            localCalendar.set(Calendar.DAY_OF_YEAR, pos1);
        }

        d = localCalendar.getTime();
        SimpleDateFormat fech = new SimpleDateFormat("EEEE, d' de 'MMMM");
        String fech1 = fech.format(d);
        proximo.setText( fech1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.miPlaca) {
            //Toast.makeText(this,"Se seleccionó la primer opción",Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, miplaca.class );
                startActivity(i);


        }
        if (id==R.id.condiciones) {
           // Toast.makeText(this,"Se seleccionó la segunda opción",Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, acercade.class );
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }


}
