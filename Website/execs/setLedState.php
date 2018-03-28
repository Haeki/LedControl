<?php
$q = $_REQUEST["q"];
file_put_contents('led.state', $q);
?>