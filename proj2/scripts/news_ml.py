import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import BernoulliNB
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn import model_selection
from sklearn.metrics import accuracy_score, mean_squared_error, average_precision_score,f1_score
from sklearn.metrics import precision_score, recall_score, roc_auc_score, roc_auc_score, roc_curve
from sklearn import preprocessing
from sklearn.preprocessing import StandardScaler
from sklearn.neural_network import MLPClassifier
from sklearn import svm
import datetime
csv_filename="../dataset/News_Final.csv"

df=pd.read_csv(csv_filename)

title_size = df.Title.apply(lambda x: len(str(x).split(' ')))
df.insert(loc=len(df.columns) - 4, column='title_size', value=title_size)

headline_size  = df.Headline.apply(lambda x: len(str(x).split(' ')))
df.insert(loc=len(df.columns) - 4, column='headline_size', value=headline_size)

days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
for i, day in enumerate(days) :
    df.insert(loc=9 + i, column=day, value=0)


for row in df.itertuples():
    date_time = row.PublishDate
    week_day = datetime.datetime.strptime(date_time, '%Y-%m-%d %H:%M:%S').weekday()
    week_day_name = days[week_day]
    df.at[row.Index, week_day_name] = 1


features=list(df.columns[6:17])
views=list(df.columns[17:20])

df['values'] = df[views].sum(axis=1)

X = df[features]
y = df['values']

popular = y > 0
unpopular = y <= 0

df.loc[popular,'values'] = 1
df.loc[unpopular,'values'] = 0



X_train, X_test, y_train, y_test = model_selection.train_test_split(X, y, test_size=0.3)


sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.transform(X_test)

# rf = RandomForestClassifier(n_estimators=100, criterion="entropy")
# clf_rf = rf.fit(X_train,y_train)
# y_pred_rf = rf.predict(X_test)
# predictedClassRF = clf_rf.predict(X_test)

# dt = DecisionTreeClassifier(criterion="gini", splitter="random")
# clf_dt = dt.fit(X_train, y_train)
# y_pred_dt = dt.predict(X_test)
# predictedClassDT = clf_dt.predict(X_test)

# knn = KNeighborsClassifier()
# clf_knn = knn.fit(X_train, y_train)
# y_pred_knn = knn.predict(X_test)
# predictedClassKNN = clf_knn.predict(X_test)

# nb = BernoulliNB()
# clf_nb = nb.fit(X_train, y_train)
# y_pred_nb = nb.predict(X_test)
# predictedClassNB = clf_nb.predict(X_test)

# lr = LogisticRegression()
# clf_lr = lr.fit(X_train, y_train)
# y_pred_lr = lr.predict(X_test)
# predictedClassLR = clf_lr.predict(X_test)

nn = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(11, 11), random_state=1)
clf_nn = nn.fit(X_train, y_train)
y_pred_nn = nn.predict(X_test)
predictedClassNN = clf_nn.predict(X_test)

# sv = svm.SVC(gamma='scale')
# clf_sv = sv.fit(X_train, y_train)
# y_pred_sv = sv.predict(X_test)
# predictedClassSV = clf_sv.predict(X_test)

# print ("============Random Forest============")
# print ('MSE RF:', round(mean_squared_error(y_test, predictedClassRF), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_rf), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_rf), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_rf, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_rf), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_rf, average='macro'), 4))

# print ("============Decision Tree============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassDT), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_dt), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_dt), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_dt, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_dt), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_dt, average='macro'), 4))

# print ("============KNN============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassKNN), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_knn), 4))
# print ('Precision Score:',round( precision_score(y_test, y_pred_knn)))
# print ('Recall Score:', round(recall_score(y_test, y_pred_knn, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_knn), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_knn, average='macro'), 4))

# print ("============Logistic Regression============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassLR), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_lr), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_lr), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_lr, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_lr), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_lr, average='macro'), 4))


print ("============Neural Network============")
print ('MSE:', round(mean_squared_error(y_test, predictedClassNN), 4))
print ('Accuracy:', round(accuracy_score(y_test, y_pred_nn), 4))
print ('Precision Score:', round(precision_score(y_test, y_pred_nn), 4))
print ('Recall Score:', round(recall_score(y_test, y_pred_nn, average='macro'), 4))
print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_nn), 4))
print ('F1 Score:', round(f1_score(y_test, y_pred_nn, average='macro'), 4))

# print ("============Support Vector Machine============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassSV), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_sv), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_sv), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_sv, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_sv), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_sv, average='macro'), 4))