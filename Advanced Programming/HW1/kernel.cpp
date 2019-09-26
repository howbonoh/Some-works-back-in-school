//
//  kernel.cpp
//  
//
//  Created by Jac on 2016/3/23.
//
//

#include "kernel.h"
#include <iostream>
#include <fstream>
using namespace std;

kernel::kernel(){}
kernel::~kernel(){}

void kernel::read(string filename)
{
    fstream f;
    f.open(filename, ios::in);
    for (int i = 0; i < 9;i++)
        for (int j = 0; j < 9; j++)
        {
            f >> Q[i][j];
        }
    f.close();
}

void kernel::printQ()
{
    for (int i = 0; i < 9; i++)
    {
        for (int j = 0; j < 9; j++)
        {
            if (Q[i][j]>0)
                cout << Q[i][j] << " ";
            else
                cout << "  ";
        }
        cout << endl;
    }
}

void kernel::printA()
{
    for (int i = 0; i < 9; i++)
    {
        for (int j = 0; j < 9; j++)
        {
                cout << A[i][j] << " ";
        }
        cout << endl;
    }
}

void kernel::initialTable()
{
    for (int i=0; i<9; i++)
        for(int j=0; j<9; j++)
        {
            for(int k=0; k<10; k++)
                Table[i][j][k]=1;
            Table[i][j][0]=0;
            if(Q[i][j]!=0)
            {
                for(int k=1; k<10; k++)
                    Table[i][j][k]=0;
                Table[i][j][Q[i][j]]=1;
            }
        }
}

bool kernel::checkTable(int i, int j, int k)
{
    if(Table[i][j][k]==0) return false;
    int count1=0;
    for (int x=1; x<10; x++)    //case 1
        count1+=Table[i][j][x];
    if (count1==1)
        return true;
    int count2=0;
    for(int i=0; i<9; i++)      //case2
        count2+=Table[i][j][k];
    if(count2==1)
        return true;
    int count3=0;
    for(int j=0; j<9; j++)
        count3+=Table[i][j][k];
    if(count3==1)
        return true;
    int count4=0;
    for(int x=i/3*3; x<i/3*3+3; x++)
        for(int y=j/3*3; y<j/3*3+3; y++)
            count4+=Table[i][j][k];
    if(count4==1)
        return true;
    else
        return false;
}

void kernel::updateTable(int i, int j, int k)
{
    if(checkTable(i,j,k)==true)
    {
        A[i][j]=k;
        for(int i=0; i<9; i++)
            Table[i][j][k]=0;
        for(int j=0; j<9; j++)
            Table[i][j][k]=0;
        for(int x=i/3*3; x<i/3*3+3; x++)
            for(int y=j/3*3; y<j/3*3+3; y++)
                Table[x][y][k]=0;
        for(int t=1; t<10; t++)
            Table[i][j][t]=0;
        Table[i][j][k]=1;
    }
    
}

void kernel::solve()
{
    initialTable();
    for(int r=0; r<10; r++)
    {
        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++)
                if(A[i][j]==0)
                for(int k=1; k<10; k++)
                    updateTable(i, j, k);
    }
}

bool kernel::checkA()
{
    int count=0;
    for(int i=0; i<9; i++)
        for(int j=0; j<9; j++)
            if(A[i][j]==0)
                count++;
    if(count!=0)
        return true;
    else
        return false;
}