var net = require('net');
fs = require('fs');

var HOST = '127.0.0.1';
var PORT = 1313;

var client = new net.Socket();

// We read the input file asynchronously. The third argument of passed to the readFile
// method is a callback function that will be invoked when the data has been read. We
// know that at this point, it is safe to start the client. We can pass the data to the
// client, so that it can send it to the server.
// Note that this is not always the best approach. It works well in this case because we are
// dealing with a small file. Hence, we don't need a lot of RAM to keep its content in
// memory. Another approach would be to read the file as we need it and to pipe it through the
// socket connecting us to the server

fs.readFile('../data/RES-1-A.csv', 'utf8', function (err,data) {
	if (err) {
		return console.log(err);
	}
	talkToServer(data);
});

// This function establishes a connection with the server, issues commands to query the
// server status and to load the content of the file. Again, we see an example of asynchronous
// code: the third argument of the connect() method is a function that is called back once the
// connection has been established.

function talkToServer(data) {
	client.connect(PORT, HOST, function() {
		console.log('CONNECTED TO: ' + HOST + ':' + PORT);
		//client.setNoDelay();
		client.write('INFO\n');
		client.write('LOAD\n');
		client.write(data);
		client.write('\n');
		client.write('ENDOFDATA\n');
		client.write('INFO\n');
		client.write('BYE\n');
	});

	// The following code registers a callback function on an event notified by the client.
	// When data is available for the client (i.e. bytes have arrived through the socket),
	// the we print it on the console.
	
	client.on('data', function(data) {    
		console.log(">>> " + data);
	});

	client.on('end', function() {    
		console.log("** socket closed");
	});
}

