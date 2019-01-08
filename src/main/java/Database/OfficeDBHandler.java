package Database;

import models.Customer;
import models.Order;
import models.OrderItem;
import models.enums.TableName;
import org.hibernate.query.Query;

public class OfficeDBHandler implements Crudeable {
  final private DaoOrder daoOrder;
  final private DaoOrderItem daoOrderItem;
  final private DaoCustomer daoCustomer;
  final private DaoChipboard daoChipboard;

  public Object insert(TableName name, Object object) {
    if (name.equals(TableName.ORDERS)) {
      daoOrder.insert((Order) object);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      daoOrderItem.insert((OrderItem) object);
    } else if (name.equals(TableName.CUSTOMERS)) {
      daoCustomer.insert((Customer) object);
    }
    return null;
  }

  public Object delete(TableName name, Query query) {
    return null;
  }

  public Object update(TableName name, Query query) {
    if (name.equals(TableName.CUSTOMERS)) {
      return daoCustomer.update(query);
    }
    return null;
  }

  public Object select(TableName name, Query query) {
    if (name.equals(TableName.CUSTOMERS)) {
      return daoCustomer.select(query);
    } else if (name.equals(TableName.CHIPBOARD)) {
      return daoChipboard.select(query);
    }
    return null;
  }
}
