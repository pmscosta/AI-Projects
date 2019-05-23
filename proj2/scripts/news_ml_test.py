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


# example of training a final regression model
from sklearn.linear_model import LinearRegression
from sklearn.datasets import make_regression


import datetime
news_final_filename = "../dataset/News_Final_WO.csv"
facebook_views_filename = "../dataset/facebook_views.csv"

news_final = pd.read_csv(news_final_filename)
news_final = news_final.drop_duplicates()
facebook_views = pd.read_csv(facebook_views_filename)
facebook_views = facebook_views.drop_duplicates()


df = news_final.merge(facebook_views, how='inner', on="IDLink")


title_size = df.Title.apply(lambda x: len(str(x).split(' ')))
# df.insert(loc=len(df.columns), column='title_size', value=title_size)
df['title_size'] = title_size

headline_size  = df.Headline.apply(lambda x: len(str(x).split(' ')))
# df.insert(loc=len(df.columns) - 4, column='headline_size', value=headline_size)
df['headline_size'] = title_size

days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']

for i, day in enumerate(days) :
    df.insert(loc=9 + i, column=day, value=0)

for row in df.itertuples():
    date_time = row.PublishDate
    week_day = datetime.datetime.strptime(date_time, '%m/%d/%Y %H:%M').weekday()
    week_day_name = days[week_day]
    df.at[row.Index, week_day_name] = 1


# Define a quick function that will return 1 (true) if the article has 0-1 share(s)
def dud_finder(popularity):
    if popularity <= 1:
        return 1
    else:
        return 0
# Create target column using the function
df['is_dud'] = df['Facebook'].apply(dud_finder)

features = ['SentimentTitle', 'SentimentHeadline'] + days
estimate = 'is_dud'

X = df[features]
y = df[estimate]

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 10)


knn = KNeighborsClassifier()
clf_knn = knn.fit(X_train, y_train)
y_pred_knn = knn.predict(X_test)
# predictedClassKNN = clf_knn.predict(X_test)

print("{} performance:".format(y_pred_knn))
print()
print(classification_report(y_test, y_pred_knn), sep='\n')


# Draw graph

# fpr, tpr, thresholds = roc_curve(y_test, knn.predict_proba(X_test)[:,1])
# plt.plot([0, 1], [0, 1], 'k--')
# plt.plot(fpr, tpr)
    
# plt.xlabel('False Positive Rate')
# plt.ylabel('True Positive Rate')
# plt.title('ROC Curves')
# plt.show()





df['prob_dud'] = knn.predict_proba(X)[:, 1]

features = ['SentimentTitle', 'SentimentHeadline', 'prob_dud'] + days
views = 'Facebook'



# df['values'] = df[views].sum(axis=1)

X = df[features]
y = df[views]

# popular = y > 100
# unpopular = y <= 100

# df.loc[popular,views] = 1
# df.loc[unpopular,views] = 0





# categorical_feature_mask = X.dtypes==object
# categorical_cols = X.columns[categorical_feature_mask].tolist()


# from sklearn.preprocessing import LabelEncoder
# le = LabelEncoder()

# X[categorical_cols] = X[categorical_cols].apply(lambda col: le.fit_transform(col))
## X[categorical_cols].head(10)

# from sklearn.preprocessing import OneHotEncoder
# ohe = OneHotEncoder(categorical_features = categorical_feature_mask, sparse=False)
# X_ohe = ohe.fit_transform(X)

# from sklearn.feature_extraction.text import CountVectorizer
# from sklearn.feature_extraction.text import TfidfTransformer

# from sklearn.pipeline import Pipeline
# text_clf = Pipeline([
#     ('vect', CountVectorizer()),
#     ('tfidf', TfidfTransformer())
# ])

# X['Title'] = X['Title'].apply(lambda x: text_clf.fit_transform([x]))


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 10)


# from sklearn.metrics import mean_squared_error

## Instantiate an XGBRegressor
# xgr = xgb.XGBRegressor(random_state=2)

# # Fit the classifier to the training set
# xgr.fit(X_train, y_train)

# y_pred = xgr.predict(X_test)

# mean_squared_error(y_test, y_pred)


# from sklearn.model_selection import GridSearchCV

# # Various hyper-parameters to tune
# xgb1 = xgb.XGBRegressor()
# parameters = {'nthread':[4], 
#               'objective':['reg:linear'],
#               'learning_rate': [.03, 0.05, .07], 
#               'max_depth': [5, 6, 7],
#               'min_child_weight': [4],
#               'silent': [1],
#               'subsample': [0.7],
#               'colsample_bytree': [0.7],
#               'n_estimators': [250]}

# xgb_grid = GridSearchCV(xgb1,
#                         parameters,
#                         cv = 2,
#                         n_jobs = 5,
#                         verbose=True)

# xgb_grid.fit(X_train, y_train)



# reg = LinearRegression()
# reg.fit(X_train, y_train)
# y_predicted = reg.predict(X_test)
# print("Mean squared error: %.2f" % mean_squared_error(y_test, y_predicted))
# print('R²: %.2f' % r2_score(y_test, y_predicted))

# sc = StandardScaler()
# X_train = sc.fit_transform(X_train)
# X_test = sc.transform(X_test)

# rf = RandomForestClassifier(n_estimators=100, criterion="entropy")
# clf_rf = rf.fit(X_train,y_train)
# y_pred_rf = rf.predict(X_test)
# predictedClassRF = clf_rf.predict(X_test)

# dt = DecisionTreeClassifier(criterion="gini", splitter="random")
# clf_dt = dt.fit(X_train, y_train)
# y_pred_dt = dt.predict(X_test)
# predictedClassDT = clf_dt.predict(X_test)

# estimator = KNeighborsClassifier()
# n_samples = X_train.shape[0]
# train_scores, test_scores = [], []
# for n in range(10, 10, n_samples):
#     estimator.fit(X_train[:n], y_train[n])
#     train_scores.append(estimator.score(X_train[:n], y_train[n]))
#     test_scores.append(estimator.score(X_test, y_test))
# plot(range(10, 10, n_samples), train_scores)
# plot(range(10, 10, n_samples), test_scores)

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

# nn = MLPClassifier(solver='lbfgs', alpha=1e-5, hidden_layer_sizes=(11, 11), random_state=1)
# clf_nn = nn.fit(X_train, y_train)
# y_pred_nn = nn.predict(X_test)
# predictedClassNN = clf_nn.predict(X_test)

# sv = svm.SVC(gamma='scale')
# clf_sv = sv.fit(X_train, y_train)
# y_pred_sv = sv.predict(X_test)
# predictedClassSV = clf_sv.predict(X_test)

print ("============Random Forest============")
print ('MSE RF:', round(mean_squared_error(y_test, predictedClassRF), 4))
print ('Accuracy:', round(accuracy_score(y_test, y_pred_rf), 4))
print ('Precision Score:', round(precision_score(y_test, y_pred_rf), 4))
print ('Recall Score:', round(recall_score(y_test, y_pred_rf, average='macro'), 4))
print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_rf), 4))
print ('F1 Score:', round(f1_score(y_test, y_pred_rf, average='macro'), 4))

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


# print ("============Neural Network============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassNN), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_nn), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_nn), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_nn, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_nn), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_nn, average='macro'), 4))

# print ("============Support Vector Machine============")
# print ('MSE:', round(mean_squared_error(y_test, predictedClassSV), 4))
# print ('Accuracy:', round(accuracy_score(y_test, y_pred_sv), 4))
# print ('Precision Score:', round(precision_score(y_test, y_pred_sv), 4))
# print ('Recall Score:', round(recall_score(y_test, y_pred_sv, average='macro'), 4))
# print ('ROC AUC Score:', round(roc_auc_score(y_test, y_pred_sv), 4))
# print ('F1 Score:', round(f1_score(y_test, y_pred_sv, average='macro'), 4))


