# Introduction

## Overview

The course is organized in **three successive parts**, that will keep busy for respectively **10 weeks**, **10 weeks** and **6 weeks**.

In **Part 1**, we will focus on **network programming** with the Socket API. **Be ready to code**. After a general introduction to Input/Output (IO) programming in Java, we will see how the **Socket API** provides abstractions to use TCP and UDP protocols. We will see that this API is available in many programming languages and will study concrete examples in Java and in C. After completing this part of the course, you will be able to **specify an application-level protocol**, as well as to **implement a client and a multi-threaded server** compliant with this specification. In the labs, we will acquire the basic skills that will be needed to do something more comprehensive later on.

In **Part 2**, we will focus on the HTTP protocol. We will still be **coding**, but we will also be dealing with **IT infrastructure issues**. We will start by looking at the **main protocol concepts**: statelessness, resource and resource representation, syntax of the requests and responses, content negotiation, connection handling, etc. In parallel, we will work on one of the labs that will be **evaluated with a grade**. In this lab, we will **implement a simplified multi-threaded HTTP server in Java**. By simplified, we mean that we will not implement the complete HTTP 1.1 specification, but only a subset. Once familiar with the protocol, we will look at what it means to design and implement a typical **web application IT infrastructure** (in other words, an environment for hosting web applications). We will start examine the notion of **systemic qualities** and talk about performance, scalability, availability and security. We will then see how **HTTP proxies and reverse proxies** play an important role in that context. We will see how these things work in practice and apply them in a lab, that will also be evaluated with a grade. In this lab, we will **design and setup a complete web application infrastructure** with a web server, an application server and a reverse proxy. 

In **Part 3**, we will look at several **other application-level protocols**. We will start with the **LDAP protocol** and study how directory servers work. In a lab that will be evaluated with a grade, we will install and configure an open source directory server, write a program to transform tabular data (.csv) into hierarchical data (.ldif), import this data and finally use LDAP filters to query the directory. We will then take a look at various **electronic mail protocols**, such as **SMTP** and **IMAP**. In the corresponding labs, we will write some code to implement clients and/or servers for these protocols. Finally, we will conclude the course by looking at some **file transfer protocols**.

## What Should I be Able to Do After the Course?

1. **Design and implement client-server applications**, starting from scratch. Starting from an idea, you should be able to design and specify a **communication protocol** for your application. In other words, you should be able to define the messages exchanged by clients and servers and to specify what should happen when these messages are received. You should then be able to implement the client and the server programs in a programming language like Java. With the **Socket API**, you should be able to use **TCP** and **UDP** protocols to send and receive data across the network. 

2. **Talk HTTP**. You should know the details of the HTTP syntax and be able to write HTTP requests and HTTP responses, playing the role of a human HTTP client or server. Of course, you should be able to interpret and analyze a stream of HTTP messages, something that is very useful when troubleshooting a web application. You should also be able to describe the key concepts underlying the HTTP specification (statelessness, resources, content negotiation, etc.). Since you should master the Socket API, you should also be able to **implement HTTP clients and servers**. You should be able to explain the difference between a **single-threaded synchronous server**, a **multi-threaded server** and a **single-threaded asynchronous server**.

3. **Design and setup a web application infrastructure**. If you were to join a start-up and were asked to create an environment for hosting a cool new social web application, you should know how to start the process (of course, you will not have all the answers…). You should know about the role of **reverse proxies** and **load balancers** in this context and should be able to install and configure these elements. You should be able to explain the notion of **systemic quality** and describe important ones (scalability, availability, security, etc.).

4. **Install, configure and use an LDAP server**. If you were asked to setup a directory server for a company, you should be able to install a server such as OpenDJ, install a client such as Apache Directory Studio. You should be able to design the Directory Information Tree to structure the information in a hierarchical manner. You should be familiar with the LDIF format and be able to write a script that generates LDIF data (for instance by transforming CSV data).

5. **Describe how eletronic mail protocols work**. You should be able to explain what are the roles of SMTP and IMAP and to describe the functions of these protocols. Using your network programming skills, you should be able to read the RFCs that specify the protocols and implement clients and servers. But you should also know that there are frameworks, libraries and toolkits that you can use in various programming languages and that take care of all the details if you want to add e-mail capabilities to an application.

6. **Use file transfer protocols**. You should know why scp, sftp and rsync are useful tools and you should be able to use them to support your work activities.

## What Should I Install on My Machine?

* The **Netbeans IDE** ([download](https://netbeans.org/downloads/)). Make sure that you download the "Java EE" or "All" bundle, so that you get the glassfish application server.
* **Wireshark** ([download](http://www.wireshark.org/))
* **Firefox with the Firebug extension** ([download](https://getfirebug.com/)
* **Node.js** ([download](http://nodejs.org/))
* The **apache httpd** server
* The **apache JMeter** testing tool ([download](http://jmeter.apache.org/))
* The **OpenDJ** open source LDAP server ([download](http://forgerock.com/products/open-identity-stack/opendj/))
* The **Apache Directory Studio** LDAP client ([download](https://directory.apache.org/studio/)) 

**IMPORTANT**: If you are using **Windows**, you **have to** download and install **Cygwin** ([download](http://www.cygwin.com/))



## Planning

Week | Date       | Lecture             | Lab                 
:---:|------------|---------------------|--------
1    | 03.03.2014 | [Introduction to Java IOs](./01-Lecture1-JavaIOs.md)   | [Playing with Java IOs](../labs/01-JavaIO)
2    | 10.03.2014 | [TCP programming](./02-Lecture2-TCPProgramming.md)     | [Implement a TCP client and a TCP server](../labs/02-TCPProgramming)
3    | 17.03.2014 | UDP programming     | Implement a UDP client and a UDP server
4    | 24.03.2014 | Specifying an application protocol  | Specify your own protocol
5    | 31.03.2014 | Implementing an application protocol | Implement a client and a server for your own protocol
6    | 07.04.2014 | Protocol concepts and syntax (1) | Implement a simple HTTP server
7    | 14.04.2014 | Protocol concepts and syntax (2) | Implement a simple HTTP server
     |            | *Eastern Holidays*    |
8    | 28.04.2014 | **Test 1**          |
9    | 05.05.2014 | Web infrastructure (1)   | Design and implement a web infrastructure
10   | 12.05.2014 | Web infrastructure (2)   | Design and implement a web infrastructure
11   | 19.05.2014 | LDAP   | Install a directory server, import data, query with LDAP filters
12   | 26.05.2014 | LDAP   | **OFF**
13   | 02.06.2014 | Electronic mail protocols | Implement a SMTP client
14   | 09.06.2014 | **OFF** | Implement an IMAP client
15   | 16.06.2014 | **Test 2**   |
16   | 23.06.2014 | File transfer protocols   | Do manipulations with rsync / SFTP


## Important Dates


Week | Date       | Description                           
:---:|------------|------------
8|28.04.2014|**Test 1**: network programming, HTTP protocol
8|28.04.2014|Submit the **HTTP server lab** by 8:00 AM (each 4 hours delay increment = 0.5 pt penalty)
11|19.05.2014|Submit the **Web infrastructure lab** by 8:00 AM (each 4 hours delay increment = 0.5 pt penalty)
14|09.06.2014|Submit the **LDAP lab** by 8:00 AM (each 4 hours delay increment = 0.5 pt penalty)
15|16.06.2014|**Test 2**: Web infrastructure, LDAP and messaging protocols

