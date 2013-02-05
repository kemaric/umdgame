package vinnie.vendemia.namespace;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.*;

import com.facebook.FacebookActivity;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

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


public class HomeScreen extends FacebookActivity {

	
	
	
	/**Facebook constants for posting score to wall added below**/
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private static boolean pendingPublishReauthorization = false;
	private static final String TAG = "MainFragment";
	
	/***********************************************************/
	
	

	static int numCorrect = 0;
	Button startQuiz,contactUsActivity, soundButton;
	Typeface font;
	TextView banner;
	static MediaPlayer sound;
	static boolean soundOn = false;
	static boolean fBookSignedIn = false;
	
	
	
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
		banner = (TextView) findViewById(R.id.tvBanner);
		banner.setText(getString(R.string.Banner));
		banner.setTypeface(font, Typeface.BOLD);
		sound = MediaPlayer.create(this, R.raw.victory);
		sound.setLooping(true);
		this.openSession();
	}
		
	
	@Override
	protected void onSessionStateChange(SessionState state, Exception exception) {
	  // user has either logged in or not ...
	  if (state.isOpened()) {
	    // make request to the /me API
		fBookSignedIn = true;
	    Request request = Request.newMeRequest(
	      this.getSession(),
	      new Request.GraphUserCallback() {
	        // callback after Graph API response with user object
	        @Override
	        public void onCompleted(GraphUser user, Response response) {
	        	  
	        }
	      }
	    );
	    Request.executeBatchAsync(request);
	  }
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
	
	 public void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){
	    	// Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.ReauthorizeRequest reauthRequest = new Session
	                    .ReauthorizeRequest(this, PERMISSIONS)
	                    .setRequestCode(REAUTH_ACTIVITY_CODE);
	        session.reauthorizeForPublish(reauthRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", "Facebook SDK for Android");
	        postParams.putString("caption", "Build great social apps and get more installs.");
	        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	        postParams.putString("link", "https://developers.facebook.com/android");
	        postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i(TAG,
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(getActivity()
	                         .getApplicationContext(),
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(getActivity().getApplicationContext(), 
	                             postId,
	                             Toast.LENGTH_LONG).show();
	                }
	            }

				private ContextWrapper getActivity() {
					// TODO Auto-generated method stub
					return null;
				}
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	public static boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == REAUTH_ACTIVITY_CODE) {
	        Session.getActiveSession().onActivityResult(this, 
	            requestCode, resultCode, data);
	    }
	 }
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
	    super.onSaveInstanceState(bundle);
	    bundle.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
	}
	
	public void postToFeed(View view) {
		publishStory();
	}
	

}




 


