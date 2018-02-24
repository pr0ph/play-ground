package exceptions;

public class DogFactory extends AnimalFactory {
    @Override
    public Dog createAnimal(String type) throws IllegalDogTypeException {
        if ("dog".equals(type)) {
            return new Dog();
        } else {
            throw new IllegalDogTypeException("Please write 'dog' as argument.");
        }
    }
}
