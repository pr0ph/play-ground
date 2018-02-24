package exceptions;

public class AnimalFactory {

    public Animal createAnimal(String type) throws IllegalAnimalTypeException {
        if ("dog".equals(type)) {
            return new Dog();
        } else {
            throw new IllegalAnimalTypeException("Please write 'dog' as argument.");
        }
    }

}
