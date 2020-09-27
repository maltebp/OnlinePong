# Online Pong
This is a repository for a final project in Advanced Programming Course at DTU.
Group members are:

  - Andreas B. G. Jensen ([AndreasBGJensen](https://github.com/AndreasBGJensen))
  - Claes Lindhardt ([claeslindhardt](https://github.com/claeslindhardt))
  - Jacob Riis Jensen ([JRJ86](https://github.com/JRJ86))
  - Simon Woldbye Lyng ([swoldbye](https://github.com/swoldbye))
  - Kristian Nikolai Andersen ([KNA1711](https://github.com/KNA1711))
  - Malte Bryndum Pedersen ([maltebp](https://github.com/maltebp))

_Project report is found in_ in repo 

## Project Description
Purpose of the project is to create an Online Multiplayer version of the classic Pong game including:

 - Multiple online multiplayer games running at same time
 - A User Registration System including hashed password
 - A User rating system (Elo-rating)
 - An API giving access to dabase used by game

The project contains 4 applications in total:

  - _Website_: Interface for user registration, and Pong game implementation
  - _Game Server_: Facilitating communication between players (finding game and sending game data)
  - _Database API_: API giving access to manipulate certain data in the database
  - _MySQL Database_: An external database containing user information (Amazon db)
  
  
## Running The Game (local):

1. Download Project as ZIP or import via Version control
2. Run server.Server class in 'src/main/java/server.Server.class'. (This starts all applications)
3. Go to a browser, and type in localhost:8080
4. Play!

