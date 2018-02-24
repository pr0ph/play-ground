package streamsandfuncint;

public class OwnerAndModelTuple {
    private String owner;
    private String model;

    public OwnerAndModelTuple(String owner, String model) {
        this.owner = owner;
        this.model = model;
    }

    @Override
    public String toString() {
        return "OwnAndLic{" +
                "owner='" + owner + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerAndModelTuple that = (OwnerAndModelTuple) o;

        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return model != null ? model.equals(that.model) : that.model == null;
    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}
