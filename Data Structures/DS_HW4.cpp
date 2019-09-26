#include <iostream>
using namespace std;

void FastTranspose(int InputSparse[3][6], int OutputSparse[3][6]){
    int arr[5]={0};
    for (int i=0; i<6; i++) {
        for (int j=0; j<=4; j++) {
            if (InputSparse[1][i]==j)
                arr[j]++;
        }
    }
    arr[4]=6-arr[4];
    arr[3]=arr[4]-arr[3];
    arr[2]=arr[3]-arr[2];
    arr[1]=arr[2]-arr[1];
    arr[0]=arr[1]-arr[0];
    for (int i=0; i<6; i++) {
        for (int j=0; j<=4; j++) {
            if (InputSparse[1][i]==j) {
                OutputSparse[0][arr[j]]=InputSparse[1][i];
                OutputSparse[1][arr[j]]=InputSparse[0][i];
                OutputSparse[2][arr[j]]=InputSparse[2][i];
                arr[j]++;
            }
        }
    }
    //fill your code here
}

void PrintMatrix(int InputSparse[3][6], int row, int col){
    int OutputM[row][col];
    for (int i=0; i<row; i++) {
        for (int j=0; j<col; j++) {
            OutputM[i][j]=0;
        }
    }
    for (int i=0; i<6; i++) {
        OutputM[InputSparse[0][i]][InputSparse[1][i]]=InputSparse[2][i];
    }
    for (int i=0; i<row; i++) {
            if(i!=0)cout<<endl;
        for (int j=0; j<col; j++) {
                cout<<OutputM[i][j]<<" ";
        }
    }
        //fill your code here
}

    int main(){
    
    /************************
     input sparse matrix list
     row:    0 0 1 1 3 3
     column: 2 4 2 3 1 2
     value:  3 4 5 7 2 6
     *************************/
    int InputSparse[3][6] = { {0, 0, 1, 1, 3, 3},
        {2, 4, 2, 3, 1, 2},
        {3, 4, 5, 7, 2, 6}};
    
    //store the output sparse matrix list
    int OutputSparse[3][6];
    
    FastTranspose(InputSparse, OutputSparse);
    
    /*******************************************
     output the matrix
     the output matrix must be a 5 * 4 matrix
     ********************************************/
    int row = 5, col = 4;
    PrintMatrix(OutputSparse, row, col);
    
    system ("pause");
    return 0;
}
