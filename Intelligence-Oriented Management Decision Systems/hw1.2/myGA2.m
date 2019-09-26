function [out tc beta w phi A B C] = myGA2(Y,n,sr,mr,g)
load Bubble.mat
Y = Bubble;
population = rand(n,40)>0.5;
srnum = round(n*sr); %存活率 servivual rate
mrnum = round(n*mr); %突變率 mutation rate
for j = 1:g
    disp(j) %g世代
    for i = 1:n
        tc = sum(population(i,1:4).*(2.^[0:3]))+550;
        beta = sum(population(i,8:17).*(2.^[0:9]))/1023;
        w = sum(population(i,18:31).*(2.^[0:13]))*0.01+0.01;
        phi = sum(population(i,32:40).*(2.^[0:8]))/511*2*pi;
        M = zeros(tc-1,3);
        M(:,1) = 1;
        M(:,2) = (tc - [1:tc-1]').^beta;
        M(:,3) = (tc - [1:tc-1]').^beta.*cos(w*log(tc-[1:tc-1]')+phi);

        N = [log(Y(1:tc-1,2))];
        
        re = regress(N,M);
        A = re(1);
        B = re(2);
        C = re(3)/B;

        E(i,1) = fitLPPL(A,B,C,tc,beta,w,phi);
    end
    E(:,2) = [1:n]';
    SE = sortrows(E,1);
    population(1:srnum,:) = population(SE(1:srnum,2),:);
    for k = srnum+1:n %從前srnum中交配
        tmp = randperm(srnum);
        father = population(tmp(1),:);
        mother = population(tmp(2),:);
        mask = rand(1,40)>0.5;
        son(mask==1) = father(mask==1);
        son(mask==0) = mother(mask==0);
        population(k,:)=son;
    end
    for k = 1:mrnum %突變
        person = ceil(rand(1)*n);
        geneno = ceil(rand(1)*40);
        population(person,geneno) = ~population(person,geneno);
    end
end
% for i = 1:n
%     tc = sum(population(i,1:7).*(2.^[0:6]));
%         beta = sum(population(i,8:17).*(2.^[0:9]))/1023;
%         w = sum(population(i,18:31).*(2.^[0:13]))*0.01+0.01;
%         phi = sum(population(i,32:40).*(2.^[0:8]))/511*2*pi;
%         M = zeros(tc-1,3);
%         M(:,1) = 1;
%         M(:,2) = (tc - [1:tc-1]').^beta;
%         M(:,3) = (tc - [1:tc-1]').^beta.*cos(w*log(tc-[1:tc-1]')+phi);
% 
%         N = [log(Y(501:500+tc-1,2))];
%         
%         re = regress(N,M);
%         A = re(1);
%         B = re(2);
%         C = re(3)/B;
% 
%         E(i,1) = fitLPPL(A,B,C,tc,beta,w,phi);
% end
E(:,2) = [1:n]';
SE = sortrows(E,1);
out = population(SE(1,2),:);
tc = sum(population(i,1:4).*(2.^[0:3]))+550;
beta = sum(population(i,8:17).*(2.^[0:9]))/1023;
w = sum(population(i,18:31).*(2.^[0:13]))*0.01+0.01;
phi = sum(population(i,32:40).*(2.^[0:8]))/511*2*pi;
M = zeros(tc-1,3);
M(:,1) = 1;
M(:,2) = (tc - [1:tc-1]').^beta;
M(:,3) = (tc - [1:tc-1]').^beta.*cos(w*log(tc-[1:tc-1]')+phi);

N = [log(Y(1:tc-1,2))];

re = regress(N,M);
A = re(1);
B = re(2);
C = re(3)/B;
X = exp(LPPL(A,B,C,tc,beta,w,phi));
plot(X,'r');
hold on;
plot(Y(:,2),'b');
% [out tc beta w phi A B C] = myGA2(Bubble,200,0.01,0.01,100)
