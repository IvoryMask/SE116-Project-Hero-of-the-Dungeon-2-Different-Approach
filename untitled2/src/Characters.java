import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Characters implements Actions{

    Level currentlevel;
    Room currentRoom;
    int healthPoint;
    String name;
    final int inventoryCapacity = 10; // Default
    ArrayList<Items> inventory;
    Scanner scan = new Scanner(System.in);
    Weapons weapon;
    Clothing cloth;
    boolean isAlive = true;

    public Characters(){
        inventory = new ArrayList<>();
        weapon = null;
        cloth = null;
        healthPoint = 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(Weapons weapon){

        if(this.weapon != null)
            this.weapon.notUse();
        this.weapon = weapon;
        weapon.use();
    }

    public void setCloth(Clothing cloth){

        if(this.cloth != null)
            this.cloth.notUse();
        this.cloth = cloth;
        cloth.use();
    }


    public void displayInventory(){

        System.out.println("INVENTORY LIST\n--------------");
        for (int i=0;i<inventory.size();i++){

            System.out.println((i+1) + "."+inventory.get(i).getName());

        }
        System.out.println("--------------");

    }

    public void addItemToInventory(Items item){

        if (inventory.size() == inventoryCapacity){

            System.out.println("!!! Your inventory is full !!!");

        }else{

            inventory.add(item);
            if(this instanceof  Hero)
                System.out.println("!!! " + item.getName() + " is added to your inventory !!!");

        }

    }

    public void dropItem(){

        Scanner keyboard = new Scanner(System.in);

        System.out.println("INVENTORY LIST\n--------------");
        for (int i=0;i<inventory.size();i++){

            System.out.println((i+1) + "."+inventory.get(i).getName());

        }
        System.out.println("--------------");

        System.out.print("\nEnter the number of item that you want to remove:");
        int itemNo = keyboard.nextInt();

        System.out.println(inventory.get(itemNo-1).getName() + " is dropped.");
        inventory.remove((itemNo-1));

    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getInventoryCapacity() {
        return inventoryCapacity;
    }

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
    }



    @Override
    public void attack() {

    }

    @Override
    public void pickUp() {
    }

    @Override
    public void drinkPotion() {
    }

    @Override
    public void movement() {
    }

}

class Hero extends Characters{

    private int currentX = 0;
    private int currentY = 0;
    private int savedPeopleCounter;

    public Hero(Level level) {

        //Default Hero
        this.name = "Unknown";
        this.savedPeopleCounter = 0;
        currentlevel = level;
        setCurrentRoom(currentlevel.level[0][0]);
    }

    public Hero(String name, int savedPeopleCounter) {
        this.name = name;
        this.savedPeopleCounter = savedPeopleCounter;
        setCurrentRoom(currentlevel.level[0][0]);
    }

    public Hero(int currentY, int currentX) {
        this.currentX = currentX;
        this.currentY = currentY;
        setCurrentRoom(currentlevel.level[0][0]);
    }

    public Hero(Room currentRoom, int currentX, int currentY, String name, int savedPeopleCounter) {
        this.currentRoom = currentRoom;
        this.currentX = currentX;
        this.currentY = currentY;
        this.name = name;
        this.savedPeopleCounter = savedPeopleCounter;
        currentlevel = new Level();
        setCurrentRoom(currentlevel.level[getCurrentY()][getCurrentX()]);
    }

    public void setCurrentlevel(Level level){
        currentlevel = level;
    }

    public void movement() {

        Scanner input = new Scanner(System.in);

        System.out.println("X: " + getCurrentX() +
                " Y: " + getCurrentY());

        currentlevel.level[getCurrentY()][getCurrentX()].setAppearance("⬛"); //Heronun olduğu yeri içi dolu kare yapıyor

        currentlevel.displayRoom();

        System.out.print("Action: ");
        String choice = input.nextLine();
        System.out.println();

        currentlevel.level[getCurrentY()][getCurrentX()].setAppearance("⬜"); //Hareket ettikten sonra bir önceki kareyi tekrar boş kutu yapıyor


        try {
            //Bastığın yöne göre heronun currentX,currentY değişkenini ve currentRoom objesini değiştiriyor.
            if (choice.equals("w")) {

                Room upperRoom = currentRoom.isDoorAvailable("w");

                if (upperRoom != null) {
                    setCurrentRoom(upperRoom);
                    setCurrentY((currentRoom.roomNumber - 1) / currentlevel.roomsInASet);
                    setCurrentX((currentRoom.roomNumber - 1) % currentlevel.roomsInASet);
                } else {
                    System.out.println("You have hit the wall");
                }

            } else if (choice.equals("s")) {

                Room belowRoom = currentRoom.isDoorAvailable("s");

                if (belowRoom != null) {
                    setCurrentRoom(belowRoom);
                    setCurrentY((currentRoom.roomNumber - 1) / currentlevel.roomsInASet);
                    setCurrentX((currentRoom.roomNumber - 1) % currentlevel.roomsInASet);
                } else {
                    System.out.println("You have hit the wall");
                }
            } else if (choice.equals("a")) {

                if (currentRoom.isDoorAvailable(currentlevel.level[getCurrentY()][getCurrentX() - 1])) {
                    setCurrentRoom(currentlevel.level[getCurrentY()][getCurrentX() - 1]);
                    setCurrentX(getCurrentX() - 1);
                } else {
                    System.out.println("You have hit the wall");
                }
            } else if (choice.equals("d")) {
                if (currentRoom.isDoorAvailable(currentlevel.level[getCurrentY()][getCurrentX() + 1])) {
                    setCurrentRoom(currentlevel.level[getCurrentY()][getCurrentX() + 1]);
                    setCurrentX(getCurrentX() + 1);
                } else {
                    System.out.println("You have hit the wall");
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("You have hit the wall");
        }

        if (choice.equals("p")) {
            pickUp();
        }

        else if (choice.equals("A")) {
            attack();
        }

        else if (choice.equals("ca")) {
            changeArmor();
        }
        else if (choice.equals("cw")) {
            changeWeapon();
        }
        else if (choice.equals("po")) {
            drinkPotion();
        }
        else if (choice.equals("i")) {
            displayInventory();
        }
        else if (choice.equals("dr")) {
            dropItem();
        }
        else if (choice.equals("st")) {
            takeStairs();
        }
    }

    public void takeStairs(){
        if(currentRoom.stairs.size() != 0){
            HashMap.Entry<Room,Level> entry = currentRoom.stairs.entrySet().iterator().next();
            Room key = entry.getKey();
            Level value = entry.getValue();
            currentlevel = value;
            currentRoom = key;
        }
    }

    public void changeArmor(){
        displayInventory();
        System.out.print("Your pick: ");
        int choice = (scan.nextInt() - 1);
        System.out.println();


        if(inventory.get(choice) instanceof Clothing){
            cloth.notUse();
            cloth = (Clothing) inventory.get(choice);
            cloth.use();
            System.out.println("Your new armor is " + cloth.getName() + "\n" );
        }
        else{
            System.out.println("Selected item is not an armor");
        }
    }

    @Override
    public void attack() {

        currentRoom.displayMonsters();

        System.out.print("Which monster do you pick? ");
        int index = ( scan.nextInt() - 1);
        System.out.print("\n");

        int damage = Math.abs(weapon.getDamagePoint() -
                currentRoom.monsters[index].cloth.getProtectionValue());

        int health = currentRoom.monsters[index].getHealthPoint() - damage;

        currentRoom.monsters[index].setHealthPoint(health);

        System.out.println("The hero caused " + damage + "HP damage to the monster\n");

        if(currentRoom.monsters[index].healthPoint <= 0){
            System.out.println("The monster is dead\n");
            currentRoom.monsters[index].dropAll(this);
        }
        else {

            damage = Math.abs(currentRoom.monsters[index].weapon.getDamagePoint() - cloth.getProtectionValue());
            healthPoint = healthPoint - damage;

            if(healthPoint <= 0) {
                gameOver();
                return;
            }

            System.out.println("The monster caused " + damage + "HP damage to the hero\n");
        }

    }

    public void gameOver(){
        isAlive = false;
    }

    @Override
    public void drinkPotion() {
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) instanceof Potion){
                healthPoint += ((Potion) inventory.get(i)).getHealingPoint();
                inventory.remove(i);
                System.out.println("Potion was drank. Hero " + name + "\'s HP: " + healthPoint);
                break;
            }
        }
    }

    @Override
    public void pickUp() {
        currentRoom.showInventory();

        System.out.print("How many items do you want to pick? ");
        int number = scan.nextInt();
        System.out.print("\n");

        for(int i = 0; i < number; i++){
            System.out.print("#" + (i+1) + " pick: ");
            int index = ( scan.nextInt() - 1);
            System.out.print("\n");

            addItemToInventory(currentRoom.inventory.get(index));
            currentRoom.inventory.remove(index);

            System.out.print(" ");
        }

    }

    public void changeWeapon(){
        displayInventory();
        System.out.print("Your pick: ");
        int choice = (scan.nextInt() - 1);
        System.out.println();

        if(inventory.get(choice) instanceof Weapons){
            weapon.notUse();
            weapon = (Weapons) inventory.get(choice);
            weapon.use();
            System.out.println("Your new weapon is " + weapon.getName() + "\n");
        }
        else{
            System.out.println("Selected item is not a weapon");
        }
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public int getSavedPeopleCounter() {
        return savedPeopleCounter;
    }

    public void setSavedPeopleCounter(int savedPeopleCounter) {
        this.savedPeopleCounter = savedPeopleCounter;
    }



}

class Monsters extends Characters{

    public void dropAll(Hero hero){

        currentRoom.addToInventory(inventory);
        inventory.clear();
        isAlive = false;

        hero.setSavedPeopleCounter(hero.getSavedPeopleCounter() + hero.currentRoom.townspeople.length);
    }
}

class Townspeople extends Characters{}