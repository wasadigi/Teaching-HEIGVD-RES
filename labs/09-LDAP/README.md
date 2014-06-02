# Lab 9: LDAP

## Table of Contents

1. [Introduction](#Introduction)
1. [Tasks](#Tasks)
1. [Deliverables](#Deliverables)

# <a name="Introduction"></a>Introduction

Almost every company and organization uses an LDAP directory at some point in time. The typical use case is to use it to store the contact details and credentials of employees. A task that is quite common, especially in companies of a certain size, is to **import** data into the directory or to **export** data from the directory. 

Be aware, however, that "importing data" requires often more than simply clicking on an import button. Very often, "importing data" first requires some **data preparation and transformation**. As an IT staff member, you are likely to one day receive some raw data (which could be an extraction from a relational database, from an XML feed) and be asked to "find the way to feed it into the corporate directory".

This is what this lab is all about.

# <a name="Tasks"></a>Tasks

Here is what you are requested to do:

* As a **starting point**, you have an **empty directory** (with no predefined structure for the Directory Information Tree (**DIT**)) and a Comma-Separated Values (**CSV**) file containing information about **people** belonging to the HEIG-VD organization. Your **first task** is actually to generate this input data. To do that, open the [LdapDataGeneration](LdapDataGeneration) project in Netbeans, run it and get the `users.csv` file.

* Your **second task** is to **analyze the content and the structure of the CSV file**, to understand what kind of attributes are defined for the personal records. Based on this analysis, you then have to **define the structure of the DIT for your directory**. In other words, you have to define which container nodes you will use under the root of the tree, which types of leaf nodes you will create in the container nodes and what attributes you will define for these leaf nodes. At this stage, you also have to define **how you want to map the values (columns) in the CSV file to entry attributes into the DIT**.

* Your **next task** is to define the procedure for doing the data preparation and import. You should consider two approaches for this. The first approach would be to write a program that parses the CSV file and that uses an LDAP library and API (such as JNDI in Java) to interact with the directory server. The second (and recommended, for performance reasons) approach would be to write a program that parses the CSV file and that generates an LDIF file based on the mapping rules you have defined in the previous task. Once this LDIF file has been generated, it can be fed into the LDAP directory by using its "Import" feature. Note that the program can be written in any language and does not require any LDAP library.

* Once you fully have analyzed the problem and designed the solution, you can move to the implementation phase. This first means doing an installation and basic configuration of the **OpenDJ** LDAP directory. This then means implementing the program that does the data preparation and import. If you decide to use the LDIF approach, **be sure to check the output (result) when you invoke the import feature of OpenDJ**. It will tell you if entries have been successfully imported or not, and why.

* Once the data has been imported, you are requested to experiment with **LDAP filters**. An LDAP filter is the equivalent to a SQL query. In other words, it is an expression that you submit to an LDAP server to search for some information (entries and attributes). You are requested to write and submit filters to answer the following questions (for each question, give the LDAP filter, the command that you have used to send the query to the server and the result that you have obtained):

  * What is the **number** (not the list!) of people stored in the directory?
  * What is the **number** of departments stored in the directory?
  * What is the **list** of people who belong to the TIC Department?
  * What is the **list** of students in the directory?
  * What is the **list** of students in the TIC Department?
  
**NOTE:** Even if you start by using a LDAP browser (such as Apache Directory Studio), you **have to** use the command line to submit the LDAP queries (i.e. use `ldapsearch`).

* The last task for the lab consists of learning about dynamic groups and how to use them. Firstly, read the [OpenDJ documentation](http://opendj.forgerock.org/opendj-server/doc/admin-guide/index/chap-groups.html) and this [paragraph](http://opendj.forgerock.org/opendj-server/doc/admin-guide/index/chap-groups.html#dynamic-groups) more specifically. When you have understood how to define dynamic groups and how to write LDAP filters based on them, answer the following questions:
 
  * What command do you run to **define a dynamic group** that represents all members of the TIN Department?
  * What command do you run to **get the list of all members of the TIN Department**?
  * What command do you run to **define a dynamic group** that represents all students with a last name starting with the letter 'A'?
  * What command do you run to **get the list** of these students?

# <a name="Deliverables"></a>Deliverables

* The `users.csv` file that you have generated on your machine.
* A report that provides the following information:
  * a diagram that represents the structure of your DIT and a textual description for it;
  * a description of how you have mapped the content of the CSV file onto the content of the DIT;
  * a description of the procedure you have designed and implemented to do the data preparation and import;
  * the source code for your program;
  * a screenshot that shows the result of the import procedure (OpenDJ screenshot if you used the `import` feature or command output)
  * the list of LDAP filters requested above, the commands you have typed to submit them to the server and the results you have obtained.
  * the list of commands you have used to define the dynamic groups and to obtain the list of their members (with the result).




