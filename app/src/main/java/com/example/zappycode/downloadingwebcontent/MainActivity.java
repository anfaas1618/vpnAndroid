package com.example.zappycode.downloadingwebcontent;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText ip_edit;
    TextView resultText;

    String storage =null;
    int result;
    Button btn=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText =findViewById(R.id.textREsult);
        ip_edit=findViewById(R.id.edit_ip);
        btn=findViewById(R.id.goo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadTask task = new DownloadTask();
               String ip=ip_edit.getText().toString();
                try { task.execute(ip);
                 Log.i("tag",storage);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("tag",""+result);
                if (result==1)
                {
                   resultText.setText("may not be a vpn");
                }
                else
                    resultText.setText("it may be a vpn");
               result=0;
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, Void> {

        @SuppressLint("WrongThread")
        @Override
        protected Void doInBackground(String... strings) {
            try {
        String ip=        strings[0];
                Document document=null;

                document = (Document) Jsoup.connect("http://192.168.43.145:4567/scan?ip="+ip).userAgent("Mozilla")
                        .cookie("auth", "token")
                        .timeout(100000).ignoreContentType(true)
                        .get();
                JSONObject obj = new JSONObject(document.toString().replace("<html>\n" +
                        " <head></head>\n" +
                        " <body>","").replace("</body>\n" +
                        "</html>",""));
                result =obj.getInt("ASNBlackListed");
                 //result = Integer.parseInt(document.toString().split("ASNBlackListed\":")[1].split("}")[0]);
                   Log.i("tag",""+result);
              //     JSONObject jsonObject;
                 //  jsonObject=document.toString().
                //storage =document.toString();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                e.printStackTrace();
            }
            return null;
        }
    }
}
