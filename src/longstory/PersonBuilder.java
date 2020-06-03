package longstory;

public class PersonBuilder {
    private final long id;
    private final int age;
    private final String firstName;
    private final String lastName;
    private final Address shippingAddress;
    private String middleName;
    private Address billingAddress;

    public PersonBuilder(long id, int age, String firstName, String lastName, Address shippingAddress) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.shippingAddress = shippingAddress;
    }

    public static long id(long id) {
        return id;
    }

    public static int age(int age) {
        return age;
    }

    public static String firstName(String firstName) {
        return firstName;
    }

    public static String lastName(String lastName) {
        return lastName;
    }

    public static Address shippingAddress(Address shippingAddress) {
        return shippingAddress;
    }

    public PersonBuilder middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public PersonBuilder billingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public Person build() {
        return new Person(id, age, firstName, middleName, lastName, shippingAddress, billingAddress);
    }
}
