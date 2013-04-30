package vinnie.vendemia.namespace;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.*;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HomeScreen extends Activity {

	
	/***********************************************************/
	
	

	static int numCorrect = 0;
	Button startQuiz,contactUsActivity, soundButton;
	Typeface font;
	TextView banner;
	static MediaPlayer sound;
	static boolean soundOn = false;
	static boolean fBookSignedIn = false;
	public static String user = null; 
	
	
	
	public void takeQuiz(View view) {
		Intent openTriviaGame = new 
				Intent("vinnie.vendemia.namespace.REALTRIVIAGAMEACTIVITY");
		startActivity(openTriviaGame);
	}
	

	public void goToContact(View view) {
		Intent goToContact = new Intent("vinnie.vendemia.namespace.CONTACTUS");
		startActivity(goToContact);
	}
	
	public void manageSound(View view) {
		if (soundOn) {
			sound.pause();
			soundOn = false;
		} else {
			sound.start();
			soundOn = true;
		}
	}
	
/*	
	
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
/*	contactUsActivity = (Button) findViewById(R.id.contactButton);
	contactUsActivity.setOnClickListener(new View.OnClickListener() {

		public void onClick(View contactView) {
			
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
	*/
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*The next two lines of code gets rid of the title and makes the app fullscreen. We want to do it right
		 * before we set the content view*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home);
		font = Typeface.createFromAsset(getAssets(), "Dragon.ttf");

		sound = MediaPlayer.create(this, R.raw.victory);
		sound.setLooping(true);
	}
		

	
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    if(soundOn) {
	    	sound.pause();
	    }
	    
	}
	
	public void onResume(){
		super.onResume();  // Always call the superclass method first

	    if( soundOn){
	    	sound.start();  
	    }
		
	}
}




 


