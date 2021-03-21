# /usr/bin/python

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def plotCoalescing():
    file_path = "../data/coalescing.csv"
    df = pd.read_csv(file_path)
    females = df.loc[df['sex'] == 'F'] # df of females
    males = df.loc[df['sex'] == 'M'] # select of males
    xfemales = females['time']
    yfemales = females['size']
    xmales = males['time']
    ymales = males['size']
    plt.plot(xfemales, yfemales)
    plt.plot(xmales, ymales)
    plt.show()

def plotPopulationGrowth():
    file_path = "../data/population.csv"
    df = pd.read_csv(file_path)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    xpopulation = df['time']
    ypopulation = df['size']
    plt.plot(xpopulation, ypopulation)
    plt.show()

def plotCoalescingAndPopulation():
    file_coalescing = "../data/coalescing.csv"
    file_population = "../data/population.csv"
    df = pd.read_csv(file_coalescing)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    females = df.loc[df['sex'] == 'F'] # df of females
    males = df.loc[df['sex'] == 'M'] # select of males
    xfemales = females['time']
    yfemales = females['size']
    xmales = males['time']
    ymales = males['size']
    plt.plot(xfemales, yfemales)
    plt.plot(xmales, ymales)
    df = pd.read_csv(file_population)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    xpopulation = df['time']
    ypopulation = df['size']
    plt.plot(xpopulation, ypopulation)
    plt.show()

def plotScatter():
    file_coalescing = "../data/coalescing.csv"
    file_population = "../data/population.csv"
    df = pd.read_csv(file_coalescing)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    females = df.loc[df['sex'] == 'F'] # df of females
    males = df.loc[df['sex'] == 'M'] # select of males
    # resizing scatter point
    sizef = np.full((1,females.shape[0]), 10)
    sizem = np.full((1,males.shape[0]), 10)
    #  print(sizef)
    # plotter scatter plot with line
    xfemales = females['time']
    #  xfemales /= 100
    yfemales = females['size']
    yfemales = np.log10(yfemales)
    xmales = males['time']
    #  xmales /= 1000
    ymales = males['size']
    ymales = np.log10(ymales)
    plt.scatter(xfemales, yfemales, s = sizef, alpha = 0.6, color = "blue")
    plt.scatter(xmales, ymales, s = sizem, alpha=0.6, color = "red")
    plt.plot(xfemales, yfemales, "--b", alpha = 0.6, label ='Aieules')
    plt.plot(xmales, ymales, "--r", alpha = 0.6, label = 'Aieux')
    df = pd.read_csv(file_population)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    xpopulation = df['time']
    #  xpopulation /= 1000
    ypopulation = df['size']
    ypopulation = np.log10(ypopulation)
    plt.plot(xpopulation, ypopulation, "green", label = 'Population')
    plt.xlabel("Years")
    plt.legend(loc = 'center left')
    plt.show()

if __name__ == "__main__":
    #  plotCoalescing()
    #  plotPopulationGrowth()
    plotScatter()
    #  plotCoalescingAndPopulation()

