# Pedigree-IFT2015

Devoir 2 dans le cadre du cours IFT2015 H2021

## Table des matières

1. [Motivation](#motivation)
2. [Prerequisites](#prerequisites)
3. [Usage](#usage)
4. [Ressources](#ressources)

## Motivation

We want to simulate the population growth over the last 2000 years

To do so, we have to simulate three types of events:
- Birth
- Death
- Reproduction

The death rate is given by the Gompertz-Makeham law that takes into account 2 parameters:
- Natural Death: Mortality rate that doubles every 8 years
- Accidental Death: constant rate fixed at 1%/year

The reproduction rules follows the Exponential Law with a fixed rate R (Poisson Process).
The women can reproduce from age 16 to 50, whereas men can do from age 16 to 73.
Like real life, our sims can cheat on their spouse, but they often prefer to stay with
their current mate.

## Prerequisites


## Usage



## Ressources

- Specification: https://ift2015h21.wordpress.com/2021/02/24/projet-2-nos-ancetres-communs/#more-363
- Wikipedia's Discrete Event Simulation: https://en.wikipedia.org/wiki/Discrete-event_simulation
- Programming in Java for Ecology and Evolutionary Biology: https://peterbeerli.com/papers/pdfs/Maddison-javaEcoEvol.pdf
- Discrete Event Simulation in Java: http://akira.ruc.dk/~keld/research/JAVASIMULATION/JAVASIMULATION-1.0/docs/Report.pdf?fbclid=IwAR0JYqjcbVOe9KTogi2tgWunaoQRSMWPWr8r02iuV8-D1d84BASIGTttNNw
- Introduction to Discrete-Event Simulation and the SimPy Language
- AlbertoCoder Population Simulator: https://github.com/AlbertoCoder97/Population-Simulator-Java

