package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeScreen extends Activity {

	Button startQuiz,contactUsActivity;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		startQuiz = (Button) findViewById(R.id.startButton);
		startQuiz.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent openTriviaGame = new 
						Intent("vinnie.vendemia.namespace.REALTRIVIAGAMEACTIVITY");
				startActivity(openTriviaGame);
			}
		});
		/*I added the "contact us" button to the home screen that way they can navigate to 
		 *the contact activity*/
		contactUsActivity = (Button) findViewById(R.id.contactButton);
		contactUsActivity.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View contactView) {
				// TODO Auto-generated method stub
			Intent goToContact = new Intent("vinnie.vendemia.namespace.CONTACTUS");
			startActivity(goToContact);
			}
		});
	}
}
