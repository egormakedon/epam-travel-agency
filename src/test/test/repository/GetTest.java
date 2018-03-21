package repository;

import entity.*;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetTest.class);

    private static final String name1 = "Agency1";
    private static final String phone1 = "12345";
    private static final Country country1 = Country.BELARUS;
    private static final byte stars1 = 5;

    private static final String name2 = "Agency2";
    private static final String phone2 = "1234567";
    private static final Country country2 = Country.RUSSIA;
    private static final byte stars2 = 1;

    private static final String name3 = "Agency3";
    private static final String phone3 = "1234511";
    private static final Country country3 = Country.ENGLAND;
    private static final byte stars3 = 10;

    private Hotel hotel1;
    private Hotel hotel2;
    private Hotel hotel3;

    private Repository repository;

    @Before
    public void init() {
        hotel1 = new Hotel(name1, phone1, country1, stars1);
        hotel2 = new Hotel(name2, phone2, country2, stars2);
        hotel3 = new Hotel(name3, phone3, country3, stars3);

        repository = Repository.getInstance();
    }

    @After
    public void destr() {
        repository.get(EntityType.HOTEL).clear();

        hotel1 = null;
        hotel2 = null;
        hotel3 = null;

        repository = null;
    }

    @Test
    public void getTrueTest1() {
        CollectionSet mockCollection = mock(CollectionSet.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.USER));

        Assert.assertEquals(mockCollection.get().size(), 0);
        LOGGER.info("Get true test 1 execute successfully");
    }

    @Test
    public void getTrueTest2() {
        repository.add(hotel1);
        repository.add(hotel2);
        repository.add(hotel3);

        CollectionSet mockCollection = mock(CollectionSet.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.HOTEL));

        Assert.assertEquals(mockCollection.get().size(), 3);
        LOGGER.info("Get true test 2 execute successfully");
    }

    @Test
    public void getFalseTest() {
        repository.add(hotel1);
        repository.add(hotel2);
        repository.add(hotel3);

        CollectionSet mockCollection = mock(CollectionSet.class);
        when(mockCollection.get()).thenReturn(repository.get(EntityType.HOTEL));

        Assert.assertNotEquals(mockCollection.get().size(), 2);
        LOGGER.info("Get false test execute successfully");
    }
}
