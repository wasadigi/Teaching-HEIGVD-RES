# Examples

### 01-BufferedIOBenchmark

**Keywords**: lecture 1, java, io, performance, file system

A Java project that illustrates basic IO processing. Different methods to write and read bytes to a file are implemented and compared. Running the program shows big performance differences, depending on how bytes are processed (byte by byte or in blocks, with a buffered stream or not).

### 02-FileIOExample

**Keywords**: lecture 1, java, io, file, filters, read loop, write loop

A Java project that shows the basics of IO processing. The example shows how to read and write bytes from/to a file. The example also shows how to write custom subclasses of `FilterInputStream` and `FilterOutputStream`, to add logic when processing bytes. The example also shows how to use an ByteArrayOutputStrean to build an array without knowing its size at the beginning of the process.

### 03-CharacterIODemo

**Keywords**: lecture 1, java, io, encoding, unicode, ByteArrayOutputStream, OutputStreamWriter

A Java project that shows how to use character encodings in Java. It shows that while Java uses Unicode once characters or Strings are created in memory, a translation needs to happen when bytes are converted into characters, and the other way around. The program also highlight typical problems that arise if the developer does not control character encodings. Problems that manifest themselves by seeing '?' or other strange characters appear in text messages.