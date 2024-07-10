package bbw.trips.demo.repository;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static org.junit.jupiter.api.Assertions.*;

public class KeyHolderFactoryTest {

    @Test
    public void testKeyHolder() {
        // Given
        KeyHolderFactory keyHolderFactory = new KeyHolderFactory();

        // When
        KeyHolder keyHolder = keyHolderFactory.keyHolder();

        // Then
        assertNotNull(keyHolder);
        assertTrue(keyHolder instanceof GeneratedKeyHolder);
    }
}
