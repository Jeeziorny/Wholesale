package Database;

import models.enums.TableName;
import org.hibernate.query.Query;

import java.util.List;

public interface Crudeable {
  Object insert(TableName name, Object object);
  Object delete(TableName name, Query query);
  Object update(TableName name, Query query);
  Object select(TableName name, Query query);
}
