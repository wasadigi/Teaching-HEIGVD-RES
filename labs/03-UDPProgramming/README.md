# Lab 3: UDP Programming

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
3. [Evaluation](#Evaluation)
6. [Tasks](#Tasks)
    1. [Task 1: Study the provided Node.js project](#Task1)
    1. [Task 2: Reimplement both the thermometer and the station in Java](#Task2)
    1. [Task 3: Task 3: Extend Your Client and Your Server to Add a Kill-Sensor Feature](#Task3)

## <a name="Introduction"></a>Introduction

The goal of this lab is to **get familiar with UDP programming in Java**, by working both with a server and a client. We will consider a **custom application-level protocol**, which is used to publish and collect sensor data over an IP network. Multiple sensors (the *thermometers*) publish measurements in UDP datagrams. A data collection node (the *station*) captures and processes these measurements.

You can work in pairs, so that you can discuss while you go through the tasks. Please make use of your two computers. At some point, try to run the client on one computer and the server on the other. Validate that you are able to communicate across the network. However, **be aware that multicast traffic is filtered on the HEIG-VD network**. You will thus observe a different behavior:

* if you run both the client and the server on the same machine
* if your run the client and the server on different machines, connected to the HEIG-VD network
* if you run the client and the server on different machines, connected to the Lab network
* if you run the client and the server on an ad-hoc wifi network (or at home)


## <a name="Objectives"></a>Objectives

After this lab, you should be able to:

* **Use Wireshark to analyze the flow of data** exchanged by a client and server, over UDP.

* **Analyze the behavior of a distributed system** generating this data, by observing console output and stuying the provided code.

* **Implement a UDP client and a UDP server in Java**, first implementing a **fire-and-forget messaging pattern** and then implementing a **request-reply messaging pattern**.


## <a name="Evaluation"></a>Evaluation

* There is **no grade for this lab**. 
* **No report to write** and submit either.
* I will however ask some of you to **present their solution in front of the class**.
* For that reason, you should be ready with:
  * **Working code in your IDE**. You should be able to walk through the code and explain how you have implemented the various functions. I expect you to run a live demo and to present the results. The demo should be "**end-to-end**", in other words it should start with the generation of the test data files.
  * **A few slides that explain what you have implemented and how**. You should write and use these slides to help you structure your explanations. I would like to see that you have understood the different sub-problems to solve (in order to implement the complete solution). I would also like to see how you have actually solved them.


## <a name="Tasks"></a>Tasks

### <a name="Task1"></a>Task 1: Study the provided Node.js project

The project is located in the [09-Thermometers](../../examples/09-Thermometers) project. Open two terminal windows, move to the project directory and type the two following commands (replace `oliechti` with your name in the second command):

```
node station.js
```

```
node thermometer.js oliechti 20 2
```

Observe what is printed in the two terminal windows. Capture some traffic with Wireshark. Use these evidences to help you analyze and understand the code in the 3 Node.js scripts. Make sure that you are able to *explain* how the application-level protocol works and how it uses multicast. Make sure that you are able to *explain* the role of each of the 3 scripts.

Based on your understanding, what should happen if you were to open a third terminal window and type the following command?

```
node thermometer.js hell 320 12
```

Do it and confirm that what you anticipated is what you observe. You should be able to draw a topology of the network, showing the different nodes, their types and the flow of messages.

### <a name="Task2"></a>Task 2: Reimplement both the thermometer and the station in Java

**Create a new Netbeans project** and implement the functionality of the Node.js scripts using the `java.net` package classes. You should implement the functionality of both the thermometer and of the station.

**Define a test procedure and validate that your implementation is correct and *interoperable***. In other words, you should not only validate that your client is able to communicate with your server. You should also validate that your Java client is able to communicate with the Node.js server, and that your Java server is able to communicate with the Node.js client.

**Be aware and cautious about one point**:

* In principle, the operating system assumes that every UDP port should be under the control of a single listening process. When the operating system receives an IP packet and opens it to find a UDP datagram, it looks at the destination port and based on a mapping table figures out to which process it should deliver the datagram.

* That is the default behavior of network programs written in Java. If you try to bind a socket on port that is already allocated to another process, you will get a nice and pretty `java.net.BindException: Address in use`.

* ** For this reason, you would think that it is not possible to run both the Node.js and the Java station at the same time. Indeed, you should *not* do that, except for testing purposes.
**

* However, Node.js behaves differently (and the behavior may depend on the operating system). In some cases, it is interesting to have several processes (of the same application) listening on the same port. Some operating systems make it possible to work in this mode, with the special socket option `SO_REUSEPORT`. If you are interested in the details, have a lot at [this](), [this](http://freeprogrammersblog.vhex.net/post/linux-39-introduced-new-way-of-writing-socket-servers/2) and [this](http://stackoverflow.com/questions/14388706/socket-options-so-reuseaddr-and-so-reuseport-how-do-they-differ-do-they-mean-t).

* In summary: if you observe that you system does not work as expected, **check what processes are listening on the UDP port**(s) used in your applications. By the way, do you know the following command?

```
lsof -i
```


### <a name="Task3"></a>Task 3: Extend Your Client and Your Server to Add a Kill-Sensor Feature

The goal of this task is for you to implement a messaging pattern, in which **the recipient of a message needs to contact the sender**. Typically, this would be to send a reply to the sender. Here, it will be to brutally kill the sender. *Don't even think about asking me why someone would come up with an idea like that…*

Here is the expected behavior:

* **The station listens on for published messages**, containing temperature measurements. As you remember, the UDP datagrams encapsulating these messages have the IP address and UDP port of the emitting thermometer in the source address and source port header fields.

* **Whenever the station receives a message from a thermometer**, it should send back a datagram containing the `KILL` message (which is something that is defined in our application-specific protocol).

* **The thermometers should not only send, but also listen for** UDP datagrams. Not those containing temperature measurements, but those containing `KILL` messages. When a thermometer receives one of these messages, it should print a "Arghhhh!" message on the console and terminate.

**Modify your Java project and implement this behavior.**

