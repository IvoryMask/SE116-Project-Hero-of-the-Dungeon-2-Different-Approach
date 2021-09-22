import java.security.SecureRandom;
import java.util.*;

public class Level{

    // attributes --> declarations
    Room[] rooms;       // Composition - Every level "has" rooms
    int sets;           // Number of sets in a level = m
    int roomsInASet;    // Number of rooms in a set = n
    int totalRooms;
    int levelID;        // Every level has its unique level number, starting on 1

    SecureRandom random = new SecureRandom();
    static int levelNumber = 0;

    static Room[][] level; // Composition - Every level consist of rooms

    // constructor --> assignments
    public Level(){

        levelNumber++;      // A new level is created, increase the number

        levelID = levelNumber;
        sets = random.nextInt(3) + 2;           // in the range of [2,4]
        roomsInASet = random.nextInt(4) + 2;    // in the range of [2,5]
        totalRooms = sets * roomsInASet;

        rooms = new Room[totalRooms];

        // Generate the rooms when the level is created
        generateRooms();
        convertRoomsToLevel();

    }

    // Member functions

    public int getLevelID(){
        return levelID;
    }

    public int getTotalRooms(){
        return totalRooms;
    }

    public Room[] getRooms(){
        return rooms;
    }

    private void generateRooms(){

        if(totalRooms == 0){
            return;
        }

        int count = 0; // Count the number of rooms created for the room's constructor

        // Create corridors/sets
        for(int i = 0; i < sets; i++){

            // Create the first room in the set
            rooms[count] = new Room("⬜", this, (count + 1));
            Room previousRoom = rooms[count];
            count++;    // Increase the number of door every time one is created

            for(int j = 1; j < roomsInASet; j++){

                // Create the room
                rooms[count] = new Room("⬜", this, (count + 1));
                Room currentRoom = rooms[count];
                count++;    // Increase the number of door every time one is created


                // Create its doors immediately after its creation

                // Backwards Door addition
                currentRoom.addDoor(previousRoom);

                // Forwards Door addition
                previousRoom.addDoor(currentRoom);


                // Update the previous room
                previousRoom = currentRoom;
            }

        }

        // connect these corridors/sets
        for(int i = 1; i < sets; i++){

            // Pick a random room from the previous set
            int min = ((i - 1) * roomsInASet);      // inclusive
            int max = min + (roomsInASet - 1);      // inclusive
            int randomNum = random.nextInt(max + 1 - min) + min;   // in the range of [min, max]
            Room previousSetConnector = rooms[randomNum];          // random room from prev. set

            // Pick a random room from the current set
            min = ((i) * roomsInASet);      // inclusive
            max = min + (roomsInASet - 1);  // inclusive
            int randomNum2 = random.nextInt(max + 1 - min) + min;       // in the range of [min, max]
            Room currentSetConnector = rooms[randomNum2];           // random room from current set

            // Join the rooms
            previousSetConnector.addDoor(currentSetConnector);
            currentSetConnector.addDoor(previousSetConnector);

        }
    }

    // Connect levels --> Add stairs
    public void addStairs(Level stairsTo){

        // Pick a random room from this level
        int min = 0;                    // inclusive
        int max = (totalRooms - 1);     // inclusive
        int randomNum = random.nextInt(max + 1 - min) + min;   // in the range of [min, max]
        Room randomRoom = rooms[randomNum];                    // random room from the level

        // Pick a random room from parameter level
        max = (stairsTo.getTotalRooms() - 1);       // inclusive
        randomNum = random.nextInt(max + 1 - min) + min;    // in the range of [min, max]
        Room randomRoom2 = stairsTo.getRooms()[randomNum];  // random room from the level

        // Add stairs
        randomRoom.stairs.put(randomRoom2, stairsTo);
        randomRoom2.stairs.put(randomRoom, this);

        for(Room room: randomRoom.stairs.keySet())
            System.out.println("-->" + room.getRoomName());
        for(Room room: randomRoom2.stairs.keySet())
            System.out.println("-->" +room.getRoomName());

    }

    //Roomdaki karelesi print ediyor.
    public void displayRoom() {

        for(int i = 0; i < sets; i++) {

            if(i != 0 ){
                Room connector = findUpperConnector(i);

                int arrow = (connector.getRoomNumber() - 1) % roomsInASet;
                int blanks =  (arrow + (arrow * (3)));

                for (int e = 0; e < blanks; e++) {
                    System.out.print(" ");
                }

                System.out.print("↑");
                System.out.println();
            }

            for(int j = 0; j < roomsInASet; j++) {
                System.out.print(level[i][j].getAppearance());

                if(j != (roomsInASet - 1))
                    System.out.print("→ ←");
            }
            System.out.println();

            if(i != (sets - 1)) {
                Room connector = findLowerConnector(i);
                //System.out.println("For set " + i + " I have found " + connector.getRoomNumber());
                int arrow = (connector.getRoomNumber() - 1) % roomsInASet;
                int blanks = (arrow + (arrow * roomsInASet));

                switch(arrow){
                    case 2: blanks--; break;
                    case 3: blanks = blanks - 3; break;
                    case 4: blanks = blanks - 6; break;
                    default: break;
                }

                for (int e = 0; e < blanks; e++) {
                    System.out.print(" ");
                }

                System.out.print("↓");
                System.out.println();
            }

        }
    }

    private Room findLowerConnector(int setNumber){
        for(int i = 0; i < roomsInASet; i++){
            Room lowerRoom = level[setNumber][i].findLowerDoor();

            if(lowerRoom != null)
                return level[setNumber][i];
        }

        return null;
    }

    private Room findUpperConnector(int setNumber){
        for(int i = 0; i < roomsInASet; i++){
            Room upperRoom = level[setNumber][i].findUpperDoor();

            if(upperRoom != null)
                return level[setNumber][i];
        }

        return null;
    }


    private void convertRoomsToLevel(){

        level = new Room[sets][roomsInASet];

        int count = 0;
        for(int i = 0; i < sets; i++){
            for(int j = 0; j < roomsInASet; j++){
                level[i][j] = rooms[count];
                level[i][j].setAppearance("⬜");
                count++;
            }
        }
    }

}


class Room {

    // attributes
    static SecureRandom random = new SecureRandom();

    String appearance;
    Level levelOfRoom;              // Composition - Every rooms "has a" level

    ArrayList<Room> doors;          // There is no limit to the number of doors in a room
    int doorsSize = 0;
    HashMap<Room, Level> stairs;    // Stairs function as a door but connects to other set

    int roomNumber;                 // Number of the room in its level
    int mySet;

    ArrayList<Items> inventory;
    Monsters[] monsters;
    Townspeople[] townspeople;

    // constructor
    public Room(String appearance) {

        this.appearance = appearance;

        doors = new ArrayList<Room>();
        stairs = new HashMap<>(2);      // Maximum number of stair that a room can have is 2
        mySet = (roomNumber - 1) / levelOfRoom.roomsInASet;

        inventory = new ArrayList<>();

        monsters = new Monsters[3];

        for(int i = 0; i < monsters.length; i++){
            monsters[i] = new Monsters();
            monsters[i].setName("");
        }

        townspeople = new Townspeople[1];

        for(int i = 0; i < townspeople.length; i++){
            townspeople[i] = new Townspeople();
        }
    }

    public Room(String appearance, Level level, int roomNumber) {

        this.appearance = appearance;

        levelOfRoom = level;
        this.roomNumber = roomNumber;

        doors = new ArrayList<Room>();
        stairs = new HashMap<>(2);      // Maximum number of stair that a room can have is 2
        mySet = (roomNumber - 1) / levelOfRoom.roomsInASet;

        inventory = new ArrayList<>();

        monsters = new Monsters[3];
        townspeople = new Townspeople[1];


        for(int i = 0; i < monsters.length; i++){
            monsters[i] = new Monsters();
            monsters[i].setName("");
        }

        townspeople = new Townspeople[1];

        for(int i = 0; i < townspeople.length; i++){
            townspeople[i] = new Townspeople();
        }
    }

    // Member functions
    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public int getRoomNumber(){
        return roomNumber;
    }

    public ArrayList<Room> getDoors(){
        return doors;
    }

    // Getter function for room number but return type is string with extra "Room" text
    public String getRoomName(){
        return ("Room-" + roomNumber);
    }

    public Level getLevelOfRoom(){
        return levelOfRoom;
    }

    // Add a door to the room
    public void addDoor(Room roomItOpens){

        // Control the validness of the parameters
        if(roomItOpens == null) {
            System.out.println("The door could not be added");
            return;
        }

        doors.add(roomItOpens);
        doorsSize++;
    }

    // Display all the available doors in the room
    public void displayDoors(){
        System.out.println("Doors in the room: ");

        for(int i = 0; i < doors.size(); i++){
            System.out.println("Door-" + (i+1) + ": " + doors.get(i).getRoomName());
        }
        System.out.println();
    }

    // Add a stair to the room
    public void addStairs(Room roomItOpens, Level levelItOpens){

        // Control the validness of the parameters
        if(roomItOpens == null || levelItOpens == null) {
            System.out.println("The stair could not be added");
            return;
        }

        stairs.put(roomItOpens, levelItOpens);
    }

    // Display all the available stair in the room
    public void displayStairs(){

        System.out.print("Stairs in the room: ");

        if(stairs.size() == 0){
            System.out.println("None");
            System.out.println();
            return;
        }

        System.out.print("\n");

        // using values() for iteration over values
        for (Level level : stairs.values()) {
            if(level.getLevelID() > levelOfRoom.getLevelID())
                System.out.println("Stairs (up)");
            else
                System.out.println("Stairs (down)");
        }

        System.out.println();
    }

    public boolean isDoorAvailable(Room room){

        /*/
        System.out.println("I want to go to: " + room.getRoomNumber());

        for(int i = 0; i < doorsSize; i++)
            System.out.println("I have: " + doors.get(i).getRoomNumber());

         */

        for(int i = 0; i < doorsSize; i++){
            if(doors.get(i).getRoomNumber() == room.getRoomNumber())
                return true;
        }

        return false;
    }

    public Room isDoorAvailable(String str){

        if(str.equals("w")){
            Room upperRoom = findUpperDoor();

            if(upperRoom != null)
                return upperRoom;
        }
        else{
            Room lowerRoom = findLowerDoor();

            if(lowerRoom != null)
                return lowerRoom;
        }

        return null;
    }

    public Room findUpperDoor(){
        for(Room room: doors){
            // If the room number of the door is smaller than first room number of my set
            if(room.getRoomNumber() < ((mySet * levelOfRoom.roomsInASet) +1))
                return room;
        }

        return null;
    }

    public Room findLowerDoor(){
        for(Room room: doors){

            // If the room number of the door is greater than last room number of my set
            if(room.getRoomNumber() > ((mySet + 1)* levelOfRoom.roomsInASet)) {
                return room;
            }
        }

        return null;
    }

    public void addToInventory(ArrayList<Items> newItems){
        inventory.addAll(newItems);
    }

    public void showInventory(){
        System.out.print("The items in the room: ");

        if(inventory.size() == 0){
            System.out.println("None");
            System.out.println();
            return;
        }

        System.out.print("\n");

        for(int i = 0; i < inventory.size(); i++){

            System.out.println( (i+ 1) + ". " );
            inventory.get(i).displayItemInfo();
        }
        System.out.println();
    }

    public void displayMonsters(){

        System.out.print("The monsters in the room: ");

        if(monsters.length == 0 || monsters[0].getName().equals("")){
            System.out.println("None");
            System.out.println();
            return;
        }

        System.out.print("\n");

        for(int i = 0; i < monsters.length; i++){
            if(monsters[i].getName().equals("")){
                break;
            }
            if(monsters[i].isAlive)
                System.out.println((i+1) + ". " + monsters[i].name);
        }
        System.out.println();
    }

    public void addMonster(Monsters monster){

        for(int i = 0; i < monsters.length; i++){
            if(monsters[i].getName().equals("")){
                monsters[i].setName(monster.getName());
                monsters[i].setWeapon(monster.weapon);
                monsters[i].setCloth(monster.cloth);
                monsters[i].currentRoom = this;
                monsters[i].inventory = monster.inventory;
                monsters[i].currentlevel = levelOfRoom;
                break;
            }
        }

    }
}