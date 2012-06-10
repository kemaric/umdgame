package vinnie.vendemia.namespace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class HomeScreen extends Activity {

	Button startQuiz;
	
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
	}
}
