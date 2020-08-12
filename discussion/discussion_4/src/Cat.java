public class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
        this.noise = "Meow!";
    }

    @Override
    public void greet(){
        System.out.println("Cat " + name + " says: " + makeNoise());
    }

    public static void main(String[] args) {
        Cat smallCat = new Cat("small", 2);
        smallCat.greet();
        Cat bigCat = new Cat("Big", 10);
        bigCat.greet();
    }

}
