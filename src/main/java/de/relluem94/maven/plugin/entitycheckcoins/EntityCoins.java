package de.relluem94.maven.plugin.entitycheckcoins;

public enum EntityCoins {
    DROPPED_ITEM(0),
    EXPERIENCE_ORB(0),
    AREA_EFFECT_CLOUD(0),
    ELDER_GUARDIAN(500),
    WITHER_SKELETON(200);

    private final int coins;

    EntityCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }
}

