package tp1.searchPatterns;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tp1.automatas.Alphabet;
import tp1.automatas.AutomatonException;
import tp1.automatas.State;
import tp1.automatas.StateSet;

/**
 * @author Vale
 * @version 1.0
 *
 * **/
public class Scanner {

	private  static String dir = System.getProperty("user.dir") + "/";
	private  BufferedReader breader = null;
	private  PatternList patterns =null;
	

	
	
	private STATUS status;
	
	/*
	 * Constructor
	 * @param filePath: relative path (and name) to the file to be scanned. 
	 * @param pattern: patterns to be search for matching in the file filePath.
	 * */
	public Scanner(String filePath, PatternList patterns) {
		status =STATUS.NONSCANNED;
		FileReader freader = null;
    	try {
	    	//Opens file
    		File file = new File(dir + filePath);
    		freader =  new FileReader(file);
	    }catch(Exception e){ 
	    	System.out.println("Problem to open file: " + dir + filePath + e.toString());
	    }
		breader = new BufferedReader(freader);
		this.patterns = patterns;
	}
	
	
	/**
	 * Search filePath for the first occurrence of 
	 * any of the patterns in the pattern list. 
	 * @returns the first line in which it finds a matching 
	 * of any of the patterns in the pattern list
	 * if there is no matching it returns null.
	 * */
	public String scanFile() throws IOException {
		status =STATUS.OK; 
		String line;
		String result =null;
		boolean found= false;
		while ((line=breader.readLine())!= null && !found) {
			try {
				found = patterns.scan(toHex(line));
				if(found) {
					result = toHex(line);
					status = STATUS.SUSPICIOUS;
				}	
			} catch (AutomatonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     //   System.out.println(toHex(line));
		}
		try{
    		breader.close();                 
        }catch (Exception e){
        	System.out.println(e.toString());
        }
		
		return result;
	}
	
	
	/**
	 * @returns the status of the scanner.
	 * **/
	public STATUS showStatus() {
		return status;
	}
	
	
	
	
	/*prints (line by line) the file associated with 
	 * the scanner in hexadecimal format
	 * Use it in case you want to take a look at the contents 
	 * of the file in hexadecimal format*/
	public void printFileToHex(){
		String line =null;
		try {
			while ((line=breader.readLine())!=null) {
					line = toHex(line);
					System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	/****private methods***/
	
	private static String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes()));

	}

}
