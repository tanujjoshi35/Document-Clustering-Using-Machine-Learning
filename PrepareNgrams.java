import java.util.*;
import java.io.*;

public class PrepareNgrams {
	static String name1="E:/MinorWork/Unique Ngrams_Tokens/Output", name2=".txt";
	private static String RESULT_FNAME;
	static int numWords=0;
	static String text="";
	public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
        return ngrams;
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public static String readFileWords(int filenum) 
    {
		String path="E:/MinorWork/Processed data after stopwords removal/Output";
		path=path+filenum+name2;
		int numWords=0;

		try {
			Scanner scanner1 = new Scanner(new File(path));
			
			while (scanner1.hasNext()) {
				text=text+scanner1.next()+ " ";
				numWords++;
			}
			scanner1.close();
		    } catch (FileNotFoundException e) {
			e.printStackTrace();
		      }
        return text;
    }

	public static void writeToFile(int filenumber,String lst)
    {	
		RESULT_FNAME = name1+filenumber+name2;
        try
        {
			FileWriter fileWriter = new FileWriter(new File(RESULT_FNAME));
            PrintWriter outFile = new PrintWriter(fileWriter);
            outFile.print(lst);
        
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


	
    public static void main(String[] args) {
		for(int numfiles=1;numfiles<=500;numfiles++){
			String completeText="";
			System.out.println("\t Processing...... Document Number: "+numfiles);
			for (int n = 1; n < 3; n++) {
				for (String ngram : ngrams(n, readFileWords(numfiles))){
					completeText+=ngram+" \r\n";
				}
            }
			writeToFile(numfiles, completeText);
			text="";
            System.out.println("\t Output File " + RESULT_FNAME);
		}

    }
}