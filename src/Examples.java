import longstory.Address;
import longstory.Person;
import longstory.PersonBuilder;
import shortstory.AnotherClass;
import shortstory.SomeClass;
import shortstory.SomeClassBuilder;

import static longstory.PersonBuilder.*;
import static shortstory.SomeClassBuilder.mandatoryFieldOne;
import static shortstory.SomeClassBuilder.mandatoryFieldTwo;

public class Examples {

    public static void longStoryExample() {
        Person person = new PersonBuilder(
                id(325),
                age(16),
                firstName("Peter"),
                lastName("Newford"),
                shippingAddress(new Address("Ship to")))
                .middleName("M")
                .billingAddress(new Address("Bill to"))
                .build();
    }

    public static void shortStoryExample() {
        SomeClass someClass = new SomeClassBuilder(
                mandatoryFieldOne("Mandatory Value"),
                mandatoryFieldTwo(new AnotherClass()))
                .optionalFieldOne(42L)
                .optionalFieldTwo("Optional Value")
                .build();
    }
}
