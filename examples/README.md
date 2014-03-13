# Examples

## Lecture 1: Java IOs

### 01-BufferedIOBenchmark

**Keywords**: lecture 1, java, io, performance, file system

A Java project that illustrates basic IO processing. Different methods to write and read bytes to a file are implemented and compared. Running the program shows big performance differences, depending on how bytes are processed (byte by byte or in blocks, with a buffered stream or not).

### 02-FileIOExample

**Keywords**: lecture 1, java, io, file, filters, read loop, write loop

A Java project that shows the basics of IO processing. The example shows how to read and write bytes from/to a file. The example also shows how to write custom subclasses of `FilterInputStream` and `FilterOutputStream`, to add logic when processing bytes. The example also shows how to use an ByteArrayOutputStrean to build an array without knowing its size at the beginning of the process.

### 03-CharacterIODemo

**Keywords**: lecture 1, java, io, encoding, unicode, ByteArrayOutputStream, OutputStreamWriter

A Java project that shows how to use character encodings in Java. It shows that while Java uses Unicode once characters or Strings are created in memory, a translation needs to happen when bytes are converted into characters, and the other way around. The program also highlight typical problems that arise if the developer does not control character encodings. Problems that manifest themselves by seeing '?' or other strange characters appear in text messages.

## Lecture 2: TCP Programming

### 04-StreamingTimeServer

**Keywords**: lecture 2, java, io, TCP, server

A very simple example of TCP server. When the server starts, it binds a server socket on any of the available network interfaces and on port 2205. It then waits until one (only one!) client makes a connection request. When the client arrives, the server does not even check if the client sends data. It simply writes the current time, every second, during 15 seconds. To test the server, simply open a terminal, do a `telnet localhost 2205` and see what you get back. Use Wireshark to have a look at the transmitted TCP segments.

### 05-DumbHttpClient

**Keywords**: lecture 2, java, io, TCP, client

This is not really an HTTP client, but rather a very simple program that establishes a TCP connection with a real HTTP server. Once connected, the client sends "garbage" to the server (the client does not send a proper HTTP request that the server would understand). The client then reads the response sent back by the server and logs it onto the console.

### 06-PresenceApplication

This project contains the code of both the server and the client for a custom application protocol. When the server starts, it listens on port 9907. The server keeps track of connected clients in a data structure (a list). Each time a client connects or disconnects, the other clients are notified.


### 07-TcpServers

**Keywords**: lecture 2, java, io, TCP, server, threads, runnable, workers

This example compares two "echo" servers. The first one is single-threaded and is able to process only one client at the time (other clients have to wait until the previous one is done). The second one is multi-threaded.

### 08-TcpServerNode

**Keywords**: lecture 2, Node.js, io, TCP, server, single-thread, non-blocking, asynchronous, callback

This example implements a simple TCP server, capable of servicing multiple servers with a single process and a single thread. Start it with the `node server.js` command.


## Lecture 3: UDP Programming

### 09-Thermometers

**Keywords**: lecture 3, Node.js, UDP, client, server

This example demonstrates a simple client-server application implemented in Node.js, which implements a simple protocol. Two scripts are provided (a third one defines constants for the protocol). Running the station.js scripts will simulate a *data collection station* that joins a multicast group and listens for UDP datagrams. Running the thermometer.js script will simulate a *smart thermometer* that publishes a tempurature measurement on a periodic basis. Try to run several thermometers in parallel and observe that the measures are received and reported by the data collection station.

### 10-BroadcastWithNodeJS

**Keywords**: lecture 3, Node.js, UDP, client, server, broadcast

This example demonstrates how to implement a broadcast protocol in Node.JS. The `publisher.js` script periodically broadcasts datagrams (putting the `255.255.255.255` broadcast address and the application-specific port in the destination fields). The `subscriber.js` script binds a datagram socket on the application-specific port and prints the content of every received datagram on the console.