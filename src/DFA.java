import java.io.File;
import java.util.Scanner;

public class DFA {

	public static void main(String[] args) {
		File file;
		String in = "";
		Scanner sc = new Scanner(System.in);
		
		try{
			while(true) { //To check file name validity
				if(args[0].contains(".txt") || in.contains(".txt")) {
					file = new File(args[0]);
					break;
				}else {
					System.out.println("Please enter a valid file name (with .txt):");
					in = sc.next();
				}
			}
		}catch(Exception e) {
			System.out.println("No filename was entered!\nPlease enter a valid file name (with .txt):");
			while(true) {
				in = sc.next();
				if(in.contains(".txt")) {
					file = new File(in);
					break;
				}else {
					System.out.println("Please enter a valid file name (with .txt):");
				}
			}
		}
		
		boolean testing = true;
		while(true) {
		
			System.out.println("Please enter a string to test: ");
			in = sc.next();
			
			//Pass 'in' to DFA logic
			//Print result			
			
			System.out.println("Would you like to check another string? Y/N");
			in = sc.next();
			if(in.contains("N")) {
				break;
			}
		}
		System.out.println("Goodbye!");
	}

	public DFA(String filename) {
		
	}
	
	
	
}
