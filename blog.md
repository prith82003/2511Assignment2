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

[This design violates the open closed principle as it utilises switch case statements in order to create and organise functionality for different types of goals. This means that adding new functionality or a new goal will require modifications to the existing method, violating the open-closed principle. Therefore, this design should be changed, using the composite pattern.]

ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

[This code is not sufficient, in order to rectify this issue, I implemented a factory pattern in order to be able to instantiate new goals and a composite pattern to evaluate and print existing goals.]

f) Open Refactoring
Merge Request 1
[Removed all calls of deprecated functions, updated functionality to match with new setPosition function in entity.]
Merge Request 2
[Refactored the way build criteria was checked when building inventory items. Factory pattern was implemented to instantiate new items. 
Composite pattern was added in order to evaluate if criteria for build materials is met. New buildable items extend Buildable class and 
must provide their criteria to be built into constructor.]
Add all other changes you made in the same format here:

Merge Request 3
[Refactored Entity creation. Used factory pattern to instantiate new entities. moved EntityFactory to its own folder/package. EntityFactory takes in a string and returns the corresponding entity.]

Merge Request 4
[Refactored BattleStatistics to make constructor protected, builder now constructs BattleStatistics. Other classes call BattleStatisticsBuilder and build it up using new constants.]

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

Choice 1 (Sun Stone & More Buildables)
Links to your merge requests
Assumptions
[
    1. Where there are multiple valid options for creating a buildable entity, keys will take precedence to treasures.
    2. After the use of a sceptre, meaning the mercenary is mind controlled and interacted with, the durability of it is reduced by one value.
       The player can mind control mercenaries 'duration' amount of time. When the durability of the sceptre is 0 or less, it will be removed
       from the inventory. Even after the sceptre is removed, the mercenary can still be mind controlled for 'mindControlDuration' amount of
       ticks.
    3. A player can hold as many sceptres in their inventory as long as it was correctly built.
    4. When trying to open a door with both a key and a sunstone in the player's inventory, the key will be used first.
    5. When mind control duration is <= 0, the mercenary no longer functions as an 'ally' and will behave as any enemy and is battleable again.
       If the player mind controls the mercenary again with a sceptre, the duration is reset and fill last for equal ticks amount.
    6. A mercenary should be bribed in preference to being mind controlled
    7. The MidnightArmour magnifier is defaulted to 1, since otherwise would be out of scale with attack and defense relative stats.
]
Design
[
    As SunStone is a 'special form of treasure', the SunStone class extends off Treasure in the collectables folder. Therefore, the Door class
    was altered to support SunStone being a replacement of Keys.

    The Sceptre and MidnightArmour exists in the buildables folder since they cannot be picked up and have certain build trees. The Mercenary
    class checks if its interactable with the player and mindControllable, since a mercenary is the only enemy affected by the sceptre. As
    seen in SceptreBuildable, keys will take precedence before treasure and then finally followed by SunStones.
]
Changes after review
[
    After review, it is identified that a mercenary should be bribed in preference to being mind controlled. The mercenary will
    only be mind controlled if the player does not have enough treasure.
]
Test list
[
    1. Test sunstone is can be picked up
    2. Test sunstone can open door
    3. Test sunstone cant open second door with different key
    4. Test sceptre can be built
    5. Test sceptre can be built up with only wood and sunstone
    6. Test sceptre can mindControls mercenary and durability
    7. Test MidnightArmour can be built
    8. Test MidnightArmour increases attack damage
    9. Test MidnightArmour cannot be built when zombies present
]
Other notes
[Any other notes]

Choice 2 (Snakes)
Links to your merge requests
Assumptions
[
    1. Snake body parts don't count towards enemy goal
    2. Sun Stone is a food item to the snake (acts the same as treasure)
    3. Snakes slither through portals
]
Design
[
    Snake Body and Snake Head will be their own entities that inherit from enemy. Since snake head is the one in charge of movement, it will
    inherit from MovingEnemy. Snake head will have a list of parts that belong to it and battle statistics. Snake body will keep track of its head
    and return the head's statistics and attributes when necessary. The snake splitting and dying will be called in the onDestroy method in the
    snake body. Snake body will call a method in snake head which will take care of all the specifics with destroying the body.
]
Changes after review
[
    Snake body will have to keep track of its previous position in order to update the body part after it. The snake body will have a method to
    update the head when the snake gets split.
]
Test list
[
    1. Test Basic Snake Creation
    2. Test Snake Pathfinding and Growth
    3. Test Snake Dying after Battle
    4. Test Snake Invisibility
    5. Test Snake Splitting
]
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