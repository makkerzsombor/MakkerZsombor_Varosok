package hu.petrik.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

public class InsertActivity extends AppCompatActivity {
    private Button buttonVissza, buttonFelvetel;
    private EditText bekertNev, bekertOrszag, bekertLakossag;
    private String baseUrl = "https://retoolapi.dev/CaBXyP/varosok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
        buttonVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buttonFelvetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bekertNev.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "Nem adott meg város nevet!", Toast.LENGTH_SHORT).show();
                } else if (bekertOrszag.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "Nem adott meg országot", Toast.LENGTH_SHORT).show();
                } else if (bekertLakossag.getText().toString().equals("")) {
                    Toast.makeText(InsertActivity.this, "Nem adott meglakosságot", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(bekertLakossag.getText().toString()) < 0){
                    Toast.makeText(InsertActivity.this, "A lakosság minimum 0 lehet!", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        String json = createJsonFromFormdata();
                        RequestTask task = new RequestTask(baseUrl, "POST", json);
                        task.execute();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(InsertActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void init() {
        buttonVissza = findViewById(R.id.buttonVissza);
        buttonFelvetel = findViewById(R.id.buttonFelvetel);
        bekertNev = findViewById(R.id.bekertNev);
        bekertOrszag = findViewById(R.id.bekertOrszag);
        bekertLakossag = findViewById(R.id.bekertLakossag);
    }

    private String createJsonFromFormdata() {
        String nev = bekertNev.getText().toString().trim();
        String orszag = bekertOrszag.getText().toString().trim();
        String lakossagText = bekertLakossag.getText().toString().trim();
        int lakossag = Integer.parseInt(lakossagText);
        Varos varos = new Varos(0, nev, orszag, lakossag);
        Gson converter = new Gson();
        return converter.toJson(varos);
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        private String requestUrl;
        private String requestMethod;
        private String requestBody;

        public RequestTask(String requestUrl) {
            this.requestUrl = requestUrl;
            this.requestMethod = "GET";
        }

        public RequestTask(String requestUrl, String requestMethod) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
        }

        public RequestTask(String requestUrl, String requestMethod, String requestBody) {
            this.requestUrl = requestUrl;
            this.requestMethod = requestMethod;
            this.requestBody = requestBody;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestMethod) {
                    case "GET":
                        response = RequestHandler.get(baseUrl);
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestBody);
                        break;
                    case "PUT":
                        response = RequestHandler.put(requestUrl, requestBody);
                        break;
                    case "DELETE":
                        response = RequestHandler.delete(requestUrl);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}