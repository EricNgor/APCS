public abstract class Skill {
    private String skillName;
    private int damage;
    private int manaCost;

    public Skill(String skillName, int damage, int manaCost) {
        this.skillName = skillName;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public String getName() {
        return skillName;
    }

    public int getDamage() {
        return damage;
    }

    public int getCost() {
        return manaCost;
    }

    public static Skill generateSkill(String name) {
        switch(name) {
            case "Spark": return new Spark();
            case "Thunderbolt": return new Thunderbolt();
            case "Splash": return new Splash();

            default: return null;
        }
    }
}

