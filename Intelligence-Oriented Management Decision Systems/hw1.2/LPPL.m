function out = LPPL(A,B,C,tc,beta,w,phi)
out = A + B*((tc-[1:tc-1]).^beta).*(1+C*cos(w*log(tc-[1:tc-1])+phi));
%plot(LPPL(log(8500),-0.1,0.01,600,0.01,10,pi))
%plot(1:644,Bubble(1:644,2))
%max price = 88.1163 -> day = 589
% beta = 0...1 phi = 0...2(pi)
%E = 兩兩相減平方
