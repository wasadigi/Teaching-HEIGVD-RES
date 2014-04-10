var express = require('express');
var app = express();

//app.set('static buffer size', Number.MAX_VALUE);

app.get('/test/redirect-absolute', function(req, res) {
	res.redirect('http://localhost:3000/test/redirect-target');
});

app.get('/test/redirect-relative-root', function(req, res) {
	res.redirect('/test/redirect-target');
});

app.get('/test/redirect-relative', function(req, res) {
	res.redirect('../redirect-target');
});

app.get('/test/redirect-target', function(req, res) {
	res.send("You have reached the final destination. Redirect worked.");
});

app.get('/test/withContentLength', function(req, res) {
  res.send('Hello, you should have received a Content-length header.');
});

app.get('/test/chunked', function(req, res) {
  res.set("Content-type", "text/plain");
  res.write('Hello, this will probably be in the first chunk.');
  res.write('And this will be in the second one.');
  res.write("I am done. You should have received 3 chunks. Bye.");
  res.end();
});

app.get('/test/connectionClose', function(req, res) {
  res.set("Connection", "close");
  res.send('Hello, you should have NOT have received any Content-length header.');
});

var server = app.listen(3000, function() {
    console.log('Listening on port %d', server.address().port);
});