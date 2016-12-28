package cn.edu.imnu.ciec.rtest22filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText= (EditText) findViewById(R.id.edit);

        String inputText=load();
        Log.d(TAG, inputText);
        if (!TextUtils.isEmpty(inputText)){
            Log.d(TAG, "onCreate: "+"ok");
            editText.setText(inputText);
            editText.setSelection(inputText.length());//光标定位到最后
        }



    }

    private String load() {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();


        try {
            in=openFileInput("inputContent");//文件名
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                         content.append(line);
                Log.d(TAG, "load: run??");
                    }
        }catch (FileNotFoundException e) {
                        e.printStackTrace();
        }catch (IOException e) {
            Log.d(TAG, "load: io nook");
                        e.printStackTrace();
        }finally {
                    try {
                        if (reader != null) {
                             reader.close();
                        }
                   }catch (IOException e){
                     e.printStackTrace();
                    }
          }
        return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText=editText.getText().toString();
        save(inputText);


    }

    private void save(String inputText) {
        FileOutputStream out=null;
        BufferedWriter writer=null;

        //第二个参数是文件操作模式，主要有两种可选模式，
        // MODE_PRIVATE（覆盖）和MODE_APPEND（追加）
        try {
            out=openFileOutput("inputContent", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            try {
                writer.write(inputText);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
                try {
                    if(writer!=null){
                    writer.close();
                    }
                     }catch (IOException e){
                    e.printStackTrace();
                    }
                 }
    }
}
