function [out A B C D] = myGa(Y,t,n,sr,mr,g)
load Bubble.mat

population = rand(n,40)>0.5;
srnum = round(n*sr); %存活率 servivual rate
mrnum = round(n*mr); %突變率 mutation rate
for j = 1:g
    disp(j); %g世代
    for i = 1:n
        A = sum(population(i,1:10).*(2.^[0:9]))*0.01-5.11;
        B = sum(population(i,11:20).*(2.^[0:9]))*0.01-5.11;
        C = sum(population(i,21:30).*(2.^[0:9]))-511;
        D = sum(population(i,31:40).*(2.^[0:9]))*0.01-5.11;
        E(i,1) = fitf2(Y,t,A,B,C,D);
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
for i = 1:n
    A = sum(population(i,1:10).*(2.^[0:9]))*0.01-5.11;
    B = sum(population(i,11:20).*(2.^[0:9]))*0.01-5.11;
    C = sum(population(i,21:30).*(2.^[0:9]))-511;
    D = sum(population(i,31:40).*(2.^[0:9]))*0.01-5.11;
    E(i,1) = fitf2(Y,t,A,B,C,D);
end
E(:,2) = [1:n]';
SE = sortrows(E,1);
out = population(SE(1,2),:);
A = sum(out(1,1:10).*(2.^[0:9]))*0.01-5.11;
B = sum(out(1,11:20).*(2.^[0:9]))*0.01-5.11;
C = sum(out(1,21:30).*(2.^[0:9]))-511;
D = sum(out(1,31:40).*(2.^[0:9]))*0.01-5.11;
%[out A B C D] = myGA(Y,[1:100],10000,0.01,0.01,100);