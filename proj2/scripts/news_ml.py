import pandas as pd
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.naive_bayes import BernoulliNB
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split, StratifiedKFold
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

import cmatrix
import lcurve
from cmatrix import plot_confusion_matrix
from lcurve import plot_learning_curve

news_final_filename = "../dataset/News_Final_WO.csv"
facebook_views_filename = "../dataset/facebook_views.csv"

news_final = pd.read_csv(news_final_filename)
news_final = news_final.drop_duplicates()

facebook_views = pd.read_csv(facebook_views_filename)
facebook_views = facebook_views.drop_duplicates()

df = news_final.merge(facebook_views, how='inner', on="IDLink")



from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()

title_size = df.Title.apply(lambda x: len(str(x).split(' ')))
df['title_size'] = scaler.fit_transform(title_size.values.reshape(-1, 1))

headline_size  = df.Headline.apply(lambda x: len(str(x).split(' ')))
df['headline_size'] = scaler.fit_transform(headline_size.values.reshape(-1, 1))

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

features = ['title_size','headline_size','SentimentTitle', 'SentimentHeadline'] + days
estimate = 'is_dud'

df['SentimentTitle'] = scaler.fit_transform(df['SentimentTitle'].values.reshape(-1, 1))
df['SentimentHeadline'] = scaler.fit_transform(df['SentimentHeadline'].values.reshape(-1, 1))

X = df[features]
y = df[estimate]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 10)


knn = KNeighborsClassifier(n_neighbors=5)
clf_knn = knn.fit(X_train, y_train)
y_pred_knn = knn.predict(X_test)


print("{} performance:".format(y_pred_knn))
print()
print(classification_report(y_test, y_pred_knn), sep='\n')




df['prob_dud'] = knn.predict_proba(X)[:, 1]

features = ['Topic', 'title_size','headline_size', 'SentimentTitle', 'SentimentHeadline', 'prob_dud'] + days
views = 'Facebook'

X = df[features]
y = df[views]

popular = y > 100
unpopular = y <= 100

df.loc[popular,views] = 1
df.loc[unpopular,views] = 0

class_names = np.array(['Not popular','Popular'])




categorical_feature_mask = X.dtypes==object
categorical_cols = X.columns[categorical_feature_mask].tolist()


from sklearn.preprocessing import LabelEncoder
le = LabelEncoder()

X[categorical_cols] = X[categorical_cols].apply(lambda col: le.fit_transform(col))

from sklearn.preprocessing import OneHotEncoder
ohe = OneHotEncoder(categorical_features = categorical_feature_mask, sparse=False)
X_ohe = ohe.fit_transform(X)


X_train, X_test, y_train, y_test = train_test_split(X_ohe, y, test_size = 0.2, random_state = 10)



# estimator = MLPClassifier(activation='tanh', solver='sgd', alpha=0.0001, hidden_layer_sizes=(11, 11), learning_rate='constant')
estimator = KNeighborsClassifier(n_neighbors=5)
# estimator = LogisticRegression()
# estimator = RandomForestClassifier(n_estimators=100, criterion="entropy")
# estimator = svm.SVC(gamma='scale')
clf = estimator.fit(X_train, y_train)
y_pred = estimator.predict(X_test)
predictedClass = clf.predict(X_test)


from sklearn.metrics import classification_report
print('Results on the test set:')
print(classification_report(y_test, predictedClass))



# Confusion matrix
np.set_printoptions(precision=2)
plot_confusion_matrix(y_test, y_pred, classes=class_names, normalize=True)
plt.show()


# Learning curve

title = "Learning Curves KNN"
# cv = ShuffleSplit(n_splits=1, test_size=0.2, random_state=0)
cv = StratifiedKFold(n_splits=5, random_state=42)
plot_learning_curve(estimator, title, X, y, ylim=(0.7, 1.01), cv=cv, n_jobs=4)
plt.show()

print ('MSE:', round(mean_squared_error(y_test, predictedClass), 4))
print ('Accuracy:', round(accuracy_score(y_test, y_pred), 4))
print ('Precision Score:', round(precision_score(y_test, y_pred), 4))
print ('Recall Score:', round(recall_score(y_test, y_pred, average='macro'), 4))
print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred), 4))
print ('F1 Score:', round(f1_score(y_test, y_pred, average='macro'), 4))