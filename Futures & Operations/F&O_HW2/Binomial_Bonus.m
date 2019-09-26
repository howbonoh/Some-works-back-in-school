function Binomial_Bonus(s0,vol,K,T,r,step,type)
NT = T*step;
x = zeros(NT,1);
mat = zeros(NT,1);
[call put] = blsprice(s0,K,r,T,vol);
for i = 1:NT
    mat(i,:) = Binomial(s0,vol,K,T,r,i,type);
    x(i,:) = put;
end
plot(mat);
hold on;
plot(x);