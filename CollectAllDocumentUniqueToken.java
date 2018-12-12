import java.util.*;
import java.io.*;
import java.sql.*;

public class CollectAllDocumentUniqueToken {
	static int[] iarr=new int[150];
	static String[] sarr=new String[150];
	static int index=-1;
	static String name1="Output", name2=".txt";
	public static void writeToFile(int[] frequency, String[] words)
    {	
		
//		String RESULT_FNAME = name1+filenumber+name2;
        try
        {
			FileWriter fileWriter = new FileWriter(new File("FinalUniqueWordsSetWithDocumentFrequency.txt"), true);
            PrintWriter outFile = new PrintWriter(fileWriter);
			for(int i=0;i<words.length;i++){
				outFile.print(words[i]+"\r\n");
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



    public static void count(){
		
//        String fileName = "E:/MinorWork/Unique Ngrams_Tokens/Output"+filenumber+".txt";
		try{
			String[] words=null;		// contain unique words
			int[] frequency=null;		// contain the frequency of unique words
			int k=-1;
			Scanner input = new Scanner(new File("UniqueWordsCollectionForAllDocument.txt"));
	  
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
			writeToFile( frequency, words);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				}
			
  }
    public static void main(String[] args) throws FileNotFoundException {
	
//		for (int i=1;i<=500; i++){
	//		System.out.println("\n\tProcessing Document: "+i);
			count();
//		}

    }
}