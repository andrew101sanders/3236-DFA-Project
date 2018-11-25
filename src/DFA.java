import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DFA {

	public static void main(String[] args) throws IOException {
		File filename = null;
		String in = "";
		Scanner sc = new Scanner(System.in);

		try{
			if(args.length > 0) {
				filename = new File(args[0]);
				if(!filename.exists()) {
					System.out.println(filename + " does not exist!\nPlease select a valid file (with .txt):");
					filename = null;
				}
			}
			else {
				System.out.println("No filename was entered!\nPlease select a valid file (with .txt):");
			}
			
			if (filename == null) {
				while(true) {
					filename = selectFile();
					if(filename != null && filename.exists()) {
						break;
					}else {
						System.out.println("You clicked Cancel; would you like to try again? (Y to continue or N to quit)");
						in = sc.next().toUpperCase();
						boolean again = false;
						if(in.contains("N")) {
							sc.close();
							System.out.println("Goodbye!");
							System.exit(0);
						}else if(in.contains("Y")){
							//continue
						}else {
							again=true;
							while(again) {
								System.out.println("Please type Y to continue or N to quit.");
								in = sc.next().toUpperCase();
								if(in.contains("N")) {
									sc.close();
									System.out.println("Goodbye!");
									System.exit(0);
								}else if(in.contains("Y")){
									again=false;
									//continue
								}
							}
						}
							
						System.out.println("Please select a valid file (with .txt):");
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			sc.close();
			System.out.println("Goodbye!");
			System.exit(0);
		}
		
		Object[] DFAtt = DFAcreate(filename);
		if(DFAtt[0] == null) { //file was invalid
			sc.close();
			System.out.println("Goodbye!");
			System.exit(0);
		}
		
		boolean testing = true;
		while(testing) {

			System.out.println("Please enter a string to test: ");
			in = sc.next();

			DFAcheck(DFAtt, in);
			//Print result			

			System.out.println("Would you like to check another string? Y/N");
			in = sc.next().toUpperCase();
			boolean again = false;
			if(in.contains("N")) {
				testing=false;
			}else if(in.contains("Y")){
				testing=true;
			}else {
				again=true;
				while(again) {
					System.out.println("Please type Y or N.");
					in = sc.next().toUpperCase();
					if(in.contains("N")) {
						again=false;
						testing=false;
						break;
					}else if(in.contains("Y")){
						again=false;
						break;
					}
				}
			}
		}
		
		sc.close();
		System.out.println("Goodbye!");
	}

	private static Object[] DFAcreate(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList<String> alphabet = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();
		ArrayList<String> startState = new ArrayList<String>();
		ArrayList<String> acceptStates = new ArrayList<String>();
		String transitionTable[][] = null;
		StringTokenizer tokn = null;
		Object mapsAndDFA[] = new Object[5];

		try {
			//first line - alphabet

			String temporary = br.readLine();
			temporary = temporary.substring(1, temporary.length()-1);
			tokn = new StringTokenizer(temporary, ",");
			while (tokn.hasMoreTokens()) alphabet.add(tokn.nextToken());
			//		for (int i = 0; i < alphabet.size(); i++) {
			//			System.out.println("---"+alphabet.get(i));
			//		}


			//second line - states
			temporary = br.readLine();
			temporary = temporary.substring(1, temporary.length()-1);
			tokn = new StringTokenizer(temporary, ",");
			while (tokn.hasMoreTokens()) states.add(tokn.nextToken());
			//		for (int i = 0; i < states.size(); i++) {
			//			System.out.println("+++"+states.get(i));
			//		}

			//third line - start state
			startState.add(br.readLine());

			//fourth line - accept states
			temporary = br.readLine();
			temporary = temporary.substring(1, temporary.length()-1);
			tokn = new StringTokenizer(temporary, ",");
			while (tokn.hasMoreTokens()) acceptStates.add(tokn.nextToken());
			//		for (int i = 0; i < acceptStates.size(); i++) {
			//			System.out.println("***"+acceptStates.get(i));
			//		}

			//rest - transition table stuff
			Map<String,Integer> alphabetMap = new HashMap<String,Integer>();
			Map<String,Integer> stateMap = new HashMap<String,Integer>();
			for (int i = 0; i < alphabet.size(); i++) {
				alphabetMap.put(alphabet.get(i), i);
			}
			for (int i = 0; i < states.size(); i++) {
				stateMap.put(states.get(i), i);
			}

			transitionTable = new String[states.size()][alphabet.size()];
			while(br.ready()) {
				temporary = br.readLine();
				tokn = new StringTokenizer(temporary, ",()->");
			
				transitionTable[stateMap.get(tokn.nextToken())][alphabetMap.get(tokn.nextToken())] = tokn.nextToken();
			}

			mapsAndDFA[0] = alphabetMap;
			mapsAndDFA[1] = stateMap;
			mapsAndDFA[2] = transitionTable;
			mapsAndDFA[3] = startState;
			mapsAndDFA[4] = acceptStates;
		}
		catch(Exception e) {
			System.out.println(file.getName() + " is not a valid DFA file!");
		}
		
		br.close();
		return mapsAndDFA;
	}

	@SuppressWarnings("unchecked")
	private static void DFAcheck(Object[] DFAtt, String in) {
		Map<String,Integer> alphabetMap = (Map<String, Integer>) DFAtt[0];
		Map<String,Integer> stateMap = (Map<String, Integer>) DFAtt[1];
		String transitionTable[][] = (String[][]) DFAtt[2];
		ArrayList<String> startState = (ArrayList<String>) DFAtt[3];
		ArrayList<String> acceptStates = (ArrayList<String>) DFAtt[4];
		boolean stringIsAccepted = false;

		String currentInput;
		String currentState = startState.get(0);
		boolean invalidInput = false;
		
		for (int i = 0; i != in.length(); i++) {
			currentInput = String.valueOf(in.charAt(i));
			System.out.println("Current state: " + currentState + ". Current input: " + currentInput);
			try {
				currentState = transitionTable[stateMap.get(currentState)][alphabetMap.get(currentInput)];
				System.out.println("Resulting state: " + currentState);
			}
			catch (Exception e) {
				System.out.println(currentInput + " is not a valid input");
				System.out.println("String " + in + " is not accepted");
				invalidInput = true;
				break;
			}
		}
		
		if (!invalidInput) {
			for(int p = 0; p<acceptStates.size(); p++) {
				if (currentState.equals(acceptStates.get(p))) stringIsAccepted = true;
			}
		
			System.out.println("String " + in + " is " + (stringIsAccepted ? "accepted" : "not accepted"));
		}
	}
	
	private static File selectFile() {
		File file = null;
		JFileChooser fileChooser = new JFileChooser("."); //"." starts in current directory
		FileFilter txtFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
		fileChooser.addChoosableFileFilter(txtFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
	      if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
	    	  file = fileChooser.getSelectedFile();
	      }
	      
	      return file;
	}
}
