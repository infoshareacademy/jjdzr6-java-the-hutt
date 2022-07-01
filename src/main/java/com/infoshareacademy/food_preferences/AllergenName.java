package com.infoshareacademy.food_preferences;

public class AllergenName {
    private boolean chocolate;
    private boolean nuts;
    private boolean eggs;
    private boolean strawberries;
    private boolean dairy;
    private String other;

    public AllergenName() {
    }

    public void setChocolate(boolean chocolate) {
        this.chocolate = chocolate;
    }

    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public void setEggs(boolean eggs) {
        this.eggs = eggs;
    }

    public void setStrawberries(boolean strawberries) {
        this.strawberries = strawberries;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public boolean isChocolate() {
        return chocolate;
    }

    public boolean isNuts() {
        return nuts;
    }

    public boolean isEggs() {
        return eggs;
    }

    public boolean isStrawberries() {
        return strawberries;
    }

    public boolean isDairy() {
        return dairy;
    }

    public String getOther() {
        return other;
    }

    @Override
    public String toString() {
        return "\n czekolada: " + chocolate +
                "\n orzechy: " + nuts +
                "\n jajka: " + eggs +
                "\n truskawki: " + strawberries +
                "\n nabiał: " + dairy +
                "\n inne: " + other + "\n";

    }

}
