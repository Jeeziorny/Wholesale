package Database;

import models.enums.TableName;
import org.hibernate.query.Query;

public class WarehousemanDBHandler implements Crudeable {
  final private DaoOrder daoOrder;
  final private DaoOrderItem daoOrderItem;

  public Object insert(TableName name, Object object) {
    return null;
  }

  public Object delete(TableName name, Query query) {
    return null;
  }

  public Object update(TableName name, Query query) {
    if (name.equals(TableName.ORDERS)) {
      return daoOrder.update(query);
    }
    return null;
  }

  public Object select(TableName name, Query query) {
    if (name.equals(TableName.ORDERS)) {
      return daoOrder.select(query);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      return daoOrderItem.select(query);
    }
    return null;
  }
}
