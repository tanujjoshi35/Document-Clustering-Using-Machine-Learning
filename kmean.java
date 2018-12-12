import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.regex.Pattern;
class kmean{
	static int temp=0;
	static String[] words=null;
	static int[] frequency=null;
	static float[] documentweights=null;
	static double[] similarity=new double[500];
	
	
	
	    static int R[]=new int[500];
		static int N[]=new int[500];
		
		public static void read(int filenum,String str){
			
			  try
        {
          
			 Scanner textFile = new Scanner(new File("E:/MinorWork/Raw Dataset/sample ("+filenum+").txt"));
            textFile.useDelimiter(Pattern.compile("[ \n\r\t,.;:?!'\"]+"));
			FileWriter fileWriter = new FileWriter(new File(str+filenum+".txt"));
            PrintWriter outFile = new PrintWriter(fileWriter);
String word;
            while (textFile.hasNext())
            {
                word = textFile.next();

            outFile.print(word + " ");
            }
			fileWriter.close();
			textFile.close();
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
		
/*		public static String[] readFile(int filenum) 
    {

		int numWords=0;
		String[] textWords=null;
		try {
			Scanner scanner1 = new Scanner(new File("E:/MinorWork/Raw Dataset/Sample("+filenum+").txt"));
			Scanner scanner2 = new Scanner(new File("E:/MinorWork/Raw Dataset/Sample("+filenum+").txt"));
			
			while (scanner1.hasNext()) {
				scanner1.next();
				numWords++;
			}
			textWords=new String[numWords];
			for (int i = 0; i < numWords; i++){
                textWords[i] = scanner2.next();
			}
			scanner1.close();
			scanner2.close();
		    } catch (FileNotFoundException e) {
			e.printStackTrace();
		      }

        return textWords;
     }
		public static void writeToFile(String[] words,int filenum, String str)
		{	
		
//		String RESULT_FNAME = name1+filenumber+name2;
        try
        {
			FileWriter fileWriter = new FileWriter(new File(str+filenum+".txt"), true);
            PrintWriter outFile = new PrintWriter(fileWriter);
			for(int i=0;i<words.length;i++){
				outFile.print(words[i]+" ");
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
*/	
		public static int rel(String s1[],double f1[],String s2[],double f2[],String s3[],double f3[])
		{
        int l1=s1.length;
        int l2=s2.length;
        int l3=s3.length;
        double d1=0.0,d2=0.0;
        int a,b;
        a=b=0;
        
        int i; 
        
        for(i=0;i<s3.length;i++)
        {
          String w=s3[i];  //word in the target string
          d1+= f3[i]-fre(w,s1,f1); //diff with relevant cluster doc
          d2+= f3[i]-fre(w,s2,f2); //diff with irrelevant cluster doc
          
        }
		
		//System.out.println("d1 is="+d1+"  d2="+d2);
        if(d1<=d2)
        {
            //System.out.println("RELEVANT ");
			return 1;
        }
        else         
			{  
		     // System.out.println("IRRELEVANT ");
               return 0;
		     }
    }
    
    public static double fre(String w,String s[],double f[])
    {
        int i;
        for(i=0;i<s.length;i++)
        { 
	      //System.out.println("to be checked"+w);
	      //System.out.println("against"+s[i]);
            if(w.equalsIgnoreCase(s[i]))
			{ 
		//       System.out.println("found "+w);
				return f[i];
			}
        }
        return 0.0;//if tne word isnt present then return frequency as 0
    }
	
	public static void dbase(String url,Connection con,Statement st, int rel, int non){
		int count=0,temp=-1;
		try {

			String str1,str2,str3="";
			str1="select * from D";

				ResultSet rs = st.executeQuery(str1+rel+";");
				while(rs.next()){count++;}

				double[] relaventfreq=new double[count];
				String[] relaventwords=new String[count];
				rs = st.executeQuery(str1+rel+";");
				while(rs.next()){
					temp++;
					relaventwords[temp] = rs.getString("Words");
					relaventfreq[temp] = rs.getFloat("Term_frequency");
				}
				
				rs = st.executeQuery(str1+non+";");
				count=0;temp=-1;
				while(rs.next()){count++;}

				double[] non_relaventfreq=new double[count];
				String[] non_relaventwords=new String[count];
				rs = st.executeQuery(str1+non+";");
				while(rs.next()){
					temp++;
					non_relaventwords[temp] = rs.getString("Words");
					non_relaventfreq[temp] = rs.getFloat("Term_frequency");
				}
				rs.close();

				
//				for(int i=0;i<relaventfreq.length;i++){
//					System.out.println("\t"+relaventwords[i]+"  "+relaventfreq[i]);
//				}
				double[] docfreq=null; // array to store documents term frequency
				String[] docwords=null; // array to store corresponding words
				

				R[0]=rel;
				N[0]=non;
                int	a=0,b=0;  //arrays to store relevant doc no.s and non relevant doc no.s
                                             //a and b are the counters for both the arrays				
				for(int i=1;i<=500;i++)
				{
					if(i==rel || i==non)
						continue;
					rs = st.executeQuery(str1+i+";");
					count=0;temp=-1;
					while(rs.next()){count++;}

					docfreq=new double[count];
					docwords=new String[count];
					rs = st.executeQuery(str1+i+";");
					while(rs.next()){
						temp++;
						docwords[temp] = rs.getString("Words");
						docfreq[temp] = rs.getFloat("Term_frequency");
					}
					System.out.println("Processing Documnet no. "+i);
			        
					if(rel(relaventwords,relaventfreq,non_relaventwords,non_relaventfreq,docwords,docfreq)==1)
					{
						 R[++a]=i;
						 
					}
					else
					{ 
				       N[++b]=i;
					   
					}
					
					
				}
				
				System.out.println("\nPrinting the relevant docs numbers : ");
				int e,f;
				System.out.print("\n\t");
				for(e=0;e<a;e++)
				 {
					 System.out.print(R[e]+", ");
				 }
				 System.out.println("\n\n Printing the non-relevant docs numbers : ");
				 System.out.print("\n\t");
				 for(f=0;f<b;f++)
				 {
					 System.out.print(N[f]+", ");
				 }
				 
					rs.close();
					
				for(int i=0;i<a;i++){
				//	String[] reldata= readFile(R[i]);
					read(R[i],"E:/MinorWork/Clusters/Relavent/Output");
					
				}
				
				for(int i=0;i<b;i++){
				//	String[] reldata= readFile(R[i]);
					read(N[i],"E:/MinorWork/Clusters/Non-Relavent/Output");
				
				}
/*
					int temp1=-1,temp2=-1;
				String[] reluniquewords=new String[80000];
				String[] nonuniquewords=new String[80000];				
				for (int i=0;i<a; i++){ //unique words for relevant documents
					rs = st.executeQuery(str1+R[i]+";");
					while(rs.next()){
						temp1++;
						reluniquewords[temp1] = rs.getString("Words");
					}
				}
				
				for (int i=0;i<b; i++){ //unique words for non-relevant documents
					rs = st.executeQuery(str1+N[i]+";");
					while(rs.next()){
						temp2++;
						nonuniquewords[temp2] = rs.getString("Words");
					}
				}
				System.out.println("Successful with Relavent unique word : "+temp1);
				System.out.println("Successful with Non-relavent unique word : "+temp2);

				String uni[]=selectuni(reluniquewords);//for relevant docs
				String nonuni[]=selectuni(nonuniquewords);//for relevant docs
				double[] relmeanfreq= freqwords(uni,url,con,st,temp1);
				double[] nonmeanfreq=freqwords(nonuni,url,con,st,temp2);
*/
				/*					for(int i=0;i<docfreq.length;i++){							// printing the doc
					System.out.println("\t\t"+docwords[i]+"  "+docfreq[i]);
				}
*/			


/*				double[] docfreqtemp=new double[relaventwords.length]; // array to store documents term frequency
				String[] docwordstemp=new String[relaventwords.length]; // array to store corresponding words				
				//converting document to same vector size as relavent doc
				for(int i=0;i<relaventwords.length;i++){
					
					int index=find(relaventwords[i],docwords);
					if(index!=-1){
						docfreqtemp[i]=docfreq[index];
						docwordstemp[i]=docwords[index];
					}else{
						docfreqtemp[i]=0;
						docwordstemp[i]=relaventwords[i];
					}
						
					
				}

					for(int i=0;i<relaventwords.length;i++){							// printing the doc
					System.out.println("\t\t"+relaventwords[i]+"  "+relaventfreq[i]+"\t\t"+docwordstemp[i]+"  "+docfreqtemp[i]);
				}
			

*/
		}catch(SQLException se){
			//Handle errors for JDBC
	 		se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
			

	}

	public static void call(int rel, int non){
		try {

			// Load MS accces driver class
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// C:\\databaseFileName.accdb" - location of your database 
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "E://MinorWork//TermFrequencyVector.accdb";

			Connection con = DriverManager.getConnection(url);
			Statement st=con.createStatement();
			dbase(url,con,st,rel, non);
			}catch(SQLException se){
				//Handle errors for JDBC
				se.printStackTrace();
			}catch(Exception e){
				//Handle errors for Class.forName
				e.printStackTrace();
			}
	}
	
    public static void count(){
		
        String fileName = "query.txt";
		try{
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
			documentweights=new float[wordCounts.size()];
			for (String word : wordCounts.keySet()) {
				int count = wordCounts.get(word);
				k++;
				words[k]=word;
				frequency[k]=count;
			}
			input.close();

			}catch (FileNotFoundException e) {
				e.printStackTrace();
				}
			
  }
  
  	public static void writeToFile(String[] words)
    {	
		
//		String RESULT_FNAME = name1+filenumber+name2;
        try
        {
			FileWriter fileWriter = new FileWriter(new File("query.txt"), false);
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

	public static void database(int filenumber,String url,Connection con,Statement st){
		
		try {

			String str1,str2,str3="";
			str1="select Term_frequency from D";
			str2=" where ";
			for(int i=0;i<words.length;i++){
				
				str3="Words=\'"+words[i]+"\';";
				ResultSet rs = st.executeQuery(str1+filenumber+str2+str3);
				while(rs.next()){
					documentweights[i] = rs.getFloat("Term_frequency");

				}
				rs.close();

			}

		}catch(SQLException se){
			//Handle errors for JDBC
	 		se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
			

	}
	
	public static void cosineSimilarity(int filenum){
		float sumfrequency=0, sumweights=0;
		for (int i=0;i<words.length;i++){

			sumfrequency+=Math.pow(frequency[i],2);
			sumweights+=Math.pow(documentweights[i],2);
			similarity[filenum]+=frequency[i]*documentweights[i];
		}	

		similarity[filenum]=similarity[filenum]/(Math.pow(sumfrequency,0.5)*Math.pow(sumweights,0.5));
	}

//	public static void clustering(int relavent, int nonrelevant)
	
	public static void main(String[] agrs){
		
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the query");
		String query=sc.nextLine();
		String[] word = query.split(" ");
		String[] ngrams=new String[2*word.length-1];
		for (int i=0;i<ngrams.length;i++){
			if(i<word.length){
				ngrams[i]=word[i];
				continue;
			}
			
			else{
				ngrams[i]=word[temp]+" "+word[temp+1];
				temp++;
			}
		}
			writeToFile(ngrams);
			count();
		System.out.println("\n\n");

		try {

			// Load MS accces driver class
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			// C:\\databaseFileName.accdb" - location of your database 
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "E://MinorWork//TermFrequencyVector.accdb";

			Connection con = DriverManager.getConnection(url);
			Statement st=con.createStatement();
	
		

		for(int i=1;i<=500;i++){
			similarity[i-1]=0;
			for(int j=0;j<words.length;j++){
			documentweights[j]=0;
			}
			database(i,url,con,st);		
				cosineSimilarity(i-1);

			if (Double.isNaN(similarity[i-1]))
				similarity[i-1]=0;
			System.out.println("Similarity "+i+"= "+similarity[i-1]);

		}

		
		}catch(SQLException se){
			//Handle errors for JDBC
	 		se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}
		double max=0, min=0;
		int maxindex=0, minindex=0;
		for(int i=0;i<similarity.length;i++){
			if(similarity[i]>=max){
				max=similarity[i];
				maxindex=i+1;
			}
			if(similarity[i]<=min){
				minindex=i+1;
				min=similarity[i];
			}
		}
		if (maxindex!=minindex)
			System.out.println("\n\tRelevant Doc = "+maxindex+"\n\tNon-Relevant Doc ="+minindex);
		else
			System.out.println("\n\tNo relevant doc found for the entered query..... ");
		
		call(maxindex, minindex);
		System.out.println("\n\tClustering Successful please check the directory of relevant and non-relavent docs");

	}
}