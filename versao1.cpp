#include <iostream>
#include <math.h>
#include <vector>
#include <iomanip>
#include <fstream> 
#define PI 3.14159265
#include <string>
#include <algorithm>

using namespace std;

vector<float> x_values_list();
vector<float> fx(vector<float>, int);
vector<int> n_de_iteracoes(int);
float funcao_sin(float);
// versao 5 23;53
int main()
{

	cout << "Git is Awesome" << endl;
	
	ofstream file;
    file.open("questao1.xls", ios::app);
    
	vector<float> valores_fx = fx(x_values_list(), 1001);

	for(int i = 0; i<valores_fx.size(); i++)
	{
		file << valores_fx.at(i) << '\t';
	}
	
    file.close();
	return 0;
}

vector<float> x_values_list()
{
	vector<float> x_value;
	
	for(float x = -3.14; x <= 3.14; x += 0.01)
	{
		x_value.push_back(x);
	} 
	return x_value;
} 

vector<float> fx(vector<float> x, int i)
{
	vector<float>y_de_x;
	float y = 0;
	
	for(int n = 1; n<i; n+=2){
		int count = 0;
		
		y = y + (4./PI * 1/n * funcao_sin(n*PI*x.at(count)));
		y_de_x.push_back(y);
		
		count++;
		if(count == x.size()) count = 0;
	}
		
	return y_de_x;
}

float funcao_sin(float param)
{
	return sin (param*PI/180);
}
