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
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DFA {

	public static void main(String[] args) throws IOException {
		File filename;
		File temp = null;
		String in = "";
		Scanner sc = new Scanner(System.in);

		try{
			while(true) { //To check file name validity
				filename = new File(args[0]);
				if(filename.exists() || temp.exists()) {
					break;
				}else {
//					temp = selectfile();
					System.out.println("Please enter a valid file name:");
					in = sc.next();
					temp = new File(in);
				}
			}
		}catch(Exception e) {
			System.out.println("No filename was entered!\nPlease enter a valid file name (with .txt):");
			while(true) {
				in = sc.next();
//				filename = selectfile();
				filename = new File(in);
				if(filename.exists()) {
					break;
				}else {
					System.out.println("Please select a valid file name (with .txt):");
				}
			}
		}
		Object[] DFAtt = DFAcreate(filename);
		boolean testing = true;
		while(testing) {

			System.out.println("Please enter a string to test: ");
			in = sc.next();

			DFAcheck(DFAtt, in);
			//Print result			

			System.out.println("Would you like to check another string? Y/N");
			in = sc.next();
			boolean again = false;
			if(in.contains("N")) {
				testing=false;
			}else if(in.contains("Y")){
				testing=true;
			}else {
				again=true;
				while(again) {
					System.out.println("Please type Y or N.");
					in = sc.next();
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


		br.close();

		Object mapsAndDFA[] = new Object[5];
		mapsAndDFA[0] = alphabetMap;
		mapsAndDFA[1] = stateMap;
		mapsAndDFA[2] = transitionTable;
		mapsAndDFA[3] = startState;
		mapsAndDFA[4] = acceptStates;
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
		
		for (int i = 0; i != in.length(); i++) {
			currentInput = String.valueOf(in.charAt(i));
			System.out.println("Current state: " + currentState + ". Current input: " + currentInput);
			currentState = transitionTable[stateMap.get(currentState)][alphabetMap.get(currentInput)];
			System.out.println("Resulting state: " + currentState);
		}
		
		for(int p = 0; p<acceptStates.size(); p++) {
			if (currentState.equals(acceptStates.get(p))) stringIsAccepted = true;
		}
		
		System.out.println("String " + in + " is " + (stringIsAccepted ? "accepted" : "not accepted"));
	}
	
//	private static File selectfile() {
//		File file = null;
//			String[] buttons = {"Okay", "Cancel"}; 
//			int rc = JOptionPane.showOptionDialog(null, "Press OK to Search for a text file that contains the DFA description", "Confirmation",
//			    JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);  
//				JFileChooser chosenfile = new JFileChooser();
//				chosenfile.setDialogTitle("Select a text file");
//				chosenfile.setFileFilter(new FileNameExtensionFilter("text file", "txt"));
//				int returnValue = chosenfile.showOpenDialog(null);
//				if (returnValue == JFileChooser.APPROVE_OPTION) {
//					file = chosenfile.getSelectedFile();
//				}
//				else {
//					JOptionPane.showMessageDialog(null, "No file chosen!", "what the heck!", JOptionPane.ERROR_MESSAGE);
//					return null;
//				}
//		return file;
//		}
//	
//	
//

}


