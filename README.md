# Ground-Control

## Package and class diagram (UML)

### MVC 

![MVC schema](/Images/mvc.png)<br>


### Model

![Model schema](/Images/model.png)


### View

![View schema](/Images/view.png)


### Controller

![Controller schema](/Images/controller.png)

## Design of behavioural aspects

![State machine](/Images/statemachine.png)

## Design Patterns

### MVC (Model-View-Controller)

![MVC pattern](/Images/mvcpattern.png)

#### Why and How?

  Our game has 3 main packages, the Model, the View and the Controller.
  
  The model only has the game data and does not depend on any other package.
  
  It will be updated by the controller, a physics engine, which receives user inputs and the update times through the view, serving as a bridge between the model and view. At each frame, the view will obtain all the useful information from the model to draw the elements. This way, every package is loosely coupled. 
  
  It also makes it easier to test our game logic, since the connections to View can be eliminated and we can call the controller with specified inputs and times.
  

### Observer 

![Observer pattern](/Images/observer.png)

#### Why and How?
  
  “Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.”
  
  Collisions and other events in our physics engine are happening all the time between different elements. This events must be transmitted to the model, so the View can output a truthful representation without keeping all the elements tightly coupled.  
  
  Participating classes: GameController (implements ContactListener, implemented natively by LibGDX),through the contact listener it must handle all the collisions of the world bodies (which have fixtures).


### Factory and FactoryMethod

#### Why 
  
  At each frame, the View knows which elements it has create, denoted by element view, for the output but it does not know how to create them, since it relies on the element model(1). Each element view knows it has to create a sprite for the output, but it doesn’t know how, it’s dependent on the concrete object (similar problem to the first) (2)
  
#### How 

  ##### Factory (1)

  In each frame, the view calls upon a factory, View Factory, given some specifications, to obtain the view it needs to draw (the product of the factory). Makes the code simpler by creating different objects without exposing the logic behind it.

  ![Factory pattern](/Images/factory.png)

  ##### MethodFactory (2)
  
  Deals with the problem of creating objects without having to specify the exact class of the object that will be created. The creation of objects it’s done by calling a factory method which is defined in the base class and overridden by it’s derived classes. In our case, the base class tells that there should be a way to create a sprite and every children defines it.

![Factory method pattern](/Images/methodfactory.png)
  
  

### FlyWeight 

![Flyweight pattern](/Images/flyweight.png)

#### Why and How?
  
 At every moment, there are several planets of some sizes in our screen. Maintaining a different sprite for each one can be expensive. 
 
 Since the sprite is shared among them (intrinsic state), only changing the position and rotation (extrinsic state), we can save a lot of space by sharing the same sprite between them (each one of the object will then apply the needed transformations, accordingly to the position, rotation etc…).

This way, we only need to give the extrinsic state to the FlyweightFactory and it will produce the desired intrinsic state. In our case, the FlyweightFactory and the MethodFactory pattern are being used together.

Participating Classes: View Factory; ElementView and all sub-classes; Game View.


### State 

![State pattern](/Images/state.png)

#### Why and How?
  
  The player can walk, run and jump but being able to do each one of these abilities depends on its current state, i.e. if the player it’s straying away in the space he can’t jump.
  
  “Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.” We start by defining an interface for the state that defines several operations that must be implemented. After we define a class for each possible state of the player that implements the state interface and defines such operations. Each class will react differently and cause different effects on the player (called the context in this scenario).
  
  Participating Classes: PlayerController.
  
  
### Game Loop 

![Game Loop pattern](/Images/gameloop.png)

#### Why and How?

  The game runs in a loop that controls everything, but we have no control over the speed of it. If the CPU is too slow or too fast, some fundamental steps from the physics engine can be missed by the user.
  
  We achieve it by decoupling the progression of the game from the CPU speed and/or user input. (Native LibGDX implementation.)
  
  Participating classes: GameView; GameController; GameModel.

## GUI Design 

### Main Menu

![Main menu mock up](/Images/menu.png)

#### Main Functionalities

* Enter a single player game
* Enter a multi player game
* Configure game 
* Exit game


### Game (First Player)

![Game mock-up](/Images/firstPlayer.png)

#### Main Functionalities

This screen corresponds to a single player game or the screen from the first player in multiplayer.

* Play the game, walking left/right and jumping 
* Pause the game and enter the pause screen

### Game (Second Player)

![Game mock-up](/Images/secondPlayer.png)

#### Main Functionalities

This screen corresponds to the second player in a multi player game.

* This player affects the game of the first player by using the accelerometer to change the gravity of the game.

### Pause

![Pause mock-up](/Images/pause.png)

#### Main Functionalities

* Exit to main menu
* Restart the game
* Continue the game
* Change the game options

## Test Design

* Player moves correctly in all directions.
* Gravity correctly changes with input.
* Planet velocity correcly changes with gravity changes.
* Player behaves correctly while getting closer to one planet (gravity).
* Player behaves correctly while getting closer to more than one planet.
* Player rotation while getting closer to a planet behaves as expected.
* Jump behaves accordingly to the user input.
* Game variables develop as expected.
* Collision with planets.
* Game ending when player falls of the map.
* Correct spawning of planets.
* Game restart acctualy resets map.
* Server can send and receive information while game develops.
* Client can send and receive information while game develops.







  



  
  


