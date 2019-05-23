import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import BernoulliNB
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split
from sklearn import model_selection
from sklearn.metrics import accuracy_score, mean_squared_error, average_precision_score,f1_score, r2_score
from sklearn.metrics import precision_score, recall_score, roc_auc_score, roc_auc_score, roc_curve
from sklearn import preprocessing
from sklearn.preprocessing import StandardScaler
from sklearn.neural_network import MLPClassifier
from matplotlib.pyplot import *
import matplotlib.pyplot as plt
from sklearn.metrics import classification_report, roc_curve, roc_auc_score
from sklearn import svm
import datetime





news_final_filename = "../dataset/News_Final_WO.csv"
facebook_views_filename = "../dataset/facebook_views.csv"

news_final = pd.read_csv(news_final_filename)
news_final = news_final.drop_duplicates()

facebook_views = pd.read_csv(facebook_views_filename)
facebook_views = facebook_views.drop_duplicates()

df = news_final.merge(facebook_views, how='inner', on="IDLink")

title_size = df.Title.apply(lambda x: len(str(x).split(' ')))
df['title_size'] = title_size

headline_size  = df.Headline.apply(lambda x: len(str(x).split(' ')))
df['headline_size'] = title_size

days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

for i, day in enumerate(days) :
    df.insert(loc=9 + i, column=day, value=0)

for row in df.itertuples():
    date_time = row.PublishDate
    week_day = datetime.datetime.strptime(date_time, '%m/%d/%Y %H:%M').weekday()
    week_day_name = days[week_day]
    df.at[row.Index, week_day_name] = 1


def dud_finder(popularity):
    if popularity <= 1:
        return 1
    else:
        return 0

df['is_dud'] = df['Facebook'].apply(dud_finder)

features = ['SentimentTitle', 'SentimentHeadline'] + days
estimate = 'is_dud'

X = df[features]
y = df[estimate]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 10)


knn = KNeighborsClassifier()
clf_knn = knn.fit(X_train, y_train)
y_pred_knn = knn.predict(X_test)


print("{} performance:".format(y_pred_knn))
print()
print(classification_report(y_test, y_pred_knn), sep='\n')






df['prob_dud'] = knn.predict_proba(X)[:, 1]

features = ['SentimentTitle', 'SentimentHeadline', 'prob_dud'] + days
views = 'Facebook'

X = df[features]
y = df[views]


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 10)



rf = RandomForestClassifier(n_estimators=5, criterion="entropy")
clf_rf = rf.fit(X_train,y_train)
y_pred_rf = rf.predict(X_test)
predictedClassRF = clf_rf.predict(X_test)


print ("============Random Forest============")
print ('MSE RF:', round(mean_squared_error(y_test, predictedClassRF), 4))
print ('Accuracy:', round(accuracy_score(y_test, y_pred_rf), 4))
print ('Precision Score:', round(precision_score(y_test, y_pred_rf), 4))
print ('Recall Score:', round(recall_score(y_test, y_pred_rf, average='macro'), 4))
print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_rf), 4))
print ('F1 Score:', round(f1_score(y_test, y_pred_rf, average='macro'), 4))
