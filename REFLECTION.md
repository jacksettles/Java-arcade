# Reflection

Add to this file to satisfy the "Reflection Updates" non-functional requirement
for this project. Please keep this document organized using Markdown. If you
click on this file in your team's GitHub repository website, then you will see
that the Markdown is transformed into nice looking HTML.

Here is a sample entry (delete this line; also: feel free to copy/paste/modify):

## SUN 2019-04-05 @ 11:55 PM EST

1. **DONE:** Downloaded the skeleton code.

2. **TODO:** Finish reading the project description.

3. **PROB:** Had a hard time scheduling physicial meetings with each other at
   first, but now that we've settled on a regular day/time things are looking
   great!


## SAT 2019-11-16 @ 1:00PM EST

1. **DONE:** Read project Desc. Familerized with starter code and event handler functionality. Decided Games: Astriods/Chess. ChessBoard has been created. Implemented way to swap scenes with current main scene. ChessPiece Parent class created.

2. **TODO:** RD - Make piece classes containing move variants. Make peices interactable and  JS - Start Asteriods class work on interaction with ship Object.

3. **PROB:** Git merging errors. Conflict problems. Converting classes to scenes.


## TUE 2019-11-19 @ 2:48PM EST

1. **DONE:** App opens to asteroids; asteroids has a ship in it that can move forward and rotate and go off screen and reappear. 1 key changes to chess! Chessboard has dynamic sizingand chess pieces. Handled merge conflicts.

2. **TODO:** Make a main menu to switch scenes. Give Asteroids its own scene so we can swap. Make an AsteroidsGame.java file to hold all asteroids related stuff.

3. **PROB:** Getting the ship to fly to where I wanted it to fly. Getting chessboard to size correctly. Getting chess colors to show - had to use constraints. Merging problems again.
    **There was another update here for 2019-11-19, but it looks like it was lost with complications due to merging**


## TUE 2019-11-26 @ 4:30PM EST

1. **DONE:** ChessPiece child classes made and integrated into ChessBoard grid. Main Menu created and swapping between scenes functionality implemented. Messed with ASCII animation through setText() and timeline. JS - Got the ship to shoot bullets in the right directions that the ship is pointing in.

2. **TODO:** Figure out ChessPiece interaction with onMouseClicked then work on restrictions for movement. Decide if ChessPieces will have ImageViews as Objects for astetic. Make Asteroid class and work on collision detection.

3. **PROB:** Interaction between ChessPiece and onMouseClicked. Trying to get Coords of Obj selected and moved. Getting the bullet's to move over time.

## WED 2019-12-4 @ 3:00PM EST

1. **DONE:** JS- Added some graphics, collision detection, and asteroids. Asteroids can fade off the side and come out on the other side. They disappear after being blown up. RD - Added piece interaction using 2d ChessPiece array. Added one movement at a time. Add pawn blocking.

2. **TODO:** JS- Scoring and levels for asteroids. RD - Pawn Attack. Other piece movement, should go smoothly now that complications have been worked out with first moving piece.

3. **PROB:** JS- Getting the asteroids to fade off the screen and back on properly. RD - figuring out 2d array was best use for talking between pieces. GridPane Col then Row is a pain with 2d array Row then Col...

## SUN 2019-12-08 @ 1:13AM EST

1. **DONE:** JS- Just finished up Asteroids code. Levels continue on infinitely (theoretically), d each one has more asteroids than the previous. Scoreboard works properly, and the game ends when you run out of lives. Also I added a Controls menu off of the main menu for both games controls descriptions. RD - Chess looks asthetically pleasing, all peice movement feels intuitive. Fixed all errors involving pieces moving out of turn. Scoring works for each team.

2. **PROB:** JS- Getting the scoreboard to update properly. I ended up making a separate timeline object to do this over and over again to check and see if either the score, the level, or the ship's lives had changed. If they did, then the scoreboard would reflect that update. RD - keeping trap of score for each team was a problem, but got an eligant solution. Reducing methods down to Checkstyle limit of 60 created some problems to fix, but got it working.