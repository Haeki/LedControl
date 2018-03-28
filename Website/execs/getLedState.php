<?php

//$ledStates = file('led.state', FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);
$ledStates = file_get_contents('led.state');
echo json_encode($ledStates);
?>