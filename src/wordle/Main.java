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
		System.out.println("Letter in green means the letter exists in the word and it is in the right place\n");
		System.out.println("Letter in yellow means the letter exists in the word but it is not in the right place\n");
		utils.checkWord(randomWord);
	}

}
