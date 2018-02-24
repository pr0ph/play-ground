package exceptions;

public class Test {

    public static void main(String[] args) {
        AnimalFactory animalFactory = new AnimalFactory();
        Animal animal;
        try {
            animal = animalFactory.createAnimal("dodg");
            System.out.println(animal.getName());
        } catch (IllegalAnimalTypeException e) {
            System.out.println(e.getMessage());
        }

    }

}
