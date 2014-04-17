var http = require( 'http' );
var port = process.env.port || 1337;

http.createServer( function ( req, res ) {
    switch( require('url').parse(req.url).pathname){
        case '/EntryPoint':
            require('./entryPoint.js').entryPoint(req,res);
            break;
        case '/OAuthCallBack':
            require('./oAuthCallBack.js').oAuthCallBack(req,res);
            break;
        default:
            require('./default').default(req,res);
            break;
    }
}).listen( port );