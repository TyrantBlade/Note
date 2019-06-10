package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private File root,gpxfile;
    private String filPath;
    private static String fileName="default";
    FileWriter writer;
    FileReader reader;
    private EditText txView;

    private static final int REQUEST_CODE_PATH=1;

    private ImageView imNew,imOpen,imDel,imSave;
    private BufferedReader bufferedReader;
    private TextView f_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txView=findViewById(R.id.textAff);  f_name=findViewById(R.id.file_name);

        imNew=findViewById(R.id.newFile);       imOpen=findViewById(R.id.openFile);
        imDel=findViewById(R.id.delContent);    imSave=findViewById(R.id.saveFile);

        imOpen.setOnClickListener(this);     imNew.setOnClickListener(this);
        imDel.setOnClickListener(this);     imSave.setOnClickListener(this);
        if(gpxfile==null){
            createFile(this,fileName);
            f_name.setText(fileName);
        }
    }

    @Override
    public void onClick(View v) {
        if (v==imDel){
            Intent intent=new Intent(this,ListFolder.class);
            intent.putExtra(ListFolder.OPERATION,true);
            startActivity(intent);
        }
        if (v==imNew){
            fileName=txView.getText().toString();
            createFile(this,fileName);
            f_name.setText(fileName);
        }
        if (v==imOpen){
            Intent intent=new Intent(this,ListFolder.class);
            intent.putExtra(ListFolder.OPERATION,false);
            startActivityForResult(intent,REQUEST_CODE_PATH);
        }
        if(v==imSave){
           String data = txView.getText().toString();
            saveFile(data);
        }
    }

    private void saveFile(String data) {
        try {
            writer = new FileWriter(gpxfile);
            writer.append(data);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_CODE_PATH){
            if (resultCode==RESULT_OK){
                this.filPath=data.getStringExtra(ListFolder.RESULT_PATH_FILE);

                txView.setText(readFile(filPath));
                fileName=gpxfile.getAbsoluteFile().getName().replace(".txt","");
                f_name.setText(fileName);
            }
            else{
                Toast.makeText(this, "No file to open", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void createFile(Context context, String name){
        try {
            root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            gpxfile = new File(root, name.replace(".txt","")+".txt");
            writer = new FileWriter(gpxfile);
            writer.append("");
            txView.setText("");
            writer.flush();
            writer.close();
            Toast.makeText(context, name+" just created", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String path){
        gpxfile=new File(path);
        StringBuilder text =new StringBuilder();
        try {
            reader=new FileReader(gpxfile);
            bufferedReader = new BufferedReader(reader);

            String line;
            while ((line=bufferedReader.readLine())!=null){
                text.append(line);
                text.append("\n");
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
