package ch.elca.visitors.persistence.nativesql;

public class Query {
    public static final String SEARCH_VISITOR_ORGANISER_SQL = "SELECT first_name, last_name, status, checked_in_time  FROM visitor v2 WHERE v2.first_name = :firstName AND v2.last_name = :lastName UNION SELECT first_name, last_name, status, date_time FROM organiser o WHERE o.first_name = :firstName AND o.last_name = :lastName LIMIT :limit OFFSET :offset";

    public static final String SEARCH_VISITOR_ORGANIZER_COUNT_SQL = "SELECT count(first_name) FROM " +
            "(select first_name, last_name, status, checked_in_time from " +
            "visitor v2 WHERE v2.first_name = :firstName AND v2.last_name = :lastName UNION SELECT first_name, last_name, status, date_time FROM organiser o WHERE o.first_name = :firstName AND o.last_name = :lastName " +
            ") as t";

    public static final String SEARCH_VISITOR_ORGANISER_WITHOUT_LIMIT_OFFSET = "SELECT first_name, last_name, status, checked_in_time  FROM visitor v2 WHERE v2.first_name = :firstName AND v2.last_name = :lastName UNION SELECT first_name, last_name, status, date_time FROM organiser o WHERE o.first_name = :firstName AND o.last_name = :lastName";
}
