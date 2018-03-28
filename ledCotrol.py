import sys
import pexpect

# Configuration values.
ADRESS = '40:00:00:C3:66:BE'


if len(sys.argv) < 2:
	print('Error must have a command as parameter!')
	print('Usage: sudo python ledControl.py cmd')
	print('Example: sudo python ledControl.py on')
	print(sys.argv)
	sys.exit(1)

cmd = sys.argv[1]

if cmd == 'on' or cmd == 'off' or cmd == 'color' or cmd=='brightness' or cmd=='speed' or cmd=='special':
	gatt = pexpect.spawn('gatttool -I')

	# Connect to the device.
	gatt.sendline('connect {0}'.format(ADRESS))
	gatt.expect('Connection successful')
else:
	print('Unknown command!')
	print('Available commands are: on, off, color (Hex Code), brightness (0-100), speed (0-100), special (80-9c)')
	sys.exit()

if cmd=='help':
	print('...')

elif cmd=='on':
	gatt.sendline('char-write-cmd 0x0008 7e0404f0ffffff00ef')

elif cmd=='off':
	gatt.sendline('char-write-cmd 0x0008 7e040410ffffff00ef')

elif cmd=='color':
	if len(sys.argv) > 2 and len(sys.argv[2]) == 6:
		gatt.sendline('char-write-cmd 0x0008 7e070503{}00ef'.format(sys.argv[2]))
	else:
		print('Wrong Color format. Color must be 6 digit Hex Code (RRGGBB)')
		gatt.sendline('disconnect')
		sys.exit(1)

elif cmd=='brightness':
	if len(sys.argv) > 2 and int(sys.argv[2]) >= 0 and int(sys.argv[2]) <= 100:
		brVal = ('{:02x}'.format(int(sys.argv[2])))
		print(brVal)
		gatt.sendline('char-write-cmd 0x0008 7e0401{}ffffff00ef'.format(brVal))
	else:
		print('Wrong Brightness Value format. Brightness Value must be between 0 and 100')
		gatt.sendline('disconnect')
		sys.exit(1)
		
elif cmd=='speed':
	if len(sys.argv) > 2 and int(sys.argv[2]) >= 0 and int(sys.argv[2]) <= 100:
		brVal = ('{:02x}'.format(int(sys.argv[2])))
		print(brVal)
		gatt.sendline('char-write-cmd 0x0008 7e0402{}ffffff00ef'.format(brVal))
	else:
		print('Wrong Speed Value format. Speed Value must be between 0 and 100')
		gatt.sendline('disconnect')
		sys.exit(1)

elif cmd=='special':
	if len(sys.argv) > 2 and len(sys.argv[2]) == 2:
		gatt.sendline('char-write-cmd 0x0008 7e0503{}03ffff00ef'.format(sys.argv[2]))
	else:
		print('Wrong Tets Value. Test Value must be 2 digit Hex Value')
		gatt.sendline('disconnect')
		sys.exit(1)

print('Command run successful')
gatt.sendline('disconnect')
sys.exit(0)
