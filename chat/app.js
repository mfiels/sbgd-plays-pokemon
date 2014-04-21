var fs = require('fs');
var http = require('http');
var net = require('net');
var socketIo = require('socket.io');

var page = fs.readFileSync('index.html');

function router(req, res) {
  res.writeHead(200, {
    'Content-Type': 'text/html'
  });
  res.end(page);
}

var app = http.createServer(router).listen(9191);

var keyService = net.connect(9090);

var io = socketIo.listen(app);
io.sockets.on('connection', function(socket) {
  socket.on('message', function(message) {
    console.log('got message: ' + message);
    keyService.write(message + '\r\n');
    socket.broadcast.emit('message', message);
  });
});
