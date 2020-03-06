package utilities;

public enum  PetStatus {

    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private String name;

    PetStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PetStatus getByName(String name){
        return  PetStatus.valueOf(name);
    }
}
