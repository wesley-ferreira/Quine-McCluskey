class parte1
{
	public static void main (String args[])
	{
		
		int[] minterms = {0, 1, 4, 9, 14, 15};
        int[][] tabela_1 = create_table(minterms);
     
     	for(int j = 0; j < 6; j++)
		{
			for(int k = 0; k < 4; k++)
		    {
		    	System.out.print(tabela_1[j][k]);
		    }
		    System.out.println();
		}
	}
	
	public static String decimal_to_binary_string_4_digits(int decimal_minterm)
	{
		String strBinaryNumber = Integer.toBinaryString(decimal_minterm); 				
		while (strBinaryNumber.length() < 4)
		{    
        	strBinaryNumber = "0" + strBinaryNumber;        
       	} 
       	return strBinaryNumber;  
    }

    public static String[] binary_digits_to_array(String strBinaryNumber) 

    {
    	 String binary_digits_array[] = strBinaryNumber.split("");
    	 return binary_digits_array;
    }

    public static int[] string_to_int_array(String[] binary_digits_array)
    {
    	int int_binary_array[]= new int[binary_digits_array.length];
        for(int i = 0; i < binary_digits_array.length; i++)
        {
            int_binary_array[i] = Integer.parseInt(binary_digits_array[i]);        
        }
        
        return int_binary_array;

    }

    public static int[][] create_table(int[] minterms)
    {
    	int[][] tabela = new int[minterms.length][3];
     
    	for(int i = 0; i < 6; i++)
        {
        	tabela[i] = string_to_int_array(binary_digits_to_array(decimal_to_binary_string_4_digits(minterms[i])));
     	}
    
        return tabela;
    }

}
