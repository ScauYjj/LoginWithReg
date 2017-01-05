package com.chinamobile.anthortest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserName;
    private EditText mUserPwd;
    private boolean isRemenber;
    private CheckBox checkBox;
    private Button btnLogin;
    private Button btnReg;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connector.getDatabase();
        //SQLiteDatabase db = LitePal.getDatabase();
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        isRemenber = sharedPreferences.getBoolean("remember_password",false);
        initView();
        boolean isClean = getIntent().getBooleanExtra("isRemember",true);
        if (isRemenber && isClean){
            String userName = sharedPreferences.getString("name","");
            String userPwd = sharedPreferences.getString("pwd","");
            mUserName.setText(userName);
            mUserPwd.setText(userPwd);
            checkBox.setChecked(true);
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        mUserName = (EditText) findViewById(R.id.edit_name);
        mUserPwd = (EditText) findViewById(R.id.edit_pwd);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        btnLogin = (Button) findViewById(R.id.btn_log);
        btnReg = (Button) findViewById(R.id.btn_reg);

        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String userName = mUserName.getText().toString();
        String userPwd = mUserPwd.getText().toString();
        if (userName.equals("")){
            Toast.makeText(MainActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPwd.equals("")){
            Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        switch (view.getId()){
            case R.id.btn_log:
                List<User> users = DataSupport.where("name=?",userName).find(User.class);
                if (users.size() !=0){
                    User user = users.get(0);
                    String pwd = user.getPwd();
                    if (userPwd.equals(pwd)){
                        editor = sharedPreferences.edit();
                        if (checkBox.isChecked()){
                            editor.putString("name",userName);
                            editor.putString("pwd",userPwd);
                            editor.putBoolean("remember_password",true);
                        }else {
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Toast.makeText(MainActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                }
                return;
            case R.id.btn_reg:
               List<User> userList = DataSupport.where("name = ?", userName).find(User.class);
                //List<Song> songs = DataSupport.where("name like ?", "song%").order("duration").find(Song.class);
               // List<User> userList = DataSupport.findAll(User.class);
                if (userList != null && userList.size() != 0){
                    Toast.makeText(MainActivity.this,"该用户名已存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setName(userName);
                user.setPwd(userPwd);
                user.save();
                Toast.makeText(MainActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
