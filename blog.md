Task 1) Code Analysis and Refactoring ⛏️

a) From DRY to Design Patterns
Links to your merge requests

i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

[Lines 26 - 53 in the the move method from the ZombieToast class contains the exact same code as lines 102 - 129 from the move method in the Mercenary class. These lines of code both make the enemy move away from the player. Furthermore, lines 55 - 61 in ZombieToast are the same as lines 91 - 101. This code makes the enemy movement random.]

ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

[The template method is an effective design pattern as it allows for the movement of the enemies classes to be broken into different steps, in which some of these classes both share. Since classes share similar steps, it can then be put into a superclass and reduce duplicate code. For instance, ZombieToast and Mercenary both move randomly and move away from the player in certain scenarios. Since these processes are similar 'steps' in code, template method allows us to break the algorithm down into the superclass and input these methods into the concrete subclasses when needed.]

iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

[First of all, complying with the template method, I created an abstract super class called movingEnemy, in which Mercenary and ZombieToast both extend off. Even though Spider also moves, its movement characteristics do not show any similarity with the other two. The codes mentioned in question (i) above both serve the same purpose of moving randomly or away from the player. Therefore I implemented these methods into movingEnemy and called it in Mercenary and ZombieToast when needed. This drastically reduces duplicate code and is also easier to understand, since the function names are very logical and easy to follow, such as 'if invisible, move randomly'.]

b) Observer Pattern

Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

[The observer pattern in object-oriented programming relates defines a one-to-many dependency between objects. The observable object is the main object that holds some data of interest, a list of observers and functionality to add, remove and notify observers. The observers provide an update method which is called by the observable object. There are many key characteristics of the observer pattern, they include: one-to-many dependency, loose coupling, and automatic notification. In the codebase provided, we can see in Game.java a case of the observer pattern. The Game class keeps track of its list of observers (sub, addingSub) which is used to notify all the observers when an update occurs in Game.java. Whenever the register function is called inside Game.java, an object which inherits Runnable is added to the observer list and is notified when the game tick occurs. Therefore, one place where the Observer Pattern is present in the codebase is in Game.java.]

c) Inheritance Design
Links to your merge requests

i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

[In classes Buildable, Arrow, Bomb, Key, Sword, Treasure, Wood, ZombieToastSpawner, Boulder, Door, Exit, Player, Portal, Switch and Wall, there are often empty methods which are inherited from the Entity class. Entity.java defines 3 abstract methods that its subclasses are required to implement, however, using this method means that subclasses which don’t need any functionality for that particular method, resulting in a refused bequest.]

ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

[In order to rectify this issue, we can create individual interfaces for each function (OnOverlap, OnDestroy, OnMoveAway) and the entities that need these functions can implement those interfaces. In GameMap and Game classes, we can filter the getEntities() to include only those that implement the necessary interface and call the function on that interface.]

d) More Code Smells
Links to your merge requests

i. What design smell is present in the above description?

[The code smell in the collectable entities resembles 'Shotgun Surgery', where any small changes you make require multiple different classes to also be altered. In this case, the same onOverlap method was being used in all classes (except Bomb.java).]

ii. Refactor the code to resolve the smell and underlying problem causing it.

[I utilised the move method, in which the onOverlap method was implemented into a new superclass called collectables. Collectables reflect all the classes in the repository execept InvincibilityPotion and InvisibilityPotion. As a result, all the duplicate code in classes Wood, Treasure, Sword, Key and Potion were removed and no more modification issues will arise.]

e) Open-Closed Goals
Links to your merge requests

i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

[Answer]

ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

[Briefly explain what you did]

f) Open Refactoring
Merge Request 1
[Briefly explain what you did]
Merge Request 2
[Briefly explain what you did]
Add all other changes you made in the same format here:

Task 2) Evolution of Requirements 👽


a) Microevolution - Enemy Goal
Links to your merge requests
Assumptions
[Any assumptions made]
Design
[Design]
Changes after review
[Design review/Changes made]
Test list
[Test List]
Other notes
[Any other notes]

Choice 1 (Insert choice)
Links to your merge requests
Assumptions
[Any assumptions made]
Design
[Design]
Changes after review
[Design review/Changes made]
Test list
[Test List]
Other notes
[Any other notes]

Choice 2 (Insert choice)
Links to your merge requests
Assumptions
[Any assumptions made]
Design
[Design]
Changes after review
[Design review/Changes made]
Test list
[Test List]
Other notes
[Any other notes]

Choice 3 (Insert choice) (If you have a 3rd member)
Links to your merge requests
Assumptions
[Any assumptions made]
Design
[Design]
Changes after review
[Design review/Changes made]
Test list
[Test List]
Other notes
[Any other notes]

Task 3) Investigation Task ⁉️
Merge Request 1
[Briefly explain what you did]
Merge Request 2
[Briefly explain what you did]
Add all other changes you made in the same format here: