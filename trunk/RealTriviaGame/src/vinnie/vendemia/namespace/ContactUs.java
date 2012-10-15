package vinnie.vendemia.namespace;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
/**
 * This class defines the contact Us activity.  This will enable users of our app
 * to email us bugs and it is also a way to get more people into the club because 
 * our contact info is on this activity.  This activity contains a button that will
 * navigate them back to the home screen and a text that tells them our email
 * address
 * @author bori
 *
 */


public class ContactUs extends Activity {
Button backToHome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*The next two lines of code gets rid of the title and makes the app fullscreen. We want to do it right
		 * before we set the content view*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.contact);
		backToHome = (Button) findViewById(R.id.HomeButton);
		backToHome.setOnClickListener(new View.OnClickListener() {
			/*this will enable the user to go to the home screen when they're
			 * done viewing this activity*/
			public void onClick(View goHomeView) {
				// TODO Auto-generated method stub
				//Intent goBackHome = new Intent("vinnie.vendemia.namespace.HOMESCREEN");
				//startActivity(goBackHome);
				onBackPressed();
			}
		});
	}
}
