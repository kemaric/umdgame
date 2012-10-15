package vinnie.vendemia.namespace;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends Activity{
	int INVISIBLE = 1;
	int VISIBLE = 0;
	EditText usernameText, passwordText;
	Button signUp, home,signIn;
	CheckBox rememberMe;
	TextView wrongUserOrPass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.sign_in);
		signUp = (Button) findViewById(R.id.signUpButton);
		signIn = (Button) findViewById(R.id.signin);
		home = (Button) findViewById(R.id.signInHome);
		rememberMe = (CheckBox) findViewById(R.id.checkbox);
		wrongUserOrPass = (TextView) findViewById(R.id.wrongText);
		wrongUserOrPass.setVisibility(INVISIBLE);//I'm not sure if this works. it doesn't seem to work.
		/*I'm getting the id's from the sign_up.xml*/
		usernameText = (EditText) findViewById(R.id.signInusername);
		passwordText = (EditText) findViewById(R.id.signInpassword);
		/*Next two lines are storing their username and password to the variables*/
		//String user = usernameText.getText().toString(); 

		signUp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("vinnie.vendemia.namespace.SIGNUP");
				startActivity(intent);
				
				/*When AMPQ is set up this will be continued*/	
			}
		});
		signIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*When AMPQ is set up this will be continued*/	
			}
		});
		home.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("vinnie.vendemia.namespace.HOMESCREEN");
				startActivity(intent);
			}
		});

	}

}
