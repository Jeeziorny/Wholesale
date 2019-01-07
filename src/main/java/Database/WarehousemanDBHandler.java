package Database;

import models.Order;
import models.enums.TableName;

import java.util.List;

public class WarehousemanDBHandler implements DBHandler {
  final private DaoOrder daoOrder;
  final private DaoOrderItem daoOrderItem;

  public Object insert(Object object) {
    return null;
  }

  public Object delete(Object object) {
    return null;
  }

  public Object update(Object oldRow, Object newRow) {
    if (oldRow instanceof Order && newRow instanceof Order) {
      DaoOrder.update(newRow);
    }
    return null;
  }

  public Object selectAll(TableName name) {
    if (name.equals(TableName.ORDERS)) {
      return DaoOrder.getAll();
    } else if (name.equals(TableName.ORDER_ITEM)) {
      return DaoOrderItem.getAll();
    }
    return null;
  }

  public Object restrictSelect(List<String> colNames, TableName name, String where) {
    if (name.equals(TableName.ORDER_ITEM)) {
      return DaoOrderItem.get(colNames, where);
    } else if (name.equals(TableName.ORDERS)) {
      return DaoOrder.get(colNames, where);
    }
    return null;
  }
}
