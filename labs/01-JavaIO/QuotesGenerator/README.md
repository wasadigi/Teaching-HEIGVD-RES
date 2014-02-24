# Quotes Generator

## What Is the Goal of this Tool? 

In order to do the first lab, you will need some text files to process. These text files should be organized in a hierarchical directory structure. I was **too lazy to create test files manually**, and I thought that you would be as well. So, I wrote this simple tool to fetch some content on the Web and to dump it to the local file system.

I have found a nice service, called [iheartquotes](http://iheartquotes.com/), which offers a simple [REST API](http://iheartquotes.com/api) to fetch quotes dynamically. We will talk about REST APIs later, but for now you just have to know that it is possible to send HTTP requests to the web service provided by iheartquotes and to receive a JSON payload with the quote.

## How Can I Use It?

I have written the tool with the Node.js platform, in javascript. To run the script, you will need to install Node.js on your machine (which should take you about 34 seconds). When this is done, you should be able to run:

```
node generator.js
```

At that point, you should have a new directory structure, with a root directory named `./quotes`. In the multi-level structure, you should find 100 quotes (which is a value I have shamelessly hard-coded in the script… I told you I was lazy).

You can then point to this directory in the Java program that you will write as part of the lab.


## How Does It Work?

Have a look at the comments in the script, the code should be pretty straightforward to understand. You will see that with just a couple of lines, I am able to send HTTP requests to a remote server, transform the JSON response into a javascript object and save the quote in the file system.