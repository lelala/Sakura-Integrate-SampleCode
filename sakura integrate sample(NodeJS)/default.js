//�o���O���հ����ɶi�J1Campus���ֳt���}�A�b�����W�u�ɥ��������ݭn�s�b
var config = require('./config.json');
exports.default = function(req, res){
    var testAcc = "teacher";//���\"student"�Ϊ�"teacher"�A����}�a��testacc�ѼƮɷ|�����w�]�w�n�����ձb���i�J�t��
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