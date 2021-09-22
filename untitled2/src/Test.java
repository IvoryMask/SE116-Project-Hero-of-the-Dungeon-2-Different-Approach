import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        //INVENTORY TEST TEST TEST
        //Creating Items
        //Armors
        Clothing clothingType1 = new Clothing();
        clothingType1.setName("Light Armor");
        clothingType1.setWeight(10); // Unit : kg
        clothingType1.setValue(50); // Currency : Lei
        clothingType1.setProtectionValue(5);

        Clothing clothingType2 = new Clothing();
        clothingType2.setName("Leather Armor");
        clothingType2.setWeight(20); // Unit : kg
        clothingType2.setValue(100); // Currency : Lei
        clothingType2.setProtectionValue(20);

        Clothing clothingType3 = new Clothing();
        clothingType3.setName("Chain Armor");
        clothingType3.setWeight(30); // Unit : kg
        clothingType3.setValue(200); // Currency : Lei
        clothingType3.setProtectionValue(30);

        //Weapons
        Swords swordType1 = new Swords();
        swordType1.setName("Dagger");
        swordType1.setWeight(5);
        swordType1.setValue(120);
        swordType1.setDamagePoint(12);
        swordType1.setRange(1);

        Swords swordType2 = new Swords();
        swordType2.setName("Short Sword");
        swordType2.setWeight(20);
        swordType2.setValue(200);
        swordType2.setDamagePoint(20);
        swordType2.setRange(3);

        Swords swordType3 = new Swords();
        swordType3.setName("Long Sword");
        swordType3.setWeight(40);
        swordType3.setValue(250);
        swordType3.setDamagePoint(30);
        swordType3.setRange(5);

        Axes axeType1 = new Axes();
        axeType1.setName("Small Axe");
        axeType1.setWeight(15);
        axeType1.setValue(220);
        axeType1.setDamagePoint(25);
        axeType1.setRange(2);

        Axes axeType2 = new Axes();
        axeType2.setName("Axe");
        axeType2.setWeight(27);
        axeType2.setValue(300);
        axeType2.setDamagePoint(35);
        axeType2.setRange(3);

        Axes axeType3 = new Axes();
        axeType3.setName("Broad Axe");
        axeType3.setWeight(45);
        axeType3.setValue(400);
        axeType3.setDamagePoint(40);
        axeType3.setRange(6);

        Bows bowType1 = new Bows();
        bowType1.setName("Short Bow");
        bowType1.setWeight(10);
        bowType1.setValue(100);
        bowType1.setDamagePoint(50);
        bowType1.setRange(20);

        Bows bowType2 = new Bows();
        bowType2.setName("Long Bow");
        bowType2.setWeight(20);
        bowType2.setValue(200);
        bowType2.setDamagePoint(20);
        bowType2.setRange(40);

        Bows bowType3 = new Bows();
        bowType3.setName("Composite Bow");
        bowType3.setWeight(30);
        bowType3.setValue(300);
        bowType3.setDamagePoint(40);
        bowType3.setRange(80);


        Level[] levels = new Level[3];
        levels[0] = new Level();
        Level previousLevel = levels[0];

        /*
        for(int i = 0; i < 2; i++){
            levels[i+ 1] = new Level();

            previousLevel.addStairs(levels[i+ 1]);
            previousLevel = levels[i+ 1];
        }

         */

        //Hero Inventory Test
        Hero hero = new Hero(levels[0]);
        hero.setHealthPoint(100);

        System.out.print("What is your hero's name: ");
        Scanner scan = new Scanner(System.in);
        hero.setName(scan.nextLine());

        hero.setWeapon(bowType1);
        hero.setCloth(clothingType1);

        System.out.println("Welcome hero " + hero.getName() + "\n");
        hero.getInventory().add(swordType1);
        hero.getInventory().add(axeType2);
        hero.getInventory().add(swordType3);
        hero.getInventory().add(clothingType2);

        for(Room room: hero.currentlevel.getRooms()) {
            Monsters monster1 = new Monsters();
            monster1.setCloth(clothingType1);
            monster1.setWeapon(bowType1);
            monster1.setName("Titan");
            monster1.addItemToInventory(new Potion());
            room.addMonster(monster1);

            Monsters monster2 = new Monsters();
            monster2.setCloth(clothingType1);
            monster2.setWeapon(bowType1);
            monster2.setName("Kiklop");
            monster2.addItemToInventory(swordType2);
            room.addMonster(monster2);

            Monsters monster3 = new Monsters();
            monster3.setCloth(clothingType1);
            monster3.setWeapon(bowType1);
            monster3.setName("Kraken");
            monster3.addItemToInventory(clothingType3);
            room.addMonster(monster3);

        }


        boolean isAlive = hero.isAlive;

        while (isAlive) {
            System.out.println("Hero " + hero.getName() + ": " + hero.getHealthPoint() + "HP");
            System.out.println("Weapon: " + hero.weapon.getName() + " Armor: " + hero.cloth.getName());

            System.out.println("Level-" + hero.currentlevel.getLevelID() + ", " + hero.getCurrentRoom().getRoomName() + "\n");

            hero.getCurrentRoom().displayDoors();
            hero.getCurrentRoom().displayStairs();
            hero.getCurrentRoom().displayMonsters();
            hero.getCurrentRoom().showInventory();

            hero.movement();
            isAlive = hero.isAlive;
        }

        System.out.println("Hero " + hero.getName() + " fought and died bravely. Game is over...");

    }

}