# HighPerformanceComputing
Implementation of K Nearest Neighbors in Java

What is K Nearest Neighbors

K Nearest Neighbors is a non-parametric, instanced-based learning algorithm
  Non-parametric meaning that instead of using a probability distribution derived from the data. It is compared directly against        the training data
  Instance-based learning, lazy learning, instead of creating an abstraction from the training data it instead stores all the   training data and compares the query to the data and classifies it according to its nearest neighbors

 K Nearest Neighbors is a simple algorithm where the test data is compared to all training data points by using one of the methods to find distance between it and the rest of the data(, takes the K nearest data points and then classifies the test data according to whatever the majority that its nearest neighbors are.

How to Choose K
There is no set way to find K so we will have to go through several methods to find the optimal value for K :
Large values for K can take a long time computationally, and if it is too large then everything is the most probable class
Small value for K, noisy values can have a large effect

Pros/Cons of K Nearest Neighbors

Pros
No assumptions are made about the data
Simple, its is easy to explain and understand
High Accuracy-it excels in classification
Classifier can updated easily as new instances with known classes are added

Cons
Since each testing data point is compared to all the training data points it can be computationally expensive
It can sensitive to noisy data, outliers
Sensitive to very unbalanced datasets

The Data Set

We are using MNIST data of handwritten digits, it contain a data set of  70,000 handwritten digits from 0-9. 60,000 of these used for training purposes while the remaining 10,000 are used to test the algorithm's accuracy.  

Mnist

Each of these images in the dataset are 28 by 28 and are gray scale. This means that each image can be changed into a 28 by 28 matrix with each cell representing a pixel, the pixels values can range from 0-255, with 0 being white and 255 being black. The benefit of it being in gray scale is that only one matrix needs to be used,while in color there would be three different matrices each one representing green, red, and blue. 

Success

The highest performance of KNN is around 97%. Our goal is to get 90 percent or higher on the MNIST test set. We believe we can achieve our goals with the proper distance formula and k selection. 

The Future
A major reason that KNN is not used more often is because of how computationally expensive it can be. Each data entry that you want to find the label of has to be compared with every other data entry. With the MNIST, that means a K  of just 1 means that about 60,000 comparisons need to be done.  Obviously, this can take a long time so a good idea would be to parallelize the comparisons, so instead of 1 comparison at a time we can do several drastically reducing the time.



