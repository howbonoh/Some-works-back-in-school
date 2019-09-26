import math
swap = [2.26,2.275,2.285,2.32,2.355,2.3975,2.44]
for i in range(len(swap)):
    swap[i] = swap[i]/100
spot_rate = []
for i in range(len(swap)):
    past = 0
    if i > 0:
        for j in range(i):
            past += swap[i]*spot_rate[j]*(i+1)
    spot_rate.append(-math.log((1 - past)/(1+swap[i])))
print(spot_rate)

