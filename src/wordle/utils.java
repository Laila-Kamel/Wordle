package wordle;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.json.simple.*;
import org.json.simple.parser.*;

public class utils {
	
	// Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    
    
    //read the word list from the file and return it into an array of words or strings
	public static String[] getWords() throws IOException, ParseException {
		JSONArray wordsJsonArray=new JSONArray();
		List<String> wordsList=new ArrayList<String>();
		String[] words = null;
		try {
			//convert the JSON wordList to Object
			Object obj=new JSONParser().parse(new FileReader("C:\\Users\\laila\\eclipse-workspace\\Wordle\\src\\wordle\\word-list.json"));
			//convert the object to JSON Array
			wordsJsonArray=(JSONArray)obj;
			//convert the JsonArray to list
			for(Object string:wordsJsonArray) {
				wordsList.add(string.toString());
			}
			//convert the List to array of strings
			words=wordsList.toArray(new String[wordsList.size()]);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words;

	}
	//method that chooses a random word from the array of words
	public static String getRandomWord(String[] words) {
		Random random=new Random();
		int randomNum=random.nextInt(words.length);// set the range of random index to array length
		return words[randomNum];
		
	}
	public static void checkWord(String word) throws IOException, ParseException {
		boolean won=false;
		int counter=0; //count how many trials the user tried
		Scanner sc=new Scanner(System.in);
		System.out.println("Please enter a word");
		String trial=sc.next();
//		System.out.println(trial);
		while(!won&&counter<5) 
		{
			if(trial.length()<5) {
				System.out.println("You entered less than 5 letters. Try again!");
			}
			else if(trial.length()>5) {
				System.out.println("You entered more than 5 letters. Try again!");
			}
			else {
				if(word.equals(trial)) {
					System.out.println(GREEN+trial.toUpperCase()+RESET);
					System.out.println("Congratulations!!!....You win");
					won=true;
					getWordDefinition(word);
					endGame(counter,won);
					break;
				}
				else {
					counter++;
					System.out.printf("Try again, you have %d trials left\n",6-counter);
					checkLettersPosition(trial,word);
//					System.out.println("Print enter a word");
//					trial=sc.next();
				}
			}
			System.out.println("Please enter a word");
			trial=sc.next();
		}
		if(counter==5) {
			System.out.println("Sorry...You lost!!");
			endGame(counter,won);
		}
	}
	
	
	// end of game and call write to file method
	public static void endGame(int counter, boolean won) {
		Trial t=new Trial(counter+1,won);
		int trial=t.trials;
		String emoji="\uD83D\uDE1E";
		String didWin="";
		if(won) {
			didWin="win";
			emoji="\uD83D\uDE03";
		}
		else
			didWin="lost";
		System.out.printf("Number of trials: %d, result: %s  %S",trial,didWin,emoji);
		try {
			writeToFile(trial,didWin,emoji);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to create a directory");
		}
	}
	
	// write the history to a file
	public static void writeToFile(int numOfTrial, String result, String em) throws IOException {
		File historyFile=new File("C:\\Users\\laila\\eclipse-workspace\\Wordle\\src\\wordle\\history.txt");
		
		try (FileWriter writer = new FileWriter(historyFile,true)) {
			String text=String.format("Number of trials: %d, result: %s %s\n",numOfTrial,result,em);
			writer.write(text);
			copyToClipboard(text);
		}
	}
	
	
	public static void checkLettersPosition(String word, String randomWord) {
		String[] wordArr=word.split("");

		for(int i=0;i<wordArr.length;i++) {
			if(wordArr[i].equals(randomWord.substring(i,i+1))) {
//				System.out.println("letter "+wordArr[i]+" is in the right position");
//				wordArr[i]=wordArr[i].toUpperCase();
				wordArr[i]=GREEN+wordArr[i].toUpperCase()+RESET;
			}
			else if(randomWord.contains(wordArr[i])){
//				System.out.println("letter "+wordArr[i]+" exists but in the wrong place");
				wordArr[i]=YELLOW+wordArr[i].toUpperCase()+RESET;
			}
			else {
//				System.out.printf("letter %s doesn't exist in the word\n",wordArr[i]);
				wordArr[i]="_";
			}
		}
		System.out.println(wordArr[0]+" "+wordArr[1]+" "+wordArr[2]+" "+wordArr[3]+" "+wordArr[4]);
	}
	

	public static void getWordDefinition(String word) throws IOException, ParseException {
		String url=String.format("https://api.dictionaryapi.dev/api/v2/entries/en/%s",word);
		URL stringUrl=new URL(url);
		String inline="";
		HttpURLConnection con=(HttpURLConnection)stringUrl.openConnection();
		con.setRequestMethod("GET");
		con.connect();
		int responseCode=con.getResponseCode();
		if(responseCode!=200) {
			throw new RuntimeException("HttpResponceCode: "+responseCode);
		}
		else {
			Scanner sc = new Scanner(stringUrl.openStream());
			while(sc.hasNext())
			{
			//add next line to the lines that already exist.
			inline+=sc.nextLine();
			}
			sc.close();
		}
		//convert the stream of strings to JSONArray it starts with [ so it should be converted to array not JSONObject
		JSONArray JArr=(JSONArray) new JSONParser().parse(inline);
		//declare a JSONObject that takes the first item in the JSONArray; i.e. the whole data without the [
		JSONObject JObj=new JSONObject();
		JObj=(JSONObject) JArr.get(0);
//		System.out.println("--------------");
//		System.out.println(JObj.get("meanings"));
		//get the meanings JSONArray from inside the object
		JArr=(JSONArray) JObj.get("meanings");
		//get the first item in the JSONArray which is the whole data of meanings
		JObj=(JSONObject) JArr.get(0);
		//get definitions JSONArray
		JArr=(JSONArray) JObj.get("definitions");
		//get the first item in the JSONArray which is the whole data of definitions
		JObj=(JSONObject) JArr.get(0);
		//get the definition value which is a string
		String s=(String) JObj.get("definition");
//		System.out.println("----------------");
		System.out.printf("%s dictionary definition: %s\n",word,s);
	}
	
	
	public static void copyToClipboard(String text) {
		Clipboard cb=Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection strSel=new StringSelection(text);
		cb.setContents(strSel, null);
	}
}