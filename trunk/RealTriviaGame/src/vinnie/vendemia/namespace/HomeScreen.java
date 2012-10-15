package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class HomeScreen extends Activity {

	

	Button startQuiz,contactUsActivity, soundButton;
	Typeface font;
	TextView banner;
	static MediaPlayer sound;
	static boolean soundOn = true;
	

	public void ContentManager(Context context) {
		// Add all of the variables to manage the content such as images and
		// fonts to this method

	}

	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    
 	   sound.pause();
	    
	}
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first

	    if (soundOn){
	    sound.start();
	    }
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*The next two lines of code gets rid of the title and makes the app fullscreen. We want to do it right
		 * before we set the content view*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home);
		sound = MediaPlayer.create(this, R.raw.victory);
		sound.start();
		
		sound.setLooping(true);
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
				Intent goToContact = new Intent("vinnie.vendemia.namespace.CONTACTUS");
				startActivity(goToContact);
			}
		});

		soundButton = (Button) findViewById(R.id.soundButton);
		soundButton.setOnClickListener(new View.OnClickListener() {
			//boolean soundOn = true;
			public void onClick(View contactView) {
				if (soundOn) {
					sound.pause();
					soundOn =false;
				} else {
					sound.start();
					soundOn = true;
				}
			}
		});
	}


}

