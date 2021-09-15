package seng202.group2.model;

import seng202.group2.model.datacategories.DataCategory;

public enum FilterType {
    /** Category match */
    EQ {
        public Filter createFilter(String category, String pattern) {
            return new Filter(category + " = " + pattern + "", FilterType.EQ);
        }
        public String toString() {
            return "=";
        }
    },
    GT {
        public Filter createFilter(String category, String pattern) {
            return new Filter(category + " > " + pattern + "", FilterType.GT);
        }
        public String toString() {
            return ">";
        }
    },
    LT {
        public Filter createFilter(String category, String pattern) {
            return new Filter(category + " < " + pattern + "", FilterType.LT);
        }
        public String toString() {
            return "<";
        }
    },
    /** Category sort */
    SORT {
        /** @param ascending ASC | DESC */
        public Filter createFilter(String category, String ascending) {
            return new Filter(category + " " + ascending, FilterType.SORT);
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
    public abstract Filter createFilter(String category, String secondParam);

    public abstract String toString();
}
