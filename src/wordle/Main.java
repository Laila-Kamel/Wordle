package wordle;

import java.io.IOException;
import java.util.Scanner;

import org.json.simple.parser.ParseException;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
//		utils.getWords();
		String randomWord=utils.getRandomWord(utils.getWords());
		System.out.println(randomWord);
		
		utils.checkWord(randomWord);
	}

}
