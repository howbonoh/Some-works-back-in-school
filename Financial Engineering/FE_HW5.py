
# coding: utf-8

# In[1]:

# 概念
# 因為risk free時 yield rate會全部反應成spot rate（R）
# 所以在risky時 將yield rate + yield spread也直接轉成spot rate（R'）
# （R' - R）就可以說是yield spread 所造成的偏差
# 之後再用此偏差值當作static spread代入公式 經過bisection method求出近似解

# 方法
# 分別算出 risk free 跟 risky bond 的 value
# 再倒推出2組的 spot rate
# 將2組 spot rate 的差值取最大最小帶入bisection
# bisection中
# 精度到小數點後10位
# bisection中因為 rate 越大 value 越小
# 所以當 value - risk_bondValue > 0 時
# low = middle


# In[1]:

# setting data par,n,c,y,yield_spread 上至下 面額、期數、債息、yield rate、yield spread
par = 100
n = 3
c = 1
y = [0.1,0.2,0.3]
yield_spread = 0.05


# In[2]:

# 設定初始 r
r = [y[0]]
riskR = [y[0]+yield_spread]
# 計算 bond value 反推出spot rate
for i in range(1,n):
    bond_value = 0
    risk_bondValue = 0
    for j in range(i + 1):
        bond_value += c/(1 + y[i])**(j + 1)
        risk_bondValue += c/(1 + y[i] + yield_spread)**(j + 1)
        if j == i:
            bond_value += par/(1 + y[i])**(j + 1)
            risk_bondValue += par/(1 + y[i] + yield_spread)**(j + 1)
    rBond = 0
    riskBond = 0
    for j in range(i):
        rBond += c/(1 + r[j])**(j + 1)
        riskBond += c/(1 + riskR[j])**(j + 1)
    rBond = bond_value - rBond
    riskBond = risk_bondValue - riskBond
    newR = ((c + par)/rBond)**(1/(i+1))
    risk_newR = ((c + par)/riskBond)**(1/(i+1))
    r.append(newR-1)
    riskR.append(risk_newR-r[i]-1)
# 讓 riskR 全部變成與 risky 和 risk free 的差距
riskR[0] = riskR[0] - r[0]
# bisection method start
high = max(riskR)
low = min(riskR)
while(abs(high - low) >= 0.0000000001):
    middle = (high + low)/2
    value = 0
    for j in range(n):
        value += c/(1 + r[j] + middle)**(j + 1)
        if j == i:
            value += par/(1 + r[j] + middle)**(j + 1)
    if value - risk_bondValue > 0:
        low = middle
    else:
        high = middle
print(middle)


# In[ ]:



