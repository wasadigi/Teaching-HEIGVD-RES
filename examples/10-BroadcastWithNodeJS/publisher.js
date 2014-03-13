/*
 This script periodically broadcasts UDP datagrams containing ads. The protocol-specified 
 port and the broadcast address are used as destination values for the datagrams.

 Usage: to start the publisher, type the following command in a terminal:

   node publisher.js

*/

// We have defined the multicast address and port in a file, that can be imported both by
// publisher.js and subscriber.js. The address and the port are part of our simple 
// application-level protocol
var protocol = require('./ad-protocol');

// We use a standard Node.js module to work with UDP
var dgram = require('dgram');

// Let's create a datagram socket. We will use it to send our UDP datagrams 
var s = dgram.createSocket('udp4');

// We have to set the socket in a state where it allows sending broadcast datagrams
// To do that, we need to bind the socket first. We don't care about the port (we trust
// the OS to assign one for us) so we pass 0. We don't care about the network interface,
// so we pass ''
s.bind(0, '', function() {
	s.setBroadcast(true);	
});

// When we publish an ad, we create a UDP datagram and stick the broadcast address
// (255.255.255.255) in the destination address.
function publishAd() {
	var payload = "Buy my Beer, it's refreshing.";
	message = new Buffer(payload);	
	s.send(message, 0, message.length, protocol.PORT, protocol.BROADCAST_ADDRESS, function(err, bytes) {
		console.log("Sending ad: " + payload + " via port " + s.address().port);
	});
}

// Let's publish an ad every 200ms
setInterval(publishAd, 200);
