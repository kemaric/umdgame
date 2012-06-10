package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreen extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run() {
				try {
					sleep(5000);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					Intent openTriviaGame = new 
							Intent("vinnie.vendemia.namespace.HOMESCREEN");
					startActivity(openTriviaGame);
				}
			}
		};
		timer.start();

	}
}
