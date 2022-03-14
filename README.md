# Battle-Boats
A java based interactive game like battleship where boats are randomly placed on a grid and you have different tools to hit all of them!

The different tools and commands you can use include:

scanner: scan an entire row or column for the number of cells that contain a boat block

missile: fire a missile to a certain coordinate and hit all initially adjacent positions as well

drone: send a drone to a coordinate to determine the size and orientation of the boat that is there (if there is one)

debugging: allows you to see where all the boats are placed before hitting them

The Game.java class is the one you will run and contains the main method for the game. It is assumed that all entered coordinates are in the format of two integers with a space between them and the program will not necessarily catch all formatting or input type issues.
