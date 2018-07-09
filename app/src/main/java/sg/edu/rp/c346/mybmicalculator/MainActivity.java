package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.calculate);
        btnReset = findViewById(R.id.reset);
        tvDate = findViewById(R.id.date);
        tvBMI = findViewById(R.id.TotalBMI);
        tvCategory =findViewById(R.id.catergory);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code for the action
                int weight = Integer.valueOf(etWeight.getText().toString());
                float height = Float.valueOf(etHeight.getText().toString());
                float bmi = weight/(height*height);
                Calendar now = Calendar.getInstance();


                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last calculated date: "+ datetime);
                tvBMI.setText("Last calculated BMI: "+ String.valueOf(bmi));

                if(bmi < 18.5){
                    tvCategory.setText("You are underweight");
                }
                else if(bmi> 24.9 && bmi < 30.0){
                    tvCategory.setText("you are overweight");
                }
                else if(bmi > 30.0){
                    tvCategory.setText("you are obese");
                }
                else{
                    tvCategory.setText("your BMI is normal");
                }



            }


        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvBMI.setText("Last calculated BMI: ");
                tvDate.setText("Last calculated Date: ");
                tvCategory.setText("");
                PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().clear().commit();

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        String Sbmi = tvBMI.getText().toString();
        String date = tvDate.getText().toString();
        String catergory = tvCategory.getText().toString();

        // obtain an instance of sharedpreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // obtain an instance of the sharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();
        // add the key-value pair
        prefEdit.putString("sbmi", Sbmi );
        prefEdit.putString("date", date );
        prefEdit.putString("catergory", catergory );
        // call commit() method to save the changes into sharedpreferences
        prefEdit.commit();
    }



    @Override
    protected void onResume() {
        super.onResume();

        //obtain an instance of the sharedpreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String result = prefs.getString("sbmi", "0.0");
        String date = prefs.getString("date","");
        String scatergory = prefs.getString("catergory", "");


        tvBMI.setText( result);
        tvDate.setText(date);
        tvCategory.setText(scatergory);

    }
}

