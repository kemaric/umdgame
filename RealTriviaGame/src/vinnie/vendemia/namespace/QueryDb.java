package vinnie.vendemia.namespace;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Set;

import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse; 
import org.apache.http.NameValuePair; 
import org.apache.http.client.HttpClient; 
import org.apache.http.client.entity.UrlEncodedFormEntity; 
import org.apache.http.client.methods.HttpPost; 
import org.apache.http.impl.client.DefaultHttpClient; 
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray; 
import org.json.JSONException; 
import org.json.JSONObject; 
import android.app.ListActivity; 
import android.content.Intent;
import android.net.ParseException; 
import android.os.Bundle; 
import android.util.Log; 
import android.widget.Toast;

public class QueryDb  {

	static JSONArray jArray; 
	


	protected static boolean postHighScore(int score, String user) throws HttpConnectionFailedThrowable {
		
		InputStream is = null; 
		
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("user", user));
		nameValuePairs.add(new BasicNameValuePair("score", Integer.toString(score)));
		

		//http post


		try{
			HttpClient httpclient = new DefaultHttpClient();
			String url = "http://mobileappdevelopersclub.com/post_score.php";
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection"+e.toString());
			throw new HttpConnectionFailedThrowable();
		}
		
		
		return true;
		//convert response to string
	}
	
	
	
	protected static ArrayList<HighScores> getHighScores() throws HttpConnectionFailedThrowable {
		String result = null; 
		InputStream is = null; 
		StringBuilder sb=null;
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		//http post


		try{
			HttpClient httpclient = new DefaultHttpClient();
			String url = "http://mobileappdevelopersclub.com/greek_search.php";
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		}catch(Exception e){
			Log.e("log_tag", "Error in http connection"+e.toString());
			throw new HttpConnectionFailedThrowable();
		}
		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			sb = new StringBuilder();
			sb.append(reader.readLine() + "\n");

			String line="0";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}
		//paring data


		ArrayList<HighScores> scores = getHighScores(result);



		return scores;
	}

	protected static ArrayList<HighScores> getHighScores(String result) {
		ArrayList<HighScores> toBeReturned = new ArrayList<HighScores>();
		
		String user = null;
		String score = null;
		String numPlays = null;
		
		try{
			jArray = new JSONArray(result);
			JSONObject json_data=null;
			for(int i=0;i<jArray.length();i++){
				json_data = jArray.getJSONObject(i);
				user = json_data.getString("id");
				score = json_data.getString("name");
				numPlays = json_data.getString("description");
				HighScores curr = new HighScores(user, score, numPlays);
				toBeReturned.add(curr);
			}
		}
		catch(JSONException e1){
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}


		
		
		return toBeReturned;



	}


	
}