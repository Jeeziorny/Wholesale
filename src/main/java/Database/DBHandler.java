package Database;

import models.TableName;
import models.enums.TableName;
import org.hibernate.query.Query;

import java.util.List;

public interface DBHandler {
  Object insert(Object object);
  Object delete(Object object);
  Object update(Object oldRow, Object newRow);
  Object selectAll(TableName name, Query query);
  Object restrictSelect(List<String> colNames, TableName name, String where);
}
