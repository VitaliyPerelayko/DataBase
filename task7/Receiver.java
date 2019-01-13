package task7;

public class Receiver {
    private int num;
    private String name;

    public Receiver() {
    }

    public Receiver(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Receiver{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
