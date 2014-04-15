//�����O�i�J�t�Ϊ��Ĥ@�ӭ����A�ѥ������i�JOAuth���{�Ҥα��v���y�{�C
var config = require('./config.json');
exports.entryPoint = function(req, res){
    //���o��1Campus�i�J�����ɩұa��application�ѼơAapplication�N��P��App�i���X��"�Ǯ�"�D���A�ڭ̻ݭn�bOAuth���o���v�ɭn�D�惡�ǮեD�������v�C
    var application = require('url').parse(req.url, true).query["application"];
    //���U��ClientID ���
    var client_id = config.ClientId;
    //���U��RedirectURI ���
    var redirect_uri = config.RedirectURI;
    //�n�D���v�����e�A�H,���j
    var scope =
    "User.Mail"//�ϥΪ̪�email��T
    + ",User.BasicInfo"//�ϥΪ̪��򥻸��
    + "," + application + ":sakura";//�ϥΪ̦b�Ǯժ��s�լ������ ex:demo.ischool.j:sakura

    //�i��http redirect�i�JOAuth�y�{
    res.writeHead( 
        302
        , { 
            'Location' : "https://auth.ischool.com.tw/oauth/authorize.php"
            + "?response_type=code"
            + "&client_id=" + client_id
            + "&redirect_uri=" + encodeURIComponent(redirect_uri)
            + "&scope=" + scope
            + "&state=" + application
        }
    );
    res.end();
};