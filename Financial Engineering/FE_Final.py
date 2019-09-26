
# coding: utf-8

# In[4]:

import numpy as np


# In[5]:

rt = 0.01844/12
pv = np.exp(-rt)
taiex = [7757,7758]
future = [7756,7760]
strikeP = []
for i in range(6900,8001,100):
    strikeP.append(i)
strikeP.extend([8200,8400,8600,8800])
buy_call = [820, 690, 630, 540, 445, 372, 304, 231, 173, 120, 80, 48.5, 15.5, 4.1, 1, 0.6]
write_call = [835, 740, 645, 550, 470, 397, 307, 235, 174, 121, 81, 49, 16.5, 4.2, 2, 0.9]
buy_put = [8.6, 12, 17, 26, 38.5, 55, 78, 109, 148, 195, 250, 323, 493, 0, 825, 0]
write_put = [9.1, 12.5, 18, 27, 39.5, 56, 79, 110, 149, 196, 258, 332, 505, 0, 855, 0]
call = [820, 740, 630, 550, 470, 397, 307, 235, 173, 120, 79, 48.5, 15.5, 4.1, 1, 0.6]
put = [9.1, 12, 18, 25, 38.5, 55, 78, 109, 148, 194, 258, 323, 493, 0, 855, 0]


# In[6]:

# by formula p + s0 = c + k*pv
def put_call_parity(c,p,s,k):
    if p != 0:
        putprice = c + k*pv - s[1]
        callprice = s[1] + p - k*pv
        if putprice != p :
            if putprice < p:
                print('You sould sell a put & a taiex and purchase a call to earn arbitrage:',abs(p - putprice)+(s[0]-s[1])-3)
            else:
                print('You sould purchase a put & a taiex and sell a call to earn arbitrage:',abs(p - putprice)-3)
#         if callprice != c :
#             if callprice < c:
#                 print('You sould sell a call and purchase a put & a taiex to earn arbitrage',abs(callprice - c)-3)
#             else:
#                 print('You sould purchase a call and sell a put & a taiex to earn arbitrage',abs(callprice - c)-(s[1]-s[0])-3)
    else:
        print('put price is not given')


# In[39]:

def put_call_future_parity(c,p,f,k):
    if p != 0:
        putprice = c + k*pv - f[0]*pv
        callprice = -k*pv + p + f[0]*pv
        if putprice != p:
            if putprice < p:
                print('You sould sell a put & a future and purchase a call to earn arbitrage:',abs(putprice - p)-3)
            else:
                print('You sould purchase a put & a future and sell a call to earn arbitrage:',abs(putprice - p)-(f[1]-f[0])-3)
#         if callprice != c :
#             if callprice < c:
#                 print('You sould sell a call and purchase a put & a taiex to earn arbitrage',abs(callprice - c)-(f[1]-f[0])-3)
#             else:
#                 print('You sould purchase a call and sell a put & a taiex to earn arbitrage',abs(callprice - c)-3)
    else:
        print('Put price is not given')


# In[40]:

def theorem3(c,p,s,k):
    if c > s[0]:
        print('By theorem3, shorting a call and longing the stock to earn arbitrage:',(c - s[0])-2)
    if p > k:
        print('By theorem3, shorting a put to earn arbitrage:',p-1)
    else:
        print('There is no arbitrage by theorem3')


# In[41]:

def theorem4(c,p,s,k):
    if c < max((s[1] - k*pv),0):
        print('By theorem4, longing a call and shorting the stock to earn arbitrage:',(max((s[1]-k*pv),0) - c)+(s[0]-s[1])-2)
    else:
        print('There is no arbitrage by theorem4')


# In[42]:

def theorem6(c,p,s,k):
    if p != 0:
        if p < max((k*pv - s[1]),0):
            print('By theorem6, longing a put and longing the stock to earn arbitage:',(max((k*pv - s[1]),0) - p)-2)
        else:
            print('There is no arbitrage by theorem6')
    else:
        print('Put price is not given')


# In[43]:

for i in range(len(strikeP)):
    print('Strike Price:',strikeP[i])
    put_call_parity(call[i], put[i], taiex, strikeP[i])
    put_call_future_parity(call[i], put[i], future, strikeP[i])
    theorem3(call[i], put[i], taiex, strikeP[i])
    theorem4(call[i], put[i], taiex, strikeP[i])
    theorem6(call[i], put[i], taiex, strikeP[i])
    print('')


# In[ ]:



