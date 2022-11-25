package hu.petrik.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonListazas, buttonUjfelvetele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonListazas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Listázás xml megnyitása
            }
        });
        buttonUjfelvetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Insert cml megnyitása
            }
        });
    }
    private void init(){
        buttonListazas = findViewById(R.id.buttonListazas);
        buttonUjfelvetele = findViewById(R.id.buttonUjfelvetele);
    }
}