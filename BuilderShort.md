Named and Optional Parameters with Builder Pattern in Java
==========================================================

Given a constructor with all possible fields to set:

```java
public class SomeClass {
    private final String mandatoryFieldOne;
    private final AnotherClass mandatoryFieldTwo;
    private final Long optionalFieldOne;
    private final String optionalFieldTwo;

    public SomeClass(
        String mandatoryFieldOne,
        AnotherClass mandatoryFieldTwo,
        Long optionalFieldOne,
        String optionalFieldTwo
    ) {
        this.mandatoryFieldOne = mandatoryFieldOne;
        ...
    }
}
```

Optional Parameters
-------------------

Create a Builder to emulate optional parameters!

```java
public class SomeClassBuilder {
    private final String mandatoryFieldOne;
    private final AnotherClass mandatoryFieldTwo;
    private Long optionalFieldOne;
    private String optionalFieldTwo;

    public SomeClassBuilder(String mandatoryFieldOne, AnotherClass mandatoryFieldTwo) {
        this.mandatoryFieldOne = mandatoryFieldOne;
        this.mandatoryFieldTwo = mandatoryFieldTwo;
    }
    
    public SomeClassBuilder optionalFieldOne(Long optionalFieldOne) {
        this.optionalFieldOne = optionalFieldOne;
        return this;
    }

    public SomeClassBuilder optionalFieldTwo(String optionalFieldTwo) {
        this.optionalFieldTwo = optionalFieldTwo;
        return this;
    }
    
    public SomeClass build() {
        return new SomeClass(mandatoryFieldOne, mandatoryFieldTwo, optionalFieldOne, optionalFieldTwo);
    }
}
```

So that you are free to create an object with both optional parameters set:

```java
    SomeClass someClass = new SomeClassBuilder("Mandatory Value", new AnotherClass(...))
            .optionalFieldOne(42L)
            .optionalFieldTwo("Optional Value")
            .build();
```

Or with any of them omitted:

```java
    SomeClass someClass = new SomeClassBuilder("Mandatory Value", new AnotherClass(...))
            .optionalFieldTwo("Optional Value")
            .build();
```

Named Parameters
----------------

Add static methods named after the mandatory parameters to emulate named parameters!

```java
public class SomeClassBuilder {
    ...
    
    public static String mandatoryFieldOne(String value) {
        return value;
    }

    public static AnotherClass mandatoryFieldTwo(AnotherClass value) {
        return value;
    }

    ...
}
```

So that it all now becomes obvious:

```java
    SomeClass someClass = new SomeClassBuilder(
            mandatoryFieldOne("Mandatory Value"),
            mandatoryFieldTwo(new AnotherClass(...)))
            .optionalFieldOne(42L)
            .optionalFieldTwo("Optional Value")
            .build();
```
