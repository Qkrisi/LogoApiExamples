import py_logo

def on_msg(client, sender, message_type, message):
	print(f"[{sender}] {message}")

client = py_logo.LogoClient("Konzol", "localhost", 51, on_message = on_msg)
client.connect()

while client.connected:
	cmd = input("$ ")
	if cmd == "_stop":
		client.disconnect()
		continue
	client.send_message(py_logo.MessageTypeSend.COMMAND, "_végrehajt " + py_logo.to_string(cmd), client.server, wait = True)
