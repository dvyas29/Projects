package com.wayne.project.utilityTest;

import com.wayne.project.utility.IRandomCharacter;
import com.wayne.project.utility.RandomCharacter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomCharacterTest {

    @Test
    public void Test1GenerateRandomAlphaNumericCharacters() {

        IRandomCharacter randomCharacter = mock(RandomCharacter.class);
        when(randomCharacter.generateRandomAlphaNumericCharacters()).thenReturn("TrM6xabOt7");
        String randomStringGenerated = randomCharacter.generateRandomAlphaNumericCharacters();
        String expectedResult = "TrM6xabOt7";
        Assertions.assertEquals(expectedResult, randomStringGenerated);
    }
}
