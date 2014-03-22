# MYPROTOCOL V1.0 Specification


```
Author              : Olivier Liechti, HEIG-VD
Last revision date  : 21.03.2014

Revision history
         22.03.2014 : did this
         23.03.2014 : added that
         24.03.2014 : modified this

```

## 1. Introduction

>*In the introduction, you have to provide a **gentle** introduction for your readers. Why should they be interested to read your specification? Why should they consider implementing your specification? **Your protocol is going to be used by the components of a distributed system**. Make sure that you explain what this distributed system is doing, what services it offers to users, and what are the main issues that its design needs to address.*

>*You have to clearly explain **what is the purpose of your protocol** and **what is the problem** it aims to solve. **Provide some broader context** and do not jump too quickly into the technical details.*

>*At this stage, "**Why is this protocol useful?**" is more important than "How does the protocol work?". Nonetheless, it is helpful to **briefly describe the key protocol concepts and principles**. The readers will then be able to find a detailed description of these principles in following sections.*

>*Last but not least, it is a very good idea to **describe the structure of the document**, in other words to briefly explain what the readers will find the different sections of the document. **This will make a big difference for the readers!***

## 2. Terminology

>*This is a glossary section, where you define the important terms used in your specification. It is obviously criticial to be clear, consistent and precise when writing a specification. Using the same terms throughout the document is therefore important.*

**TERM_NAME**: definition

**TERM_NAME**: definition


## 3. Protocol Overview

>*In this section, you should provide **all the information that is required for the readers to do a high-level design of the software components** that will use your protocol to interact with each other. The readers should have a clear picture of which components are involved, why they need to interact with each other and how they interact with each other.*


### 3.1. System Architecture

>*Insert at least one **component diagram** here, to show a high-level overview of the system. The diagram should show the different types of components, their interfaces and their high-level interactions.*

<center><img width=520 src="images/04/componentDiagram.png"></center>

### 3.2. System Components

>*In this paragraph, you should describe all components in your distributed system. In some protocols, it means that you will describe a client and a server. In other protocols, you will have more components, such as gateways or proxies. Sometimes, it is valuable to include users, or more precisely user roles, in the list.*

>*In the template, we have created a placeholder paragraph that describes one of the components (COMPONENT_NAME_1). You should have as many such paragraphs as there are components in your system (and in the components diagram that you have provided before).*

#### 3.2.1. Component COMPONENT_NAME_1

>*In this paragraph, the component COMPONENT_NAME_1 should be described. **What is its role** in the overall system architecture? What are the **important and interesting aspects** that the readers should be aware of? What is/are the interface(s) that are provided by the component? How do users interact with the component? Typically, it is possible to describe a component with a couple of paragraphs. This is not the place to provide all technical details yet.*

### 3.3. Interactions Between Components

>*Insert one or more **sequence diagrams** here, to show the high-level interactions between the components (request-replies, discovery, etc.). This is not the place to give all the details, but it should give the reader a general understanding of what is happening at runtime. For each sequence diagram, explain the reason for which the components are interacting with each other (i.e. state the purpose of the interaction).*

<center><img width=520 src="images/04/sequenceDiagram.png"></center>



## 4. Protocol Details

>*In this section, you should provide **all the information that is required for the readers to do a detailed design and an implementation of the software components** that will use your protocol to interact with each other. The readers should find a clear description of the communication patterns, the syntax and semantics of messages and of other rules defined by the protocol*.

### 4.1. Transport Protocols and Connections

> *In this paragraph, you should explain which protocols are used to transport application-level messages. You should define the standard port(s) (in the same way that HTTP defines 80 as the standard TCP port). You should also explain how the components start to interact with each other. If a connection is established, then how is it established and what is the procedure that needs to be implemented?*

### 4.2. State Management

>*In this paragraph, you should explain whether your protocol is stageful or stateless. If it is stageful, then you should describe all the states in which an application session can be, what events can happen in each of these states and what are the possible transitions between the states and what happens during these transitions.*

>*Start by showing a state machine diagram, which will give an overview to the readers. Then provide a detailed description of each state.*

<center><img width=320 src="images/04/stateMachineDiagram.png"></center>


#### 4.2.1. State NAME_OF_THE_STATE_1

### 4.3. Message Types, Syntax and Semantic

#### Message MESSAGE_TYPE_1

### 4.4. Miscelaneous Considerations

Reliability, flow control, caching, scalability, content negotiation, 


### 4.5. Security Considerations

## 5. Examples

>*In this section, you should **give examples of interactions between the components** of the distributed system. In previous sections, you have specified detailed rules (messaging patterns, session states, message syntax and semantics, etc.). Developers will use these detailed specifications when they implement your protocol.*

>*But by providing examples in the document, **you will help them in two ways**. Firstly, it will be **easier for them to understand what your protocol does and how**. Analyzing at a concrete dialog between a client and a server is often easier than directly digging into a more abstract definition. Secondly, it will allow them to **confirm their understanding of the protocol specification**. After reading the different rules specified in the previous sections, they will have a way to check that they have understood them correctly.*


## 6. References

