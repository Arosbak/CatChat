# Cat-Chat

A small server application that allows users to chat over a local network. The application containts the serverside executable and a clientside executable. The server application allows multiple users to connect to a single chat.

## Usage

To run the server simply run the `server.jar` file by:

```
java server.jar
```

Once the server is launched any number of clients can connect over a local network. The server listens on port `59298`. Once launched the server will display the local IP address of the machine it is running on. To launch a client app simply run the `client.jar` with following arguments:

```
java client.jar <username> <server IP address>
```
Once succesfully connect the client app will able to send messages to other connected clients. For images to work in the client app a `resources` folder containg the images should be place in the same directory as `client.jar`.
