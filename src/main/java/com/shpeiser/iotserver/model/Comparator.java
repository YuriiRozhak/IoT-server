package com.shpeiser.iotserver.model;

public enum Comparator {
    EQUAL("=") {
        @Override
        public boolean check(Double val1, Double val2) {
            return val1.equals(val2);
        }
    },
    NOT_EQUAL("!=") {
        @Override
        public boolean check(Double val1, Double val2) {
            return !val1.equals(val2);
        }
    },
    GREATER_THAN(">") {
        @Override
        public boolean check(Double val1, Double val2) {
            return val1 > val2;
        }
    },
    LESS_THAN("<") {
        @Override
        public boolean check(Double val1, Double val2) {
            return val1 < val2;
        }
    },
    GREATER_THAN_OR_EQUAL(">=") {
        @Override
        public boolean check(Double val1, Double val2) {
            return val1 >= val2;
        }
    },
    LESS_THAN_OR_EQUAL("<=") {
        @Override
        public boolean check(Double val1, Double val2) {
            return val1 <= val2;
        }
    };

    private final String symbol;

    Comparator(String symbol) {
        this.symbol = symbol;
    }

    public abstract boolean check(Double val1, Double val2);

    @Override
    public String toString() {
        return symbol;
    }

    public static Comparator getBySymbol(String symbol) {
        for (Comparator comparator : Comparator.values()) {
            if (comparator.symbol.equals(symbol)) {
                return comparator;
            }
        }
        throw new RuntimeException("Invalid comparator symbol: " + symbol);
    }
}