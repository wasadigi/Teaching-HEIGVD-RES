# Lab 6-7: HTTP Implementation

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
2. [Procedure and Tasks](#ProcedureAndTasks)
3. [Evaluation](#Evaluation)

## <a name="Evaluation"></a>Evaluation

This lab will be evaluated with a grade. You should work in teams of 2 students. You will be evaluated both on the client-side (which you will work on this week) and on the server-side implementation (which you will work on next week).

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

