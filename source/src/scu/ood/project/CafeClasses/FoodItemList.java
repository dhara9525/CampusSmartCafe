package scu.ood.project.CafeClasses;

/**
 * List of food items
 */
public class FoodItemList {
    FoodItem cafeFoodItem;

    /**
     * constructor
     * @param foodItem
     */
    public FoodItemList(FoodItem foodItem) {
        super();
        cafeFoodItem = foodItem;
    }

    /**
     * @return food item title for list display
     */
    public String toString() {
        return cafeFoodItem.getItemName() + " $" + cafeFoodItem.getItemPrice()
                + " (Calories: " + cafeFoodItem.getItemCalories() + ", Vegan: " + cafeFoodItem.getIsVegan() + ")";
    }
}
