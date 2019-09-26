#include "Rectangle.cpp"
#include<iostream>
using namespace std;

int main(){
    // test construtors and get-functions
    Rectangle *r1 = new Rectangle(1,4,2,8);
    cout << "R1's height: " << r1->getHeight() << endl;
    cout << "R1's width: " << r1->getWidth() << endl;
    cout << "R1's area: " << r1->getArea() << endl;
    cout << "R1's perimeter: " << r1->getPerimeter() << endl;
    cout << endl;
    
    Rectangle *r2 = new Rectangle(1,1,3,8);
    cout << "R2's height: " << r2->getHeight() << endl;
    cout << "R2's width: " << r2->getWidth() << endl;
    cout << "R2's area: " << r2->getArea() << endl;
    cout << "R2's perimeter: " << r2->getPerimeter() << endl;
    cout << endl;
    
    Rectangle *r3 = new Rectangle();
    cout << "R3's height: " << r3->getHeight() << endl;
    cout << "R3's width: " << r3->getWidth() << endl;
    cout << "R3's area: " << r3->getArea() << endl;
    cout << "R3's perimeter: " << r3->getPerimeter() << endl;
    cout << endl;
    
    // test ==
    cout << (*r1 == *r2) << endl;
    Rectangle *r4 = new Rectangle(1,4,2,8);
    cout << (*r1 == *r4) << endl;
    
    // test destructor
    delete r1, r2, r3, r4;
    return 0;
}
