# Lab 5: Protocol Implementation

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
2. [Procedure and Tasks](#ProcedureAndTasks)
3. [Evaluation](#Evaluation)

## <a name="Introduction"></a>Introduction

In this lab, you will use the [Application Protocol Toolkit](../../examples/12-ApplicationProtocolToolkit) to implement a custom application-level protocol. 


## <a name="Options"></a>Options

For this lab, you have 3 options:

1. **Option 1**: implement **your own specification** (produced in the last lab). You can choose either the *discovery protocol* or the *compute service protocol* (and of course, you can implement both).

2. **Option 2**: implement a **server** compliant with the [Tako Compute Protocol](https://github.com/j-bischof/Smart-Calculator/blob/master/specification/rfc-tcp.txt) (proposed by J. Bischof and H. Haiken)

3. **Option 3**: implement both a **discovery listener** and a **discovery speaker** compliant with the [Discovery Protocol](https://github.com/rbuache/Smart-Calculator/blob/master/specification/SpecificationV1.md) (proposed by R. Buache and M. Frohlich)


## <a name="ProcedureAndTasks"></a>Procedure and Tasks

* **Create a new fork of the Smart Calculator [repo](https://github.com/wasadigi/Smart-Calculator)**. In the **specification folder**, add a text file that contains the protocol specification that you have decided to implement. In the README.md file, **make sure to include the name of the 2 students working on this new lab**.  
 
* **Start by studying the protocol specification**, especially if you decide to implement one that was specified by another group. After attending Monday's presentations, this should be pretty straightforward.

* **Continue by reading the description of the Application Toolkit Protocol** and by studying through the implementation of the Ping Pong protocol provided with the toolkit. **Note:** I have copied the current version of the Application Protocol Toolkit in the Smart Calculator repo, in the `implementation` folder.

* **Create a new package** for your protocol and implement the required classes, at the transport, syntactic and semantic layers.

## <a name="ProcedureAndTasks"></a>Evaluation

This lab will not be evaluated with a grade. However, it is **not optional** and some of you will be expected to do a presentation and a demo on May 5th, 2014.

** Extra Super Bonus Grade ALERT!!**

You have another chance to get a bonus grade, under the following conditions:

* You work either **alone** or in groups of **two students**.
* You implement a **fully functional system** (with **clean code**), which allows you to **demonstrate** the [initial story](https://github.com/wasadigi/Teaching-HEIGVD-RES/tree/master/labs/04-ProtocolSpecification#the-story).
* You **publish your work** in a **new** fork of the Smart Calculator [repo](https://github.com/wasadigi/Smart-Calculator).
* You **publish the specification of the implemented protocol** in this fork (it does not have to be the one you have produced last week).
* You provide **complete and detailed instructions** in the README.md file, which allow me to clone your repo, build your software components, execute them and go through a demo script.
* You **record a webcast** that shows your system in action, publish it on YouTube and link it from your README.md file.

I understand that this is a lot of work. For this reason, if you deliver all of these requirements, you will get a **double-flex 6.0**. Double-flex means that you will have the **freedom** to decide how I will count two extra 6.0 before computing your final grade for the module: either as 2 extra "written test grades", as 2 "lab grades" or as 1 of each.

***Students who are up to the challenge are kindly asked to let me know until June 2nd at the latest. Live demos will be schedules on Monday, June 23rd.***