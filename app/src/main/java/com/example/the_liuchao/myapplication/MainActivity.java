package com.example.the_liuchao.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.example.the_liuchao.myapplication.db.Child;
import com.example.the_liuchao.myapplication.db.Parent;
import com.example.the_liuchao.myapplication.test.TestActivity;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    DbManager.DaoConfig config = new DbManager.DaoConfig().setDbDir(new File("/sdcard"))
            .setDbName("test.db")
            .setDbVersion(2)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                public void onDbOpened(DbManager dbManager) {
                    // 开启WAL, 对写入加速提升巨大
                    dbManager.getDatabase().enableWriteAheadLogging();
                }
            }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                /**数据库更新*/
                public void onUpgrade(DbManager dbManager, int oldVersion, int newVersion) {
//                     dbManager.addColumn(class,"columnName");
                    try {
                        dbManager.dropDb();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
//                     dbManager.dropTable(class);
                }
            });
    @ViewInject(R.id.testGet)
    private TextView test;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.fab)
    private FloatingActionButton fab;

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        setSupportActionBar(toolbar);
        startActivity(new Intent(this, TestActivity.class));
    }

    /**
     * 一般请求
     *
     * @param view
     */
    @Event(value = R.id.testGet, type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
    private void testMethod(View view) {
        RequestParams params = new RequestParams("https://www.baidu.com");
        Callback.Cancelable cancelable
                = x.http().get(params, new Callback.CommonCallback<String>() {
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, "返回结果：" + result, Toast.LENGTH_SHORT).show();
                System.err.println(result);
            }

            public void onError(Throwable throwable, boolean b) {
                throwable.printStackTrace();
                Toast.makeText(MainActivity.this, "失败" + throwable.getStackTrace() + "--" + b, Toast.LENGTH_SHORT).show();
            }

            public void onCancelled(CancelledException e) {
                Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
            }

            public void onFinished() {
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
            }
        });
//        cancelable.cancel();//取消请求
    }

    @Event(value = R.id.testDb, type = View.OnClickListener.class)
    private void testDownload(View view) {
        String temp = "";
        TextView resultTv = (TextView) view;
        try {
            DbManager db = x.getDb(config);
            Parent parent = new Parent();
            parent.setName("zhangsan");
            parent.setEmail("the_liuchao@sina.cn");
            parent.setPhone("18216102786");
            parent.setTime(System.nanoTime() + "");
            Parent tempParent =  db.findFirst(Parent.class);
            if(tempParent!=null)
            db.delete(tempParent);
            Child child = new Child();
            child.setName("lisi");
            db.save(parent);
            child.setParentId(db.findFirst(Parent.class).getId());
            db.saveBindingId(child);     //保存对象关联数据库生成的id
            List<Child> children = db.selector(Child.class).findAll();
            temp += "parentId:" + children.get(0).getParentId()+ "\n"+"child:"+children.get(0).toString()+"\n parentSize:"+db.findAll(Parent.class).size();
            System.err.println(temp);
            for (int i=0;i<children.size();i++){
                System.err.println(children.get(i).toString());
            }
            parent = db.findFirst(Parent.class);
            resultTv.setText(parent.toString());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


}
