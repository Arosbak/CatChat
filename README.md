# Cat-Chat

A small server application that allows users to chat over a local network. The application containts the serverside executable and a clientside executable. The server application allows multiple users to connect to a single chat.

## Usage (source-code)

In order to run the application the `Server` class needs to be run first and after that a`Client` class can be run. 
If the imported images do not work/load, the path to the images should be specified in the code.

The order in which the clients are run does not matter. If three clients are initialized, there should be three pop-up windows. In these windows any message can be typed and sent by pressing the send button. The sent message will appear on all the pop-up windows which creates a chat on a local network.
