#include "Rectangle.h"

Rectangle::Rectangle()
{
    x=0; y=0; h=0; w=0;
}
Rectangle::Rectangle(int tx,int ty,int th,int tw)
{
    x=tx; y=ty; h=th; w=tw;
}
Rectangle::~Rectangle(){};

int Rectangle::getHeight(){return h;}
int Rectangle::getWidth(){return w;}
int Rectangle::getArea(){return h*w;}
int Rectangle::getPerimeter(){return 2*(h+w);}
bool Rectangle::operator==(const Rectangle &r)
{
    if (this==&r) return true;
    if ((x==r.x)&&(y==r.y)&&(h==r.h)&&(w==r.w)) return true;
    else return false;
};
