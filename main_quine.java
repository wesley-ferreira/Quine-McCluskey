public class main_quine
{
	public static void main (String args[])
	{
		int[] minterms = {4, 5, 6, 8, 9, 10, 13}; 
		int[] dontCare = {0, 7, 15};
//		int[] minterms = {4,8,10,11,12,15};
//		int[] dontCare = {9,14};
		quine(minterms, dontCare);
	}
		
	public static int[][] quine(int minterms[], int dontCare[])
	{		
		int allTerms[] = new int[minterms.length + dontCare.length];
		
		for(int i = 0; i < minterms.length; i++)
		{
			allTerms[i] = minterms[i]; 
		}
		
		for(int i = 0; i < dontCare.length; i++)
		{
			allTerms[allTerms.length - i - 1] = dontCare[i]; 
		}
		
		int tabela[][] = create_table(allTerms);
		int tabelaMinterms[][] = create_table(minterms);
		
		int tabelaAux1[][] = create_reduced_matrix(tabela);
		int tabelaAux2[][] = create_reduced_matrix(tabelaAux1);
		
		int notUsedMinterms1[][] = not_used_minterms(tabelaAux1);
		int notUsedMinterms2[][] = not_used_minterms(tabelaAux2);
				
//		System.out.println("\nColuna 1");
//		print_table(tabela);
//		
//		System.out.println("\nColuna 2");
//		print_table(tabelaAux1);
//		
//		System.out.println("\nMini termos não usados coluna 2");
//		print_table(notUsedMinterms1);
//		
//		System.out.println("\nColuna 3");
//		print_table(tabelaAux2);
//		
//		System.out.println("\nMini termos não usados coluna 3");
//		print_table(notUsedMinterms2);
		
		int [][] primary_implication_table = result_implication_table(notUsedMinterms1, notUsedMinterms2);
		primary_implicant_chat(primary_implication_table, tabelaMinterms);
		
		return primary_implication_table;
	}
	
	public static void primary_implicant_chat(int not_used_minterms[][], int[][] initial_minterms)
	{		
		System.out.println("not used minterms");
		print_table(not_used_minterms);
		System.out.println("tabela minterms");
		print_table(initial_minterms);	
			
		int newMatrix[][] = new int [initial_minterms.length][not_used_minterms.length];
		
		//percorre a matriz e coloca tudo como -1
		for(int i = 0; i < newMatrix.length; i++)
		{
			for(int j = 0; j < newMatrix[i].length; j++)
			{
				newMatrix[i][j] = -1;
			}
		}
		
		int primary_implication_added = 0;
		for(int i = 0; i < initial_minterms.length; i++)
		{
			for(int j = 0; j < not_used_minterms.length; j++)
			{				
				for(int k = 0; k < 4; k++)
				{
					if(k == 3 && ((not_used_minterms[j][k] == 2) || (initial_minterms[i][k] == not_used_minterms[j][k])))
					{
						newMatrix[i][primary_implication_added] = j;
						primary_implication_added++;
						break;
					}
					if(not_used_minterms[j][k] == 2) continue;
					if(initial_minterms[i][k] == not_used_minterms[j][k]) continue;
					break;
				}
				
			}
			primary_implication_added = 0;
		}
		
		int usedIndex[] = new int[newMatrix.length];
		
		for(int i = 0; i < newMatrix.length; i++)
		{
			int length = 0;			
			for(int j = 0; j < newMatrix[i].length; j++)
			{
				if(newMatrix[i][j] != -1) length++;
				else break;
			}
			usedIndex[i] = length;
		}
		
		System.out.println("\nImprime X's");
		
		for(int i = 0; i < newMatrix.length; i++)
		{
			for(int j = 0; j < usedIndex[i]; j++)
			{
				System.out.print(newMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		//se só tiver apenas um implicante primário, passar a ser um implicante primário essencial
		//ou seja, se usedIndex[i] == 1
		int essential_implication[][] = new int[newMatrix.length][];
		System.out.println();
		
		int essential_added = 0;
		for(int i = 0; i < usedIndex.length; i++)
		{
			System.out.println(usedIndex[i]);
			if(usedIndex[i] == 1)
			{
				essential_implication[essential_added] = not_used_minterms[newMatrix[i][0]]; 
				essential_added++;
			}
		}
//		int newMatrixWithoutEssential[][] = new int [usedIndex.length - essential_added][not_used_minterms.length];

		System.out.println("\nessential implications");
		print_table(create_matrix_from_biggest_matrix(essential_implication, essential_added));
	}
		
	public static int[][] result_implication_table(int matrix1[][], int matrix2[][])
	{
		int newMatrix[][] = new int[50][];		
		int itensAdded = 0;
		
		for(int i = 0; i < matrix1.length; i++)
		{
			if(!item_exist_in_matrix(newMatrix, matrix1[i]))
			{
				newMatrix[itensAdded] = matrix1[i];
				itensAdded++;
			}
		}
		
		for(int i = 0; i < matrix2.length; i++)
		{
			if(!item_exist_in_matrix(newMatrix, matrix2[i]))
			{
				newMatrix[itensAdded] = matrix2[i];
				itensAdded++;
			}
		}		
		return create_matrix_from_biggest_matrix(newMatrix, itensAdded);
	}
	
	public static boolean item_exist_in_matrix(int matrix[][], int term[])
	{
		for (int i = 0; i < matrix.length; i++)
		{
			if(matrix[i] == null) break;
			
			if(matrix[i][0] == term[0] && matrix[i][1] == term[1] && matrix[i][2] == term[2] &&  matrix[i][3] == term[3])
			{
				return true;
			}
		}
		return false;
	}
		
	public static int[][] not_used_minterms(int matrix[][])
	{
		int newMatrix[][] = new int[50][];
		int minTermsNotUsed = 0;
		
		for(int i = 0; i < matrix.length - 1; i++)
		{
			boolean minTermUsed = false;
			
			for(int j = 0; j < matrix.length; j++)
			{
				if(qnt_of_different_terms(matrix[i], matrix[j]) == 1)
				{
					minTermUsed = true;
					break;
				}
			}
			
			if(minTermUsed) continue;
			newMatrix[minTermsNotUsed] = matrix[i];
			minTermsNotUsed++;
		}
		return create_matrix_from_biggest_matrix(newMatrix, minTermsNotUsed);
	}	
	
	public static int[][] create_matrix_from_biggest_matrix(int matrix[][], int realUsedLines)
	{
		int newMatrix[][] = new int[realUsedLines][];
		
		for(int i = 0; i< realUsedLines; i++)
		{
			newMatrix[i] = matrix[i];
		}		
		return newMatrix;
	}
	
	public static int[][] create_reduced_matrix(int matrix[][])
	{		
		int newMatrix[][] = new int[50][];		
		int mintermsCreated = 0;
		for(int i = 0; i < matrix.length - 1; i++)
		{			
			for(int j = i + 1; j < matrix.length; j++)
			{				
				if(qnt_of_different_terms(matrix[i], matrix[j]) == 1)
				{
					newMatrix[mintermsCreated] = create_minterm_from_terms(matrix[i], matrix[j]);
					mintermsCreated++;
				}
			}
		}
		return create_matrix_from_biggest_matrix(newMatrix, mintermsCreated);
	}
					
	public static int[] create_minterm_from_terms(int minterms1[], int minterms2[])
	{
		int newTerm[] = new int[minterms1.length];
		
		for(int i = 0; i < minterms1.length; i++)
		{
			if(minterms1[i] == minterms2[i]) 
			{
				newTerm[i] = minterms1[i];
			}
			else
			{
				newTerm[i] = 2;
			}
		}		
		return newTerm;
	}
		
	public static void print_table(int table[][])
	{
		for(int j = 0; j < table.length; j++)
		{
			print_minterm(table[j]);
			System.out.println();
		}
	}
	
	public static void print_minterm(int minterm[])
	{
		for(int i = 0; i < minterm.length; i++)
		{
			if(minterm[i] == 2) System.out.print("-"); 
			else System.out.print(minterm[i]);
		}
	}
		
	public static int qnt_of_different_terms(int minterms1[], int minterms2[])
	{		
		int qnt_of_different_terms = 0;		
		for(int i = 0; i<4; i++)
		{
			if(minterms1[i] != minterms2[i])
			{
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
