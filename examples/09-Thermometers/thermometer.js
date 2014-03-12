/*
 This program simulates a "smart" thermometer, which publishes the measured temperature
 on a multicast group. Other programs can join the group and receive the measures. The
 measures are transported in json payloads with the following format:

   {"timestamp":1394656712850,"location":"kitchen","temperature":22.5}

 Usage: to start a thermometer, type the following command in a terminal
        (of course, you can run several thermometers in parallel and observe that all
        measures are transmitted via the multicast group):

   node thermometer.js location temperature variation

*/

var protocol = require('./sensor-protocol');

// We use a standard Node.js module to work with UDP
var dgram = require('dgram');

// Let's create a datagram socket. We will use it to send our UDP datagrams 
var s = dgram.createSocket('udp4');

// Let's define a javascript class for our thermometer. The constructor accepts
// a location, an initial temperature and the amplitude of temperature variation
// at every iteration
function Thermometer(location, temperature, variation) {

	var that = this;

	this.location = location;
	this.temperature = temperature;
	this.variation = variation;
		
    // We will simulate temperature changes on a regular basis. That is something that
    // we implement in a class method (via the prototype)	
	Thermometer.prototype.update = function() {  
	  var delta = that.variation - (Math.random()*that.variation*2);
	  that.temperature = (that.temperature + delta); // we use 'that' because update() will be invoked from the setInterval function ('this' will point to that function)	
	  //console.log("Updating " + that.location + ", " + that.temperature + " thermometer's temperature: " + that.temperature + ", variation: " + that.variation + ", delta: " + delta);
	
	  // Let's create the measure as a dynamic javascript object, 
	  // add the 3 properties (timestamp, location and temperature)
	  // and serialize the object to a JSON string
	  var measure = new Object();
	  measure.timestamp = Date.now();
	  measure.location = that.location;
	  measure.temperature = that.temperature;
	  var payload = JSON.stringify(measure);
	
	  // Finally, let's encapsulate the payload in a UDP datagram, which we publish on
	  // the multicast address. All subscribers to this address will receive the message.
	  message = new Buffer(payload);	
	  s.send(message, 0, message.length, protocol.PROTOCOL_PORT, protocol.PROTOCOL_MULTICAST_ADDRESS, function(err, bytes) {
	  	console.log("Sending payload: " + payload + " via port " + s.address().port);
	  });
	
	}
    
    // Let's take and send a measure every 200 ms
	setInterval(that.update, 200);
	
}

// Let's get the thermometer properties from the command line attributes
// Some error handling wouln't hurt here...
var location = process.argv[2];
var temp = parseInt(process.argv[3]);
var variation = parseInt(process.argv[4]);

// Let's create a new thermoter - the regular publication of measures will
// be initiated within the constructor
var t1 = new Thermometer(location, temp, variation);


