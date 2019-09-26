
# coding: utf-8

# In[44]:

# a = 1 + irr
# 原f(x) = 9702 - 19700*a^(-1) + 10000*a^(-2)
# 求 irr 即為 NPV = 0 = f(x) 所以可以將其同乘 a^2
# f(x) = 9702a^2 - 19700a + 10000
# 求左邊解
rate = 1.2
temp = rate
F = 9702*(rate**2) - 19700*rate + 10000
dF = 19404*rate - 19700
rate2 = rate - (F/dF)
rate = rate2
while(abs(temp - rate) > 0.000001):
    temp = rate
    F = 9702*(rate**2) - 19700*rate + 10000
    dF = 19404*rate - 19700
    rate2 = rate - (F/dF)
    rate = rate2
print(rate)


# In[45]:

# f(x) = 9702a^2 - 19700a + 10000
# 求右邊解
rate = 1.0
temp = rate
F = 9702*(rate**2) - 19700*rate + 10000
dF = 19404*rate - 19700
rate2 = rate - (F/dF)
rate = rate2
while(abs(temp - rate) > 0.000001):
    temp = rate
    F = 9702*(rate**2) - 19700*rate + 10000
    dF = 19404*rate - 19700
    rate2 = rate - (F/dF)
    rate = rate2
print(rate)


# In[ ]:



