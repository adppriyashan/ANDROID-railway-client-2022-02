package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetek.railwayandroid.Models.CustomUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

public class Login extends AppCompatActivity  implements Validator.ValidationListener {

    @NotEmpty
    @Email
    EditText username;

    @NotEmpty
    EditText password;

    TextView goToRegister;

    ProgressDialog progress;
    Button loginButton;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validator = new Validator(this);
        validator.setValidationListener(this);

        progress=new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait");
        progress.setCancelable(false);

        initProcess();
    }

    @Override
    public void onValidationSucceeded() {
        loginProcess();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            if (view instanceof EditText) {
                ((EditText) view).setError(error.getCollatedErrorMessage(this));
            }
        }
    }

    private void loginProcess(){
        try {
            JSONObject _loginCredentails=new JSONObject();
            _loginCredentails.put("email",username.getText().toString());
            _loginCredentails.put("password",password.getText().toString());
            Response resp=new CustomUtils(Login.this,"login").doPost(_loginCredentails);
            if(resp.isSuccessful()){

                JSONObject respData=new JSONObject(resp.body().toString());

                if(resp.code()==200){
                    //save auth key
                    startActivity(new Intent(Login.this,Dashboard.class));
                }else{
                    Toast.makeText(Login.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(Exception e){
            Toast.makeText(Login.this,"Something Wrong",Toast.LENGTH_SHORT).show();
        }
    }

    private void initProcess() {
        goToRegister=findViewById(R.id.login_go_to_register);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        loginButton=findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });
    }
}