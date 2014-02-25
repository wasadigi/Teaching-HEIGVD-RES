# Lab 1: Playing with Java IOs


## Introduction

The goal of this lab is to **get familiar with some of the Java IO classes and interfaces**. It will only be a first encounter and we will not have the time to dig into all the details, but will get a feel for what it means to work with **data sources**, **data destinations** and **data streams**. You will also see that it is fairly easy to interact with the **file system** in Java.


## Objectives

* Learn how to use the `File.java` class of the `java.io` package to interact with the file system. 
* Learn how to programmatically explore the file system, by listing the content of directories and dynamically building file paths.
* Learn how to write a loop to read characters from a data stream (i.e. from a Reader).
* Learn how to write characters to a data stream (i.e. to a Writer).
* Learn how to write `FilterWriters` to apply transformation 'on the fly', as characters are written to a wrapped Writer. Learn how to combine several `FilterWriters` (i.e. to combine filters)


## Evaluation

* There is **no grade for this lab**. 
* **No report to write** and submit either.
* I will however ask some of you to **present their solution in front of the class on Monday, March 10th**.
* For that reason, you should be ready with:
  * **Working code in your IDE**. You should be able to walk through the code and explain how you have implemented the various functions. I expect you to run a live demo and to present the results. The demo should be "**end-to-end**", in other words it should start with the generation of the test data files.
  * **A few slides that explain what you have implemented and how**. You should write and use these slides to help you structure your explanations. I would like to see that you have understood the different sub-problems to solve (in order to implement the complete solution). I would also like to see how you have actually solved them.

## Specifications

* Your goal is to write an application that **recursively traverses a portion of your file system**, starting in a specific directory. During this traversal, you will read the content of text files. You will also **apply two basic transformations** to these files and **save the result in new files**.

* You will not start from scratch, but use the [FileSystemRobot](FileSystemRobot) project as a starting point.

* In the **first transformation**, you will replace every occurrence of 'a' or 'A' with a '@' character. You will also replace every occurrence of 'e' or 'E' with a '3' character.

* In the **second transformation**, you will simply replace every character with its uppercase value (i.e. 'z' will become 'Z').

* For every processed file named `filename.extension`, you will write the result into a new file name `filename.extension.out`.


Test data directory **before** running your program:

```
folderA
  folderA-1
    a.txt
    b.txt
    x.png
  folderA-2
    c.txt
    y.gif
folderB
  d.txt
folderC
  e.txt
  f.txt
```

Test data directory **after** running your program:

```
folderA
  folderA-1
    a.txt
    a.txt.out
    b.txt
    b.txt.out
    x.png
  folderA-2
    c.txt
    c.txt.out
    y.gif
folderB
  d.txt
  d.txt.out
folderC
  e.txt
  e.txt.out
  f.txt
  f.txt.out
```

Test data file **before** running your program (`a.txt`):

```
Hello, this is just a test to check my DATA conversion program.
```

Test data file **after** running your program (`a.txt`):

```
H3LLO, THIS IS JUST A T3ST TO CH3CK MY D@T@ CONVERSION PROGR@M.
```

## Class Diagram for the FileSystemRobot Project

![](https://rawgithub.com/wasadigi/Teaching-HEIGVD-RES/master/labs/01-JavaIO/Figures/class-diagram.svg)


### Task 1: Clone this Github Repository and Open the FileSystemRobot in Netbeans

We will use **git** and **Github** as a way to share material for the course. You will most likey use **git** and **Github** ***a lot*** in your school projects and most imporantly **in your future job**.

In a few words, **git** is a **distributed version control system**, which you can use to manage your files (source code, documentation, etc.). It is already very useful if you work on your own, but it is critical if you work in a team and need to coordinate your activities with others. **Github**, on the other hand, is an **online service that adds a web front-end, social coding features and various tools on top of a hosted git infrastructure**. It has become one of the hot places for open source projects, which you should visit on a regular basis.

If you have never worked with git, then take the time to go through this [simple guide](http://rogerdudler.github.io/git-guide/) and to follow this [immersion](http://gitimmersion.com). If you do not have the time to go through all the material, start with the sessions "Getting Started", "Basics" and "Multiple Repositories".

After getting the git binaries, you will essentially issue the following commands in a terminal:

```
git config --global user.name "Your Name"
git config --global user.email "your_email@heig-vd.ch"
```

To clone the **Github repository** on your local machine, start by setting up **git** on your machine. You will find useful information [here](https://help.github.com/articles/set-up-git) and [there](http://git-scm.com/book/en/Getting-Started-First-Time-Git-Setup). Then, open a terminal and type the following command:

```
git clone git@github.com:wasadigi/Teaching-HEIGVD-RES.git
```

This should create a clone of my repository on your local machine. You will never push modifications to my repository. However, when I add or edit content, you will be able to get it by pulling commits from the origin master. You will do that by issuing the following command:

```
git pull origin master
```

Without digging into the details, `origin` means that you want to pull modifications from my repository (because you cloned it, so it is the origin of your local repository). Also, `master` means that you want to pull changes from a **branch** called `master`. You don't need to worry about that for now. Since you will not use branches initially, you can also simply type the following command to get my updates (git will figure out that you are referring to origin and master):

```
git pull
```


### Task 1: Understand What You Need To Achieve (15 minutes)

Read the specifications very carefully. Study the class diagram and make sure that you understand the overall structure of the program. If you don't understand something, speak up!

### Task 2: Generate Test Data (15 minutes)

In the main part of the lab, you will work on a collection of text files and apply transformation to their content. To be able to test your code, you should create a folders tree containing text files. Instead of doing that manually, you can use the [QuotesGenerator](QuotesGenerator) tool written for that purpose. 

Have a look at the [README](QuotesGenerator/README.md) file, follow the instructions, run the tool and generate your test data. Spend some time looking at the code in the [node.js script](QuotesGenerator/generator.js). You don't need to understand everything in that script, but you should realize that writing a client program that dynamically fetches data from a web service is pretty easy. We will get back to that later in the course.


### Task 3: Find a Way To Recursively Traverse the File System (20 minutes)

Have a look at the [File.java](http://docs.oracle.com/javase/7/docs/api/java/io/File.html) javadoc documentation. Spend some time browsing through the [java tutorial](http://docs.oracle.com/javase/tutorial/essential/io/fileio.html). If you think about it, what you need is:

* a way to list the content of directory
* a way to check if a path corresponds to a file or to a directory
* a way to combine these two mechanisms in a tree traversal algorithm

### Task 4: Find a Way To Open a Text File and to Read All of its Content (15 minutes)

Here, you will need to use the [FileReader.java](http://docs.oracle.com/javase/7/docs/api/java/io/FileReader.html) class. You will need to write a loop to read chunks of characters from the data stream. That should be very easy, especially if you read the [java tutorial](http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html).

### Task 5: Find a Way to Write Characters to a Text File (15 minutes)

That is something that you will be able to the with the [FileWriter.java](http://docs.oracle.com/javase/7/docs/api/java/io/FileWriter.java) class. Again, there are examples in the [java tutorial](http://docs.oracle.com/javase/tutorial/essential/io/charstreams.html), so it should be a piece of cake.

### Task : Make Sure That You Have Understood FilterWriters and the Decorator Pattern ()

Go back to the course material, read the 

### Task : Put All Pieces Together (60 minutes)

1. Read and understand what the `FileSystemRobot.java` class is doing. Open all of the other files and read the comments. Make sure that you understand how the classes are supposed to collaborate. **If it is not crystal clear for you, speak up!**
1. Implement the `DFSFileSystemExplorer.java` class. This is where you will implement the recursive file system traversal algorithm.
1. Implement the `WeirdFileProcessor.java` class. This is where you will open the input and output files and where you will write the read loop. This is also where you will wrap the `FileWriter` with your two `FilterWriter` subclasses.
1. Implement the `UppercaseFilterWriter.java` class. This is where you will transform characters to their uppercase value before writing them to the wrapped `Writer`.
1. Implement the `ScramblerFilterWriter.java` class. This is where you will transform some of the characters to '@' and '3' before writing them to the wrapped `Writer`.
1. Run the program on the test data you generated at the beginning of the lab.
1. Check the results.