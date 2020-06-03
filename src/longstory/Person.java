package longstory;

public class Person {
    private final long id;
    private final int age;
    private final String firstName;
    private final String middleName; // An optional field
    private final String lastName;
    private final Address shippingAddress;
    private final Address billingAddress; // Also optional, may be the same as shippingAddress

    public Person(
            long id,
            int age,
            String firstName,
            String middleName,
            String lastName,
            Address shippingAddress,
            Address billingAddress
    ) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }
}
