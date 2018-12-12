import java.util.*;
import java.io.*;
import java.sql.*;

public class WordCount {
	static int[] iarr=new int[150];
	static String[] sarr=new String[150];
	static int index=-1;
    public static void database(int[] frequency, String[] words, int filenumber){
		
		try {

			// Load MS accces driver class
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// C:\\databaseFileName.accdb" - location of your database 
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "E://MinorWork//TermFrequencyVector.accdb";

			Connection con = DriverManager.getConnection(url);
			Statement st=con.createStatement();

			String str1,str2;
			str1="CREATE TABLE D";
			str2=" ([Words] varchar(30), [Term_frequency] float)";			
			st.executeUpdate(str1+filenumber+str2); // CREATE TABLE
			str1="INSERT INTO D";
			str2=" VALUES ";
			int total_words=frequency.length;
//			String str3="(\'"+words[0]+"\', "+frequency[0]+")";
			for(int i=1;i<=frequency.length;i++){
				String str3=" (\'"+words[i-1]+"\', "+((float)frequency[i-1]/(float)total_words)+");";
				st.executeUpdate(str1+filenumber+str2+str3);
//				System.out.println(frequency[i-1]/total_words);
			}
//			str3+=";";
//			System.out.println(str1+filenumber+str2+str3);
//			st.executeUpdate(str1+filenumber+str2+str3);
			st.close();          con.close();


//			System.out.println("Connection Succesfull");
			} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			index++;
			iarr[index]=filenumber;
			sarr[index]=e.getMessage();

			}

	}

  public static void count(int filenumber){
		
        String fileName = "E:/MinorWork/Unique Ngrams_Tokens/Output"+filenumber+".txt";
		try{
			String[] words=null;
			int[] frequency=null;
			int k=-1;
			Scanner input = new Scanner(new File(fileName));
	  
        // count occurrences
			Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
			while (input.hasNextLine()) {
				String next = input.nextLine().toLowerCase();
				if (!wordCounts.containsKey(next)) {
					wordCounts.put(next, 1);
				} else {
					wordCounts.put(next, wordCounts.get(next) + 1);
				}
			}	
			words=new String[wordCounts.size()];
			frequency=new int[wordCounts.size()];
        // get cutoff and report frequencies
        System.out.println("Total Unique words = " + wordCounts.size());
 //       System.out.print("Minimum number of occurrences for printing? ");
 //       int min = console.nextInt();
			for (String word : wordCounts.keySet()) {
				int count = wordCounts.get(word);
 //           	if (count >= min)
  //              System.out.println(count + "\t" + word);		// print the unique world with their frequencies
				k++;
				words[k]=word;
				frequency[k]=count;
			}
			input.close();
//			for(int i=0;i<frequency.length;i++){System.out.println(frequency[i]);}
			database(frequency,words, filenumber);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				}
			
  }
    public static void main(String[] args) throws FileNotFoundException {
	
		for (int i=1;i<=500; i++){
			System.out.println("\n\tProcessing Document: "+i);
			count(i);
			System.out.println("\tOutput database D"+i+"\n");
		}
//		System.out.println("\n\nDoc. No. \t Error Message");
//		for(int j=0;j<iarr.length;j++){
//			System.out.println(iarr[j]+"\t\t"+sarr[index]);
//		}
    }
}