// We use two of the standard node.js modules. The first one gives us
// access to file system operations, the second one gives us an easy
// way to send HTTP requests and process the results

var fs = require('fs');
var http = require('http');


// We found a service that offers a REST API to fetch random quotes
// We will send "HTTP GET" requests to this API and get json objects
// in return. 

var options = {
	hostname: 'iheartquotes.com',
	port: 80,
	path: '/api/v1/random?min_lines=5&format=json',
	method: 'GET'
};


// In order to fetch a quote, we will simply use the http module and process
// the result asynchronously. It is possible that the response comes back in
// multiple chuncks, so we concatenate all of them until we have received the
// full response.
//
// We then transform the response to a json object. We use the "tags" array 
// property to decide in which folder(s) we will create the file. We use the
// index parameter to decide how we will name the file. Along the way, we make
// sure that all directories in the path are created (starting with the 'quotes'
// root directory).

function fetchAndArchiveQuote(index) {
	http.get(options, function(res) {
		var data = '';
		res.on('data', function(chunk) {
			data += chunk;
		});
		res.on('end', function() {
			var obj = JSON.parse(data);

			var filename = "./quotes/";
			if (!fs.existsSync(filename)) {
				fs.mkdirSync(filename);					
			}

			for (var i = 0; i < obj.tags.length; i++) {
				filename += obj.tags[i] + "/";
				if (!fs.existsSync(filename)) {
					fs.mkdirSync(filename);					
				}
			}
			filename += "quote-" + index + ".txt";
			
			fs.writeFile(filename, obj.quote, function(err) {
			    if(err) {
			        console.log(err);
			    } else {
			        console.log("A new quote was saved: " + filename);
			    }
			});

		})
	});
}

// Let us fetch and archive 100 quotes.

for (var i=0; i<100; i++) {
	fetchAndArchiveQuote(i);
}
