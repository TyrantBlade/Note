package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private File root,gpxfile;
    FileWriter writer;
    private EditText txView;
    public int i;

    //
    private ImageView imNew,imOpen,imDel,imSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i=0;

        txView=findViewById(R.id.textAff);

        imNew=findViewById(R.id.newFile);       imOpen=findViewById(R.id.openFile);
        imDel=findViewById(R.id.delContent);    imSave=findViewById(R.id.saveFile);

        imOpen.setOnClickListener(this);     imNew.setOnClickListener(this);
        imDel.setOnClickListener(this);     imSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==imDel){
            txView.setText("");
        }
        if (v==imNew){
            createFile(this,txView.getText().toString());
        }
        if (v==imOpen){
            Intent intent=new Intent(this,ListFolder.class);
            startActivity(intent);
        }
    }

    public void createFile(Context context, String sBody) {
        try {
            root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            String nomFichier="Note ";
            String num=String.valueOf(i);

            nomFichier=nomFichier+num;

            gpxfile = new File(root, nomFichier+".txt");
            writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            i++;
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
