# Lecture 4: Specifying an Application-Level Protocol

## Table of Contents

1. [Objectives](#Objectives)
2. [Lecture](#Lecture)
   1. [What Is An Application-Level Protocol?](#WhatIsAnApplicationLevelProtocol)
   1. [What Do Protocol Specifications Look Like?](#WhatDoProtocolSpecificationsLookLike)
   1. [Documentation and Modeling Techniques](#DocumentationAndModelingTechniques)
      1. [Textual Descriptions](#TextualDescriptions)
      1. [Grammars](#Grammars)
      1. [Component Diagrams](#ComponentDiagrams)
      1. [Sequence Diagrams](#SequenceDiagrams)
      1. [State Machine Diagrams](#StateMachineDiagrams)
      1. [Scenarios and Examples](#ScenariosAndExamples)
   1. [A Template for Your Application-Level Protocol Specifications](#Template)

3. [Resources](#Resources)
   1. [MUST read](#ResourcesMustRead)
   2. [Additional Resources](#ResourcesAdditional)
4. [What Should I Know For The Test And The Exam?](#Exam)


## <a name="Objectives"></a>Objectives

The goal of this lecture is to look at what is typically found in application-level protocol specifications, **so that you equipped to write specifications for your own protocols**. 

For this purpose, we will **consider concrete examples** and examine **several RFC documents**. This will show that while there is no standard template for writing application-level protocol specifications, some aspects are almost always described, in one way or another.

In this lecture, we will also **go through a number of modeling and documentation techniques** (including different types of diagrams) that are very useful when writing a specification. You should be familiar with most of them, having seen them in other contexts (e.g. in software engineering courses). But one goal of this lecture is to show that they are valuable when **describing the behavior of distributed systems**.

Based on the previous topics, we will finally **propose a [template](ProtocolSpecificationTemplate.md) for writing protocol specifications**. It is not meant to be a rigid document structure and you should feel free to adapt it to your needs (depending on the protocol scope, sections may need to be added or adapted). **The goal of the template is to make it easier for you to start writing your specification, compared to starting from a blank page**.

## <a name="Lecture"></a>Lecture

### <a name="WhatIsAnApplicationLevelProtocol"></a>1. What Is An Application-Level Protocol?

Our goal is to learn how to write an application-level protocol specification. In other words, it is to learn **what elements need to be specified** and **how they should be specified**. *But what is an application-level protocol anyways?* We propose the following definition:

> An application-level protocol is a **set of rules**, which define how the components of a distributed system interact with each other so that a **service** can be fulfilled for the benefit of **one or more users**.

If we take the example of **HTTP**, the protocol specifies *how* web browsers should interact with web servers (using a request-reply communication pattern), so that users can navigate in a collection of hypertext documents. If we take the example of **SMTP**, the protocol specifies *how* mail clients, mail servers and mail transfer agents communicate with each other, so that users can send e-mails to each other across an IP network.

What do we mean by **set of rules**? What do these rules concretely consist of? Here are some of the important and frequent the questions that they aim to answer:

* What are the **different components** in the distributed system. What are their **roles** and what **functions** do they provide?

* How do components **find each other**, before they can start an interaction? Do the clients need to know the address of available of servers (through out-of-band agreement) or does the protocol support **dynamic discovery** of peers?

* Once they have found each other, **how do components start to interact** with each other? Are some components listening for connection requests? Are some components initiating connection requests? How does that happen?

* How do components **exchange messages**? Do they use a **stream**-based approach or a **datagram**-based approach? **Which transport-level protocol(s)** is/are used to exchange messages? Are there **standard ports** that components should use?

* What is the **format**, in other words what is the **syntax**, of the different types of messages exchanged by the components?

* What are the **actions** that should be **triggered** after the reception of different types of messages? In other words, what is the **semantics** of the messages?

* Is the interaction between components **stateful** or **stateless**? Do the messages exchanged between two peers belong to some kind of *connection* or *session*, with an associated state? Or, in the contrary, are the messages exchanged by two components independent from each other?
 
* For **stateful protocols**, what are the **different states** in which a session can be? What are the possible **transitions** between these states? What types of **messages** can be exchanged in these different states? Is there some **data** associated with each of the states?

* How **secure** are the interactions between the different components in the system? Are there things that the components need to do in order to ensure some level of security? 

Most application-level protocol specifications aim to answer these questions in one form or the other. **In addition, each specification addresses issues that are relevant to their particular context**. For example, an e-mail protocol may answer questions about address formats and a peer-to-peer protocol may answer questions about the management of an overlay network. For this reason, when you design a protocol for your own application, you can use the previous list of questions as a **starting point**, but **you should complement it with questions that are specific to your particular application**!

### <a name="WhatDoProtocolSpecificationsLookLike"></a>2. What Do Protocol Specifications Look Like?

If you find yourself in a position to design and specify your own application-level protocol, you will most likely have to consider the list of questions presented above. **But how should you answer these questions in a well structured and easy to read document?** 

A good idea is to **look at what others have done in the past**. Needless to say, there are plenty of open protocol specifications available for study. The IETF web site is a well-known source of information, with a large body of [Internet Drafts](https://www.ietf.org/id-info/) and [Request For Comments](https://www.ietf.org/rfc.html) documents.

If you are not familiar with application-level RFC documents, here are a few suggestions to get you started. These are protocols that we will study later in the course anyways, so you will have to read (*at least part of*) the specifications. But it would also be beneficial for you to have a first look now:

* The [HTTP 1.0](https://tools.ietf.org/html/rfc1945) protocol is defined in RFC 1945 (60 pages). It is a good idea to read the 1.0 version of the protocol before the 1.1 version, because it is much shorter and therefore is a gentle introduction to protocol specifications. What is nice with the HTTP protocol is that it is **TCP- and text-based**, which means that with a terminal and a telnet command, you can easily impersonate an HTTP client and run experiments by crafting and sending your own requests. Other interesting aspects the specification include the fact that it is an example for a **stateless protocol** and that it uses a **BNF grammar **to define the syntax of messages. 

* The [POP3](http://tools.ietf.org/html/rfc1225) protocol is defined in RFC 1225 (16 pages). It defines the interaction between a **mail client** and a **mail server**, for the purpose of retrieving the content of a mailbox. There are several nice things about this specification: it is concise and easy to read, it is an example of a **stateful protocol**, it defines a TCP-, text-based protocol and it defines a protocol that is supported by most mail providers. That makes it an ideal candidate for interactive experimentation.

* The [SMTP](http://tools.ietf.org/html/rfc2821) protocol is defined in RFC 2821 (79 pages). It defines the interactions that take place between **mail clients**, **mail servers** and **other components** for the purpose of **transferring e-mail messages** across the Internet. Like HTTP and POP3, SMTP lands itself very well to interactive experimentation from a terminal. You can use telnet to establish a connection with a mail server and send e-mails. If you do that, you will see that it is possible to specify any value for the `From:` mail header (which shows that it is trivial to spoof e-mails). If you want to experiment with the protocol, you can try to use your existing mail provider. Depending on the security restrictions, you may have to access the server from a given network (e.g. from the HEIG-VD local network when using the HEIG-VD SMTP server). You may also have to authenticate yourself, using one of the supported [authentication methods](http://www.fehcom.de/qmail/smtpauth.html) (by the way, this is a topic covered in several RFCs). If you encounter a server that supports the PLAIN authentication method, the following command (if your replace USERNAME, DOMAIN and PASSWORD values) will be useful to you.

  `perl -MMIME::Base64 -e 'print encode_base64("\0USERNAME\@DOMAIN\0PASSWORD");'`
  
* The IRC protocol, **or rather the IRC protocols**, are defined in [RFC 2810](https://tools.ietf.org/html/rfc2810), [RFC 2811](https://tools.ietf.org/html/rfc2811), [RFC 2812](https://tools.ietf.org/html/rfc2812) and [RFC 2813](https://tools.ietf.org/html/rfc2813). The specification is interesting for many reasons, but one that we would like to mention here is that it provides **a good example for specifying a protocol in several complementary documents**. Initially, all aspects of the protocol were specified in a single document, [RFC 1459](https://tools.ietf.org/html/rfc1459). Later on, the specification was split and different topics are now treated in distinct RFCs. **We will use the same approach in the lab, as it will make it easier to split the specification work between different groups**.

* In the previous lecture, we have already mentioned two interesting protocol specifications. The first one was the specification for the [TFTP](http://tools.ietf.org/html/rfc1350) protocol, the second one was the specification for the [CoAP](http://tools.ietf.org/html/draft-ietf-core-coap-18) protocol.


### <a name="DocumentationAndModelingTechniques"></a>3. Documentation And Modeling Techniques

When writing a protocol specification, you should use a combination of different techniques. Remember: **your objective is to make it as easy as possible for the readers to understand your specification**. Using the following techniques will help you achieve this objective.

In the following paragraphs, we will **look at concrete examples** and see how the **previously mentioned RFCs use a combination of** [textual descriptions](#TextualDescriptions), [grammars](#Grammars), [component diagrams](#ComponentDiagrams), [sequence diagrams](#SequenceDiagrams), [state machine diagrams](#StateMachineDiagrams) and [scenarios](#ScenariosAndExamples).


#### <a name="TextualDescriptions"></a>3.1. Textual Descriptions

The first technique that you will use when writing your specification is… **the production of textual descriptions**. This is perhaps obvious, but it is of course an important part of any protocol specification. When writing your specification, you should think about the following points:

* **Provide context**. If you are writing a technical document, in which you describe a lot of technical details, you have to make it as easy as possible for the reader to go through your document. Start by stating and describing the problem, or sub-problem, that you want to solve.

* Be **very precise** and **consistent**. Use the same terms systematically when describing the elements of your protocol. For this reason, it is a good idea to include a **terminology** section in your specification.

* Be **concise**, but **complete** and avoid **ambiguity**. Remember that different people might be implementing your specification and that all resulting software components should be interoperable. In other words, they should be able to interact with each other, following the rules that you have defined in your protocol. For this reason, it is important that the document **reduces the risk of misinterpretation as much as possible**.



As an example, consider the **IMPS RFC**: [`https://tools.ietf.org/html/rfc2795`](https://tools.ietf.org/html/rfc2795). If you read the abstract, you will immediately understand what the protocol aims to achieve. It is clear, concise and to the point. Despite its publication date, it is *really* worth going through this RFC and use its structure and style as an inspiration.

```
Network Working Group                                     S. Christey
Request for Comments: 2795                         MonkeySeeDoo, Inc.
Category: Informational                                  1 April 2000


               
The Infinite Monkey Protocol Suite (IMPS)


Status of this Memo

   This memo provides information for the Internet community.  It does
   not specify an Internet standard of any kind.  Distribution of this
   memo is unlimited.

Copyright Notice

   Copyright (C) The Internet Society (2000).  All Rights Reserved.

Abstract

   This memo describes a protocol suite which supports an infinite
   number of monkeys that sit at an infinite number of typewriters in
   order to determine when they have either produced the entire works of
   William Shakespeare or a good television show.  The suite includes
   communications and control protocols for monkeys and the
   organizations that interact with them.
```

#### <a name="Grammars"></a>3.2. Grammars

In some protocols, the structure of messages exchanged by components is simple and their number is limited. When that is the case, a textual description may be more than enough to document the protocol. Think of a basic "echo" protocol, based on which a client sends a text string to a server and receives the same text string in return.

In other protocols, however, **system components may exchange different types of messages, which may have a somewhat complex structure involving different parameters and data structures.** When this is the case, using a grammar and a notation such as the [Augmented Backus-Naur Form](http://en.wikipedia.org/wiki/Backus%E2%80%93Naur_Form) (defined in [RFC 5234](http://tools.ietf.org/html/rfc5234)) is a good idea.

As an example, consider these two sections of the **HTTP 1.0 RFC**: [https://tools.ietf.org/html/rfc1945#section-2](https://tools.ietf.org/html/rfc1945#section-2) and [https://tools.ietf.org/html/rfc1945#section-4](https://tools.ietf.org/html/rfc1945#section-4). If you continue to read the RFC, you will see how recursively applying the grammar production rules allows you to get the complete syntax of HTTP requests and responses. **The use of the notation will guide you through a top-down process**, which will allow you to quickly get a global idea of the message structure and then to gradually get more and more details. It is a good thing. 



```
2. Notational Conventions and Generic Grammar

2.1 Augmented BNF

   All of the mechanisms specified in this document are described in
   both prose and an augmented Backus-Naur Form (BNF) similar to that
   used by RFC 822 [7]. Implementors will need to be familiar with the
   notation in order to understand this specification. The augmented BNF
   includes the following constructs:
```


```
4. HTTP Message

4.1 Message Types

   HTTP messages consist of requests from client to server and responses
   from server to client.

       HTTP-message   = Simple-Request           ; HTTP/0.9 messages
                      | Simple-Response
                      | Full-Request             ; HTTP/1.0 messages
                      | Full-Response

   Full-Request and Full-Response use the generic message format of RFC
   822 [7] for transferring entities. Both messages may include optional
   header fields (also known as "headers") and an entity body. The
   entity body is separated from the headers by a null line (i.e., a
   line with nothing preceding the CRLF).

       Full-Request   = Request-Line             ; Section 5.1
                        *( General-Header        ; Section 4.3
                         | Request-Header        ; Section 5.2
                         | Entity-Header )       ; Section 7.1
                        CRLF
                        [ Entity-Body ]          ; Section 7.2

       Full-Response  = Status-Line              ; Section 6.1
                        *( General-Header        ; Section 4.3
                         | Response-Header       ; Section 6.2
```

#### <a name="ComponentDiagrams"></a>3.3. Component Diagrams

**Component diagrams** are useful to **describe the different types of components** in a distributed system, their **interfaces** and their **interactions**. Note that end-users are often considered as components and represented on these diagrams. 

**Component diagrams are particularly useful in systems where there are more than a two component types** (a client and a server). Think of protocols that govern the interactions between users, clients, proxies, gateways, servers, etc. They are also very effective at giving an **immediate overall understanding** of the system to the reader.

As an example, consider Section 2.3 of the **FTP RFC**: [http://tools.ietf.org/html/rfc959](http://tools.ietf.org/html/rfc959). One interesting element of this diagram is that it highlights the fact that there are **two communication channels** established between the client and the server. The first one is used for sending commands, the other one is used for transferring data. **This is an important aspect of the protocol and it is made obvious to the reader in the component diagram**.


```
2.3.  THE FTP MODEL

      With the above definitions in mind, the following model (shown in
      Figure 1) may be diagrammed for an FTP service.

                                            -------------
                                            |/---------\|
                                            ||   User  ||    --------
                                            ||Interface|<--->| User |
                                            |\----^----/|    --------
                  ----------                |     |     |
                  |/------\|  FTP Commands  |/----V----\|
                  ||Server|<---------------->|   User  ||
                  ||  PI  ||   FTP Replies  ||    PI   ||
                  |\--^---/|                |\----^----/|
                  |   |    |                |     |     |
      --------    |/--V---\|      Data      |/----V----\|    --------
      | File |<--->|Server|<---------------->|  User   |<--->| File |
      |System|    || DTP  ||   Connection   ||   DTP   ||    |System|
      --------    |\------/|                |\---------/|    --------
                  ----------                -------------

                  Server-FTP                   USER-FTP

      NOTES: 1. The data connection may be used in either direction.
             2. The data connection need not exist all of the time.

                      Figure 1  Model for FTP Use
```

#### <a name="SequenceDiagrams"></a>3.4. Sequence Diagrams

**Sequence diagrams** are useful to **describe the flow of messages exchanged by two or more system components**. These diagrams make it easy to see what types of messages are exchanged by components, but also the order in which they are exchanged and the actions they trigger. 

You are certainly aware that the [**Unified Modeling Language**](http://www.uml.org/) defines a rich [syntax](http://www.uml-diagrams.org/sequence-diagrams.html) for [sequence diagrams](http://www.agilemodeling.com/artifacts/sequenceDiagram.htm). **Should you use that syntax to specify and document your own protocols?** It depends: sometimes, you will be able to capture the important information simply with one vertical line per component (where the line represents the *lifeline* of the component) and with simple annotated arrows (where an arrow represents the *transfer of a message*). Sometimes, however, you will find it handy to be able to represent **asynchronous calls**, or to make **request-replies patterns** visually explicit.

As an example, consider Section 4 of the **SIP RFC**: [http://tools.ietf.org/html/rfc3261#section-4](http://tools.ietf.org/html/rfc3261#section-4). The diagram shows the **flow of messages** exchanged in a **VoIP system**. It makes **sequences of events**, where one event triggers another one, easy to grasp.

```
RFC 3261            SIP: Session Initiation Protocol           June 2002


                     atlanta.com  . . . biloxi.com
                 .      proxy              proxy     .
               .                                       .
       Alice's  . . . . . . . . . . . . . . . . . . . .  Bob's
      softphone                                        SIP Phone
         |                |                |                |
         |    INVITE F1   |                |                |
         |--------------->|    INVITE F2   |                |
         |  100 Trying F3 |--------------->|    INVITE F4   |
         |<---------------|  100 Trying F5 |--------------->|
         |                |<-------------- | 180 Ringing F6 |
         |                | 180 Ringing F7 |<---------------|
         | 180 Ringing F8 |<---------------|     200 OK F9  |
         |<---------------|    200 OK F10  |<---------------|
         |    200 OK F11  |<---------------|                |
         |<---------------|                |                |
         |                       ACK F12                    |
         |------------------------------------------------->|
         |                   Media Session                  |
         |<================================================>|
         |                       BYE F13                    |
         |<-------------------------------------------------|
         |                     200 OK F14                   |
         |------------------------------------------------->|
         |                                                  |

         Figure 1: SIP session setup example with SIP trapezoid

      INVITE sip:bob@biloxi.com SIP/2.0
      Via: SIP/2.0/UDP pc33.atlanta.com;branch=z9hG4bK776asdhds
      Max-Forwards: 70
      To: Bob <sip:bob@biloxi.com>
      From: Alice <sip:alice@atlanta.com>;tag=1928301774
      Call-ID: a84b4c76e66710@pc33.atlanta.com
      CSeq: 314159 INVITE
      Contact: <sip:alice@pc33.atlanta.com>
      Content-Type: application/sdp
      Content-Length: 142
```


#### <a name="StateMachineDiagrams"></a>3.5. State Machine Diagrams

**State machine diagrams** are helpful when documenting… (surprise, surprise) **stateful protocols**. In stateful protocols, two components establish a connection. As they exchange messages and as time passes, **the connection transitions from one state to another**. Depending on the current state, the component will **expect to receive a specific subset of messages** (e.g. according to some protocols, a server will refuse some commands if the client has not been authenticated yet). **Very often, the reception of a message will both trigger a state transition and generate a response message to the sender**.

Like for sequence diagrams, the Unified Modeling Language defines a [syntax](http://www.uml-diagrams.org/state-machine-diagrams.html) for [state machine diagrams](http://www.agilemodeling.com/artifacts/stateMachineDiagram.htm). Very often, you will not need all of the elements defined in the UML syntax and will have enough with simple rectangles and annotated arrows. If you add well written text to the diagram, both in small annotations and in full paragraphs, you should get a specification that is easy to work with and to implement.

As an example, consider Section 4 of the **IMAP RFC**: [http://tools.ietf.org/html/rfc3501#section-3](http://tools.ietf.org/html/rfc3501#section-3). The diagram shows the limits of ASCII art… line drawings are a bit easier to read in this case! Note that in the RFC, it really is **the combination of the diagram and of the textual description of every state** that provides a clear specification.


```

                   +----------------------+
                   |connection established|
                   +----------------------+
                              ||
                              \/
            +--------------------------------------+
            |          server greeting             |
            +--------------------------------------+
                      || (1)       || (2)        || (3)
                      \/           ||            ||
            +-----------------+    ||            ||
            |Not Authenticated|    ||            ||
            +-----------------+    ||            ||
             || (7)   || (4)       ||            ||
             ||       \/           \/            ||
             ||     +----------------+           ||
             ||     | Authenticated  |<=++       ||
             ||     +----------------+  ||       ||
             ||       || (7)   || (5)   || (6)   ||
             ||       ||       \/       ||       ||
             ||       ||    +--------+  ||       ||
             ||       ||    |Selected|==++       ||
             ||       ||    +--------+           ||
             ||       ||       || (7)            ||
             \/       \/       \/                \/
            +--------------------------------------+
            |               Logout                 |
            +--------------------------------------+
                              ||
                              \/
                +-------------------------------+
                |both sides close the connection|
                +-------------------------------+

         (1) connection without pre-authentication (OK greeting)
         (2) pre-authenticated connection (PREAUTH greeting)
         (3) rejected connection (BYE greeting)
         (4) successful LOGIN or AUTHENTICATE command
         (5) successful SELECT or EXAMINE command
         (6) CLOSE command, or failed SELECT or EXAMINE command
         (7) LOGOUT command, server shutdown, or connection closed
```


#### <a name="ScenariosAndExamples"></a>3.6. Scenarios and Examples

Last but not least, something that is *really* helpful when reading a protocol specification is to **see some concrete examples of how the components interact with each other**. When you write the specification, think of a client interacting with a server. Think about the sequence of concrete requests and responses exchanged during the dialogue. Document them, as they will be very informative, especially when used in conjunction with sequence or state machine diagrams**. Because they are **concrete**, they will be easy to read and will give a clear picture of how the protocol works (*even if not all details are captured in a single scenario*).

As an example, consider Section 10 of the **POP3 RFC**: [http://tools.ietf.org/html/rfc1939#section-10](http://tools.ietf.org/html/rfc1939#section-10).


```
10. Example POP3 Session

      S: <wait for connection on TCP port 110>
      C: <open connection>
      S:    +OK POP3 server ready <1896.697170952@dbc.mtview.ca.us>
      C:    APOP mrose c4c9334bac560ecc979e58001b3e22fb
      S:    +OK mrose's maildrop has 2 messages (320 octets)
      C:    STAT
      S:    +OK 2 320
      C:    LIST
      S:    +OK 2 messages (320 octets)
      S:    1 120
      S:    2 200
      S:    .
      C:    RETR 1
      S:    +OK 120 octets
      S:    <the POP3 server sends message 1>
      S:    .
      C:    DELE 1
      S:    +OK message 1 deleted
      C:    RETR 2
      S:    +OK 200 octets
      S:    <the POP3 server sends message 2>
      S:    .
      C:    DELE 2
      S:    +OK message 2 deleted
      C:    QUIT
      S:    +OK dewey POP3 server signing off (maildrop empty)
      C:  <close connection>
      S:  <wait for next connection>
```

### <a name="Template"></a>4. A Template for Your Application-Level Protocol Specifications

If you read various Internet Drafts and RFC documents, you will see that **there is not a unique way to structure them**. The table of contents is always a bit different, even if several common topics are treated in one way or another.  Nevertheless, some questions are almost always treated in one way or another. These are the questions that we have enlisted before. 

**So, if you are in a situation where you need to write a specification for your own protocol, how should you proceed?** Starting from a blank page can be a bit intimidating...

To make your life easier, [here](ProtocolSpecificationTemplate.md) is a **proposed template that you can use as a starting point** to write your own protocol specifications. **You will have to adapt it** according to your context and you needs. Don't be afraid to add sections and to move things around if you think that it makes the document easier to read.


## <a name="Resources"></a>Resources</a>

### <a name="ResourcesMustRead"></a>MUST read

### <a name="ResourcesAdditional"></a>Additional resources


## <a name="Exam"></a>What Should I Know For The Test and The Exam?

Here is a **non-exhaustive list of questions** that you can expect in the written tests and exams:

