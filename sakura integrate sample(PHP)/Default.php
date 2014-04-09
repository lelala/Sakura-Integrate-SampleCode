<?php
//這頁是測試偵錯時進入1Campus的快速網址，在正式上線時本頁應不需要存在
require_once "config.php";

$testAcc = "teacher";//允許"student"或者"teacher"，當網址帶著testacc參數時會直接已設定好的測試帳號進入系統
$testapp = $EntryPoint;

header(
    "Location:"
        ."https://web2.ischool.com.tw/"
            . "?testacc=" . $testAcc
            . "&testapp=" . urlencode($testapp)
);

?>