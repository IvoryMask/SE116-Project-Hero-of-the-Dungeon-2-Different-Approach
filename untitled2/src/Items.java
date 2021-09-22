public class Items {

    String name;
    int weight;
    int value;
    boolean isUsed;

    public Items(){
        isUsed = false;
    }

    public void use(){
        isUsed = true;
    }

    public void notUse(){
        isUsed = false;
    }

    public void displayItemInfo(){

        System.out.println("-------------------------------------- \n" +
                "Type   : " + getName() +"\n" +
                "Weight : " + getWeight() + " Kg\n" +
                "Value  : " + getValue() + " Lei\n" +
                "--------------------------------------\n");

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



}

class Clothing extends Items{

    private int protectionValue;

    @Override
    public void displayItemInfo() {

        System.out.println("********* Armor Information ********* \n" +
                "-------------------------------------- \n" +
                "Type   : " + getName() +"\n" +
                "Weight : " + getWeight() +  " Kg\n" +
                "Value  : " + getValue() + " Lei\n" +
                "Protection : " + getProtectionValue() +"\n" +
                "--------------------------------------\n");

    }

    public int getProtectionValue() {
        return protectionValue;
    }

    public void setProtectionValue(int protectionValue) {
        this.protectionValue = protectionValue;
    }


    /*static class LightClothing extends Clothing{}
    static class LeatherArmor extends Clothing{}
    static class ChainMailArmor extends Clothing{}*/

}

class Weapons extends Items {

    private int damagePoint;
    private int range;

    @Override
    public void displayItemInfo() {

        System.out.println("********* Weapon Information *********\n" +
                "--------------------------------------\n" +
                "Type   : " + getName() + "\n" +
                "Weight : " + getWeight() + " Kg\n" +
                "Value  : " + getValue() + " Lei\n" +
                "Damage : " + getDamagePoint() + "\n" +
                "Range  : " + getRange() + " m\n" +
                "--------------------------------------\n");


    }


    public int getDamagePoint() {
        return damagePoint;
    }

    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }



}

class Swords extends Weapons {
    /*static class Dagger extends Swords {}

    static class ShortSword extends Swords {}

    static class Longsword extends Swords {}*/
}

class Axes extends Weapons {
    /*static class SmallAxe extends Axes {}

    static class Axe extends Axes {}

    static class BroadAxe extends Axes {}*/
}

class Bows extends Weapons {
    /*static class ShortBow extends Bows {}

    static class Longbow extends Bows {}

    static class CompositeBow extends Bows {}*/

}

class Potion extends Items{

    final int healingPoint = 20;
    static int counter = 1;

    public Potion(){
        name = "Potion-" + counter;
        counter++;

        weight = 10;
        value = 20;
    }

    public int getHealingPoint(){
        return healingPoint;
    }
}




