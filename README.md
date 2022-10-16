# Cat-Chat

A small server application that allows users to chat over a local network. The application containts the serverside executable and a clientside executable. The server application allows multiple users to connect to a single chat.

## Usage

To run the server simply run the `server.jar` file by:

```
java -jar server.jar
```

By default the server listens for incoming requests on port `59298`. To change the port number simply run the `server.jar` with a port number argument:

```
java -jar server.jar <port number>
```

Once the server is launched a GUI will display the local IP address of the machine it is running on and the port number. After which, any number of clients can connect over a local network. To launch a client app simply run the `client.jar` with following arguments:

```
java -jar client.jar <username> <server IP address> <port number>
```

Once succesfully connected, the client app will be able to send messages to other connected clients. For images to work in the client app a `resources` folder containg the images should be place in the same directory as `client.jar`.
