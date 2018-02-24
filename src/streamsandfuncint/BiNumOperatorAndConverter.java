package streamsandfuncint;

@FunctionalInterface
interface BiNumOperatorAndConverter<T extends Number, U> {
    U convert(T x, T y);
}

