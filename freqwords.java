  double fr[]=new double[uni.length]; //for storing the frequency of the unique words
                       //No. of relevant docs -> a+

                        String s1; int i,j;
                         for(i=0;i<uni.length;i++)  //for every unique word
                           {  for(j=0;j<a;j++)        // for every relevant doc      
                             {
                               s1="SELECT Term_frequency from D"+j+"WHERE Words="+uni[i];
                               ResultSet f=st.executeQuery(st);
                               fr[i]+=f.getDouble("Term_frequency");   //summing the frequency
                               
                              }
                           fr[i]/=uni.length;
                           }
                         