package shortstory;

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
        this.mandatoryFieldTwo = mandatoryFieldTwo;
        this.optionalFieldOne = optionalFieldOne;
        this.optionalFieldTwo = optionalFieldTwo;
    }
}
