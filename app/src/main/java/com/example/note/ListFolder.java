package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFolder extends AppCompatActivity {
    public static final String RESULT_PATH_FILE="RESULT_PATH_FILE";
    public static final String OPERATION="OPERATION";
    public Boolean toDelete=false;

    List <String> array;
    ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_folder);

        toDelete= getIntent().getBooleanExtra(OPERATION,true);


        listView =findViewById(R.id.list);
        array = new ArrayList<String>();

        File root=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"Notes");
        listerFichiers(root);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                if (!toDelete){
                    returnPath(item);
                }
                else{
                    File file=new File(item);
                    file.delete();
                    adapter.remove(item);
                }
            }
        });
    }

    private void listerFichiers(File root) {
        File[] files=root.listFiles();
        array.clear();
        for (File f: files){
            array.add(f.getPath());
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listView.setAdapter(adapter);
    }
    public void returnPath(String item){
        Intent data=new Intent();
        data.putExtra(RESULT_PATH_FILE,item);
        setResult(RESULT_OK,data);
        finish();
    }
}