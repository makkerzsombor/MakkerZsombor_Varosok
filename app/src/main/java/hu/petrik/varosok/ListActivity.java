package hu.petrik.varosok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private Button buttonVissza;
    private TextView listaHelye;
    private String baseUrl = "https://retoolapi.dev/CaBXyP/varosok";
    private List<Varos> varosok = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();
        buttonVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void init(){
        buttonVissza = findViewById(R.id.buttonVissza);
        listaHelye = findViewById(R.id.listaHelye);
        listaHelye.setMovementMethod(new ScrollingMovementMethod());
        RequestTask task= new RequestTask("GET");
        task.execute();
    }

    private class RequestTask extends AsyncTask<Void, Void, Response>{
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
                switch (requestMethod){
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

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Response response){
            Gson converter = new Gson();
            super.onPostExecute(response);
            if(response == null){
                Toast.makeText(ListActivity.this, "unable_to_connect", Toast.LENGTH_SHORT).show();
                return;
            }
            if(response.getResponseCode() >= 400){
                Toast.makeText(ListActivity.this, response.getContent(), Toast.LENGTH_SHORT).show();
                return;
            }
            switch (requestMethod){
                case "GET":
                    String content = response.getContent();
                    varosok = Arrays.asList(converter.fromJson(content, Varos[].class));
                    System.out.println("!"+varosok);
                    listaHelye.setText(varosok.toString());
                    break;
                default:
                    if (response.getResponseCode() >= 201 && response.getResponseCode() < 300){
                        listaHelye.setText("");
                        RequestTask task = new RequestTask(baseUrl);
                        task.execute();
                    }
                    break;
            }
        }
    }


}