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

public class SignupActivity extends Activity{

	private EditText _nameText, _emailText, _passwordText;
	private Button _signupButton;
	private TextView _linkLogIn;
	private LoginDataBaseAdapter logInDataBaseAdepter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		logInDataBaseAdepter = new LoginDataBaseAdapter(this);
		logInDataBaseAdepter = logInDataBaseAdepter.open();
		
		_nameText = (EditText)findViewById(R.id.input_name);
		_emailText = (EditText)findViewById(R.id.input_email);
		_passwordText = (EditText)findViewById(R.id.input_password);
		_signupButton = (Button)findViewById(R.id.btn_signup);
		_linkLogIn = (TextView)findViewById(R.id.link_login);
		
		_signupButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				signUp();				
			}
		});
		
		_linkLogIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				logIn();				
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		logInDataBaseAdepter.close();
	}

	protected void logIn() {
		Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		this.finish();
	}

	@SuppressLint("NewApi")
	protected void signUp() {
		String name = _nameText.getText().toString();
		String email = _emailText.getText().toString();
		String password = _passwordText.getText().toString();
		
		if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
			_emailText.setError("Valid email is required");
			return;
		}
		
		logInDataBaseAdepter.insertEntry(name, email, password);
		Toast.makeText(getApplicationContext(), "Account created!!!", Toast.LENGTH_LONG).show();
		this.finish();
	}

}
