
# coding: utf-8

# In[1]:

import pandas as pd
import datetime
from dateutil.relativedelta import relativedelta


# In[2]:

def actual_actual(yieldOfMaturity,couponRate,settlement,maturity):
    maturity = pd.to_datetime(maturity, format='%Y-%m-%d')
    settlement = pd.to_datetime(settlement, format='%Y-%m-%d')
    coupon = 100*couponRate/2
    temp = []
    temp.append(maturity)
    flag = 0
    while temp[flag] > settlement:
        temp.append(temp[flag] - relativedelta(months=6))
        flag += 1
    omega = (temp[flag-1] - settlement) / (temp[flag-1] - temp[flag])
    accruedInterest = coupon*(1 - omega)
    dirty = 100*(1+yieldOfMaturity/2)**((1-flag-omega))
    for i in range(flag):
        dirty += coupon*(1+yieldOfMaturity/2)**((-omega-i))
    print('Dirty Price:' + str(dirty))
    print('Clean Price:' + str(dirty - accruedInterest))


# In[3]:

def thirty_360(yieldOfMaturity,couponRate,settlement,maturity):
    maturity = pd.to_datetime(maturity, format='%Y-%m-%d')
    settlement = pd.to_datetime(settlement, format='%Y-%m-%d')
    coupon = 100*couponRate/2
    temp = []
    temp.append(maturity)
    flag = 0
    while temp[flag] > settlement:
        temp.append(temp[flag] - relativedelta(months=6))
        flag += 1
    omega = 30*(temp[flag-1].month - settlement.month)/180
    accruedInterest = coupon*(1-omega)
    dirty = 100*(1+yieldOfMaturity/2)**((1-flag-omega))
    for i in range(flag):
        dirty += coupon*(1+yieldOfMaturity/2)**((-omega-i))
    print('Dirty Price:' + str(dirty))
    print('Clean Price:' + str(dirty - accruedInterest))


# In[4]:

actual_actual(0.03,0.1,'1993-7-1','1995-3-1')


# In[5]:

thirty_360(0.03,0.1,'1993-7-1','1995-3-1')


# In[ ]:



