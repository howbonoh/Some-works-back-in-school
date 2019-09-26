

# coding: utf-8

# In[9]:

# a = 1 + irr
# 原f(x) = 9702 - 19700*a^(-1) + 10000*a^(-2)
# 求 irr 即為 NPV = 0 = f(x) 所以可以將其同乘 a^2
# f(x) = 9702a^2-19700a+10000
# 求左邊解
high = 2.0
low = 1.015
while(high - low >= 0.0001):
    middle = (high + low)/2
    value = 9702*(middle**(2)) - 19700*middle + 10000
    if value > 0:
        high = middle
    else:
        low = middle
rate = middle - 1
print(rate)


# In[8]:

# f(x) = 9702a^2-19700a+10000
# 求右邊解
high = 1.015
low = 0.0
while(high - low >= 0.0001):
    middle = (high + low)/2
    value = 9702*(middle**(2)) - 19700*middle + 10000
    if value > 0:
        low = middle
    else:
        high = middle
rate = middle - 1
print(rate)


# In[ ]:



