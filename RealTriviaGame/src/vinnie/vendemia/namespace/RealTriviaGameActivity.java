package vinnie.vendemia.namespace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RealTriviaGameActivity extends Activity {

	ArrayList < Questions > list ;
	ArrayList <Questions> tempList, tempListCopy;
	private int count,correct;
	RadioButton rBtn0,rBtn1,rBtn2,rBtn3, temp;
	RadioGroup RDG1;
	TextView question,display;
	Button startQuiz;
	int i = 0;
	Button submit;
	Random rgen = new Random();  // Random number generator
	Typeface font;
	MediaPlayer sound;
	
	
	public void onPause() {
	    super.onPause();  // Always call the superclass method first

 	   sound.pause();
	    
	}
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first

	    sound.start();  
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*The next two lines of code gets rid of the title and makes the app fullscreen. We want to do it right
		 * before we set the content view*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		sound = MediaPlayer.create(this, R.raw.victory);
		font = Typeface.createFromAsset(getAssets(), "Dragon.ttf");

		question = (TextView) findViewById(R.id.view);
		
		//Sets the font of the question
		question.setTypeface(font);
		display = (TextView) findViewById(R.id.display);

		submit= (Button) findViewById(R.id.button1);
		submit.setText("Submit");	
		RDG1 =(RadioGroup)findViewById(R.id.radioGroup1);
		
		rBtn0 = (RadioButton) findViewById(R.id.radio0);
		rBtn1 = (RadioButton) findViewById(R.id.radio1);
		rBtn2 = (RadioButton) findViewById(R.id.radio2);
		rBtn3 = (RadioButton) findViewById(R.id.radio3);
		
		//Sets the font of the buttons
		rBtn0.setTypeface(font);
		rBtn1.setTypeface(font);
		rBtn2.setTypeface(font);
		rBtn3.setTypeface(font);
		
		RDG1.clearCheck() ; // clear the default selection of  first radio button in radio group

		//open, read, and parse text file in to questions
		try {
			/*This gets all the questions*/
			PlayWithRawFiles();

		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), 
					"Problems: " + e.getMessage(), 1).show();
		}

		count=0;
		tempList= new ArrayList<Questions>();
		tempListCopy= new ArrayList<Questions>();
		tempListCopy.addAll(list);
		/*
		 * This is a copy of the original list.
		 * it can be used to select a subgroup of questions from the original list
		 * ex: if there are 100 questions we copy 10 random questions in the new array
		 * the quiz will continue until all the questions in templist have been answered 
		 */

		//	this loop will add 10 random questions to tempList
		for (int i =0 ; i< 10 ; i++ ){
			/* rgen 
			 * Returns a pseudo-random uniformly distributed int in the half-open range [0, n). 
			 */
			int index=	rgen.nextInt(tempListCopy.size());
			tempList.add(tempListCopy.get(index));
			tempListCopy.remove(index);

		}


		//tempList.addAll(list);// this is only until we get enough questions
		//Collections.shuffle(tempList);
		tempListCopy.addAll(tempList);
		tempListCopy.clear();

		loadNewQuestion();

	}

	/***************************************************************************************
	 * uses the global variable "count" to load the count-th question form the 
	 * tempList. 
	 * This method populated the text fields and the radio buttons with the appropriate 
	 * questions and responses.
	 ***************************************************************************************/
	private void loadNewQuestion() {
		question.setText(tempList.get(count).getQuestion());//set question

		//get a shuffled array of the possible answers 
		String[] temp=tempList.get(count).getAnswers();

		// Populate radio buttons 
		rBtn0.setText(temp[0]);
		rBtn0.setChecked(false); // by default the first radio button is selected, This is un-selecting the radio button 
		rBtn1.setText(temp[1]);
		rBtn2.setText(temp[2]);
		rBtn3.setText(temp[3]);
	}
	/*********************************************************************************************
	 * Compares the user answer to the correct answer and changes the following:
	 * 1) sets the "AnswerdCorrectly" field of the current question to (true/false).
	 * 2) sets the "UserAnswer" field of the current question to the answer selected by the user.
	 * 3) sets the "IsAnswerd" field of the current question to 'true'
	 * uses the global variable 'count' as the index of tempList 
	 *********************************************************************************************/
	private int chechQuestion(){

		// finding the radio button selected by the user and save it in a 'temp' radio button
		
		int id =  RDG1.getCheckedRadioButtonId();
		
		if (id != -1 ) { 
			temp =(RadioButton) findViewById(id);
			/*
			 * WE NEED TO ADD THIS!
			 * need to put in a null check to make sure a radio button was selected 
			 * otherwise if user presses the button without selecting a radio button this code will crash
			 */
	
			if (temp.getText() != null && temp.getText().equals( tempList.get(count).getCorrectAns() ) ){
				tempList.get(count).setAnswerdCorrectly(true);
			}else {
				tempList.get(count).setAnswerdCorrectly(false);
			}
			tempList.get(count).setUserAnswer((String) temp.getText());
			tempList.get(count).setIsAnswerd(true);
			 
		}
		

		return id ;
		
	}


	/***********************************************************************************
	 * Action listener for the 'submit' button in the main.xml.
	 * Onclick this button will grade the user answer
	 * if at the last question the Stats get printed
	 * if not the last question the new question is loaded. 
	 *
	 * @param view
	 *********************************************************************************/
	public void nextQuestion(View view){		

		/*
		 * If we are at the last question:
		 * 1)	check the question. 
		 * 2)	Disable the button, so user can't keep clicking.
		 * 3) 	print the stats. 
		 * 4) end function. 
		 */
		if (tempList.size()-1 == count ){
			if ( chechQuestion()!= -1){ // checkQuestion() will return -1 if no answer was selected
				submit.setEnabled(false);
				printStats2();
				return;
			}
		}
		// we are not at the last question 
		if ( chechQuestion()!= -1){ 
			count++; // increment global variable used for the index of tempList.
			loadNewQuestion(); // load the new question's values and fields .
			RDG1.clearCheck() ;
			//temp.setChecked(false);//un-check current radio button.
		}
	}


	/********************************************************************************
	 * This Method 
	 * 1) Creates set number of TextViews and ImageViews.
	 * 2) Populates the TextViews
	 * 3)
	 */

	private void printStats2 (){
	
		String result="";
		setContentView(R.layout.results);
		
		//create and  define our 11 textViews 1 for each questions and one for the final result
		TextView q1d=(TextView) findViewById(R.id.textView1); 
		TextView q2d=(TextView) findViewById(R.id.textView2); 
		TextView q3d=(TextView) findViewById(R.id.textView3); 
		TextView q4d=(TextView) findViewById(R.id.textView4); 
		TextView q5d=(TextView) findViewById(R.id.textView5); 
		TextView q6d=(TextView) findViewById(R.id.textView6); 
		TextView q7d=(TextView) findViewById(R.id.textView7); 
		TextView q8d=(TextView) findViewById(R.id.textView8); 
		TextView q9d=(TextView) findViewById(R.id.textView9); 
		TextView q10d=(TextView) findViewById(R.id.textView10); 
		TextView fin=(TextView) findViewById(R.id.textViewTotal); 
		
		//define 10 image 1 for each question
		//q = question , i = image
		ImageView q1i=(ImageView) findViewById(R.id.imageView1); 
		ImageView q2i=(ImageView) findViewById(R.id.imageView2); 
		ImageView q3i=(ImageView) findViewById(R.id.imageView3); 
		ImageView q4i=(ImageView) findViewById(R.id.imageView4); 
		ImageView q5i=(ImageView) findViewById(R.id.imageView5); 
		ImageView q6i=(ImageView) findViewById(R.id.imageView6); 
		ImageView q7i=(ImageView) findViewById(R.id.imageView7); 
		ImageView q8i=(ImageView) findViewById(R.id.imageView8); 
		ImageView q9i=(ImageView) findViewById(R.id.imageView9); 
		ImageView q10i=(ImageView) findViewById(R.id.imageView10);
		
		
		// put textVews and ImageVews in Arrays
		TextView myr[] = {q1d,q2d,q3d,q4d,q5d,q6d,q7d,q8d,q9d,q10d};
		ImageView myImageArray[] = {q1i,q2i,q3i,q4i,q5i,q6i,q7i,q8i,q9i,q10i};
		
		tempList.get(count).setUserAnswer((String) temp.getText());
	
		
		//TextView dis=(TextView) findViewById(R.id.textView1); 
		//dis.setBackgroundColor(Color.GREEN);
		Button goHome = (Button) findViewById(R.id.goHome); 
		Button reStart = (Button) findViewById(R.id.reTakeQuiz); 
		
		
		correct= 0;
		int i=0 ; // counter for question numbers 0 indexed
		for (Questions cur : tempList){
			if (cur.isAnswerdCorrectly()){
				correct++;
				result+=(cur.getQuestion()+ "\n");
				result+=("You answered correctly with: "  + cur.getCorrectAns() +"\n\n" );
				//dis.setText( result);
				myr[i].setText( result);
				//myr[i].setBackgroundColor(Color.GREEN);
				myr[i].setTextColor(Color.GREEN);
				myImageArray[i].setImageResource(R.drawable.check); 

			}	
			else{
				result+=(cur.getQuestion()+ "\n");
				result+=("You answered incorrectly with: "  +cur.getUserAnswer() +"\n\n" );
				// Might want to add correct answers with : + "\nThe correct answer is: " + cur.getCorrectAns()  +"\n\n" );
				
				//dis.setText(result );
				myr[i].setText(result);
				//myr[i].setBackgroundColor(Color.RED);
				myr[i].setTextColor(Color.RED);
				myImageArray[i].setImageResource(R.drawable.x);
			}	
			
			i++;
			result="";
		}


		fin.setText("you got " + correct + " correct out of " + tempList.size() +"\n"  );
		fin.setBackgroundColor(Color.GRAY);
		

		goHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent openTriviaGame = new 
						Intent("vinnie.vendemia.namespace.HOMESCREEN");
				startActivity(openTriviaGame);
			}
		});

		reStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent openTriviaGame1 = new 
						Intent("vinnie.vendemia.namespace.REALTRIVIAGAMEACTIVITY");
				startActivity(openTriviaGame1);
				
			}
		});

	}


	/**********************************************************************************************
	 * This method parses input.txt and creates each question object.
	 * "input.txt" must be formatted correctly
	 * 	Format must be the "question" separated by an "~"
	 * 	then followed by the 4 options, with the correct answer last each separated with "~".
	 * ex:
	 * What color is the sky?~Green~Yellow~Purple~Blue
	 * The file must also not end with a "/n" (new-line character).
	 * 
	 * This method will throw a IOException.
	 *
	 **********************************************************************************************/
	public void PlayWithRawFiles() throws IOException {   

		String  delims = "[~]";
		list = new ArrayList< Questions> () ;

		String str="";

		InputStream is = this.getResources().openRawResource(R.raw.input);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		if (is!=null) {							
			while ((str = reader.readLine()) != null) {	

				String [] temp = str.split(delims);

				Questions newQuestion = new Questions (temp);
				list.add(newQuestion);
			}				
		}		
		is.close();	
	}
}