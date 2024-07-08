package bbw.trips.demo.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/** Component that is used to provide a GeneratedKeyHolder instance thorugh spring to make it mockable within the repository tests. */
@Component
@AllArgsConstructor
public class KeyHolderFactory {
    /**
     *  Will return a GeneratedKeyHolder instance.
     *
     * @return new GeneratedKeyHolder();
     */
    public KeyHolder keyHolder() {
        return new GeneratedKeyHolder();
    }
}