//
//  kernel.h
//  
//
//  Created by Jac on 2016/3/23.
//
//

#ifndef ____kernel__
#define ____kernel__

#include <string>
#include <stdio.h>
using namespace std;

class kernel{
public:
    kernel();
    ~kernel();
    void read(string filename);
    void solve();
    void printQ();
    void printA();
    void updateTable(int,int,int);
    bool checkTable(int,int,int);
    void initialTable();
    bool checkA();
private:
    int Q[9][9];
    int A[9][9]={0};
    int Table[9][9][10];
};


#endif /* defined(____kernel__) */
