function out = KNNfor3(testdata,X,L,K)
dist = sum((repmat(testdata,[size(X,1) 1])' - X').^2);
M = [dist' [1:size(X,1)]'];
M2 = sortrows(M,1);
L1 = L(M2(1:K,2));
H = round(K/4);
L2 = L(M2(1:H,2));
Y = sum(L1==0)+sum(L2==0);
N = sum(L1==1)+sum(L2==1);
if(Y>N)
    out = 0;
else
    out = 1;
end