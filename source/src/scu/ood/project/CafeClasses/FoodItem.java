package scu.ood.project.CafeClasses;

/**
 * food item class
 */
public class FoodItem {
    String itemName;
    int calories;
    int itemPrice;
    Boolean isVegan;
    Boolean isLowSodium;
    Boolean isLowCholesterol;

    /**
     * constructor
     * @param itemName
     * @param itemPrice
     * @param calories
     * @param isVegan
     */
    public FoodItem(String itemName, int itemPrice, int calories, boolean isVegan, 
    		        boolean isLowSodium, boolean isLowCholesterol) {
        this.itemName = itemName;
        this.calories = calories;
        this.itemPrice = itemPrice;
        this.isVegan = isVegan;
        this.isLowSodium = isLowSodium;
        this.isLowCholesterol = isLowCholesterol;
    }

    /**
     * @return food item name
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * @return  item calories
     */
    public int getItemCalories() {
        return this.calories;
    }

    /**
     * @return item price
     */
    public int getItemPrice() {
        return this.itemPrice;
    }

    /**
     * @return check to see if item is vegan
     */
    public String getIsVegan(){
        return this.isVegan.equals(true) ? "yes" : "no";
    }

}
