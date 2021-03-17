# /usr/bin/python

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd

def plotCoalescing():
    file_path = "../data/coalescing.csv"
    df = pd.read_csv(file_path)
    df = df.sort_values(by = 'size') # TO FIX: check condition
    females = df.loc[df['sex'] == 'F'] # df of females
    males = df.loc[df['sex'] == 'M'] # select of males
    xfemales = females['time']
    yfemales = females['size']
    xmales = males['time']
    ymales = males['size']
    plt.plot(xfemales, yfemales)
    plt.plot(xmales, ymales)
    plt.show()
    # print(females)

def plotPopulationGrowth():
    file_path = "../data/population.csv"
    df = pd.read_csv(file_path)
    df = df.sort_values(by = 'time') # TO FIX: check condition
    xpopulation = df['time']
    ypopulation = df['size']
    #  print(xpopulation)
    plt.plot(xpopulation, ypopulation)
    plt.show()

if __name__ == "__main__":
    plotCoalescing()
    plotPopulationGrowth()

