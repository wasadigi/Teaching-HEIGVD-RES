# Lab 2: TCP Programming

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
3. [Evaluation](#Evaluation)
4. [Specifications](#Specifications)
6. [Tasks](#Tasks)
    1. [Task 1: Study the specification and the code provided for the server](#Task1)
    1. [Task 2: Execute the server and play with it](#Task2)
    1. [Task 3: Write a client compliant with the protocol specification](#Task3)
    1. [Task 4: Extend the server to implement the Roulette Protocol V2](#Task4)

## <a name="Introduction"></a>Introduction

The goal of this lab is to **get familiar with TCP programming in Java**, by working both with a server and a client. We will consider a **custom application-level protocol**, the "Quiz Roulette" protocol, which is used to write sadistic client-server applications. More on this later.

[![](images/roulette.gif)](http://www.imdb.com/title/tt0077416/)

You can work in pairs, so that you can discuss while you go through the tasks. Please make use of your two computers. Run the client on one computer and the server on the other. Validate that you are able to communicate across the network.


## <a name="Objectives"></a>Objectives

After this lab, you should be able to:

* **Use the telnet command from a terminal to connect to a TCP server** and act as a *human client*. In other words, you should be able to type and send commands to the server and see the responses on the console.

* **Use Wireshark to analyze the flow of data** exchanged by your TCP server and client.

* **Write a TCP client in Java**, using the Socket class. You should be able to explain the relationship that exists between sockets, input and output streams. You should be able to explain how sockets are identified (describe the 4 attributes) and explain where and how this information is found in TCP segments.

* **Write a TCP server in Java**, using the ServerSocket and Socket classes. You should be able to describe the overall structure of the program. In other words, you should be able to describe what classes need to be implemented, what are their responsibilities and how they interact with each other.

* **Write a multi-threaded TCP server in Java**, using the Runnable interface and the Thread class. You should be able to describe how the notion of **worker** is a useful abstraction to describe what is happening within a server. You should be able to describe that at least two types of workers are found in network servers. You should be able to **describe the role of these workers by making an analogy** to a real-world situation (e.g. what happens in a restaurant or in a supermarket).





## <a name="Evaluation"></a>Evaluation

* There is **no grade for this lab**. 
* **No report to write** and submit either.
* I will however ask some of you to **present their solution in front of the class**.
* For that reason, you should be ready with:
  * **Working code in your IDE**. You should be able to walk through the code and explain how you have implemented the various functions. I expect you to run a live demo and to present the results. The demo should be "**end-to-end**", in other words it should start with the generation of the test data files.
  * **A few slides that explain what you have implemented and how**. You should write and use these slides to help you structure your explanations. I would like to see that you have understood the different sub-problems to solve (in order to implement the complete solution). I would also like to see how you have actually solved them.

## <a name="Specifications"></a>Specifications

* Your goal is to write an application that **recursively traverses a portion of your file system**, starting in a specific directory. During this traversal, you will read the content of text files. You will also **apply two basic transformations** to these files and **save the result in new files**.

### The Roulette Protocol V1

* **The Roulette Protocol is a session-oriented (stateful) protocol**. This means that clients establish a connection, send any number of commands to the server, process the responses and finally send a command to terminate the session with a special command (defined in the protocol). 

* The Roulette Protocol uses **TCP** as the underlying transport protocol. The **default port is 1313**.

* **A client session can be in two different states**. In the initial state, the server accepts the following commands: `HELP`, `RANDOM`, `LOAD`, `INFO` and `BYE`. When the client sends the `LOAD` command, the session enters into the other state. In this state, the server reads client data line by line (and interprets each line as the full name of a student to add to the data store) until the `ENDOFDATA` is sent by the client. When this is the case, the session state goes back to the initial state.


* **The protocol defines the following commands**, which are sent by the clients and processed by the servers as follows:

Command | Processing done by the server       | Response               
:---:|--------
`HELP`    | The server retrieves the commands defined by the protocol version. | `Commands: [HELP, RANDOM, LOAD, INFO, BYE]`
`RANDOM`    | The server randomly selects one of the students in its store. | `{'fullname' : 'olivier liechti'}`, where `olivier liechti` is the name of the victim.
`LOAD`    | The server changes the state of the session and starts reading client data line by line until it gets a line with the `ENDOFDATA` string. Every new line is interpreted as the full name of a new student that is added to the data store.  | After receiving the `LOAD` command immediately returns `Send your data [end with ENDOFDATA]`. After receiving `ENDOFDATA`, the server sends back `DATA LOADED`. 
`INFO`    | The server retrieves the protocol version and the number of students currently in the store. | `{'protocol version' : '1', numberOfStudents' : 3}`, where `3` is the number of students currently in the server data store
`BYE`    | The server closes the connection.  | *No response*


### The Roulette Protocol V2

The version 2 of the Roulette Procol specifies minor modifications, as follows:

* the default port is 2613

* the protocol defines the following **additional commands**:

Command | Processing done by the server       | Response               
:---:|--------
`CLEAR`    | The server resets the data store by clearing all students | `DATASTORE CLEARED`
`LIST`    | The server fetches the list of students in the store | `{'students' : [{'fullname' : 'john doe'}, {'fullname' : 'bill smith'}]}`, where the value of students is an array containing all students in the store.

* the protocol **modifies** the following **existing commands** defined in version 1:

Command | Processing done by the server       | Response               
:---:|--------
`LOAD`    | No change in the processing. | `{'status' : 'success', 'number of new students' : 3`}, where 3 is the number of student lines sent by the client.
`BYE`    | No change in the processing. | `{'status' : 'success', 'number of commands' : 12`}, where 12 is the number of commands sent by the client during the session.


## <a name="Tasks"></a>Tasks

### <a name="Task1"></a>Task 1: Study the specification and the code provided for the server

The first step of the lab is for you to **read  the [specifications](#Specifications) of the Roulette Protocol very carefully**. Make sure that you understand what the client can send to the server after being connected, how the server should react and what the client should receive back.

When this is clear, **go through the code** of the [QuizRouletteServer project](QuizRouletteServer). Assuming that you are familiar with the material presented in class and with the various examples, you should be able to grasp the role of the different classes and methods quite easily. You should be able to make the connection between the specification and the code.

**If that is not the case, go back to this material and do some studying. It is not worth trying to progress blindly, otherwise…**

![](images/progressing-blindly.gif)

### <a name="Task2"></a>Task 2: Execute the server and play with it

Execute the server program and make sure that it starts as expected and that it listens on the specified port. Open a terminal window and connect to the server by typing:

```
telnet localhost 1313
```

Once connected, remember that **you are acting as the client**. Hence, it is up to you to send commands by typing them in the terminal window (followed by return). Send commands and validate that you observe is in line with your understanding of the protocol specification. Try to send incorrect commands as well. Also, make sure to you use the `LOAD` command and to create several students.

After your first test, **open several terminal windows** and open several connections in parallel. This will allow you to validate that the server is able to server multiple clients in parallel. Try to load data from the different clients and validate that they **see the same server data**.

Have a look in the [QuizRouletteClient](QuizRouletteClient) folder, read the code in the [client.js](QuizRouletteClient/client.js) file and type the following command and make sure that you understand what is happening:

```
node client.js
```

Finally, **run Wireshark and capture traffic** between your clients and the server. **Inspect the TCP segments** and study the content on the header fields (source address, source port, destination address, destination port). Compare these values for outgoing and incoming traffic. Make sure that you understand the relationship between these header values and how sockets are identified on both sides of the communication channel.

### <a name="Task3"></a>Task 3: Write a client compliant with the protocol specification

**Implement a client compliant with the protocol specification in Java**. You will use this client to **populate the student repository with the content of a file** on your local disk. The client has to implement the following functionality:

1. The client should establish a connection with a Roulette server.
2. The client should send the `INFO` command to get the status of the server. It should process the answer and present it to the user.
2. The client should send a `LOAD` command.
1. The client should read the content of the [RES-1-A.csv](data/RES-1-A.csv) file line by line. The file contains the names of the students attending this course. Therefore, the client should send each read line to the server.
2. When the content of the file has been sent, the client should send the `ENDOFDATA` command.
3. The client should send the `INFO` command and process the result to validate the result and to present it to the user.
4. Finally, the client should send the `BYE` command to close the connection.

### <a name="Task4"></a>Task 4: Extend the server to implement the Roulette Protocol V2

The last task of the lab is for you to **extend the server project** and to **add an implementation of the version 2** of the Roulette protocol. In other words, when you are done the server should be able to work both in V1 and in V2 modes.

Make sure that you have understood the difference between the two protocol versions. Analyze what is being asked and specifcy how you are going to implement it. It will essentially be done by storing some state in new variables and by serializing content in server responses. **This should be a piece of cake!**

![](images/cake.gif)

