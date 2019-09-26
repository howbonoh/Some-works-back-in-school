function[out]=fib2(in)
if in==0
    out=0;
else if in==1
    out=1;
    else
        out=fib2(in-2)+fib2(in-1);
    end
end