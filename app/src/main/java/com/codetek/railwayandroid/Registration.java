package com.codetek.railwayandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetek.railwayandroid.Models.CustomUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONObject;

import java.util.List;

import okhttp3.Response;

public class Registration extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    private EditText name;

    @NotEmpty
    @Email
    private EditText email;

    @NotEmpty
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    private EditText password;

    @NotEmpty
    @ConfirmPassword
    private EditText passwordConfirmation;

    private Button registerButton;
    private TextView backToLogin;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initProcess();
    }

    private void initProcess() {

        validator = new Validator(this);
        validator.setValidationListener(this);

        name=findViewById(R.id.registration_name);
        email=findViewById(R.id.registration_email);
        password=findViewById(R.id.registration_password);
        passwordConfirmation=findViewById(R.id.registration_password_confirmation);
        registerButton=findViewById(R.id.registration_create_and_account);
        backToLogin=findViewById(R.id.registration_back_to_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registrationProcess(){
        try {
            JSONObject _loginCredentails=new JSONObject();
            _loginCredentails.put("name",name.getText().toString());
            _loginCredentails.put("email",email.getText().toString());
            _loginCredentails.put("password",password.getText().toString());
            _loginCredentails.put("password_confirmation",passwordConfirmation .getText().toString());
            Response resp=new CustomUtils(Registration.this,"register").doPost(_loginCredentails);
            if(resp.code()==200){
                Toast.makeText(Registration.this, "Registration Succeed.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(Registration.this,"Something Wrong",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValidationSucceeded() {
        registrationProcess();
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
}