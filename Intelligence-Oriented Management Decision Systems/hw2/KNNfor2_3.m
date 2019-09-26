% zsad
function out = KNNfor2_3(testdata,X,L,K)
Y = X';
dist = sum(abs(repmat(testdata,[size(X,1) 1])' - mean((repmat(testdata,[size(X,1) 1])'))- Y+mean(Y)));
M = [dist' [1:size(X,1)]'];
M2 = sortrows(M,1);
L1 = L(M2(1:K,2));
if(sum(L1==0)>sum(L1==1))
    out = 0;
else
    out = 1;
end