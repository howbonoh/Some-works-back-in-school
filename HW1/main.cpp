//
//  main.cpp
//  Sudoku
//
//  Created by Jac on 2016/3/23.
//  Copyright (c) 2016年 Jac. All rights reserved.
//

#include <iostream>
#include "kernel.h"
using namespace std;

int main(int argc, const char * argv[]) {
    kernel x;
    x.read("Question1.txt");
    x.printQ();
    x.solve();
    x.printA();
    return 0;
}
