class Quine_falta_prime {

	public static void main (String args[])
	{
		
//		int[] minterms = {0, 4, 8, 5, 6, 9, 10, 7, 13, 15};
//		int[] minterms = {4,5,6,12,13,15};
		int[] minterms = {4,8,9,10,11,12,14,15};
		
		
		int[][] table = create_table(minterms);
				
		quine(table);
	}
	
	
	public static int[][] quine(int tabela[][]){
		
		int tabelaAux1[][] = create_reduced_matrix(tabela);
		int tabelaAux2[][] = create_reduced_matrix(tabelaAux1);
		
		int notUsedMinterms1[][] = not_used_minterms(tabelaAux1);
		int notUsedMinterms2[][] = not_used_minterms(tabelaAux2);
		
//		System.out.println("\nColuna 2");
//		print_table(tabelaAux1);
		
		System.out.println("\nMini termos nao usados coluna 2");
		print_table(notUsedMinterms1);
		
//		System.out.println("\nColuna 3");
//		print_table(tabelaAux2);
		
		
		System.out.println("\nMini termos nao usados coluna 3");
		print_table(notUsedMinterms2);
		
		
		return tabela;
	}
	
	
	public static int[][] not_used_minterms(int matrix[][]){
		int newMatrix[][] = new int[50][];
		
		int minTermsNotUsed = 0;
		for(int i = 0; i < matrix.length - 1; i++){
			boolean minTermUsed = false;
			
			for(int j = 0; j < matrix.length; j++){
				 if(qnt_of_different_terms(matrix[i], matrix[j]) == 1) {
					 minTermUsed = true;
					 if(minTermUsed) break;
				 }
			}
			
			if(minTermUsed) continue;
			newMatrix[minTermsNotUsed] = matrix[i];
			minTermsNotUsed++;
		}
		
		return create_matrix_from_biggest_matrix(newMatrix, minTermsNotUsed);
	}
	
	
	public static int[][] create_matrix_from_biggest_matrix(int matrix[][], int realUsedLines){
		int newMatrix[][] = new int[realUsedLines][];
		
		for(int i = 0; i< realUsedLines; i++){
			newMatrix[i] = matrix[i];
		}
		
		return newMatrix;
	}
	
	
	
	
	public static int[][] create_reduced_matrix(int matrix[][]){
		
		int newMatrix[][] = new int[50][];
		
		int mintermsCreated = 0;
		for(int i = 0; i < matrix.length - 1; i++){
			
			for(int j = i + 1; j < matrix.length; j++){
				
				 if(qnt_of_different_terms(matrix[i], matrix[j]) == 1) {
					 newMatrix[mintermsCreated] = create_minterm_from_terms(matrix[i], matrix[j]);
					 mintermsCreated++;
				 }
			}
		}
		return create_matrix_from_biggest_matrix(newMatrix, mintermsCreated);
	}
			
			
			
	public static int[] create_minterm_from_terms(int minterms1[], int minterms2[]){
		int newTerm[] = new int[minterms1.length];
		
		for(int i = 0; i < minterms1.length; i++){
			if(minterms1[i] == minterms2[i]) {
				newTerm[i] = minterms1[i];
			} else {
				newTerm[i] = 2;
			}
		}
		
		return newTerm;
	}
	
	
	public static void print_table(int table[][]){
		for(int j = 0; j < table.length; j++) {
			print_minterm(table[j]);
			System.out.println();
		}
	}
	
	public static void print_minterm(int minterm[]){
		for(int i = 0; i < minterm.length; i++){
			if(minterm[i] == 2) System.out.print("-"); 
			else System.out.print(minterm[i]);
		}
	}
	
	
	
	public static int qnt_of_different_terms(int minterms1[], int minterms2[]){
		
		int qnt_of_different_terms = 0;
		
		for(int i = 0; i<4; i++){
			if(minterms1[i] != minterms2[i]){
				qnt_of_different_terms++;
			}
		}
		
		return qnt_of_different_terms;
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
    	
    	for(int i = 0; i < minterms.length; i++)
    	{
    		tabela[i] = string_to_int_array(binary_digits_to_array(decimal_to_binary_string_4_digits(minterms[i])));
    	}
    
    	return tabela;
    }
}
