import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.crm.model.Client;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EntitySQLMetaDataImplTest {

    @Test
    @DisplayName("Тест методов класса entityClassMetaDataImp")
    void entityClassMetaDataImplTest() {
        EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);

        //when
        String selectAll = entitySQLMetaDataClient.getSelectAllSql();
        String selectByIdSql = entitySQLMetaDataClient.getSelectByIdSql();
        String insertSql = entitySQLMetaDataClient.getInsertSql();
        String updateSql = entitySQLMetaDataClient.getUpdateSql();

        //then
        assertThat(selectAll).isEqualTo("select id, name from Client");
        assertThat(selectByIdSql).isEqualTo("select id, name from Client where id = ?");
        assertThat(insertSql).isEqualTo("insert into Client(name) values (?)");
        assertThat(updateSql).isEqualTo("update Client set name = ? where id = ?");

    }

}
