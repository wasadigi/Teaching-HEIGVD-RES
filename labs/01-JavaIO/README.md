# Lab 1: Playing with Java IOs

## Introduction

The goal of this lab is to **get familiar with some of the Java IO classes and interfaces**. It will only be a first encounter and we will not have the time to dig into all the details, but will get a feel for what it means to work with **data sources**, **data destinations** and **data streams**.

## Objectives

* Learn how to use the `File.java` class of the `java.io` package to interact with the file system. 
* Learn how to programmatically explore the file system, by listing the content of directories and dynamically building file paths.
* Learn how to write a loop to read characters from a data stream (i.e. from a Reader).
* Learn how to write characters to a data stream (i.e. to a Writer).
* Learn how to write `FilterWriters` to apply transformation 'on the fly', as characters are written to a wrapped Writer. Learn how to combine several `FilterWriters` (i.e. to combine filters)

## Tasks

### Task 1: Understand What You Need To Achieve (10 minutes)

* Your goal is to **recursively traverse a portion of your file system**, starting in a specific directory. During this traversal, you will read the content of text files. You will also **apply two basic transformations** to these files and save the result in new files.

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