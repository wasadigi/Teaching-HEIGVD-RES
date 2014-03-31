# Lecture 5: Protocol Implementation

## Table of Contents

1. [Objectives](#Objectives)
2. [Lecture](#Lecture)
   1. [Overview of the Application Protocol Toolkit](#Overview)
   1. [Example: the Ping Pong Protocol](#PingPong)
   1. [Implementing the Ping Pong Protocol with Application Protocol Toolkit](#Implementation)
   1. [What's Next?](#WhatNext)
3. [What Should I Know For The Test And The Exam?](#Exam)


## <a name="Objectives"></a>Objectives

In the previous lectures, we have started by **studying core network programming techniques**. We have first learned how to use [IO APIs](01-Lecture1-JavaIOs.md). We have then learned how to use the Socket API to develop client-server applications that use the [TCP](02-Lecture2-TCPProgramming.md) and [UDP](03-Lecture3-UDPProgramming.md) transport protocols. We have **studied how application-level protocols are specified**, what topics are typically covered in RFCs and what techniques can be used to [document](04-Lecture4-ProtocolSpecification.md) these topics. We have proposed a [template](ProtocolSpecificationTemplate.md) for writing our own application-level protocols and used it in the last lab.

In this lecture, we will build on the acquired knowledge and skills to talk about **protocol implementation**. In other words, we will consider the following question: *Given a protocol specification, how do I concretely design and implement a software component that is compliant with the specification?*

To answer the question, we will use an **Application Protocol Toolkit** that was developed specifically for this course. The toolkit is a collection of Java interfaces and classes that make the implementation of protocols easier. We will look at these abstractions to have concrete examples of what needs to be done in a network component. From the concrete code, we will also describe the issues in more generic terms.

**The Application Protocol Toolkit is available [here](../examples/12-ApplicationProtocolToolkit).**


## <a name="Lecture"></a>Lecture

### <a name="Overview"></a>1. Overview of the Application Protocol Toolkit

#### Objectives

**Implementing an application-level protocol implies doing many different things**. There are certainly things to do with the **Socket API**, both to receive and send data to other system components. This generally involves either **multi-threaded** or **asynchronous processing**. There are also things to do around data **parsing**, since system components exchange structured message. The specification may define a **textual format** (possibly based on XML or JSON) or a **binary format**, but in any case there is a need to **serialize** and **deserialize** data. In addition to these fairly low-level tasks, there are also things to do around **messaging patterns**, **session management**, **statement management** and **protocol semantics**. Add **reliability**, **error handling** and **security** on top of these issues and you end up with a fairly complex task.

The goal of the Application Protocol Toolkit is to **provide an extensible skeleton** that illustrates how all of these issues can be dealt with in a structured way. The toolkit was not developed with performance and robustness in mind, as our focus was more on its pedagogic aspects.

[![](images/05/gears.jpg)](http://fineartamerica.com/featured/steampunk-gears-horology-mike-savad.html)


#### Layered Structure

The toolkit has been designed with a **layered structure**, which fosters **[separation of concerns](http://deviq.com/separation-of-concerns)**. Each layer can focus on a specific set of issues and rely on the services provided by the underlying layer. In a course about network programming, [this should not be surprising](http://en.wikipedia.org/wiki/OSI_model). The main important abstractions are shown in the following diagram:

<img width=800 src=images/05/toolkit-overview.png>

* In the **bottom layer**, we are dealing mostly with **data transport issues**. The role of the `Interface Controller` is to use an underlying transport protocol (such as TCP or UDP) and to manage the interactions with other system components. Typically, it is an `Interface Controller` that would bind a Socket and accept incoming connections and messages. The `Interface Controller` is also responsible for keeping track of sessions.

* In the **middle layer**, we are dealing with **syntax** issues (*i.e. what kind of messages do system components exchange with each other?*). The role of a `De-/Serializer` is to transform raw data (`Bytes`) coming from the underlying layer into application-level `Messages`, and vice versa.

* In the **top layer**, we are dealing with **semantic** issues (*i.e. what happens when certain messages are received or when other events happen?*). The role of the `State Machine` is to define the different `States` in which a session can be, what types of `Events` can trigger transitions between these `States` and what `Actions` can be executed in various situations. The role of the `Context` abstraction is to make it possible for the `State Machine` to execute `Actions` (such as sending back a response, starting a timer, updating a state variable, etc.). A different `Context` instance is associated to every `Session`, which makes it possible to forward responses to the right remote component. 

### <a name="PingPong"></a>2. Example: the Ping Pong Protocol

To illustrate how the Application Protocol Toolkit works and can be used to implement client-server applications, let us consider an example: the Ping Pong protocol. As the name implies, do not expect it to be very useful. Nevertheless, it illustrates questions that arise in many real protocols. Let us start with the specification of the protocol.

[![](images/05/pingpong_lady.jpg)](http://notanotherhockeyblog.com/2012/04/17/nashville-shook-by-ping-pong-gate/)


#### Specifications

* The Protocol defines interactions between a **Client** and a **Server**. People use the Client to play a simple game against the Server. They use a "ping" and a "pong" command to move a virtual ball back and forth. To be **successful**, they need to use the commands **in turn** and **rapidly enough**.

* The Server accepts **TCP** connections on port **2904**.

* Once a connection has been established, the Server **reads Client input line by line**. **Every line sent by the client is considered as a Protocol Message**.

* There are 3 types of Messages: **Commands**, **Results** and **Notifications**.

* The Client can send **Commands** to the Server. After processing a Command, the Server always sends back a **Result** to the Client. In some occasions, the Server may also take the initiative and send a **Notification** message to Client (without the need for a previous Command).

* The protocol defines **5 commands**: `ping`, `pong`, `score`, `help` and `bye`. 

* The **syntax** of protocol messages is defined by the following grammar. Note that result messages contain a **status code**, which is 200 if the command sent by the client was valid and 400 otherwise. Also note that the protocol defines a way to pass **arguments** with when sending commands, but that none of the current commands use this possibility.


```
  message               = (commandMessage | resultMessage | notificationMessage) CRLF
  
  commandMessage        = command SP *(argument)
  resultMessage         = "[RESULT]: " statusCode SP result
  notificationMessage   = "[NOTIFY]: " TEXT
  
  command               = "ping" | "pong" | "score" | "help" | "bye"
  argument              = TEXT
  statusCode            = 200 | 400
```

* A session can be in one of the following **states**: `STATE_START`, `STATE_PING`, `STATE_PONG`, `STATE_END` shown in the following state machine diagram:

<img width=800 src=images/05/state-diagram.png>

* The protocol is **stateful**. The session starts once the TCP connection has been established and lasts until the client sends the `Bye` command or the TCP connection closes.

* When the connection is established, the session transitions into `STATE_START` and then immediately into `STATE_PING`. It stays in `STATE_PING` until the client sends the `pong` or the `bye` command. In the first case, it transitions into the `STATE_PONG` (waiting for a `ping` command). In the second case, it transitions into `STATE_END`.

* When the session is in `STATE_PING` or in `STATE_PONG` and the client does not send a valid command within 3 seconds, the server sends a notification to the the client. A counter is also increased.

* When the session is in `STATE_PING` and the client sends a `pong` command, the number of "misses" is increased by 1. When the client sends a `ping` command, the number of "successes" is increased by 1. When the session is in `STATE_PONG` the opposite rules are applied.

* When the session is in `STATE_PING` or in `STATE_PONG` the client can send the `help` and the `score` commands. The server returns results that respectively provide a description of accepted commands or the current score.



* Scenario

The following interaction between a client and a server illustrates how the protocol can be used in practice and how the server behaves when it receives commands and when time passes.


```
Session setup
001  Server: -- accepts connection requests on TCP port 2904 --
002  Client: -- makes a connection request --
003  Server: notification: Welcome to the Ping Pong Server. Do you want to ping?

Service usage
004  Client: ping
005  Server: [RESULT]: 200 Nice one!
006  Client: ping
007  Server: [RESULT]: 200 Ooops…
008  Client: ping
009  Server: [RESULT]: 200 Ooops…
010  Client: pong
011  Server: [RESULT]: 200 Nice one!
012  Client: score
013  Server: [RESULT]: 200 Missed: 2 Successful: 2 Late: 0
014  Client: -- is inactive for 3 seconds
015  Server: [NOTIFY]: You have been inactive for too long!
014  Client: -- is inactive for 3 more seconds
016  Server: [NOTIFY]: You have been inactive for too long!
014  Client: -- is inactive for 3 more seconds
017  Server: [NOTIFY]: You have been inactive for too long!
018  Client: score
019  Server: [RESULT]: 200 Missed: 2 Successful: 2 Late: 3
020  Client: jadkjakldsfjk
021  Server: [RESULT]: 400 Invalid command

Session termination
022  Client: bye
023  Server: [RESULT]: bye bye, nice playing with you
024  Server: [NOTIFY]: closing the connection…
025  Server: -- close connection --
026  Client: -- close connection --
```


### <a name="Implementation"></a>3. Implementing the Ping Pong Protocol with Application Protocol Toolkit

Implementing a Ping Pong server is fairly easy with the Application Protocol Toolkit. Here is what needs to be done:

* The protocol specification defines a number of values, such as the default TCP port, the types of messages, the status codes, etc. We have defined a `PingPongProtocol` class to define these values as **constants** and **enumerated types**.

* At the **transport layer**, we need an `IInterfaceController`. The protocol specification tells us that we need to use TCP and that we have to read messages line by line. It turns out that the Toolkit provides us with a `TcpLineInterfaceController` class that does exactly what we need. We have decided to subclass it and to define a `PingPongInterfaceController` to have a complete set of abstractions in our `ch.heigvd.res.toolkit.pingpong` package.

* At the **syntactic layer**, we need a `IProtocolSerializer` to handle the conversion between wire-level data and application-level messages. We have implemented the class `PingPongProtocolSerializer` for that purpose. The protocol specification tells us that messages have a very simple structure, so parsing data is not difficult. 

* At the **semantic layer**, we need a `IStateMachine` that defines how the server should react to different types of events. Most events are triggered when a message has been received from the client, but there are others (invalid messages have been received, the client has been idle for too long, etc). We have extended the `AbstractStateMachine` class and implemented the `PingPongProtocolStateMachine` class. This is where we implement the game logic and where we decide what to do and how to respond when the client sends us messages.

* Finally, we have implemented the `PingPongProtocolHandler` which is used to connect the various components together. The `main()` method in the `ApplicationProtocolToolkit` class creates the objects and starts the server.


### <a name="WhatNext"></a>4. What's Next?

Depending on the protocol that you want to implement with the Application Protocol Toolkit, there are a number of things that you might consider:

* Implement a `IInterfaceController` that uses **UDP instead of TCP**. Even without a transport-level connection, it is possible to manage sessions at the application level. For instance, the datagrams carrying the protocol messages may also contain a session id. This would allow the `IInterfaceController` to detect new sessions and to signal the arrival of messages to the correct state machines. Even without an explicit session id, the `IInterfaceController` may identify sessions based on the source IP address and port (in other words, it may assume that datagrams coming from the same source all belong to the same session).

* Implement a `IInterfaceController` that uses **an application-level protocol to transport messages instead of TCP**. Think of e-mail for example: it would be possible to use a mailbox to support the interaction between a server and its clients. The clients would send e-mails containing protocol messages to that mailbox. The server would periodically check for new e-mails, extract the commands and notify the state machine. Protocol responses could also be sent by e-mail.

* Implement a `IProtocolSerializer` that **parses and generates JSON or XML**. In the Ping Pong protocol, the structure of the messages is very simple. Many application-level protocols use human-readable formats to encode messages and JSON has clearly gained a lot of popularity. If your protocol uses JSON to encode its messages, then using a library like [jackson](https://github.com/FasterXML/jackson) would be the way to go.




