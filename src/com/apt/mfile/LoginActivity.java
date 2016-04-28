package com.apt.mfile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	 private static final String TAG = "LoginActivity";
	 private static final int REQUEST_SIGNUP = 0;
	 
	 private EditText inpEmail, inpPassword;
	 private Button btnlogIn;
	 private TextView txtSignUp;
	 private LoginDataBaseAdapter loginDataBaseAdapter;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.activity_login);
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		inpEmail = (EditText)findViewById(R.id.editText_email);
		inpPassword = (EditText)findViewById(R.id.editText_password);
		btnlogIn = (Button)findViewById(R.id.button_login);
		txtSignUp = (TextView)findViewById(R.id.link_signup);
		
		btnlogIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logIn();				
			}
		});
		
		txtSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				signUp();				
			}
		});
	}
	
	 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		loginDataBaseAdapter.close();
	}

	protected void signUp() {
		Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
		startActivityForResult(intent, REQUEST_SIGNUP);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

	@SuppressLint("NewApi")
	private void logIn() {
	
		String email = inpEmail.getText().toString();
		String password = inpPassword.getText().toString();
		
		if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
			inpEmail.setError("Valid email is required");
			return;
		}
		
		String storedPassword = loginDataBaseAdapter.getSinlgeEntry(email);
		
		if(storedPassword.equals(password)){
			Toast.makeText(getApplicationContext(), "Log In success!!!", Toast.LENGTH_LONG).show();
			this.finish();
		} else {
			Toast.makeText(getApplicationContext(), "Log In failed!!!", Toast.LENGTH_LONG).show();
		}
	}
}
