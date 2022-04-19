import cv2
import matplotlib.pyplot as plt
import numpy as np

from sklearn import *
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error

import numpy as np
import numpy.linalg as nplg
import math
import numpy as np

from sklearn import *
from sklearn.discriminant_analysis import QuadraticDiscriminantAnalysis
from sklearn.metrics import confusion_matrix
#labels = np.uint8(np.loadtxt("label.txt"))

# 預測資料
data = np.array([])
# 預測標籤
label = None

# 開啟資料特徵值檔案
with open("iris_x.txt") as f:
    list = []
    for i in range(150):
        list.append(np.array(f.readline().split(), dtype=float))
    data = np.array(list)

# 開啟資料標籤檔案
with open("iris_y.txt") as f:
    label = np.array(f.read().split(), dtype=int)

# 切割資料，指定 random_state = 20220413
x_train, x_test, y_train, y_test = train_test_split(data, label, random_state=20220413);

# 使用線性迴歸類別
clt = LinearRegression()

# 擬合
clt.fit(x_train, y_train)

# 取得 MSE 值
mse = mean_squared_error(y_test, clt.predict(x_test))

# 印出結果
print("MSE =", mse)

# 撰寫 QuadraticDiscriminantAnalysis
class _QuadraticDiscriminantAnalysis():
    # 建構子
    def __init__(self):
        self.mu = np.array([])
        self.cov = np.array([])
    # 擬合函式
    def fit(self, data, label):
        mu = []
        cov = []
        for i in range(np.max(label) + 1):
            pos = np.where(label == i)[0]
            tmp_data = data[pos,:]
            tmp_cov = np.cov(np.transpose(tmp_data))
            tmp_mu = np.mean(tmp_data,axis=0)
            mu.append(tmp_mu)
            cov.append(tmp_cov)
        self.mu = np.array(mu)
        self.cov = np.array(cov)
    # 預測函式
    def predict(self, tests):
        result = []
        for test in tests:
            list = []
            for i in range(len(self.mu)):
                # 使用 Quadratic discriminant classifier 的式子
                value = - 0.5 * math.log(nplg.norm(self.cov[i])) - 0.5 * np.dot(np.dot(np.transpose(test - self.mu[i]), nplg.inv(self.cov[i])), (test - self.mu[i]))
                list.append(value)
            values = np.array(list)
            result.append(np.argmax(values))
        return result
# 使用自己寫的 Quadratic Discriminant Analysis 類別
qda = _QuadraticDiscriminantAnalysis()

# 擬合
qda.fit(x_train, y_train)

# 準確率函數，回傳 float 準確率
def getAccurate(y_true, y_pred) -> float:
    correct = 0
    for i in range(len(y_pred)):
        if(y_pred[i] == y_true[i]):
            correct += 1
    return correct / len(y_true)

# Confusion matrix，回傳 np.array
def getConfusionMatrix(y_true, y_pred) -> np.array:
    n = len(np.unique(y_true))
    result = np.empty((n, n), dtype=np.uint)
    result.fill(0)
    for i in range(len(y_pred)):
        result[y_true[i]][y_pred[i]] += 1
    return result

# 預測
    
'''
predicted = qda.predict(x_test)

# 輸出結果
print("Accurate = %.4f%%" % (getAccurate(y_test, predicted) * 100))
print(f"Confusion Matrix: \n{getConfusionMatrix(y_test, predicted)}")
'''


# 使用 sklearn 的 QuadraticDiscriminantAnalysis 類別
qda = QuadraticDiscriminantAnalysis()

# 擬合
qda.fit(x_train, y_train)

# 預判
predicted = qda.predict(x_test)

# 得到準確率
accuracy = qda.score(x_test, y_test)

# 輸出結果
print("Accurate = %.4f%%" % (float(accuracy) * 100))
print(f"Confusion Matrix: \n{confusion_matrix(y_test, predicted)}")

