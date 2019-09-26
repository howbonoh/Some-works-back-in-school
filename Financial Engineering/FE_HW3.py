
# coding: utf-8

# In[1]:

# HW3 習題8
# Macaulay Duration
def duration(number,couponRate,face,marketYield):
    coupon = face*couponRate
    MacD = 0
    presentValue = 0
    for i in range(number):
        flag = i+1
        MacD = MacD + flag*coupon*(1+marketYield)**(-flag)
        presentValue = presentValue + coupon*(1+marketYield)**(-flag)
        if (flag == number):
            MacD = MacD + flag*face*(1+marketYield)**(-flag)
            presentValue = presentValue + face*(1+marketYield)**(-flag)
    MacD = MacD/presentValue
    print(MacD)


# In[2]:

#當債息=0時，存續期間應與到期時間相等
duration(6,0,100,0.08)
# 當債息提高時，存續期間應下降，因為本金佔現值的比例縮小
duration(6,0.08,100,0.08)
# 當債息下降時，存續期間應上升，因為本金佔現值的比例增大
duration(6,0.05,100,0.08)


# In[3]:

# HW3 習題9
# price change percentage
def priceChangePercentage(number,couponRate,face,marketYield):
    coupon = face*couponRate
    MacD = 0
    presentValue = 0
    for i in range(number):
        flag = i+1
        MacD = MacD + flag*coupon*(1+marketYield)**(-flag)
        presentValue = presentValue + coupon*(1+marketYield)**(-flag)
        if (flag == number):
            MacD = MacD + flag*face*(1+marketYield)**(-flag)
            presentValue = presentValue + face*(1+marketYield)**(-flag)
    MacD = MacD/presentValue
    ModD = MacD/(1+marketYield)
    print('當殖利率變動一個basis point時，價格變動的百分比為：' + str(-ModD*0.0001*100) + '%')


# In[4]:

priceChangePercentage(6,0.08,1000,0.08)


# In[ ]:



