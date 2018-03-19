import socket;

serverIP = "127.0.0.1"
serverPort = 9008
msg = "Python ping"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))

# odbieranie

buff = []

buff, address = client.recvfrom(1024)
print("python udp client received msg: " + str(buff,'cp1250' ))