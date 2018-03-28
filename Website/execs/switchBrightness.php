<?php

$q = $_REQUEST["q"];
$out = array();

if ($q !== "") {
	exec('python /var/www/exec/ledCotrol.py brightness '.$q, $out);
}
foreach ($out as &$value) {
    echo $value;
}
?>