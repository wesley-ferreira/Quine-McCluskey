public class Quine_McCluskey
{
	public static void main (String args[])
	{
		//int[] minterms = {4, 5, 6, 8, 9, 10, 13}; 
		//int[] dontCare = {0, 7, 15};
		
		int[] minterms = {4,8,10,11,12,15};
		int[] dontCare = {9,14};
		
		int result[][] = quine(minterms, dontCare);
		System.out.println("\nImplicantes primos essenciais: ");
		print_table(result);
	}
		
	
	/**
	 * REALIZA TODA A OPERAÇÃO NECESSáRIA PARA O QUINE MCCLUSKEY, ENCONTRANDO OS IMPLICANTES PRIMOS
	 * E OS IMPLICANTES PRIMOS ESSENCIAIS
	 *  
	 * @param  MINTERMS MINETERMOS INICIAIS, FORNECIDO PELO USUáRIO
	 * @param  DONTCARE DONTCARE'S INICIAIS, FORNECIDO PELO USUáRIO
	 * @return MATRIZ DE IMPLICANTES PRIMOS ESSENCIAIS 
	 */
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
		
		int [][] prime_implicant_table = result_implicant_table(notUsedMinterms1, notUsedMinterms2);
	 	return prime_implicant_chart(prime_implicant_table, tabelaMinterms);
	}
	
	/**
	 * DADAS UMA MATRIZ DE IMPLICANTES PRIMOS E UMA OUTRA DE MINITERMOS INICIAIS, EXCLUINDO OS "DON'T CARE",
	 * RETORNA OS IMPLICANTES PRIMOS ESSENCIAIS 
	 *  
	 * @param  not_used_minterms MATRIZ DE MINETERMOS QUE AINDA NÃO FORAM USADOS
	 * @param  initial_minterms MATRIZ DE MINETERMOS DE ENTRADA DO PROGRAMA
	 * @return MATRIZ DE IMPLICANTES PRIMOS ESSENCIAIS 
	 */
	
	//TODO: DESCOBRIR OS IMPLICANTES PRIMOS QUE FALTAM PARA QUE TODOS OS MINITERMOS SEJAM COBERTOS 
	public static int[][] prime_implicant_chart(int not_used_minterms[][], int[][] initial_minterms)
	{		
//		system.out.println("not used minterms");
//		print_table(not_used_minterms);
//		system.out.println("tabela minterms");
//		print_table(initial_minterms);	
			
		int newMatrix[][] = new int [initial_minterms.length][not_used_minterms.length];
		
		//PERCORRE A MATRIZ E COLOCA TUDO COMO -1
		for(int i = 0; i < newMatrix.length; i++)
		{
			for(int j = 0; j < newMatrix[i].length; j++)
			{
				newMatrix[i][j] = -1;
			}
		}
		
		
		//COMPARA TODOS OS MINITERMOS INICIAIS COM OS MINITERMOS NÃO USADOS
		int prime_implicant_added = 0; //MUDAR O INDEX DOS ELEMENTOS QUE SERÃO ADICIONADOS NA MATRIZ "newmatrix"
		for(int i = 0; i < initial_minterms.length; i++) //PERCORRE TODOS OS MINITERMOS INICIAIS
		{
			for(int j = 0; j < not_used_minterms.length; j++) //PERCORRE TODOS OS MINITERMOS NÃO USADOS
			{				
				for(int k = 0; k < 4; k++) //PERCORRE E COMPARA BIT A BIT OS ELEMENTOS DAS MATRIZES initial_minterms E not_used_minterms
				{
					//SE K FOR IGUAL A 3 (ULTIMO BIT) E not_used_minterms[J][K] == 2 (not_used_minterms[J][K] == '-')
					//	OU initial_mintermS[I][K] == not_used_minterms[J][K] (O BIT EM initial_minterms[I][K] É IGUAL AO BIT EM not_used_minterms[I][K])
					//ENTAO ADICIONA O INDICE DO not_used_minterms NA MATRIZ "newmatrix"
					
					//K == 3 SIGNIFICA QUE TODOS OS BIT ANTERIORES Já FORAM CONFERIDOS E PERMITEM QUE O MINITERMO 
					//PODE REPRESENTAR O MINETERMO initial_minterms[I]
					if(k == 3 && ((not_used_minterms[j][k] == 2) || (initial_minterms[i][k] == not_used_minterms[j][k]))) 
					{
						//ADICIONA INDICE DO not_used_minterms QUE FOI VERIFICADO E PODE REPRESENTAR initial_minterms[I]
						newMatrix[i][prime_implicant_added] = j;
						//INCREMENTA prime_implicant_added PARA QUE NA PROXIMA INSERÇÃO NA MATRIZ NÃO OCORRA EM CIMA DO INDICE QUE FOI GRAVADO AGORA
						prime_implicant_added++;
						//PARA O LAÇO DE REPETIÇÃO DE "K"
						break;
					}
					//SE not_used_minterms[J][K] == 2 (not_used_minterms[J][K] == '-'), NÃO IMPORTA O VALOR DE initial_minterms[I], POIS SERá VALIDO TANTO PARA '0'
					//QUANDO PARA '1', ENTAO USA-SE O  'CONTINUE' PARA PROSSEGUIR NO LAÇO DE 'K'
					if(not_used_minterms[j][k] == 2) continue;
					//SE initial_minterms[I][K] == not_used_minterms[J][K] (0 == 0 OU 1 == 1), ENTAO USA-SE O  'CONTINUE' PARA PROSSEGUIR NO LAÇO DE 'K'
					if(initial_minterms[i][k] == not_used_minterms[j][k]) continue;
					//SE NENHUMA DESSAS OPÇÕES FOREM SATISFEITAS PARA O LAÇO DE REPETIÇÃO DE 'K', É PROVAVEL QUE NUNCA CHEGUE AQUI, 
					//MAS É BOM USAR POR UMA QUESTAO DE SEGURANÇA
					break;
				}
				
			}
			//QUANDO TERMINAR DE COMPARAR initial_minterms[I] COM TODOS OS not_used_minterms ENTAO prime_implicant_added = 0
			//ISSO SERVE PARA QUE OS INDICES QUE FOREM ADICIONADOS NA newmatrix COMEÇEM A SER ADICIONADOS NO INDICE 0
			prime_implicant_added = 0;
		}
		
		//ARRAY QUE GUARDA O COMPRIMENTO DE CADA ARRAY NO ARRAY DE ARRAY'S "newmatrix"
		//SERVIRá PARA SABER QUAL O MINITERMO PODE SER REPRESENTADO APENAS POR UM IMPLICANTE PRIMáRIO
		int usedIndex[] = new int[newMatrix.length];
		
		for(int i = 0; i < newMatrix.length; i++)
		{
			int length = 0;			
			for(int j = 0; j < newMatrix[i].length; j++)
			{
				//OS ELEMENTOS QUE GUARDAM -1 NÃO DEVERÃO SER CONTADOS E REPRESENTAM O FIM DO ARRAY
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
		
		//SE TIVER APENAS UM 1 IMPLICANTE PRIMO, PASSAR A SER UM IMPLICANTE PRIMO ESSENCIAL
		//OU SEJA, SE usedindex[I] == 1
		int essential_implicant[][] = new int[newMatrix.length][];
		System.out.println();
		
		//CONTA QUANTOS IMPLICANTES PRIMOS ESSENCIAIS FORAM CRIADOS
		int essential_added = 0;
		for(int i = 0; i < usedIndex.length; i++)
		{
			System.out.println(usedIndex[i]);
			if(usedIndex[i] == 1)
			{
				essential_implicant[essential_added] = not_used_minterms[newMatrix[i][0]]; 
				essential_added++;
			}
		}
//		int newMatrixWithoutEssential[][] = new int [usedIndex.length - essential_added][not_used_minterms.length];

		return create_matrix_from_biggest_matrix(essential_implicant, essential_added);
	}
		
	
	/*
		RECEBE DUAS MATRIZES E ADICIONA AS LINHAS DA matrix1 E DEPOIS AS DA matrix2 EM UMA NOVA MATRIZ
		CONFERINDO ANTES SE A LINHA JA NAO EXISTE NA newMatrix PARA QUE NAO HAJA LINHAS REPETIDAS 
		  
		@param  matrix1 primeira matriz
		@param  matrix2 primeira matriz
		@return matrix1 + matrix2, SEM REPETIR ELEMENTOS 
	 */
	public static int[][] result_implicant_table(int matrix1[][], int matrix2[][])
	{
		int newMatrix[][] = new int[50][];		
		int itensAdded = 0;
		
		for(int i = 0; i < matrix1.length; i++)
		{
			//SE ITEM NAO EXISTE NA MATRIZ, ENTÃO ADICIONA NA MATRIZ
			if(!item_exist_in_matrix(newMatrix, matrix1[i]))
			{
				newMatrix[itensAdded] = matrix1[i];
				itensAdded++; 
			}
		}// NESSE PONTO, TODAS AS LINHAS DA matrix1 COMPOEM A newMatriz QUE NAO DEVE LINHAS REPETIDAS
		

		//ESTE LOOP ADICIONA AS LINHAS DA matrix2 NA newMatrix A PARTIR
		//DA POSICAO itensAdded QUE CRESCEU NO LOOP ANTERIOR(ADICIONANDO AS LINHAS DA matrix1)
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
	
	//FUNÇÃO BOOLEANA QUE RECEBE UMA MATRIZ E UM ARRAY RETORNA VERDADEIRO SE EXISTE ALGUMA LINHA DA MATRIZ IGUAL AO ARRAY.
	//FUNÇÃO GENÉRICA.
	public static boolean item_exist_in_matrix(int matrix[][], int term[])
	{
		for (int i = 0; i < matrix.length; i++)
		{
			if(matrix[i] == null) break;
			
			if(matrix[i][0] == term[0] && matrix[i][1] == term[1] && matrix[i][2] == term[2] && matrix[i][3] == term[3])
			{
				return true;
			}
		}
		return false;
	}
		
	//COMPARA CADA LINHA DE UMA MATRIZ COM TODAS AS OUTRAS A FIM DE CRIAR UMA NOVA MATRIZ EM QUE SUAS LINHAS SAO MINTERMOS 
	//QUE NAO TIVERAM APENAS UMA DIFERENCA COM QUALQUER OUTRO (NO METODO TABULAR FEITO COM PAPEL E CANETA,
	//O PROCEDIMENTO DE MARCAR UMA LINHA EH EQUIVALENTE A ESTA FUNCAO).
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
			minTermsNotUsed++; //QUANTIDADE DE LINHAS NAO USADAS QUE SERA O NUMERO DE LINHAS DA MATRIZ DA SAIDA
		}
		return create_matrix_from_biggest_matrix(newMatrix, minTermsNotUsed);
		//EXEMPLO: SE ESSA FUNCAO RECEBESSE UMA MATRIZ A[][]={{0,0,0,0}, {0,0,0,1}, {0,0,1,0}, {1,1,1,1}},
		//O RETORNO SERIA UMA NOVA MATRIZ B CONTENDO APENAS A LINHA {1,1,1,1} DA MATRIZ PARAMETRO.
	}	
	
	//ORDEM DE USO 6 => RECEBE UMA MATRIZ E O NUMERO DE LINHAS DA MATRIZ QUE SERá CRIADA. FUNÇÃO GENÉRICA.
	public static int[][] create_matrix_from_biggest_matrix(int matrix[][], int realUsedLines)
	{
		int newMatrix[][] = new int[realUsedLines][];
		
		for(int i = 0; i < realUsedLines; i++)
		{
			newMatrix[i] = matrix[i];
		}		
		return newMatrix; //RETONA UMA MATRIZ 
	}
	
	//ORDEM DE USO 7 => FUNCAO QUE TORNA DESNECESSARIA A SEPARACAO DOS MINTERMOS EM GRUPOS. COMPARA TODAS AS LINHAS DA
	// MATRIZ INICIAL E RETORNA UMA NOVA MATRIZ EM QUE SUAS LINHAS SÃO ARRAYS 
	//EM QUE O COMPUTADOR ENCONTROU APENAS UMA DIFERENCA NAS QUATRO COLUNAS. NESTE ALGORITMO NAO É NECESSARIO SEPARAR OS
	//MINTERMOS EM GRUPOS PORQUE ESSA DIVISÃO SÓ SERVE PARA FACILITAR A VISUALIZAÇÃO DO DIGITOS NO PAPEL E APROXIMAR 
	//AS LINHAS QUE PROVAVELMENTE TERÃO UMA SO UM ELEMENTO DE DIFERENCA. 
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
					mintermsCreated++; //QUANTIDADE DE LINHAS DA PROXIMA MATRIZ CRESCENDO. SERVIRA DE PARAMENTRO
					//PARA CRIACAO DA PROXIMA MATRIX.
				}
			}
		}
		return create_matrix_from_biggest_matrix(newMatrix, mintermsCreated); //EXEMPLO: SE O PARAMETRO FOSSE A MATRIZ
		// matrix[][] = {{0,0,0,0}, {0,0,0,1}, {1,1,1,1}}, O RETORNO SERIA UMA MATRIZ F[][]={{0,0,0,2}},
		// QUE SE IMPRESSA, MOSTRARIA 000-
	}
	
	//ORDEM DE USO 5 => RECEBE E COMPARA OS DIGITOS DA MESMA COLUNA DE DOIS ARRAYS (QUE INICIALMENTE SÃO AS LINHAS DA MATRIZ INICIAL)
	//E RETORNA UM ARRAY COM O DIGITO QUE SE DEFERENCIOU SUBSTITUIDO POR 2.
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
		return newTerm; //EXEMPLO SE ESTA FUNCÃO RECEBESSE A[] = {0,0,0,0} E B[] = {0,1,0,0}, ELA RETORNARIA UM ARRAY C[] = {0,2,0,0}.
	}
	
    //IMPRIME UMA MATRIZ DE FORMA QUE OS ELEMENTOS IGUAIS A 2 SEJAM IMPRESSOS COMO TRAÇO "-" 
	public static void print_table(int table[][])
	{
		for(int j = 0; j < table.length; j++)
		{
			print_minterm(table[j]);
			System.out.println();
		}
	}
	
	//FUNÇÃO USADA MAIS DE UMA VEZ. ELA RECEBE UM ARRAY E SE ALGUM DOS ELEMENTOS FOR O NÚMERO 2
	//É IMPRESSO (APENAS IMPRIME, NAO SUSTITUE) "-" NO LUGAR DELE. 
	public static void print_minterm(int minterm[])
	{
		for(int i = 0; i < minterm.length; i++)
		{
			if(minterm[i] == 2)
			System.out.print("-"); //SE RECEBESSE a[] = {0,2,1,2}, SERIA IMPRESSO 0-1-
			else System.out.print(minterm[i]);
		}
	}
	
	//RECEBE DOIS ARRAYS (QUE SAO DUAS LINHAS DE UMA MATRIZ) COMO PARAMETRO E RETORNA O NUMERO DE TERMOS QUE SAO DIFERENTES
	public static int qnt_of_different_terms(int minterms1[], int minterms2[])
	{		
		int qnt_of_different_terms = 0;		
		for(int i = 0; i < 4 ; i++)
		{
			if(minterms1[i] != minterms2[i])
			{
				qnt_of_different_terms++;
			}
		}		
		return qnt_of_different_terms;// SE OS PARAMETROS FOSSEM A[] = {0, 0, 0, 1} e B[] = {1, 1, 1, 1}, A FUNÇÃO RETORNARIA 3,
		                                           //CASO FOSSEM C[] = {0, 1, 0, 0} e D[] = {0, 1, 0, 1}, A FUNÇÃO RETORNARIA 1. 
	}

	//ORDEM DE USO 1 => RECEBE UM NUMERO INTEIRO/MINTERMO E O CONVERTE EM UM BINARIO DE 4 DIGITOS EM FORMA DE STRING	
	public static String decimal_to_binary_string_4_digits(int decimal_minterm) 
	{
		String strBinaryNumber = Integer.toBinaryString(decimal_minterm); 				
		while (strBinaryNumber.length() < 4)
		{    
        	strBinaryNumber = "0" + strBinaryNumber;        
       	} 
       	return strBinaryNumber;  // EXEMPLO: SE A FUNCAO RECEBEU 4, ELA RETORNARA UMA STRING "0100".
    }

    //ORDEM DE USO 2 => RECEBE UMA STRING CONTENDO OS DIGITOS EM BINARIO (EX:0001 OU 1111) E RETORNA UM ARRAY COM ESTES DIGITOS.
    public static String[] binary_digits_to_array(String strBinaryNumber) 

    {
    	String binary_digits_array[] = strBinaryNumber.split("");
    	return binary_digits_array;  // RETORNA ARRAY DE STRING EX: array[] = {0, 0, 0, 1} ou array[] = {1, 1, 1, 1};
    }

    //ORDEM DE USO 3 => RECEBE UM ARRAY DE STRING CONTENDO OS DIGITOS BINARIOS DE UM NUMERO E RETORNA ESTE ARRAY NO TIPO INTEIRO.
    public static int[] string_to_int_array(String[] binary_digits_array)
    {
    	int int_binary_array[]= new int[binary_digits_array.length];
        for(int i = 0; i < binary_digits_array.length; i++)
        {
            int_binary_array[i] = Integer.parseInt(binary_digits_array[i]);        
        }
        return int_binary_array; // EXEMPLO: RECEBEU string array[] = { 0, 1, 0, 1} e retorna int array[] = { 0, 1, 0, 1}.
    }

    //ORDEM DE USO 4 => RECEBE TODOS OS ARRAYS REFERENTES AOS MINTERMOS (EX: int array[] = { 0, 0, 1, 1}) E COM UM LAÇO OS TRANSFORMA
    // NAS LINHAS DE UMA MATRIZ DE INTEIROS
    public static int[][] create_table(int[] minterms)
    {
		int[][] tabela = new int[minterms.length][3];
    	
    	for(int i = 0; i < minterms.length; i++)
    	{
    		tabela[i] = string_to_int_array(binary_digits_to_array(decimal_to_binary_string_4_digits(minterms[i])));
    	}   
       	return tabela; //RETORNA MATRIZ  EX: int matriz[][] = {{0, 0, 0, 0},
       	// 													   {0, 0, 0, 1}...
       	//                                                     {1, 1, 1, 1}}    
    }
}
