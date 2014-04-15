//這頁是測試偵錯時進入1Campus的快速網址，在正式上線時本頁應不需要存在
var config = require('./config.json');
exports.default = function(req, res){
    var testAcc = "teacher";//允許"student"或者"teacher"，當網址帶著testacc參數時會直接已設定好的測試帳號進入系統
    res.writeHead( 
        302
        , { 
            'Location' : "https://web2.ischool.com.tw/"
                + "?testacc=" + testAcc
                + "&testapp=" + encodeURIComponent(config.EntryPoint) 
        }
    );
    res.end();
};