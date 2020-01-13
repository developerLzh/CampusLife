package com.xiaoka.wangxi.campuslife.pages.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiaoka.wangxi.campuslife.R;
import com.xiaoka.wangxi.campuslife.pages.main.MainActivity;
import com.xiaoka.wangxi.campuslife.widget.LoadingButton;

import io.reactivex.functions.Consumer;

public class LoginActivity extends AppCompatActivity {

    LoadingButton loginBtn;

    TextView registerText;

    TextView resetPsw;

    TextView loginReg;

    EditText editAccount;
    EditText editPsw;

    ImageView eye;

    CheckBox checkboxAgreement;
    CheckBox checkboxRemember;

    TextView textAgreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_button);
        loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });

        checkPermission();
    }

    public void checkPermission() {
        RxPermissions permissions = new RxPermissions(this);
        permissions.setLogging(true);
        permissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        Log.e("tag", permission.name);
                    }
                });
    }
}
