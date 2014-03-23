# Lab 4: Protocol Specification

## Table of Contents

1. [Introduction](#Introduction)
2. [Objectives](#Objectives)
2. [Procedure and Tasks](#ProcedureAndTasks)
3. [Evaluation](#Evaluation)

## <a name="Introduction"></a>Introduction

In this lab, **you will not be writing any code**. Instead, you will **write a specification** for a custom application-level protocol, starting from an [informal description](#Story) of the problem that the protocol is supposed to solve.

But don't worry: in a follow-up lab, you will have the pleasure to do an implementation of your specification. So you have to make sure that what you produce this week is clear and detailed enough to make your life easier in the future!


## <a name="Objectives"></a>Objectives

The **first objective** of the lab is to **learn how to write a protocol specification** by doing it. In the [lecture](../../lectures/04-Lecture4-ProtocolSpecification.md), we have seen the types of questions that need to be addressed and the techniques that can be used to define the protocol rules. We have seen a [template](../../lectures/ProtocolSpecificationTemplate.md) for authoring protocol specifications. In this lab, you will put all of this in practice. 

The **second objective** of the lab will be to go through an **analysis and design process**. Starting from a high-level and informal description of **what** a particular system does, you will have to propose a **distributed software architecture** for this system. You will have to **identify the components** that constitute the system and define their **role**.

The **third objective** of the lab will then be to **define a set of rules** to specify how these components should interact with each other. In other words, the goal will be to **identify what elements need to be specified** in an **application-level protocol** for the system. Last but not least, you will have to **define a set of rules**, so that the components can find each other, contact each other and communicate with each other. In other words, you will have to **identify the elements of your protocol and capture them in your specification document**.


## <a name="ProcedureAndTasks"></a>Procedure and Tasks

### Monday afternoon

1. **Read and study [The Story](#Story)**, individually, and make a first sketch of the **system architecture** that would support the scenario. (10')

2. Write down a **list of the questions** that you think you will need to define in the **protocol** (focus on the protocol, not on the implementation of the components). (10')

3. Form **groups of 4 students** and get together around a table. (2')

4. **Share** your respective designs and lists of questions; discuss and produce a synthesis in a couple of slides. (20')

5. Propose a way to **split the specification into two parts** (by identifying 2 groups of related questions). **Split the groups into 2 pairs of students**. Decide which part of the specification is going to be written by which pair. (5')

6. **Be ready to do a 5' presentation in front of the class**.


### Before Thursday morning

1. **[Fork](https://help.github.com/articles/fork-a-repo)** the following repository on Github. Forking means that you will have **your own copy of the repo hosted on Github**. You will be able to push your local commits to your fork (working in a team). I will be able to access your work. (5')

2. After you have created the fork, you will need to **clone it on your local machine** (make sure that you do not clone *this* repository, but that you clone your own fork!). (5')

3. **Validate your setup** by doing commit, push and pull operations with your fork. You should do that in pairs, to confirm that you are able to work collaboratively on the same content. (15')

4. Go through the [POP3](http://tools.ietf.org/html/rfc1225), [HTTP](https://tools.ietf.org/html/rfc1945), [SMTP](http://tools.ietf.org/html/rfc2821) and [IRC](https://tools.ietf.org/html/rfc2810) specifications. You don't have to read the entire documents, but you should i) analyze their structure (table of contents), ii) read the introduction and iii) browse through the sections that seem the most relevant for helping you achieve the objectives of the lab. (60')


### Starting on Thursday Morning...

1. **Design your protocol by defining the rules that will govern interactions between the system components**. As a suggestion, start by sketching out interaction scenarios (i.e. consider use cases). From these concrete dialogues, you will then be able to generalize and formalize the elements of the protocol. (60')

1. **Write the specification** for your protocol. You can use the proposed specification [template](../../lectures/ProtocolSpecificationTemplate.md) presented in the lecture and the various documentation and modeling tools. But feel free to adapt them depending on your needs. (120')

2. When writing the specification, **you should always ask yourself**: if I give this specification to someone who will implement a client or a server, will have they all the information they need? Is everything clear and non ambiguous? You should also ask yourself: if the client and the server are implemented by two different people, am I sure that they will be able to interact with each other smoothly?

## <a name="Evaluation"></a>Evaluation

* There is a **BONUS GRADE** for this lab: the group which produces the best specification will get an extra lab grade (**most probably a 6.0**, but that will ultimately depend on the quality).

* On **early Monday morning (31.03.2014)**, I will look at all the 16 GitHub forks and go through the specification documents. I will select between 2 and 4 groups, who I will ask to do a 5' presentation in the afternoon.

* Judging both the document and the presentation, I will select the best group and award the bonus grade.


## <a name="Story"></a>The Story

Peter is a 2nd year CS student. 

Today, when he arrived at school at 8 AM, he took his "**Smart Calculator**" out of his bag and turned it on. 

After a few seconds, he saw on the screen that 3 "**Compute Engines**" were available. The first one was labeled "Maguro (private)", the second one was labeled "Saba (public)" and the third one was labeled "Sake (public)".

Peter selected "Saba" and was presented with a list of 3 "**Compute Functions**": `ADD(x,y)`, `MULT(x,y)` and `MIN(…)`. He first selected `ADD(x,y)`. At this point, he was told that the function would compute the sum of 2 numeric values. He was asked to provide 2 values in an entry form. After submitting these values, he got back the result (the sum of the two values). He then selected `MIN(…)` and was told that the function would find the minimum value in a list of numeric values. He then saw a form where he could enter a value in a field, and where he had the choice between 2 buttons: "Compute result" and "Add value". Clicking on "Add value" cleared the entry field (but updated a label, showing the number of previously entered values). After entering 5 values, Peter clicked on "**Compute result**" and was presented with the result.

Peter then noticed that **a 4th "Compute Engine" had appeared in the list**, labeled "Ika (private)". He selected it and was presented with a form stating that an **authentication was required**. In the form, there were a "user id" and a "password" fields. There were also two buttons labeled "**Login**" and "**Register**". Since Peter had never used "Ika", he clicked on "Register". He was presented with a form where he had to enter a user id, an e-mail address and a password. After clicking on a "Submit" button, Peter was notified that his account had been created. Redirected to the login form, he entered his user id and his password and clicked on the "Login" button. 

At this point, Peter was presented with a list of 4 "functions": `ADD(x,y)`, `SIN(X)`, `SUM(…)` and `TOUPPERCASE(s)`. He selected the last one, was told that the function would convert a text parameter to its uppercase value. He entered "I love protocols" in an entry field, clicked on the "Compute result" button and was presented wit the result: "I REALLY, REALLY LOVE PROTOCOLS".

At noon, Peter was happy. The "Smart Calculator" does not do much, but it is doing it in such a cool way! From a documentation page on the University Intranet, he knew that he had the possibility to operate his own "Compute Engine" and to provide his own "Compute Functions". 