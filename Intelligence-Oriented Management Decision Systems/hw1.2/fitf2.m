function E= fitf2(Y,t,A,B,C,D)
E = sqrt(sum((A*t.^B+C*cos(D*t)-Y).^2)/length(Y));
%f2([1:100])
%fitf2(Y,[1:100],0.6,1.2,100,0);