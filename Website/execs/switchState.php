<?php

$q = $_REQUEST["q"];
$out = array();

if ($q == "on") {
    exec('python /var/www/exec/ledCotrol.py on', $out);
}else if ($q == "off") {
    exec('python /var/www/exec/ledCotrol.py off', $out);
}

foreach ($out as &$value) {
	echo $value;
}
?>