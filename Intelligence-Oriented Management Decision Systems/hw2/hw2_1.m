load CBCL_DB;
Train_nPF = Train_PF;
Train_nNF = Train_NF;
for i = 1 : size(Train_nPF,1)
    Train_nPF(i,:) = (Train_nPF(i,:)-mean(Train_nPF(i,:)))/std(Train_nPF(i,:));
end
for i = 1 : size(Train_nNF,1)
    Train_nNF(i,:) = (Train_nNF(i,:)-mean(Train_nNF(i,:)))/std(Train_nNF(i,:));
end
Train_DB = [Train_nPF;Train_nNF];
Train_L = [ones(size(Train_PF,1),1);zeros(size(Train_NF,1),1)];
for i = 1 : size(Test_PF,1)
    LPF(i) = myKNN(Test_PF(i,:),Train_DB,Train_L,10);
end
for i = 1 : 472
    LNF(i) = myKNN(Test_NF(i,:),Train_DB,Train_L,10);
end
disp(sum(LPF==1)/length(LPF));
disp(sum(LNF==1)/length(LNF));