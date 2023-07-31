package ma.sir.ged.zynerator.service;

public class Attribute {
    private String name;
    private String type;
    private Class complexeType;

    public Attribute() {
    }

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Attribute(String name, String type, Class complexeType) {
        this.name = name;
        this.type = type;
        this.complexeType = complexeType;
    }

    public Attribute(String name) {
        this.name = name;
        this.type = "String";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
