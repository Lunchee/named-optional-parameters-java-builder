Named and Optional Parameters with Builder Pattern in Java
==========================================================

As we know, Java does not have named and optional parameters,
and you can't write anything like this:

```java
Person person = new Person(age = 16, name = "Peter"); 
```

Instead, usually we write:

```java
Person person = new Person(16, "Peter");
```  

While in this case you can guess what `16` and `Peter` mean,
situation becomes less obvious when amount of parameters grows:

```java
Person person = new Person(325, 16, "Peter", true);
``` 

To find out what are `325` and `true`, you would need to peek into
definition of the method. It would be much better if we could write:

```java
Person person = new Person(id = 325, age = 16, name = "Peter", isActive = true);
```

Optional Parameters
-------------------

Now, let's assume that a Person may have a shipping and a billing address:

```java
Person person = new Person(325, 16, "Peter", new Address("Ship to"), new Address("Bill to"));
```

In some cases a billing address is same as a shipping address,
so let's create one more constructor to cover this situation:

```java
class Person {
    // A constructor with all fields
    public Person(long id, int age, String name, Address shippingAddress, Address billingAddress) {...}
    
    // A constructor without a billing address
    public Person(long id, int age, String name, Address shippingAddress) {
        this(id, age, name, shippingAddress, shippingAddress);
    }
}
```

The `billingAddress` parameter becomes an *optional* parameter that we may omit when we create a Person.

Now, let's imagine, a Person has a last name and may have a middle name. This is how the class could look like:

```java
public class Person {
    private final long id;
    private final int age;
    private final String firstName;
    private final String middleName; // An optional field
    private final String lastName;
    private final Address shippingAddress;
    private final Address billingAddress; // Also optional, may be the same as shippingAddress
}
```

As you can see, we have a plenty of fields, among which `middleName` and `billingAddress` may be omitted upon
creation of an object.<br/>
To accommodate such a scenario when two fields may not be present, we would create four constructors:

```java
    // A constructor with all fields present (mandatory fields are hidden for sake of brevity)
    public Person(...String middleName, Address billingAddress...) {
        this.id = id;
        this.age = age;
        ...
    }
    
    // A constructor without a middle name
    public Person(...Address billingAddress...) {
        this(id, age, firstName, null, lastName, shippingAddress, billingAddress);
    }

    // A constructor with a billing address same as a shipping address
    public Person(...String middleName...) {
        this(id, age, firstName, middleName, lastName, shippingAddress, shippingAddress);
    }

    // A constructor without a middle name and with a billing address same as a shipping address
    // (a constructor with all mandatory fields).
    public Person(long id, int age, String firstName, String lastName, Address shippingAddress) {
        this(id, age, firstName, null, lastName, shippingAddress, shippingAddress);
    }
```

So, if we add one more optional field, say, `email`... I guess you know that it would be a total of eight constructors.

Builder Pattern
---------------

There are several solutions to this tricky situation, and one of them is the
[Builder Pattern](https://en.wikipedia.org/wiki/Builder_pattern).

A Builder is usually implemented as a separate class with the same set of fields as in the target class
(in our case, `Person`):

```java
public class PersonBuilder {
    private long id;
    private int age;
    private String firstName;
    private String middleName;
    private String lastName;
    private Address shippingAddress;
    private Address billingAddress;

    ...
```

And with a bunch of setter methods for each field, named after the fields
(usually, `withFieldName()`, but we will stick with `fieldName()`):

```java
    ...

    public PersonBuilder id(long id) {
        this.id = id;
        return this;
    }
    
    public PersonBuilder age(int age) {
        this.age = age;
        return this;
    }

    ... and so on for each field of the builder ...
```

It also has a method to build a `Person`:

```java
    ...

    public Person build() {
        return new Person(id, age, firstName, middleName, lastName, shippingAddress, billingAddress);
    }       
}
```

Note that every setter method of the Builder returns the same Builder back to you:

```java
    public PersonBuilder withId(int id) {
        this.id = id;
        return this;
    }
```

This is made to allow us to chain builder methods and create an object almost as if we used named arguments:

```java
    Person person = new PersonBuilder()
        .id(325)
        .age(16)
        .firstName("Peter")
        .lastName("Newford")
        .shippingAddress("Ship to")
        .billingAddress("Bill to")
        .build();
```

Now, when we can easily choose which fields we would like to set, and which to omit,
we can remove all superfluous constructors of Person and leave only the one with all fields.
The builder will call this constructor in all cases, providing `null`'s to omitted arguments.

Say, if we construct an object like this:

```java
Person person = new PersonBuilder()
        .id(325)
        .age(16)
        .shippingAddress("Ship to")
        .build();
```

The builder will call the constructor of Person with these arguments:

```java
new Person(325, 16, null, null, null, "Ship to", null);
```

But wait, the Builder allowed us to skip some required parameters: `firstName` and `lastName`.
How can we ensure that we won't forget to set all mandatory fields?
One possible solution is to write tests to cover all cases where a Person is created.
That's good, but a compile-time check would be even better.
Usually this is solved by adding a constructor to a Builder that accepts all the mandatory fields:

```java
public class Builder {
    ...fields...

    public Builder(int id, int age, String firstName, String lastName, Address shippingAddress) {...}
   
    ...setter methods...
}
``` 

Good, now we have a flexible way to create a `Person` object, leaving any number of optional fields behind:

```java
Person person = new PersonBuilder(325, 16, "Peter", "Newford", "Ship to")
      .billingAddress("Bill to")
      .build();
```

Named Parameters in the Builder
-------------------------------

We have solved the problem with optional parameters and four superfluous constructors,
but the constructor of the Builder itself still does not look neat. Namely, it's hard to understand what arguments mean.
What is `325` and `16`? We can't tell that by simply reading the code. Let's improve this situation.

Here is a little trick to make things clearer. In the Builder we define static methods, named after mandatory parameters
of the constructor. The methods just return a given value and do nothing else:

```java
public static int id(int id) {
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

public static String shippingAddress(String shippingAddress) {
    return shippingAddress;
}
``` 

And use these methods to wrap arguments of the constructor:

```java
Person person = new PersonBuilder(
        id(325),
        age(16),
        firstName("Peter"),
        lastName("Newford"),
        shippingAddress(new Address("Some Address")))
        .build();
```

Now it looks almost like named arguments!

Result
------

Here is the full builder chain with named and optional parameters:

```java
Person person = new PersonBuilder(
        id(325),
        age(16),
        firstName("Peter"),
        lastName("Newford"),
        shippingAddress(new Address("Some Address")))
        .billingAddress(new Address("Some other Address"))
        .middleName("M")
        .build();
```

Notice how easy it is now to understand what each argument means.
Plus, the Builder allows us to choose any optional parameters we need.
In fact, you can use the trick with static methods anywhere, not only with a Builder.