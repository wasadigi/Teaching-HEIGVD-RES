# Lab 6-7: HTTP Implementation

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
2. [Procedure and Tasks](#ProcedureAndTasks)
3. [Evaluation](#Evaluation)

## <a name="Evaluation"></a>Evaluation

This lab will be evaluated with a grade. You should work in teams of 2 students. You will be evaluated both on the client-side (which you will work on this week) and on the server-side implementation (which you will work on next week).

**Important note:** when you implement the client, you **CANNOT** use the URLConnection class of the JDK and **HAVE TO** use the Socket class. In other words, you have to do a low-level implementation of the HTTP protocol.

## <a name="Requirements"></a>Requirements

You have to implement a Java HTTP library, which a program can use to submit HTTP requests and to get corresponding HTTP responses. The library should handle the parsing of HTTP messages. On the client side, it should be able to deal with the main headers. It should also be able to deal with HTTP redirections.

The objective of the first week is to implement the client-side of the library. To help you with your job, you have received:

* A `HttpLibrarySkeleton` project, where you will implement your classes and methods.
* a `HttpLibraryTest` project, which you **should not change**, and which you will use to validate your work.
* A `test-server` node.js project, which provides you with a test HTTP server (used by the JUnit tests)


## <a name="Tasks"></a>Tasks

* **Start by running the test server:**
  * go in the `test-server` folder and type `node server.js`
  * inspect the source code of the `server.js` file
  * use `telnet` from a terminal to send HTTP requests to the test server and inspect the responses

* **Continue by opening the Netbeans projects provided as a starting point:**
  * Open the `HttpLibrarySkeleton` and the `HttpLibraryTest` projects in Netbeans.
  * the first one is the project where you will write your code (it is an empty shell for now)
  * the second one is a JUnit project that you will use to test your implementation (**YOU SHOULD NOT MODIFY CODE IN THIS PROJECT!!!**)
  * in Netbeans, go in the `HttpLibraryTest` project; open the `Test Packages` folder, right click on `ch.heigvd.res.http.test` and select **Test Package** in the contextual menu.
  * this should launch the JUnit tests and you should see a list of **red** warnings in the **Test Results** window; this is **normal**: your job will be to turn them **green** before the end of the lab! 
  
* **Now, you can proceed to design and implementation**
  * You should work only in the `HttpLibrarySkeleton` project.
  * You will need to add your classes and to implement methods in the provided class.
  * Start by looking at the comments in the interfaces, they should make it clear what you need to implement, in terms of behavior.
  * Start by implementing the `LineInputStream`, which you will use to read the beginning of HTTP messags (line by line, with `CRLF` as end-of-line markers)
  * Proceed with the implementation of the HTTP client classes.
  * As you proceed with the implementation, run the JUnit tests to check your progress.
  
## <a name="Tasks2"></a>Tasks, Phase 2

* **When the client-side part of the library works, you can proceed with the server side**
    * You will apply the same logic: the library skeleton and the test projects have been updated to include placeholders and validators to guide you through the implementation of a basic HTTP server.
    * You should use the provided automated tests to figure out which URLs you need to process and what you should do when you receive requests with these URLs on the request line.
    
* **Here are a couple of comments about the requirements**
    * Your HTTP server should be able to handle multiple clients concurrently (we have seen how to use workers and threads for that purpose a few weeks ago).
    * Your HTTP server should be able to serve static content from files stored in your local file system (in other words, you should use IO classes both to interact with files and with sockets).
    * Your HTTP server does not have to implement chunked encoding. It can use the `Connection: close` header. For static content, however, it has to send the proper `Content-length` header.

## <a name="Tasks2"></a>How to Submit Your Work

1. Create a `lastName1lasName2.zip` archive that contains:
   * a `README.md` file, which should contain your **first names and last names**, any comments that you might have about your work (if there are things that you have not been able to do, explain what and why).
   
   * As many of you have already told me, one or two automated tests stay red (the one testing the radar.oreilly.com website is always red, the one testing www.lotaris.com is sometimes red). This is normal. Please analyze why (by investigating the cause and analyzing the returned response). Explain what you have understood in the `README.md` file.
   
   * the Netbeans project with your sources.

2. Send your archive to Yannick Iseli (he has an address at HEIG-VD), with the subject **[RES] Lab Submission LastName1 LastName2**.
   


## <a name="Tasks2"></a>Evaluation Criteria

To evaluate your work, we will:

* Open your Netbeans project and make a **Clean and Build** to generate a `.jar` file. Warning: if your code does not compile, you will not reach the 4.0 grade.
* Use your `.jar` file in our project, with our test classes and methods.
* Run the automated tests.
* Look at your code. We will look at the code structure, quality and readability. We will also look at the coding guidelines (names used for classes, methods and variables), at the presence of informative comments.