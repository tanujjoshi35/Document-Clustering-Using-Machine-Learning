//import java.excude.*;
import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class StopWords {
	static String name1="E:/MinorWork/Processed data after stopwords removal/Output", name2=".txt";
	private static String RESULT_FNAME;
	static String[] stopWords = null;
	static int numstopWords=0;
    public static boolean isStopWord(String word)
    {
        boolean found = false;  
		for(int i=0;i<numstopWords;i++){
			if (word.equalsIgnoreCase(stopWords[i])){
				found=true;
				break;
			}
			else
				continue;
	    }
		return found;
    }


    public static String[] readStopWords() 
    {
 


		try {
			Scanner scanner1 = new Scanner(new File("E:/MinorWork/stop.txt"));
			Scanner scanner2 = new Scanner(new File("E:/MinorWork/stop.txt"));
			
			while (scanner1.hasNext()) {
				scanner1.next();
				numstopWords++;
			}
//			System.out.println("Num of stopwords are: "+ numstopWords);
			stopWords=new String[numstopWords];
			for (int i = 0; i < numstopWords; i++){
                stopWords[i] = scanner2.next();
//				System.out.println(stopWords[i]);
			}
			scanner1.close();
			scanner2.close();
		    } catch (FileNotFoundException e) {
			e.printStackTrace();
		      }

        return stopWords;
     }

    public static void removeStopWords(int filenumber)
    {	
		String str1="E:/MinorWork/Raw Dataset/sample (";
		String str2=").txt";
		String textFilename=str1+filenumber+str2;
        String word;
		RESULT_FNAME = name1+filenumber+name2;

        try
        {
            Scanner textFile = new Scanner(new File(textFilename));
            textFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));
			FileWriter fileWriter = new FileWriter(new File(RESULT_FNAME));
            PrintWriter outFile = new PrintWriter(fileWriter);

            while (textFile.hasNext())
            {
                word = textFile.next();

                if (isStopWord(word))
 //                   System.out.print(word + " ");
					  System.out.print("");
                else
                    outFile.print(word + " ");
            }
			fileWriter.close();
			textFile.close();
			outFile.close();
			System.out.println();
            System.out.println("\t Output File " + RESULT_FNAME);
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
		
/*      finally
        {
            textFile.close();
            outFile.close();
        }
 */   }

    public static void main(String[] arg)
    {
        readStopWords();
		for(int k=1;k<=500;k++){   // K is the number of documents to be processed. Here 5 documents are processed.
			System.out.println("\t Processing...... Document Number: "+k);
			removeStopWords(k);
			System.out.println();
			System.out.println();
		}
    }
}