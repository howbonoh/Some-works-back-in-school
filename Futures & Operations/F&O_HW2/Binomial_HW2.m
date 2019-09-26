function out = Binomial_HW2(s0,vol,K,T,r,step,type)

NT = T*step;
dt = T/NT;
u = exp(vol*sqrt(dt));
d = 1/u;
a= exp(r*dt);
p = (a-d)/(u-d);
q = 1-p;

f = zeros(NT+1,NT+1);

for j = 0:NT
    f(NT+1,j+1) = max(K-s0*(u^(NT-j))*(d^j),0);
end
for i = NT:-1:1
    for k = 1:i
        value = (p.*f(i+1,k)+q.*f(i+1,k+1)).*exp(-r*dt);
        if type == 'a'
            f(i,k) = max(K-s0*(u^(i-k))*(d^(k-1)),value);
        elseif type == 'e'
            f(i,k) = value;
        end
    end
end
out = [u d p f(1,1)];