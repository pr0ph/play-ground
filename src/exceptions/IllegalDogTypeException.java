package exceptions;

public class IllegalDogTypeException extends IllegalAnimalTypeException {
    public IllegalDogTypeException(String message) {
        super(message);
    }
}
