package Database;

import models.Customer;
import models.Order;
import models.OrderItem;
import models.enums.TableName;
import models.enums.User;

import java.util.List;

public class OfficeDBHandler implements DBHandler {
  final private DaoOrder daoOrder;
  final private DaoOrderItem daoOrderItem;
  final private DaoCustomer daoCustomer;
  final private DaoChipboard daoChipboard;

  public Object insert(Object object) {
    if (object instanceof Order) {
      DaoOrder.insert(object);
    } else if (object instanceof OrderItem) {
      DaoOrderItem.insert(object);
    } else if (object instanceof Customer) {
      DaoCustomer.insert(object);
    }
    return null;
  }

  public Object delete(Object object) {
    return null;
  }

  public Object update(Object oldRow, Object newRow) {
    if (oldRow instanceof Customer && newRow instanceof Customer) {
      DaoCustomer.update(oldRow, newRow);
    }
    return null;
  }

  public Object selectAll(TableName name) {
    if (name.equals(TableName.CUSTOMERS)) {
      DaoCustomer.getAll();
    } else if (name.equals(TableName.CHIPBOARD)) {
      DaoChipboard.getAll();
    }
    return null;
  }

  public Object restrictSelect(List<String> colNames, TableName name, String where) {
    if (name.equals(TableName.CUSTOMERS)) {
      DaoCustomer.get(colNames, where);
    } else if (name.equals(TableName.CHIPBOARD)) {
      DaoChipboard.get(colNames, where);
    }
    return null;
  }
}
