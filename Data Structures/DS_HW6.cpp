#include <iostream>
#include <string>
#include <stack>
using namespace std;

int priority(char a)
{
    int p;
    if (a == '+' || a == '-')
        p=1;
    else if (a=='*' || a=='/')
        p=2;
    return p;
}

int main() {
    string infix;
    cout<<"Input an infix expression:";
    cin>>infix;
    char rinfix[infix.size()];
    for (int i=0, j=infix.size()-1 ; i<infix.size(), j>-1; i++, j--) {
        rinfix[j]=infix[i];
    }
    stack<char> s1, s2; //s1 for the operators, s2 for the characters
    for (int i=0; i<infix.size(); i++)
    {
        if (rinfix[i]=='+' || rinfix[i]=='-' || rinfix[i]=='*' || rinfix[i]=='/')
        {
            while (!s1.empty() && priority(s1.top())>priority(rinfix[i])&& s1.top()!=')') {
                s2.push(s1.top());
                s1.pop();
                }
            s1.push(rinfix[i]);
        }
        else if (rinfix[i]==')')
            s1.push(rinfix[i]);
        else if (rinfix[i]=='(')
        {
            while (s1.top()!=')')
            {
                s2.push(s1.top());
                s1.pop();
            }
            s1.pop();
        }
        else
            s2.push(rinfix[i]);
    }
    while (!s1.empty())
    {
        s2.push(s1.top());
        s1.pop();
    }
    cout<<"Its prefix expression is:";
    while (!s2.empty())
    {
        cout<<s2.top();
        s2.pop();
    }
    return 0;
}
