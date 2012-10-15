package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity{
	Button signUp,home;
	EditText user, pass, retypePass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		signUp = (Button) findViewById(R.id.signup);
		home = (Button) findViewById(R.id.signUphome);
		user = (EditText) findViewById(R.id.signUpusername);
		pass = (EditText) findViewById(R.id.signUpPassword);
		retypePass = (EditText) findViewById(R.id.signUpPassword2);

		home.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("vinnie.vendemia.namespace.HOMESCREEN");
				startActivity(intent);
			}
		});
		signUp.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*When AMPQ is set up this will be continued*/	
			}
		});
	}

}
