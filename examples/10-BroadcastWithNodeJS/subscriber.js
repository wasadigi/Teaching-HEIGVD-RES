/*
 This script binds a datagram socket on the protocol-specified port and listens for
 broadcasted advertisments. Whenever a new ad is received, a message is printed on the
 console.

 Usage: to start the subscriber, type the following command in a terminal:

   node subscriber.js

*/

// We have defined the multicast address and port in a file, that can be imported both by
// publisher.js and subscriber.js. The address and the port are part of our simple 
// application-level protocol
var protocol = require('./ad-protocol');

// We use a standard Node.js module to work with UDP
var dgram = require('dgram');

// Let's create a datagram socket. We will use it to listen for datagrams published in the
// multicast group by thermometers and containing measures
var s = dgram.createSocket('udp4');
s.bind(protocol.PORT, function() {
  console.log("Listening for broadcasted ads");
});

// This call back is invoked when a new datagram has arrived.
s.on('message', function(msg, source) {
	console.log("Ad has arrived: '" + msg + "'. Source address: " + source.address + ", source port: " + source.port);
});
