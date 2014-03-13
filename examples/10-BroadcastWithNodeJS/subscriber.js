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
