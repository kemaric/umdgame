package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends Activity {

	Button startQuiz,contactUsActivity;
	Typeface font;
	Context context;
	TextView banner;
	
	public void ContentManager(Context context) {
		// Add all of the variables to manage the content such as images and
		// fonts to this method
		
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		font = Typeface.createFromAsset(getAssets(), "Dragon.ttf");
		banner = (TextView) findViewById(R.id.tvBanner);
		banner.setText(getString(R.string.Banner));
		banner.setTypeface(font, Typeface.BOLD);
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
