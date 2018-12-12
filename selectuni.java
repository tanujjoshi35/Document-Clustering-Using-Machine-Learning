public static  String[] selectuni(String uni[])			// select unique words from an given string array
{	String us[]=new String[400];
    int l=1;
    us[0]=uni[0];
    int c=0;
    for(i=1;i<uni.lenght;i++)
      {    
	        c=0;  //resetting the flag variable.
             for(j=0;j<l;j++)
             {
                 if(uni[i].equalsIgnoreCase(us[j]))  //word already present
                 {
                    c++;  //flag variable
                    break;//check the next word
                 } 
             }
			 if(c==0)
             {
			  us[l]=uni[j];  // word isnt present hence it is now entered into the new array
              l++; // word is inserted at the end of the array
			 }
      }
      //l is the new length of the  array

	  return us;
	  }
	  