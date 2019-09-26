function[out]=fib1(in)
if in==0
    out=0;
else if in==1
    out=1;
else
        a=0;
        b=1;
        for i=2:in
            temp=b;
            out=a+b;
            b=out;
            a=temp;
        end
    end
end