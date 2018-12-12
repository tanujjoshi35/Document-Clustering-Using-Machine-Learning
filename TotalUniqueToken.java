import java.util.*;
import java.io.*;
import java.sql.*;

public class TotalUniqueToken {
//	static int[] iarr=new int[150];
//	static String[] sarr=new String[150];
	static int index=-1;
	static String name1="Output", name2=".txt";
	public static void writeToFile(int[] frequency, String[] words)
    {	
        try
        {
			FileWriter fileWriter = new FileWriter(new File("FinalUniqueWordsSetWithDocumentFrequency.txt"), true);
            PrintWriter outFile = new PrintWriter(fileWriter);
			for(int i=0;i<words.length;i++){
				outFile.print(words[i]+" "+frequency[i]+"\r\n");
			}
			fileWriter.close();
			outFile.close();
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
		catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

	
	public static void database(int[] frequency, String[] words){
		
		try {

			// Load MS accces driver class
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// C:\\databaseFileName.accdb" - location of your database 
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "E://MinorWork//InverseDocumentFrequency.accdb";

			Connection con = DriverManager.getConnection(url);
			Statement st=con.createStatement();

			String str1,str2;
			str1="CREATE TABLE Inverse_Document_Frequency";
			str2=" ([Words] varchar(30), [Inverse Document frequency] float)";			
			st.executeUpdate(str1+str2); // CREATE TABLE
			str1="INSERT INTO Inverse_Document_Frequency";
			str2=" VALUES ";
			int total_words=frequency.length;
//			String str3="(\'"+words[0]+"\', "+frequency[0]+")";
			for(int i=1;i<=frequency.length;i++){
				String str3=" (\'"+words[i-1]+"\', "+Math.log(2225/frequency[i-1])+");";
				st.executeUpdate(str1+str2+str3);
				System.out.println("word no. "+(i-1));
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
//			iarr[index]=filenumber;
//			sarr[index]=e.getMessage();

			}

	}



    public static void count(){
		
        String fileName = "E:/MinorWork/UniqueWordsCollectionForAllDocument.txt";
		try{
			String[] words=null;		// contain unique words
			int[] frequency=null;		// contain the frequency of unique words
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
			
			for (String word : wordCounts.keySet()) {

				int count = wordCounts.get(word);
				k++;
				words[k]=word;
				frequency[k]=count;
			}
			input.close();
			System.out.println(words.length);
			database(frequency, words);
//			writeToFile(frequency, words);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				}
			
  }
    public static void main(String[] args) throws FileNotFoundException {
			count();
	}
}