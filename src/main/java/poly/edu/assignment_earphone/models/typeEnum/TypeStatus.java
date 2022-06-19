package poly.edu.assignment_earphone.models.typeEnum;

public enum TypeStatus {
    ONLINE(0), OFFLINE(1);
    private Integer valueStatus;

    TypeStatus(Integer valueStatus) {
        this.valueStatus = valueStatus;
    }

    public Integer getValueStatus() {
        return valueStatus;
    }

    public void setValueStatus(Integer valueStatus) {
        this.valueStatus = valueStatus;
    }
}
