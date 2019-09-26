function E= fitLPPL(A,B,C,tc,beta,w,phi)
load Bubble.mat
Y = Bubble(1:tc-1,2);
temp = (((A + B*((tc-[1:tc-1]).^beta).*(1+C*cos(w*log(tc-[1:tc-1])+phi)))'-Y));
temp = temp.^2;
E = sqrt(sum(temp));
%E = sqrt(sum(sum(((A + B*((tc-[1:tc-1]).^beta).*(1+C*cos(w*log(tc-[1:tc-1])+phi)))'-Y)).^2)/length(Y)));