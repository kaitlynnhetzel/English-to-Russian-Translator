/**
 * RussianTranslator class
 * This class has two main functions: 
 * 1) A flash-card like 'Commonly Used Russian Words' function
 * 2) A sentence translator that has the user choose a SUBJECT, VERB, and PREDICATE
 *    from a pre-chosen list of Russian words. At the end, the program will display
 *    a grammatically correct English, Russian, and anglified-Russian sentence that
 *    users can learn
 *    
 * @author Kaitlynn Hetzel
*/

import java.io.*;
import java.util.*;


public class RussianTranslator {
	//declaring a printstream that is capable of handling UTF-8 characters
	//used throughout the program for the Cyrillic characters
	static PrintStream out = null;
	
	
	public static void main(String[] args) throws Exception {
		
		//Initializes the UTF-8 capable printstream
		out = new PrintStream(System.out, true, "UTF-8");
		out.println("Привет! And Welcome to English-to-Russian 1.0!");
		
		mainMenu();
		
	}

	
	/**
	 * The "Main Menu" method
	 * Holds the main menu to allow users to access the Commonly Used Words method and
	 * the Sentence Translator function, and to exit the program if user so desires
	*/
	public static void mainMenu(){
		Scanner kb = new Scanner(System.in);
		boolean invalid;
		
		//main menu table
		System.out.println("");
		System.out.println("Main Menu: ");
		System.out.println("\t1. Review Commonly Used Words");
		System.out.println("\t2. Sentence Translator");
		System.out.println("\t0. Exit the Program");
		System.out.println("");
		
		
		int menuOpt = -1;
		do{
			System.out.print("Please enter a menu option: ");
			try{
				
				menuOpt = kb.nextInt();
				if(menuOpt > 2 || menuOpt < 0){
					System.out.println("I'm sorry, but that is not a valid menu option! Try again.");
					invalid = true;
				}
				else{
					invalid = false;
				}
			}
			catch(InputMismatchException ime){
				System.out.println("I'm sorry, but that is not a valid menu option! Try again.");
				kb.next();
				invalid = true;
			}
		}while (invalid);
		
		//opens the "commonly used words" function of the class
		if(menuOpt == 1){
			System.out.println("");
			System.out.println("Commonly Used Words:");
			commonlyUsedWords();
		}
		
		//opens the "sentence translator" function of the class
		if(menuOpt == 2){
			System.out.println();
			sentenceTranslator();
		}
		
		//shuts down the program
		if(menuOpt == 0){
			out.println("Thanks for using my program. До Свидания!!");
			System.exit(0);
		}
					
	}

	/**
	 * The "Commonly Used Words" Function
	 * Allows the user to choose a common English word or phrase and have the program
	 * translate that word quickly and efficiently.
	 * 
	*/
	public static void commonlyUsedWords(){
		Scanner kb = new Scanner(System.in);
		BufferedReader fileScan = null;
		
		//opening a scanner to read in the Commonly Used Words file
		try{
			fileScan = new BufferedReader(new FileReader("CommonlyUsedWords.txt"));
		}
		catch(Exception e){
			System.out.println("Something went wrong. Check your file locations and try again");
			System.exit(0);
		}
		
		//HashMap for holding the information of the Commonly Used Words
		HashMap<String, String[]> comWords = new HashMap<String, String[]>();
		String line = null;

	
		//this will set the English keyword of the HashMap comWords
		String engKeyword = "";
		//this will hold the set of comWords keys 
		String keyList[] = new String[12];
		
		int i = 0;
		int countOne = 1;
		int countTwo = 2;
		
		try{
			while(((line = fileScan.readLine()) != null)&&(i < 12)){
				
				//the file is delimited by commas, so parts[] holds the different pieces
				//  of the file lines in a primitive array
				String parts[] = line.split(",");
				
				//these two initialize the English keyword and put that word into the key list
				engKeyword = parts[0];
				keyList[i] = parts[0];
				
				//innerArray holds the Russian word and the anglified-Cyrillic Russian word
				String[] innerArray = new String[2];
				innerArray[0] = parts[1];
				innerArray[1] = parts[2];
				
				//this puts the Key and the innerArray into the HashMap
				comWords.put(keyList[i], innerArray);
				
				//This begins the main menu - because I wanted a two-column table, I
				//divided the data up and had it print out the odd numbers/their words in the first
				//column and even numbers/their words in the second column
				if ((i%2 == 0)){
					
					out.print(countOne+": "+engKeyword);
					countOne = countOne +2;
					
					//These if-else statements help with the formatting of the table, hence the printing of tabs
					if(engKeyword.length() < 5){
						out.print("\t"+"\t"+"\t");
					}
					else if (engKeyword.length() <8){
						out.print("\t"+"\t");
					}
					else{
						out.print("\t");
					}
				}
				
				else{
					out.print(countTwo+": "+engKeyword+"\n");
					countTwo = countTwo +2;
				}
			
				i++;
			}
			System.out.println("0: Back to Main Menu");
		}
		
		catch(IOException ioe){
			System.out.println("Something went wrong. Program closing.");
			System.exit(0);
		}


		System.out.println();
		
		//This do-while loop has the user pick a word to translate 
		boolean invalid = true;
		int ans = -1;
		
		do{
			System.out.print("Please choose a number to translate that word: ");
			try{
				ans = kb.nextInt();
				invalid = false;
				if(ans < 0 || ans > 12){
					System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
					invalid = true;
				}
				
			}
			catch(InputMismatchException ime){
				System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
				kb.next();
				invalid = true;
			}

		}while(invalid);
		
		
		if (ans == 0){
			mainMenu();
		}
		
		//because the answer the user gives for an actual word is 1-12, the place that the keyword is located in the
		//keyword array is from 0-11, so we must have ans-1 after we take care of the 0/mainMenu() case
		ans = (ans-1);
		String key = keyList[ans];
		
		//the russianWords[] array holds the Russian word and the anglified-Cyrillic word.
		String russianWords[] = comWords.get(key);
		
		//the print-out of the answr
		out.println("'"+key+"' in Russian is: "+russianWords[0]+"/"+russianWords[1]);
		System.out.println();
				
		
		//Now, the program checks to see if they want to review more commonly used words
	    String userAns = "";
		boolean validCheck;
		do{
			System.out.print("Would you like to review more commonly used words? Yes or no: ");
			userAns = kb.next();
			
			//if yes, the method calls itself
			if(userAns.equalsIgnoreCase("yes")){
				validCheck = false;
				System.out.println();
				commonlyUsedWords();
			}
			//if not, the program calls the Main Menu method
			else if(userAns.equalsIgnoreCase("no")){
				validCheck = false;
				mainMenu();
			}
			else{
				System.out.println("That is not a valid option. Try again");
				validCheck = true;
			}
			
		}while (validCheck);
	}
	
	/**
	 * The "Sentence Translator" function
	 * This function has the user pick a SUBJECt, VERB, and PREDICATE from a pre-selected list of words.
	 * In the end, the program displays grammatically-correct English, Russian, and Anglified-Cyrillic sentences
	*/
	public static void sentenceTranslator(){
		System.out.println("Welcome to the sentence translator!");
		System.out.println("Please choose a SUBJECT from the list below.");
		System.out.println();
		System.out.println("SUBJECTS:");
		Scanner kb = new Scanner(System.in);
		BufferedReader fileScan = null;
		
		//Opening up the file with the SUBJECT list
		try{
			fileScan = new BufferedReader(new FileReader("Nouns.txt"));
		}
		catch(Exception e){
			System.out.println("Something went wrong. Check your file locations and try again");
			System.exit(0);
		}
		
		//HashMap that will contain all of the SUBJECT information
		HashMap<String, String[]> subject = new HashMap<String, String[]>();
		String line = null;

		//The String and the String[] that will hold the Subject keywords
		String subKeyword = "";
		String subList[] = new String[12];
		
		int i = 0;
		int countOne = 1;
		int countTwo = 2;
		
		try{
			while(((line = fileScan.readLine()) != null)&&(i < 12)){
				//parts[] holds the comma-delimited lines of the file
				String parts[] = line.split(",");
				
				//creation/addition of the keyword
				subKeyword = parts[0];
				subList[i] = parts[0];
				
				//creation of the Russian word/Anglified cyrillic word array
				String[] innerArray = new String[2];
				innerArray[0] = parts[1];
				innerArray[1] = parts[2];
				
				//adding the key & array to the HashMap
				subject.put(subList[i], innerArray);
				
				//Table creation, like in CUW Method
				if ((i%2 == 0)){
					out.print(countOne+": "+subKeyword);
					countOne = countOne +2;
					
					//Spacing for the table
					if(subKeyword.length() < 4){
						out.print("\t"+"\t"+"\t");
					}
					else if (subKeyword.length() <8){
						out.print("\t"+"\t");
					}
					else{
						out.print("\t");
					}
					
				}
				else{
					out.print("\t"+countTwo+": "+subKeyword+"\n");
					countTwo = countTwo +2;
				}
			
				i++;
			}
		}
		catch(IOException ioe){
			System.out.println("Something went wrong. Program closing.");
			System.exit(0);
		}


		System.out.println();
		
		
		//SUBJECT selection section
		boolean invalid = true;
		int ans = -1;
		
		do{
			System.out.print("Please select a number to choose your SUBJECT: ");
			try{
				ans = kb.nextInt();
				invalid = false;
				if(ans < 1 || ans > 12){
					System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
					invalid = true;
				}
				
			}
			catch(InputMismatchException ime){
				System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
				kb.next();
				invalid = true;
			}

		}while(invalid);
		
		//Getting the user answer, and then the KEY for the HashMap
		ans = (ans-1);
		String key = subList[ans];
		
		//I created the files to be "the Key.txt", so by joining the key String with ".txt", you will
		//get the file necessary to create the next part of the sentence
		String verbCommand = key+ ".txt";
		
		//russianSubject[] holds the Russian word & the latin-Russian word
		String russianSubject[] = subject.get(key);
		System.out.println("Your SUBJECT choice is: "+key);
		out.println("'"+key+"' in Russian is: "+russianSubject[0]+"/"+russianSubject[1]);
		System.out.println();
		
		//Here, I start creating the "sentences" in English, Russian, and Latin.
		//I add the subjects to the empty strings
		String sentenceConstructor = russianSubject[0];
		String englishConstructor = key;
		String latinConstructor = russianSubject[1];
		
		System.out.println("Okay! Now, let's choose a verb.");
		System.out.println();
		System.out.println("The VERBS available for the subject '"+key+"' are:");
		
		BufferedReader fileScan2 = null;
		
		//For whatever reason, "I.txt" was not being read in by the Scanner correctly just by 
		//the appending of ".txt" to "I", so I had to hard encode it. Everything else reads in properly
		if(ans == 0){
			verbCommand = "I.txt";
		}
		
		try{
			fileScan2 = new BufferedReader(new FileReader(verbCommand));
		}
		catch(Exception e){
			System.out.println("Something went wrong. Check your file locations and try again");
			System.exit(0);
		}		
		
		//Same principals as the SUBJECT portion are being applied to the VERB portion of the program
		//You'll notice that the encoding is almost exactly the same as above.
		HashMap<String,String[]> verbs = new HashMap<String, String[]>();
		line = null;
		
		String verbKeyword = "";
		
		String verbList[] = new String[8];
		
		i = 0;
		int counter = 1;
		int counterTwo = 2;
		
		try{
			while(((line = fileScan2.readLine()) != null)&&(i < 8)){
				
				String parts[] = line.split(",");
				verbKeyword = parts[0];				
				verbList[i] = parts[0];
				
				//innerArray is longer than it previously was because I had six values encoded in the VERB text files
				String[] innerArray = new String[5];
				
				innerArray[0] = parts[1];
				innerArray[1] = parts[2];
				innerArray[2] = parts[3];
				innerArray[3] = parts[4];
				innerArray[4] = parts[5];
				
				verbs.put(verbList[i], innerArray);
				
				//creation of the table
				if ((i%2 == 0)){
					out.print(counter+": "+verbKeyword);
					counter = counter +2;
					
					//spacing here is weird because "Know" was not reading in as 4 characters, but rather 5 characters
					//I have no idea why, so I just went with it and adjusted accordingly
					if((counter-2)== 1 && (ans == 0 || ans == 1 || ans == 5 || ans == 6 || ans == 7  )){
						out.print("\t"+"\t");
					}
					else if (verbKeyword.length() < 5 && ( (counter - 2) > 1) ){
						out.print("\t"+"\t");
					}
					else{
						out.print("\t");
					}
				}
				else{
					out.print(counterTwo+": "+verbKeyword+"\n");
					counterTwo = counterTwo +2;
				}
			
				i++;
			}
		}
		catch(IOException ioe){
			System.out.println("Something went wrong. Program closing.");
			System.exit(0);
		}
		
		System.out.println();
		
		//verb selction below
		invalid = true;
		ans = -1;
		
		do{
			System.out.print("Please choose a number to choose your VERB: ");
			try{
				ans = kb.nextInt();
				invalid = false;
				if(ans < 1 || ans > (counterTwo - 2)){
					System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
					invalid = true;
				}
				
			}
			catch(InputMismatchException ime){
				System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
				kb.next();
				invalid = true;
			}

		}while(invalid);		
	
		//converting user answer and getting the key value
		ans = (ans-1);
		key = verbList[ans];
		
		//getting the Russian and the Latin words from the HashMap
		String russianVerb[] = verbs.get(key);
		System.out.println("Your VERB choice is: "+key);
		out.println("'"+russianVerb[2]+"' in Russian is: "+russianVerb[3]+"/"+russianVerb[4]);
		
		
		//Creation of the predicate filename
		String predicateCommand = russianVerb[2]+".txt";
		
		//appending the verb to the sentences
		sentenceConstructor = sentenceConstructor+" "+russianVerb[0];
		englishConstructor = englishConstructor+" "+key.toLowerCase();
		latinConstructor = latinConstructor+" "+russianVerb[1].toLowerCase();
		System.out.println();
		
		System.out.println("Now,let's choose a PREDICATE");		
		
		
		//"choosing a PREDICATE portion" - works identically to the SUBJECT selection 
		BufferedReader fileScan3 = null;
		
		try{
			fileScan3 = new BufferedReader(new FileReader(predicateCommand));
		}
		catch(Exception e){
			System.out.println("Something went wrong. Check your file locations and try again");
			System.exit(0);
		}
		HashMap<String, String[]> predicate = new HashMap<String, String[]>();
		line = null;

		
		String predKeyword = "";
		String predList[] = new String[6];
		
		i = 0;
		countOne = 1;
		countTwo = 2;
		
		try{
			while(((line = fileScan3.readLine()) != null)&&(i < 6)){
				//reading in the file and alotting the keys/values in to their correct spots
				String parts[] = line.split(",");
				predKeyword = parts[0];
				predList[i] = parts[0];
				String[] innerArray = new String[2];
				innerArray[0] = parts[1];
				innerArray[1] = parts[2];
				
				//adding the keys/arrays in to the HashMap
				predicate.put(predList[i], innerArray);
				
				//table creation
				if ((i%2 == 0)){
					out.print(countOne+": "+predKeyword);
					countOne = countOne +2;

					//spacing stuff
					if(predKeyword.length() <= 5){
						out.print("\t"+"\t"+"\t");
					}
				
					else if (predKeyword.length() < 14 ){
						out.print("\t"+"\t");
					}	
					else{
						out.print("\t");			
					}
				
				}
				else{
					out.print(countTwo+": "+predKeyword+"\n");
					countTwo = countTwo +2;
				}
			
				i++;
			}
		}
		catch(IOException ioe){
			System.out.println("Something went wrong. Program closing.");
			System.exit(0);
		}


		System.out.println();
		
	    invalid = true;
		ans = -1;
		
		//predicate selection
		do{
			System.out.print("Please select a number to choose your PREDICATE: ");
			try{
				ans = kb.nextInt();
				invalid = false;
				if(ans < 1 || ans > 6){
					System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
					invalid = true;
				}
				
			}
			catch(InputMismatchException ime){
				System.out.println("I'm sorry, but that is not a valid menu option. Try again.");
				kb.next();
				invalid = true;
			}

		}while(invalid);
			
		//ans conversion/key creation
		ans = (ans-1);
		key = predList[ans];
		
		// get the Russian words
		String russianPredicate[] = predicate.get(key);

		System.out.println("Your PREDICATE choice is: "+key);
		out.println("'"+key+"' in Russian is(as a predicate): "+russianPredicate[0]+"/"+russianPredicate[1]);
		System.out.println();
		
		//Append the predicates and a "." to the end of the sentences
		sentenceConstructor = sentenceConstructor+" "+russianPredicate[0]+".";
		englishConstructor = englishConstructor+" "+key.toLowerCase()+".";
		latinConstructor = latinConstructor+" "+russianPredicate[1].toLowerCase()+".";		
		
		//display the final result
		System.out.println("Ok, great! All pieces are in!");
		System.out.println("");
		
		System.out.println("Your sentence in English is: '"+englishConstructor+"'");
		System.out.println("In Russian, your sentence is: '"+sentenceConstructor+"'/'"+latinConstructor+"'");
		System.out.println();
		
		
		//Allows the user to either create another sentence or go back to the main menu
	    String userAns = "";
		boolean validCheck;
		do{
			System.out.print("Would you like to create more sentences? Yes or no: ");
			userAns = kb.next();
			
			if(userAns.equalsIgnoreCase("yes")){
				validCheck = false;
				System.out.println();
				sentenceTranslator();
			}
			else if(userAns.equalsIgnoreCase("no")){
				validCheck = false;
				mainMenu();
			}
			else{
				System.out.println("That is not a valid option. Try again");
				validCheck = true;
			}
			
		}while (validCheck);		
	}
}	


