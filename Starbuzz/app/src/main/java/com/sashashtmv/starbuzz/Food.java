package com.sashashtmv.starbuzz;

public class Food {

    private String name;
    private String description;
    private int imageResourceID;

    public static final Food[] foods = {
            new Food("Jelli", "dish from thickened to jelly-like mass from cooling of meat broth with pieces of meat",
                    R.drawable.jelli),
            new Food("Olivie", "The most popular meat salad",
                    R.drawable.olivie),
            new Food("Meat-gril", "The finest aroma of freshly cooked meat on charcoal",
                    R.drawable.meat_gril)};

    public Food(String name, String description, int imageResourceID) {
        this.name = name;
        this.description = description;
        this.imageResourceID = imageResourceID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
