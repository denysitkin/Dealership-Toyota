package toyota_app.model;

import java.math.BigDecimal;

public class AccountingTableInfo {
    private int idOperation;
    private String typeOperation;
    private BigDecimal sum;
    private int idDelivery;
    private int idSale;
    private BigDecimal currentBudget;

    public AccountingTableInfo(int idOperation, String typeOperation, BigDecimal sum, int idDelivery, int idSale, BigDecimal currentBudget) {
        this.idOperation = idOperation;
        this.typeOperation = typeOperation;
        this.sum = sum;
        this.idDelivery = idDelivery;
        this.idSale = idSale;
        this.currentBudget = currentBudget;
    }

    public BigDecimal getCurrentBudget() {
        return currentBudget;
    }

    public int getIdOperation() {
        return idOperation;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public int getIdDelivery() {
        return idDelivery;
    }

    public int getIdSale() {
        return idSale;
    }
}
