package shortstory;

public class SomeClassBuilder {
    private final String mandatoryFieldOne;
    private final AnotherClass mandatoryFieldTwo;
    private Long optionalFieldOne;
    private String optionalFieldTwo;

    public SomeClassBuilder(String mandatoryFieldOne, AnotherClass mandatoryFieldTwo) {
        this.mandatoryFieldOne = mandatoryFieldOne;
        this.mandatoryFieldTwo = mandatoryFieldTwo;
    }

    public static String mandatoryFieldOne(String value) {
        return value;
    }

    public static AnotherClass mandatoryFieldTwo(AnotherClass value) {
        return value;
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