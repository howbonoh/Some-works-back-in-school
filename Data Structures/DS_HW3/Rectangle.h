#ifndef RECTANGLE_H
#define RECTANGLE_H

class Rectangle{
    
    //public member functions:
public:
    Rectangle(); // constructor
    Rectangle(int tx, int ty, int th, int tw); // construtor
    ~Rectangle(); // destructor
    int getHeight(); // return height of this rectangle
    int getWidth(); // return width of this rectangle
    int getArea(); // return area of this rectangle
    int getPerimeter(); // return perimeter of this rectangle
    bool operator==(const Rectangle& r); // Return boolean value of if two rectangles are the same or not
    
    //private member data and functions:
private:
    int x; // bottom-left x-coordinate
    int y; // bottom-left y-coordinate
    int h; // height
    int w; // width
};

#endif
