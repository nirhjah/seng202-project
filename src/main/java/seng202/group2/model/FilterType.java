package seng202.group2.model;

import seng202.group2.datacategories.DataCategory;

public enum FilterType {
    /** Category match */
    EQ {
        public Filter createFilter(DataCategory category, String pattern) {
            return new Filter(category.getSQL() + "= '" + pattern + "'", FilterType.EQ);
        }
    },
    GT {
        public Filter createFilter(DataCategory category, String pattern) {
            return new Filter(category.getSQL() + "> '" + pattern + "'", FilterType.EQ);
        }
    },
    /** Category sort */
    SORT {
        /** @param ascending ASC | DESC */
        public Filter createFilter(DataCategory category, String ascending) {
            return new Filter(category.getSQL() + ascending, FilterType.SORT);
        }
    };

    /**
     * Create a Filter
     *
     * @param category DataCategory that you want to filter
     * @param secondParam Extra context for the specific enum implementation
     */
    public abstract Filter createFilter(DataCategory category, String secondParam);
}
