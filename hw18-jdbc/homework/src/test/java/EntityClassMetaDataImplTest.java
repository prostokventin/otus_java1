import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


public class EntityClassMetaDataImplTest {

    @Test
    @DisplayName("Тест методов класса EntityClassMetaDataImpl")
    void entityClassMetaDataImplTest() throws NoSuchMethodException, NoSuchFieldException {

        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);

        //when
        var className = entityClassMetaDataClient.getName();
        var classContructor = entityClassMetaDataClient.getConstructor();
        var classIdField = entityClassMetaDataClient.getIdField();
        var classAllFields = entityClassMetaDataClient.getAllFields();

        //then
        assertThat(className).isEqualTo("Client");
        assertThat(classContructor).isEqualTo(Client.class.getDeclaredConstructor(Long.class, String.class));
        assertThat(classIdField).isEqualTo(Client.class.getDeclaredField("id"));
        assertThat(classAllFields).isEqualTo(Arrays.stream(Client.class.getDeclaredFields()).toList());
    }
}
