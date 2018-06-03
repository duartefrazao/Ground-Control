# Ground-Control

|Table of Contents|
|:---------------:|
|[Package and class diagram (UML)](#package-and-class-diagram-uml)|
|[Design of behavioural aspects](#design-of-behavioural-aspects)|
|[Design Patterns](#design-patterns)|
|[GUI Design and Main Functionalities](#gui-design-and-main-functionalities)|
|[Test Design](#test-design)|


## Package and class diagram (UML)

### MVC 

![MVC schema](/ImagesFinal/mvc.png)



### Model

![Model schema](/ImagesFinal/model.png)


### View

![View schema](/ImagesFinal/view.png)


### Controller

![Controller schema](/ImagesFinal/controller.png)

## Design of behavioural aspects

![State machine](/ImagesFinal/statemachine.png)

## Design Patterns

### MVC (Model-View-Controller)

![MVC pattern](/ImagesFinal/mvcpattern.png)

#### Why and How?

  Our game has 3 main packages, the Model, the View and the Controller.
  
  The model only has the game data and does not depend on any other package.
  
  It will be updated by the controller, a physics engine, which receives user inputs and the update times through the view, serving as a bridge between the model and view. At each frame, the view will obtain all the useful information from the model to draw the elements. This way, every package is loosely coupled. 
  
  It also makes it easier to test our game logic, since the connections to View can be eliminated and we can call the controller with specified inputs and times.
  

### Observer 

![Observer pattern](/ImagesFinal/observer.png)

#### Why and How?
  
  “Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.”

  Collisions and other events in our physics engine are happening all the time between different elements. This events must be transmitted to the model, so the View can output a truthful representation without keeping all the elements tightly coupled. When some object collides with something, it will notify every related with him and the engine will handle it, this way we keep every object decoupled.

  Participating classes: GameController (implements ContactListener, implemented natively by LibGDX),through the contact listener it must handle all the collisions of the world bodies (which have fixtures).

### Factory and FactoryMethod

#### Why 
  
  At each frame, the View knows which elements it has create, denoted by element view, for the output but it does not know how to create them, since it relies on the element model(1). In the beginning of the game or when a collision occurs, the game controller will need to create a new planet, in order to keep the total number os planets balances(1). Each element view knows it has to create a sprite for the output, but it doesn’t know how, it’s dependent on the concrete object (similar problem to the first) (2)
  
#### How 

  ##### Factory (1)

  In each frame, the view calls upon a factory, View Factory, given some specifications, to obtain the view it needs to draw (the product of the factory). Makes the code simpler by creating different objects without exposing the logic behind it. The same applies to the game controller. When needed, he calles upon a planet factory which, with some specifications, creates a new planet and adds it to the world, without exposing any of the logic. This makes the code much simpler to read and keeps everything decoupled. The buttons are also created using a factory. 

  ![Factory pattern](/ImagesFinal/factory.png)

  ##### MethodFactory (2)

  Deals with the problem of creating objects without having to specify the exact class of the object that will be created. The creation of objects it’s done by calling a factory method which is defined in the base class and overridden by it’s derived classes. In our case, we need several different sprites for each type of planet, comet, player and explosion. To simplify, the base class defines that there must be a way to create a sprite and each one of the children implements such creation.

![Factory method pattern](/ImagesFinal/methodfactory.png)
  
  

### FlyWeight 

![Flyweight pattern](/ImagesFinal/flyweight.png)

#### Why and How?
  
 At every moment, there are several planets of some sizes in our screen. Maintaining a different sprite for each one can be expensive. 
 
 Since the sprite is shared among them (intrinsic state), only changing the position and rotation (extrinsic state), we can save a lot of space by sharing the same sprite between them (each one of the object will then apply the needed transformations, accordingly to the position, rotation etc…).

This way, we only need to give the extrinsic state to the FlyweightFactory and it will produce the desired intrinsic state. In our case, the FlyweightFactory and the MethodFactory pattern are being used together.

Participating Classes: View Factory; ElementView and all sub-classes; Game View.


### State 

![State pattern](/ImagesFinal/state.png)

#### Why and How?
  
  The player can be idling, running, jumping or floating but being able to do each one of these abilities depends on its current state, i.e. if the player it’s straying away in the space he can’t jump. Each one of this state will also react differently to user input, i.e., if the player is on a planet and the user presses the right button, he should go right, but if he is floating, it should do nothing at all.

“Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.” We start by defining an interface for the state that defines several operations that must be implemented. After we define a class for each possible state of the player that implements the state interface and defines such operations. Each class will react differently and cause different effects on the player (called the context in this scenario).

  Participating Classes: PlayerController.
  
  
### Game Loop 

![Game Loop pattern](/ImagesFinal/gameloop.png)

#### Why and How?

  The game runs in a loop that controls everything, but we have no control over the speed of it. If the CPU is too slow or too fast, some fundamental steps from the physics engine can be missed by the user.
  
  We achieve it by decoupling the progression of the game from the CPU speed and/or user input. 
  
  Participating classes: GameView; GameController; GameModel.

## GUI Design and Main Functionalities 

### Main Menu

![Main menu](/ImagesFinal/menu.png)

#### Main Functionalities

* Enter a single player game
* Enter a multi player game
* Exit game


### Game (First Player)

![Game ](/ImagesFinal/firstPlayer.png)

#### Main Functionalities

This screen corresponds to a single player game or the screen from the first player in multiplayer.

* Play the game, walking left/right and jumping 
* Pause the game and enter the pause screen

### Game (Second Player)

![Game](/ImagesFinal/secondPlayer.png)

#### Main Functionalities

This screen corresponds to the second player in a multi player game.

* This player affects the game of the first player by using the accelerometer to change the gravity of the game.
* Pause the game and enter the pause screen

### Pause

![Pause](/ImagesFinal/pause.png)

#### Main Functionalities

* Exit to main menu
* Continue the game


### Player Selector

![Selector](/ImagesFinal/selector.png)

#### Main Functionalities

* Exit to main menu
* Connect as first player
* Connect as second player

### First Player Connection

![Connection First](/ImagesFinal/connectionFirst.png)

#### Main Functionalities

* Exit to main menu
* Wait for a client to connect and start the game

### First Player Connection

![First Player Connection](/ImagesFinal/connectionSecond.png)

#### Main Functionalities

* Exit to main menu
* Enter server ip 
* Connect to server

### Game Over

![GameOver](/ImagesFinal/gameOver.png)

#### Main Functionalities

* Exit to main menu
* Restart

### Lost Connection

![Lost Connection](/ImagesFinal/lostConnection.png)

#### Main Functionalities

* Exit to main menu

## Test Design

* Game variables develop as expected.
* Correct spawning of planets, comets, explosions and Player.
* Correct removal of planets and comets.
* Explosions disappearing after some time spent. 
* Correct game model update cycle.
* Comets disappearing after some time. 
* Randomized planet size and type creation.







  



  
  


