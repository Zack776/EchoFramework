# EchoFramework
A simple cilent-server framework including proxy servers and custom handlers to handle specific client interactions
Echo connnects "clients" to "servers" via a "handler" class created by the server when a client attempts a connection. 
Both client and handler are subclasses of the correspondent class, a class deisgned to send and receive messages
Echo Framework can also become more protective of data through the use of a proxy server. Instead of the main server telling potiental connectors
its information, clients can only see the information from the proxy server.
Various handlers have both basic utility purpose, such as math handler, or server to buff up secuirty such as through firewall hander.
