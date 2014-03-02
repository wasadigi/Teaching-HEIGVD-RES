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

A very simple example of TCP server. When the server starts, it binds a server socket on any of the available network interfaces and on port 2205. It then waits until one (only one!) client makes a connection request. When the client arrives, the server does not even check if the client sends data. It simply writes thebcurrent time, every second, during 15 seconds. To test the server, simply open a terminal, do a "telnet localhost 2205" and see what you get back. Use Wireshark to have a look at the transmitted TCP segments.

### 05-DumbHttpClient

**Keywords**: lecture 2, java, io, TCP, client

This is not really an HTTP client, but rather a very simple program thatestablishes a TCP connection with a real HTTP server. Once connected, the client sends "garbage" to the server (the client does not send a proper HTTP request that the server would understand). The client then reads the response sent back by the server and logs it onto the console.