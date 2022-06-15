package poly.edu.assignment_earphone.models;

public enum TypeCondition {
    NEW(0), USED(1);

    private Integer valueCondition;

    TypeCondition(Integer valueCondition) {
        this.valueCondition = valueCondition;
    }

    public Integer getValueCondition() {
        return valueCondition;
    }

    public void setValueCondition(Integer valueCondition) {
        this.valueCondition = valueCondition;
    }
}
