<?php
//�o���O���հ����ɶi�J1Campus���ֳt���}�A�b�����W�u�ɥ��������ݭn�s�b
require_once "config.php";

$testAcc = "teacher";//���\"student"�Ϊ�"teacher"�A����}�a��testacc�ѼƮɷ|�����w�]�w�n�����ձb���i�J�t��
$testapp = $EntryPoint;

header(
    "Location:"
        ."https://web2.ischool.com.tw/"
            . "?testacc=" . $testAcc
            . "&testapp=" . urlencode($testapp)
);

?>