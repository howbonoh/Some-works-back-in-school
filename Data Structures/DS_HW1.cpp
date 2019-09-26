#include <iostream>
using namespace std;

int pro(int n)
{
    int product=1;
    for (int i=1;i<=n; i++)
        product*=i ;
    return product;
}

int sum(int n)
{
    int sum=0;
    for (int i=1; i<=n; i++)
        sum+=i;
    return sum;
}

int main() {
    int n;
    while(cin>>n)
    {
        int *arr=new int[n];
        if (1<=n&&n<=5){
            for (int i=0; i<n; i++)
                *(arr+i)=pro(i+1);
            cout<<pro(n)<<endl;
        }
        else if (5<n&&n<=10){
            for (int i=0; i<n; i++)
                *(arr+i)=sum(i+1);
            cout<<sum(n)<<endl;
        }
        else
        {
            cout<<"The input is illegal."<<endl;
            break;
            
        }
        delete []arr;
    }
        return 0;
}
