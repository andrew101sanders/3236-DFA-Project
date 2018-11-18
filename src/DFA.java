import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DFA {

	public static void main(String[] args) {
		File filename;
		File temp = new File("");
		String in = "";
		Scanner sc = new Scanner(System.in);
		
		try{
			while(true) { //To check file name validity
				filename = new File(args[0]);
				if(filename.exists() || temp.exists()) {
					break;
				}else {
					System.out.println("Please enter a valid file name:");
					in = sc.next();
					temp = new File(in);
				}
			}
		}catch(Exception e) {
			System.out.println("No filename was entered!\nPlease enter a valid file name (with .txt):");
			while(true) {
				in = sc.next();
				filename = new File(in);
				if(filename.exists()) {
					break;
				}else {
					System.out.println("Please enter a valid file name (with .txt):");
				}
			}
		}
		
		boolean testing = true;
		while(testing) {
		
			System.out.println("Please enter a string to test: ");
			in = sc.next();
			
//			DFA(filename, in);
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

	private static void DFA(File file, String in) {

	}
	
}
