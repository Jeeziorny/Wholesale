package Database;

import Database.DataAccessObject.DaoOrder;
import models.*;
import models.enums.TableName;
import org.hibernate.query.Query;

public class CeoDBHandler implements Crudeable {
  final private DaoOrder daoOrder = DaoOrder.getInstance();
  final private DaoOrderItem daoOrderItem;
  final private DaoCustomer daoCustomer;
  final private DaoChipboard daoChipboard;
  final private DaoIncome daoIncome;
  final private DaoWarehouse daoWarehouse;
  final private DaoChipboardSize daoChipboardSize;

  public Object insert(TableName name, Object object) {
    if (name.equals(TableName.ORDERS)) {
      daoOrder.insert((Order) object);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      daoOrderItem.insert((OrderItem) object);
    } else if (name.equals(TableName.CUSTOMERS)) {
      daoCustomer.insert((Customer) object);
    } else if (name.equals(TableName.CHIPBOARD)) {
      daoChipboard.insert((Chipboard) object);
    } else if (name.equals(TableName.INCOME)) {
      daoIncome.insert((Income) object);
    } else if (name.equals(TableName.WAREHOUSE)) {
      daoWarehouse.insert((Income) object);
    } else if (name.equals(TableName.CHIPBOARD_SIZE)) {
      daoChipboardSize.insert((ChipboardSize) object);
    }
    return null;
  }

  public Object delete(TableName name, Query query) {
    if (name.equals(TableName.ORDERS)) {
      return daoOrder.delete(query);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      return daoOrderItem.delete(query);
    } else if (name.equals(TableName.CUSTOMERS)) {
      return daoCustomer.delete(query);
    } else if (name.equals(TableName.CHIPBOARD)) {
      return daoChipboard.delete(query);
    } else if (name.equals(TableName.INCOME)) {
      return daoIncome.delete(query);
    } else if (name.equals(TableName.WAREHOUSE)) {
      return daoWarehouse.delete(query);
    } else if (name.equals(TableName.CHIPBOARD_SIZE)) {
      return daoChipboardSize.delete(query);
    }
    return null;
  }

  public Object update(TableName name, Query query) {
    if (name.equals(TableName.ORDERS)) {
      return daoOrder.update(query);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      return daoOrderItem.update(query);
    } else if (name.equals(TableName.CUSTOMERS)) {
      return daoCustomer.update(query);
    } else if (name.equals(TableName.CHIPBOARD)) {
      return daoChipboard.update(query);
    } else if (name.equals(TableName.INCOME)) {
      return daoIncome.update(query);
    } else if (name.equals(TableName.WAREHOUSE)) {
      return daoWarehouse.update(query);
    } else if (name.equals(TableName.CHIPBOARD_SIZE)) {
      return daoChipboardSize.update(query);
    }
    return null;
  }

  public Object select(TableName name, Query query) {
    if (name.equals(TableName.ORDERS)) {
      return daoOrder.select(query);
    } else if (name.equals(TableName.ORDER_ITEM)) {
      return daoOrderItem.select(query);
    } else if (name.equals(TableName.CUSTOMERS)) {
      return daoCustomer.select(query);
    } else if (name.equals(TableName.CHIPBOARD)) {
      return daoChipboard.select(query);
    } else if (name.equals(TableName.INCOME)) {
      return daoIncome.select(query);
    } else if (name.equals(TableName.WAREHOUSE)) {
      return daoWarehouse.select(query);
    } else if (name.equals(TableName.CHIPBOARD_SIZE)) {
      return daoChipboardSize.select(query);
    }
    return null;
  }

  public void backup() {}

  public void restore() {}
}
