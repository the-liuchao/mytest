package com.example.the_liuchao.myapplication.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.the_liuchao.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        listView = (ListView) findViewById(R.id.listview1);
        getData();
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.list_item, new String[]{"name", "age"}, new int[]{R.id.tv_name, R.id.tv_age});
        listView.setAdapter(adapter);
    }
    private List<Map<String, Person>> getData() {
        List<Map<String, Person>> listMap = new ArrayList<>();
        List<Person> list = new ArrayList<>();
        Map<String, Person> maps = null;
        Gson gson = new Gson();
        String jsonStr = "[{'name':'AAA','age':'20'},{'name':'BBB','age':'21'},{'name':'CCC','age':'22'},{'name':'DDD','age':'23'}]";
        listMap = gson.fromJson(jsonStr, new TypeToken<List<Map<String, String>>>() {
        }.getType());
//        Map<String, String> map = null;
//        for (Person person : list) {
//            map = new HashMap<String, String>();
//            map.put("name", person.getName());
//            map.put("age", person.getAge());
//            listMap.add(map);
//        }
        return listMap;
    }
}
