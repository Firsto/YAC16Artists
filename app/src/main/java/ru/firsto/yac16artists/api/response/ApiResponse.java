package ru.firsto.yac16artists.api.response;

public class ApiResponse {
    public Type type = Type.NOT_DEFINED;

    /**
     * Типы ответов от сервера
     */
    public enum Type {
        /**
         * Тип: данные были взяты только из локальной бд
         */
        FROM_BD_ONLY,
        /**
         * Данные загружены напрямую, из апи
         */
        FROM_API,
        /**
         * Без особого указания
         */
        NOT_DEFINED
    }
}
