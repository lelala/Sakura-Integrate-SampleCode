<?php
//�����O�i�J�t�Ϊ��Ĥ@�ӭ����A�ѥ������i�JOAuth���{�Ҥα��v���y�{�C
require_once "config.php";

//���o��1Campus�i�J�����ɩұa��application�ѼơAapplication�N��P��App�i���X��"�Ǯ�"�D���A�ڭ̻ݭn�bOAuth���o���v�ɭn�D�惡�ǮեD�������v�C
$application = $_REQUEST["application"];//"" + Request["application"];
//���U��ClientID ���
$client_id = $ClientId;
//���U��RedirectURI ���
$redirect_uri = $RedirectURI;
//�n�D���v�����e�A�H,���j
$scope =
"User.Mail"//�ϥΪ̪�email��T
. ",User.BasicInfo"//�ϥΪ̪��򥻸��
. "," . $application . ":sakura";//�ϥΪ̦b�Ǯժ��s�լ������ ex:demo.ischool.j:sakura
//�i��http redirect�i�JOAuth�y�{
header(
    "Location:"
        ."https://auth.ischool.com.tw/oauth/authorize.php"
            . "?response_type=code"
            . "&client_id=" . $client_id
            . "&redirect_uri=" . urlencode($redirect_uri)
            . "&scope=" . $scope
            . "&state=" . $application
);
?>