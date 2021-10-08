package seng202.group2.model;

import seng202.group2.model.datacategories.DataCategory;

public enum FilterType {
    /** Category match */
    EQ {
        public Filter createFilter(DataCategory category, String pattern) {
            if (category.getValueType() == String.class) {
                pattern = "'" + pattern + "'";
            }
            return new Filter(category.getSQL() + " = " + pattern + "", FilterType.EQ);
        }
        public String toString() {
            return "=";
        }
    },
    GT {
        public Filter createFilter(DataCategory category, String pattern) {
            if (category.getValueType() == String.class) {
                pattern = "'" + pattern + "'";
            }
            return new Filter(category.getSQL() + " > " + pattern + "", FilterType.GT);
        }
        public String toString() {
            return ">";
        }
    },
    GT_EQ {
        public Filter createFilter(DataCategory category, String pattern) {
            if (category.getValueType() == String.class) {
                pattern = "'" + pattern + "'";
            }
            return new Filter(category.getSQL() + " >= " + pattern + "", FilterType.GT_EQ);
        }
        public String toString() {
            return ">=";
        }
    },
    LT {
        public Filter createFilter(DataCategory category, String pattern) {
            if (category.getValueType() == String.class) {
                pattern = "'" + pattern + "'";
            }
            return new Filter(category.getSQL() + " < " + pattern + "", FilterType.LT);
        }
        public String toString() {
            return "<";
        }
    },
    LT_EQ {
        public Filter createFilter(DataCategory category, String pattern) {
            if (category.getValueType() == String.class) {
                pattern = "'" + pattern + "'";
            }
            return new Filter(category.getSQL() + " <= " + pattern + "", FilterType.LT_EQ);
        }
        public String toString() {
            return "<=";
        }
    },
    RANGE {
        public Filter createFilter(DataCategory category, String between) {
            return new Filter(category.getSQL() + " " + between, FilterType.RANGE);
        }
        public String toString() {
            return "RANGE";
        }
    },
    /** Category sort */
    SORT {
        /** @param ascending ASC | DESC */
        public Filter createFilter(DataCategory category, String ascending) {
            return new Filter(category.getSQL() + " " + ascending, FilterType.SORT);
        }
        public String toString() {
            return "SORT";
        }
    };

    /**
     * Create a Filter
     *
     * @param category DataCategory that you want to filter
     * @param secondParam Extra context for the specific enum implementation
     */
    public abstract Filter createFilter(DataCategory category, String secondParam);

    public abstract String toString();
}
