Train_DB = [Train_PF;Train_NF];
Train_L = [ones(size(Train_PF,1),1);zeros(size(Train_NF,1),1)];
for i = 1 : size(Test_PF,1)
    LPF(i) = KNNfor3(Test_PF(i,:),Train_DB,Train_L,10);
end
for i = 1 : 472
    LNF(i) = KNNfor3(Test_NF(i,:),Train_DB,Train_L,10);
end
disp(sum(LPF==1)/length(LPF));
disp(sum(LNF==1)/length(LNF));