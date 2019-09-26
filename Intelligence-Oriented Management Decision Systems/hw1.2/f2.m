function out = f2(t)
out = 0.6*t.^1.2+100*cos(0.4*t)+randn(size(t))*5;
