package poly.edu.assignment_earphone.models.typeEnum;

public enum TypeEarPhone {
    BLUETOOTH(0), WIRED_HEADPHONE(1);


    private Integer valueEarPhone;

    TypeEarPhone(Integer valueEarPhone) {
        this.valueEarPhone = valueEarPhone;
    }

    public Integer getValueEarPhone() {
        return valueEarPhone;
    }

    public void setValueEarPhone(Integer valueEarPhone) {
        this.valueEarPhone = valueEarPhone;
    }
}
