import socket;

# Ex2 - wiadomość zmieniona na " żołta gęś i kodowanie na utf-8 ( zamiast cp1250) i serverport na 9008

serverIP = "127.0.0.1"
serverPort = 9008
msg = "żółta gęś"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))






