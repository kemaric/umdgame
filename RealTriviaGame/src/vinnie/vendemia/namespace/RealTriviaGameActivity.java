package vinnie.vendemia.namespace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*The next two lines of code gets rid of the title and makes the app fullscreen. We want to do it right
		 * before we set the content view*/
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
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
		/*Ely this is causing a problem because when I tried to play the game and I click the first radio button it doesnt select -sometimes- 
		 * we'll  have to change it. I'm currently thinking of what we can do - bori*/
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
			loadNewQuestion(); // lead the new question's values and fields .
			RDG1.clearCheck() ;
			//temp.setChecked(false);//un-check current radio button.
		}
	}




	private void printStats2 (){
		String result="";
		setContentView(R.layout.results);
		TextView dis=(TextView) findViewById(R.id.textView1); 
		Button goHome = (Button) findViewById(R.id.goHome); 
		Button reStart = (Button) findViewById(R.id.reTakeQuiz); 

		correct= 0;
		//int i=1 ; // counter for question numbers
		for (Questions cur : tempList){
			if (cur.isAnswerdCorrectly()){
				correct++;
				result+=(cur.getQuestion()+ "\n");
				result+=("You answered correctly with: "  + cur.getCorrectAns() +"\n\n" );
				dis.setText( result);

			}	
			else{
				result+=(cur.getQuestion()+ "\n");
				result+=("You answered incorrectly with: "  +cur.getUserAnswer() + "\nThe corrent answer is: " + cur.getCorrectAns()  +"\n\n" );
				dis.setText(result );
			}	

		}

		//		for (int i = 0 ;  i<= tempList.size()-1 ; i++){
		//			if (tempList.get(i).isAnswerdCorrectly()){
		//				correct++;
		//				result+=("you answered question "+ (i+1)+ " correctly with: "  + tempList.get(i).getCorrectAns() +"\n" );
		//				dis.setText( result);
		//			}	
		//			else{
		//				result+=("you answered question "+ (i+1)+ " incorrectly with: "  +tempList.get(i).getUserAnswer() + " the correct answer is: " + tempList.get(i).getCorrectAns()  +"\n" );
		//				dis.setText(result);
		//
		//			}
		//		}




		result+=("you got " + correct + " correct out of " + tempList.size() +"\n"  );


		dis.setText(result);






		goHome.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent openTriviaGame = new 
						Intent("vinnie.vendemia.namespace.HOMESCREEN");
				startActivity(openTriviaGame);
			}
		});

		reStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent openTriviaGame = new 
						Intent("vinnie.vendemia.namespace.REALTRIVIAGAMEACTIVITY");
				startActivity(openTriviaGame);
			}
		});

	}




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