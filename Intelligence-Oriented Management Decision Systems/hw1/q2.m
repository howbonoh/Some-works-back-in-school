load('HW1_2.mat')
for i=1:1000
    M(i,1)=X(i).*X(i).*Y(i).*Y(i);
    M(i,2)=X(i).*X(i).*Y(i);
    M(i,3)=X(i).*X(i);
    M(i,4)=X(i).*Y(i).*Y(i);
    M(i,5)=X(i).*Y(i);
    M(i,6)=X(i);
    M(i,7)=Y(i).*Y(i);
    M(i,8)=Y(i);
    M(i,9)=1;
end
A=regress(Z,M);
disp(A);